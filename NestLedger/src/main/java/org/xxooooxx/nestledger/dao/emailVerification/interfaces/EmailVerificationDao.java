/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/26.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.emailVerification.interfaces;

import org.xxooooxx.nestledger.to.EmailVerificationDB;

import java.util.Date;
import java.util.List;

public interface EmailVerificationDao {
    EmailVerificationDB getEmailVerificationByUid(String uid);
    EmailVerificationDB getEmailVerificationByToken(String token);
    void updateEmailVerification(
            String uid, String token, String emailAddress, Date expireAt, List<Date> verificationHistory
    );
}
