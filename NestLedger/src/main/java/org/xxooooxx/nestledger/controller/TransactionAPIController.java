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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.service.transaction.interfaces.TransactionService;
import org.xxooooxx.nestledger.vo.transaction.request.TransactionCreateRequestData;
import org.xxooooxx.nestledger.vo.transaction.response.TransactionGetResponseData;

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
}
