/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.transaction.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.transaction.interfaces.TransactionDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.to.TransactionDB;
import org.xxooooxx.nestledger.utility.MongoDbUpdateUtility;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionUpdateRequestData;

@Component
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public TransactionDB createTransaction(TransactionCreateRequestData data) {
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.setTitle(data.getTitle());
        transactionDB.setNote(data.getNote());
        transactionDB.setMoney(data.getMoney());
        transactionDB.setDate(data.getDate());
        transactionDB.setType(data.getType());
        transactionDB.setUserId(data.getUserId());
        transactionDB.setTagId(data.getTagId());
        transactionDB.setLedgerId(data.getLedgerId());
        transactionDB.setVersion(data.getVersion());
        return mongoTemplate.insert(transactionDB);
    }

    @Override
    public TransactionDB getTransaction(String id) {
        TransactionDB transactionDB = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(id)), TransactionDB.class
        );
        if (transactionDB == null) {
            throw new CustomException(CustomExceptionEnum.TRANSACTION_NOT_FOUND);
        }
        return transactionDB;
    }

    @Override
    public TransactionDB updateTransaction(TransactionUpdateRequestData data) throws IllegalAccessException {
        Query query = new Query(Criteria.where("_id").is(data.get_id()));
        Update update = MongoDbUpdateUtility.getFullUpdate(data);

        if (!update.getUpdateObject().isEmpty()) {
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(query, update, options, TransactionDB.class);
        }
        return null;
    }
}
