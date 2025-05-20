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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.ledgerInvite.interfaces.LedgerInviteDao;
import org.xxooooxx.nestledger.to.LedgerInviteDB;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteGetRequestData;

import java.util.List;

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

    public LedgerInviteDB getLedgerInviteById(String inviteId) {
        Query query = new Query(Criteria.where("_id").is(inviteId));
        return mongoTemplate.findOne(query, LedgerInviteDB.class);
    }

    public List<LedgerInviteDB> getLedgerInvite(LedgerInviteGetRequestData data) {
        Criteria criteria = new Criteria();
        if (data.getLedgerId() != null) {
            criteria.and("ledgerId").is(data.getLedgerId());
        }
        if (data.getReceiveUserId() != null) {
            criteria.and("receiveUserId").is(data.getReceiveUserId());
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, LedgerInviteDB.class);
    }

    public void deleteLedgerInvite(String inviteId) {
        Query query = new Query(Criteria.where("_id").is(inviteId));
        mongoTemplate.remove(query, LedgerInviteDB.class);
    }

    public void deleteLedgerInviteByLedgerId(String ledgerId) {
        Query query = new Query(Criteria.where("ledgerId").is(ledgerId));
        mongoTemplate.remove(query, LedgerInviteDB.class);
    }
}
