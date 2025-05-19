/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.ledger.implementations;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.utility.MongoDbUpdateUtility;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerUpdateRequestData;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class LedgerDaoImpl implements LedgerDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public LedgerDB createLedger(LedgerCreateRequestData createData) {
        LedgerDB ledgerDB = new LedgerDB();
        ledgerDB.setUserIds(new ArrayList<>());
        ledgerDB.setTitle(createData.getTitle());
        ledgerDB.getUserIds().add(createData.getUserId());
        ledgerDB.setTotalExpense(0);
        ledgerDB.setTotalIncome(0);
        ledgerDB.setVersion(createData.getVersion());
        return mongoTemplate.insert(ledgerDB);
    }

    @Override
    public LedgerDB getLedger(String id) {
        LedgerDB ledgerDB = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), LedgerDB.class);
        if (ledgerDB == null) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
        return ledgerDB;
    }

    @Override
    public LedgerDB updateLedger(LedgerUpdateRequestData data, boolean withCheckAuth) throws IllegalAccessException {
        // Block update title when ledger is main ledger
        LedgerDB ledgerDB = getLedger(data.get_id());
        if (ledgerDB == null) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        } else if (data.getTitle() != null &&
                ledgerDB.getTitle().startsWith("[Main]") &&
                !Objects.equals(data.getTitle(), ledgerDB.getTitle())
        ) {
            throw new CustomException(CustomExceptionEnum.INVALID_CHANGE_MAIN_LEDGER_TITLE);
        }

        // Block unauthorized update
        if (withCheckAuth) {
            String uid = UserContext.getUid();
            if (!ledgerDB.getUserIds().contains(uid)) {
                throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_UPDATE_LEDGER);
            }
        }

        Query query = new Query(Criteria.where("_id").is(data.get_id()));
        Update update = MongoDbUpdateUtility.getFullUpdate(data);

        if (!update.getUpdateObject().isEmpty()) {
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(query, update, options, LedgerDB.class);
        }
        return null;
    }

    @Override
    public void deleteLedger(String ledgerId) {
        LedgerDB ledgerDB = getLedger(ledgerId);
        if (ledgerDB == null) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
        String uid = UserContext.getUid();
        if (!ledgerDB.getUserIds().contains(uid)) {
            throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_DELETE_LEDGER);
        }
        if (ledgerDB.getTitle().startsWith("[Main]")) {
            throw new CustomException(CustomExceptionEnum.INVALID_DELETE_MAIN_LEDGER);
        }
        mongoTemplate.remove(ledgerDB);
    }

    @Override
    public void incrementTotalIncome(String ledgerId, Integer amount) {
        Query query = new Query(Criteria.where("_id").is(ledgerId));
        Update update = new Update().inc("totalIncome", amount);
        UpdateResult result = mongoTemplate.updateFirst(query, update, LedgerDB.class);
        if (result.getMatchedCount() == 0) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
    }

    @Override
    public void incrementTotalExpense(String ledgerId, Integer amount) {
        Query query = new Query(Criteria.where("_id").is(ledgerId));
        Update update = new Update().inc("totalExpense", amount);
        UpdateResult result = mongoTemplate.updateFirst(query, update, LedgerDB.class);
        if (result.getMatchedCount() == 0) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
    }

    @Override
    public LedgerDB ledgerUserJoin(String uid, String ledgerId) {
        Query query = new Query(Criteria.where("_id").is(ledgerId));
        Update update = new Update().addToSet("userIds", uid);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return mongoTemplate.findAndModify(query, update, options, LedgerDB.class);
    }

    @Override
    public LedgerDB ledgerUserLeave(String uid, String ledgerId) {
        Query query = new Query(Criteria.where("_id").is(ledgerId));
        Update update = new Update().pull("userIds", uid);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return mongoTemplate.findAndModify(query, update, options, LedgerDB.class);
    }
}
