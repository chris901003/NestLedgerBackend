/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/26.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.emailVerification.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxooooxx.nestledger.dao.emailVerification.interfaces.EmailVerificationDao;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.emailVerification.interfaces.EmailVerificationService;
import org.xxooooxx.nestledger.to.EmailVerificationDB;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.utility.TokenGenerator;
import org.xxooooxx.nestledger.utility.UserContext;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private EmailVerificationDao emailVerificationDao;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public void sendEmailVerification(String emailAddress) {
        String uid = UserContext.getUid();
        UserInfoDB userInfoDB = userInfoDao.getUserInfoByEmail(emailAddress);
        if (userInfoDB != null) {
            throw new CustomException(CustomExceptionEnum.EMAIL_ALREADY_EXISTS);
        }
        EmailVerificationDB emailVerificationDB = emailVerificationDao.getEmailVerificationByUid(uid);
        List<Date> filteredHistory = new ArrayList<>();
        if (emailVerificationDB != null) {
            List<Date> history = emailVerificationDB.getVerificationHistory();
            filteredHistory = history.stream()
                    .filter(d -> new Date().getTime() - d.getTime() <= 24 * 60 * 60 * 1000L)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if (filteredHistory.size() >= 3) {
            throw new CustomException(CustomExceptionEnum.EMAIL_VERIFICATION_LIMIT_EXCEEDED);
        }
        String token = TokenGenerator.generateToken();
        Date expireAt = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L);
        filteredHistory.add(new Date());
        emailVerificationDao.updateEmailVerification(
                uid, token, emailAddress, expireAt, filteredHistory
        );
        try {
            sendEmail(token, emailAddress);
        } catch(Exception e) {
            throw new CustomException(CustomExceptionEnum.SEND_EMAIL_VERIFICATION_FAILED);
        }
    }

    private void sendEmail(String token, String address) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String verificationLink = "https://nlbackendmain.xxooooxx.org:1172/v1/user/verify-email?token=" + token;

        helper.setTo(address);
        helper.setSubject("請驗證您的電子郵件地址 - 共同記帳 (NestLedger)");

        String content = """
        <p>親愛的用戶您好，</p>
        <p>您近期在 <strong>共同記帳 (NestLedger)</strong> 中提出了更改電子郵件地址的申請。</p>
        <p>為了確保這個新信箱是您本人所有，請點擊以下連結完成驗證：</p>
        <p>
            <a href="%s" style="display:inline-block;padding:10px 20px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;">
                確認新信箱
            </a>
        </p>
        <p>若您無法點擊按鈕，請將以下網址複製並貼上至瀏覽器開啟：</p>
        <p><a href="%s">%s</a></p>
        <hr>
        <p>✅ 此連結將於 24 小時後失效，請盡快完成驗證。</p>
        <p>如果這不是您本人操作，請忽略此封郵件，您的帳戶將不會受到任何更動。</p>
        <p>祝 好，<br>共同記帳 (NestLedger) 團隊<br>xxooooxx</p>
        """.formatted(verificationLink, verificationLink, verificationLink);

        helper.setFrom("service@xxooooxx.org", "共同記帳 (NestLedger)");
        helper.setSubject("請確認您的新電子郵件地址 - 共同記帳 (NestLedger)");
        helper.setText(content, true);

        mailSender.send(message);
    }
}
