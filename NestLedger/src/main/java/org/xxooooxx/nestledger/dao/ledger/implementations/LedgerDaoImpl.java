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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        ledgerDB.setTotalExpense(BigDecimal.ZERO);
        ledgerDB.setTotalIncome(BigDecimal.ZERO);
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
}
