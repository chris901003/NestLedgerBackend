/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/26.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.to;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("email_verifications")
public class EmailVerificationDB {
    @Id
    private String id;

    private String uid;

    private String token;

    private String emailAddress;

    private Date expireAt;

    private List<Date> verificationHistory;
}
