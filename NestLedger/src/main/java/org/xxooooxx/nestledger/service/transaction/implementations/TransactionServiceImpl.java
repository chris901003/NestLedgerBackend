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
import org.xxooooxx.nestledger.dao.tag.interfaces.TagDao;
import org.xxooooxx.nestledger.dao.transaction.interfaces.TransactionDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.transaction.interfaces.TransactionService;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.to.TagDB;
import org.xxooooxx.nestledger.to.TransactionDB;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionQueryRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionUpdateRequestData;
import org.xxooooxx.nestledger.vo.transaction.response.TransactionGetResponseData;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private TagDao tagDao;

    @Transactional
    @Override
    public TransactionGetResponseData createTransaction(TransactionCreateRequestData data) {
        checkIsInUserInLedger(data.getLedgerId());
        TransactionDB transactionDB = transactionDao.createTransaction(data);
        if (Objects.equals(data.getType(), "income")) {
            ledgerDao.incrementTotalIncome(data.getLedgerId(), data.getMoney());
        } else {
            ledgerDao.incrementTotalExpense(data.getLedgerId(), data.getMoney());
        }

        checkTagIsValid(data.getTagId(), data.getLedgerId());
        tagDao.incrementTagUsingCount(data.getTagId(), 1);
        return new TransactionGetResponseData(transactionDB);
    }

    @Override
    public TransactionGetResponseData getTransaction(String transactionId) {
        TransactionDB transactionDB = transactionDao.getTransaction(transactionId);
        checkIsInUserInLedger(transactionDB.getLedgerId());
        return new TransactionGetResponseData(transactionDB);
    }

    @Transactional
    @Override
    public TransactionGetResponseData updateTransaction(TransactionUpdateRequestData data) throws IllegalAccessException {
        TransactionDB transactionDB = transactionDao.getTransaction(data.get_id());
        TagDB originalTagDB = tagDao.getTag(transactionDB.getTagId());
        checkIsInUserInLedger(transactionDB.getLedgerId());
        TransactionDB updatedTransactionDB = transactionDao.updateTransaction(data);

        if (Objects.equals(transactionDB.getType(), "income")) {
            ledgerDao.incrementTotalIncome(transactionDB.getLedgerId(), -transactionDB.getMoney());
        } else {
            ledgerDao.incrementTotalExpense(transactionDB.getLedgerId(), -transactionDB.getMoney());
        }
        if (Objects.equals(updatedTransactionDB.getType(), "income")) {
            ledgerDao.incrementTotalIncome(transactionDB.getLedgerId(), updatedTransactionDB.getMoney());
        } else {
            ledgerDao.incrementTotalExpense(transactionDB.getLedgerId(), updatedTransactionDB.getMoney());
        }

        checkTagIsValid(updatedTransactionDB.getTagId(), updatedTransactionDB.getLedgerId());
        if (!originalTagDB.get_id().equals(updatedTransactionDB.getTagId())) {
            tagDao.incrementTagUsingCount(originalTagDB.get_id(), -1);
            tagDao.incrementTagUsingCount(updatedTransactionDB.getTagId(), 1);
        }
        return new TransactionGetResponseData(updatedTransactionDB);
    }

    @Override
    public void deleteTransaction(String id) {
        TransactionDB transactionDB = transactionDao.getTransaction(id);
        checkIsInUserInLedger(transactionDB.getLedgerId());
        if (Objects.equals(transactionDB.getType(), "income")) {
            ledgerDao.incrementTotalIncome(transactionDB.getLedgerId(), -transactionDB.getMoney());
        } else {
            ledgerDao.incrementTotalExpense(transactionDB.getLedgerId(), -transactionDB.getMoney());
        }
        transactionDao.deleteTransaction(id);
    }

    @Override
    public List<TransactionGetResponseData> queryTransactions(TransactionQueryRequestData data) {
        checkIsInUserInLedger(data.getLedgerId());
        List<TransactionDB> transactionDBList = transactionDao.queryTransactions(data);
        return transactionDBList.stream()
                .map(TransactionGetResponseData::new)
                .toList();
    }

    private void checkIsInUserInLedger(String ledgerId) {
        LedgerDB ledgerDB = ledgerDao.getLedger(ledgerId);
        if (ledgerDB == null) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
        if (!ledgerDB.getUserIds().contains(UserContext.getUid())) {
            throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_OPERATION_ON_TRANSACTION);
        }
    }

    private void checkTagIsValid(String tagId, String ledgerId) {
        TagDB tagDB = tagDao.getTag(tagId);
        if (tagDB == null) {
            throw new CustomException(CustomExceptionEnum.TAG_NOT_FOUND);
        }
        if (!tagDB.getLedgerId().equals(ledgerId)) {
            throw new CustomException(CustomExceptionEnum.TAG_NOT_IN_LEDGER);
        }
    }
}
