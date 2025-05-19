/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.ledger.interfaces;

import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerUpdateRequestData;


public interface LedgerDao {
    LedgerDB createLedger(LedgerCreateRequestData createData);
    LedgerDB getLedger(String ledgerId);
    LedgerDB updateLedger(LedgerUpdateRequestData data, boolean withCheckAuth) throws IllegalAccessException;
    void deleteLedger(String ledgerId);
    void incrementTotalIncome(String ledgerId, Integer amount);
    void incrementTotalExpense(String ledgerId, Integer amount);
    LedgerDB ledgerUserJoin(String uid, String ledgerId);
    LedgerDB ledgerUserLeave(String uid, String ledgerId);
}
