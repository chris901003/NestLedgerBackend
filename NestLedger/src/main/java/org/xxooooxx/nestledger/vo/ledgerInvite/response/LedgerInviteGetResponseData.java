/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.ledgerInvite.response;

import lombok.Data;
import org.xxooooxx.nestledger.to.LedgerInviteDB;

@Data
public class LedgerInviteGetResponseData {
    private String _id;
    private String ledgerId;
    private String sendUserId;
    private String receiveUserId;
    private Integer version;

    public LedgerInviteGetResponseData(LedgerInviteDB data) {
        this._id = data.get_id();
        this.ledgerId = data.getLedgerId();
        this.sendUserId = data.getSendUserId();
        this.receiveUserId = data.getReceiveUserId();
        this.version = data.getVersion();
    }
}
