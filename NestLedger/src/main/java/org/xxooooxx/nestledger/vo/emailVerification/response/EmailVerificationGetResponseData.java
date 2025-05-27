/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/27.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.emailVerification.response;

import lombok.Data;
import org.xxooooxx.nestledger.to.EmailVerificationDB;

import java.util.Date;

@Data
public class EmailVerificationGetResponseData {
    private String uid;
    private String emailAddress;
    private Date expireAt;

    public EmailVerificationGetResponseData(EmailVerificationDB data) {
        this.uid = data.getUid();
        this.emailAddress = data.getEmailAddress();
        this.expireAt = data.getExpireAt();
    }
}
