/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.transaction.interfaces;

import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionQueryRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionUpdateRequestData;
import org.xxooooxx.nestledger.vo.transaction.response.TransactionGetResponseData;

import java.util.List;

public interface TransactionService {
    TransactionGetResponseData createTransaction(TransactionCreateRequestData data);
    TransactionGetResponseData getTransaction(String transactionId);
    TransactionGetResponseData updateTransaction(TransactionUpdateRequestData data) throws IllegalAccessException;
    void deleteTransaction(String transactionId);
    List<TransactionGetResponseData> queryTransactions(TransactionQueryRequestData data);
}
