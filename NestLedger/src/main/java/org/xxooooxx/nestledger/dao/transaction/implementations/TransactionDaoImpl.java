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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.transaction.interfaces.TransactionDao;
import org.xxooooxx.nestledger.to.TransactionDB;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;

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
}
