package org.xxooooxx.nestledger.service.emailVerification.interfaces;

import org.xxooooxx.nestledger.vo.emailVerification.response.EmailVerificationGetResponseData;

public interface EmailVerificationService {
    EmailVerificationGetResponseData getEmailVerification();
    void sendEmailVerification(String emailAddress);
}
