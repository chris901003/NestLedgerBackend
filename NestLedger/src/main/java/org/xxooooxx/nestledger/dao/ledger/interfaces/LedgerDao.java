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
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreate;

public interface LedgerDao {
    LedgerDB createLedger(LedgerCreate createData);
}
