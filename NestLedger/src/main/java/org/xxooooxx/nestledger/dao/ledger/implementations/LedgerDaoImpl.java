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
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreate;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class LedgerDaoImpl implements LedgerDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public LedgerDB createLedger(LedgerCreate createData) {
        LedgerDB ledgerDB = new LedgerDB();
        ledgerDB.setUserIds(new ArrayList<>());
        ledgerDB.setTitle(createData.getTitle());
        ledgerDB.getUserIds().add(createData.getUserId());
        ledgerDB.setTotalExpense(BigDecimal.ZERO);
        ledgerDB.setTotalIncome(BigDecimal.ZERO);
        ledgerDB.setVersion(createData.getVersion());
        return mongoTemplate.insert(ledgerDB);
    }
}
