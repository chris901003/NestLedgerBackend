/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.ledger.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.tag.interfaces.TagDao;
import org.xxooooxx.nestledger.dao.transaction.interfaces.TransactionDao;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.service.ledger.interfaces.LedgerService;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerUpdateRequestData;
import org.xxooooxx.nestledger.vo.ledger.response.LedgerGetResponseData;

@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    @Transactional
    public LedgerGetResponseData createLedger(LedgerCreateRequestData createData) {
        LedgerDB ledgerDB = ledgerDao.createLedger(createData);
        userInfoDao.userJoinLedger(createData.getUserId(), ledgerDB.get_id());
        return new LedgerGetResponseData(ledgerDB);
    }

    @Override
    public LedgerGetResponseData getLedger(String ledgerId) {
        LedgerDB ledgerDB = ledgerDao.getLedger(ledgerId);
        return new LedgerGetResponseData(ledgerDB);
    }

    @Override
    public LedgerGetResponseData updateLedger(LedgerUpdateRequestData updateData) throws IllegalAccessException {
        LedgerDB ledgerDB = ledgerDao.updateLedger(updateData, true);
        return new LedgerGetResponseData(ledgerDB);
    }

    @Override
    @Transactional
    public void deleteLedger(String ledgerId) {
        tagDao.deleteTagsByLedgerId(ledgerId);
        transactionDao.deleteTransactionsByLedgerId(ledgerId);
        ledgerDao.deleteLedger(ledgerId);
    }
}
