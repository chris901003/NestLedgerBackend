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
import org.springframework.data.domain.Sort;
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
import org.xxooooxx.nestledger.vo.transaction.request.TransactionQueryRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionUpdateRequestData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    @Override
    public void deleteTransaction(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        TransactionDB transactionDB = mongoTemplate.findAndRemove(query, TransactionDB.class);
        if (transactionDB == null) {
            throw new CustomException(CustomExceptionEnum.TRANSACTION_NOT_FOUND);
        }
    }

    @Override
    public List<TransactionDB> queryTransactions(TransactionQueryRequestData data) {
        List<Criteria> conditions = new ArrayList<>();
        conditions.add(Criteria.where("ledgerId").is(data.getLedgerId()));
        if (data.getSearch() != null) {
            String keyword = ".*" + Pattern.quote(data.getSearch()) + ".*";
            conditions.add(Criteria.where("title").regex(keyword, "i"));
        }
        if (data.getStartDate() != null || data.getEndDate() != null) {
            Criteria dateCriteria = Criteria.where("date");
            if (data.getStartDate() != null) {
                dateCriteria.gte(data.getStartDate());
            }
            if (data.getEndDate() != null) {
                dateCriteria.lte(data.getEndDate());
            }
            conditions.add(dateCriteria);
        }
        if (data.getTagId() != null) {
            conditions.add(Criteria.where("tagId").is(data.getTagId()));
        }
        if (data.getType() != null) {
            conditions.add(Criteria.where("type").is(data.getType()));
        }
        if (data.getUserId() != null) {
            conditions.add(Criteria.where("userId").is(data.getUserId()));
        }

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(conditions.toArray(new Criteria[0])));

        if (data.getSortOrder() == 1) {
            query.with(Sort.by(Sort.Direction.DESC, "date"));
        } else {
            query.with(Sort.by(Sort.Direction.ASC, "date"));
        }
        query.skip((long) (data.getPage() - 1) * data.getLimit());
        query.limit(data.getLimit());
        return mongoTemplate.find(query, TransactionDB.class);
    }
}
