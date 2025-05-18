/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.ledgerInvite.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.ledgerInvite.interfaces.LedgerInviteDao;
import org.xxooooxx.nestledger.to.LedgerInviteDB;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;

@Component
public class LedgerInviteDaoImpl implements LedgerInviteDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public LedgerInviteDB createLedgerInvite(LedgerInviteCreateRequestData data) {
        LedgerInviteDB ledgerInviteDB = new LedgerInviteDB();
        ledgerInviteDB.setLedgerId(data.getLedgerId());
        ledgerInviteDB.setSendUserId(data.getSendUserId());
        ledgerInviteDB.setReceiveUserId(data.getReceiveUserId());
        ledgerInviteDB.setVersion(data.getVersion());
        return mongoTemplate.insert(ledgerInviteDB);
    }
}
