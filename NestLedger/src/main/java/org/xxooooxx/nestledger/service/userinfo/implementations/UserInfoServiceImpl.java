/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.userinfo.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xxooooxx.nestledger.dao.emailVerification.interfaces.EmailVerificationDao;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.userinfo.interfaces.UserInfoService;
import org.xxooooxx.nestledger.to.EmailVerificationDB;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private EmailVerificationDao emailVerificationDao;

    @Transactional
    @Override
    public UserInfoGetResponse createUserInfoIfNeeded(String id) {
        try {
            UserInfoDB existingUserInfo = userInfoDao.getUserInfoById(id);
            log.info("User info already exists: {}", id);
            return new UserInfoGetResponse(existingUserInfo);
        } catch(CustomException e) {
            log.info("Creating new user info: {}", id);
            UserInfoDB newUserInfo = createUserInfo(id);

            // Create main ledger
            LedgerCreateRequestData ledgerCreateRequestData = new LedgerCreateRequestData();
            ledgerCreateRequestData.setTitle("[Main]:" + id);
            ledgerCreateRequestData.setUserId(id);
            ledgerCreateRequestData.setVersion(1);
            LedgerDB ledgerDB = ledgerDao.createLedger(ledgerCreateRequestData);

            newUserInfo = userInfoDao.userJoinLedger(id, ledgerDB.get_id());
            return new UserInfoGetResponse(newUserInfo);
        }
    }

    @Override
    public UserInfoGetResponse getUserInfoById(String id) {
        UserInfoDB userInfoDB = userInfoDao.getUserInfoById(id);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public List<UserInfoGetResponse> getMultipleUserInfoById(List<String> ids) {
        List<UserInfoDB> userInfoDBList = userInfoDao.getMultipleUserInfoById(ids);
        return userInfoDBList.stream()
                .map(UserInfoGetResponse::new)
                .toList();
    }

    @Override
    public UserInfoGetResponse getUserInfoByEmail(String email) {
        UserInfoDB userInfoDB = userInfoDao.getUserInfoByEmail(email);
        if (userInfoDB == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public UserInfoGetResponse updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException {
        if (userInfoDao.getUserInfoById(data.getId()) == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        UserInfoDB userInfoDB = userInfoDao.updateUserInfo(data);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public UserInfoGetResponse changeQuickLogLedger(String uid, String ledgerId) {
        UserInfoDB userInfoDB = userInfoDao.changeQuickLogLedger(uid, ledgerId);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public UserInfoGetResponse deleteUserInfo(String id) {
        UserInfoDB userInfoDB = userInfoDao.deleteUserInfo(id);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public UserInfoGetResponse uploadAvatar(String id, MultipartFile file) throws IOException, IllegalAccessException {
        String filePath = "C:\\Users\\ediet\\Documents\\Codes\\NestLedgerDB\\files\\" + id + "\\";
        String fileName = id + ".jpg";

        // If folder does not exist, create it
        java.io.File folder = new java.io.File(filePath);
        if (!folder.exists()) {
            boolean isCreated = folder.mkdirs();
        }
        file.transferTo(new File(filePath + fileName));

        UserInfoUpdateRequestData data = new UserInfoUpdateRequestData();
        data.setId(id);
        data.setAvatar(filePath + fileName);
        UserInfoDB userInfoDB = userInfoDao.updateUserInfo(data);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public boolean updateUserEmail(String token) {
        EmailVerificationDB emailVerificationDB = emailVerificationDao.getEmailVerificationByToken(token);
        if (emailVerificationDB == null) return false;
        userInfoDao.updateUserEmail(emailVerificationDB.getUid(), emailVerificationDB.getEmailAddress());
        return true;
    }

    private UserInfoDB createUserInfo(String id) {
        return userInfoDao.createUserInfo(id);
    }
}
