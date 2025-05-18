/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.service.transaction.interfaces.TransactionService;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionQueryRequestData;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionUpdateRequestData;
import org.xxooooxx.nestledger.vo.transaction.response.TransactionGetResponseData;

import java.util.List;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionAPIController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public Response<TransactionGetResponseData> createTransaction(
            @RequestBody @Valid TransactionCreateRequestData data
    ) {
        TransactionGetResponseData responseData = transactionService.createTransaction(data);
        return Response.success(responseData);
    }

    @GetMapping("/get")
    public Response<TransactionGetResponseData> getTransaction(
            @RequestParam String transactionId
    ) {
        TransactionGetResponseData responseData = transactionService.getTransaction(transactionId);
        return Response.success(responseData);
    }

    @PatchMapping("/update")
    public Response<TransactionGetResponseData> updateTransaction(
            @RequestBody @Valid TransactionUpdateRequestData data
    ) throws IllegalAccessException {
        TransactionGetResponseData responseData = transactionService.updateTransaction(data);
        return Response.success(responseData);
    }

    @DeleteMapping("/delete")
    public Response<Void> deleteTransaction(
            @RequestParam String transactionId
    ) {
        transactionService.deleteTransaction(transactionId);
        return Response.success(null);
    }

    @PostMapping("/query")
    public Response<List<TransactionGetResponseData>> queryTransaction(
            @RequestBody @Valid TransactionQueryRequestData data
    ) {
        List<TransactionGetResponseData> responseData = transactionService.queryTransactions(data);
        return Response.success(responseData);
    }
}
