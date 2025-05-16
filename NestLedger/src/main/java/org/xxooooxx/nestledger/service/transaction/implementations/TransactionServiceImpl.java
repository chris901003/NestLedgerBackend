/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.transaction.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.transaction.interfaces.TransactionDao;
import org.xxooooxx.nestledger.service.transaction.interfaces.TransactionService;
import org.xxooooxx.nestledger.to.TransactionDB;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.response.TransactionGetResponseData;

import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Transactional
    public TransactionGetResponseData createTransaction(TransactionCreateRequestData data) {
        TransactionDB transactionDB = transactionDao.createTransaction(data);
        if (Objects.equals(data.getType(), "income")) {
            ledgerDao.incrementTotalIncome(data.getLedgerId(), data.getMoney());
        } else {
            ledgerDao.incrementTotalExpense(data.getLedgerId(), data.getMoney());
        }
        return new TransactionGetResponseData(transactionDB);
    }
}
