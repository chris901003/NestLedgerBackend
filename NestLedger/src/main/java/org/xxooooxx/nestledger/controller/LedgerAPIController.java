/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.service.ledger.interfaces.LedgerService;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreate;
import org.xxooooxx.nestledger.vo.ledger.response.LedgerGetResponse;

@RestController
@RequestMapping("/v1/ledger")
public class LedgerAPIController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/create")
    public Response<LedgerGetResponse> createLedger(@RequestBody LedgerCreate createData) {
        String uid = UserContext.getUid();
        if (createData.getUserId() == null) {
            createData.setUserId(uid);
        }
        return Response.success(ledgerService.createLedger(createData));
    }
}
