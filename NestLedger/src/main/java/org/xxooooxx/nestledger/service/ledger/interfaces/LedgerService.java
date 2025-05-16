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

import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerUpdateRequestData;
import org.xxooooxx.nestledger.vo.ledger.response.LedgerGetResponseData;

public interface LedgerService {
    LedgerGetResponseData createLedger(LedgerCreateRequestData createData);
    LedgerGetResponseData getLedger(String ledgerId);
    LedgerGetResponseData updateLedger(LedgerUpdateRequestData updateData) throws IllegalAccessException;
}
