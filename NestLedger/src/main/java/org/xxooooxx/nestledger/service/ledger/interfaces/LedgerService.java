/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.ledger.interfaces;

import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreate;
import org.xxooooxx.nestledger.vo.ledger.response.LedgerGetResponse;

public interface LedgerService {
    LedgerGetResponse createLedger(LedgerCreate createData);
}
