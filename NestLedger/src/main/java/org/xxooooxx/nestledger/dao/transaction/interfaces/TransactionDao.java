/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.transaction.interfaces;

import org.xxooooxx.nestledger.to.TransactionDB;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionUpdateRequestData;

public interface TransactionDao {
    TransactionDB createTransaction(TransactionCreateRequestData data);
    TransactionDB getTransaction(String transactionId);
    TransactionDB updateTransaction(TransactionUpdateRequestData data) throws IllegalAccessException;
}
