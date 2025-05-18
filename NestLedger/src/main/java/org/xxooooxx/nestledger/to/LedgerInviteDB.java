/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
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

@Data
@Document("ledger-invites")
public class LedgerInviteDB {
    @Id
    private String _id;
    private String ledgerId = "";
    private String sendUserId = "";
    private String receiveUserId = "";
    private Integer version;
}
