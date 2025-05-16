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

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.ledger.interfaces.LedgerService;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreateRequestData;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerUpdateRequestData;
import org.xxooooxx.nestledger.vo.ledger.response.LedgerGetResponseData;

@RestController
@RequestMapping("/v1/ledger")
public class LedgerAPIController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/create")
    public Response<LedgerGetResponseData> createLedger(@RequestBody LedgerCreateRequestData createData) {
        String uid = UserContext.getUid();
        if (createData.getUserId() == null) {
            createData.setUserId(uid);
        }
        return Response.success(ledgerService.createLedger(createData));
    }

    @GetMapping("/get")
    public Response<LedgerGetResponseData> getLedger(@RequestParam String ledgerId) {
        String uid = UserContext.getUid();
        LedgerGetResponseData response = ledgerService.getLedger(ledgerId);
        if (!response.getUserIds().contains(uid)) {
            throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_GET_LEDGER);
        }
        return Response.success(ledgerService.getLedger(ledgerId));
    }

    @PatchMapping("/update")
    public Response<LedgerGetResponseData> updateLedger(
            @RequestBody @Valid LedgerUpdateRequestData updateData
    ) throws IllegalAccessException {
        return Response.success(ledgerService.updateLedger(updateData));
    }

    @DeleteMapping("/delete")
    public Response<Void> deleteLedger(@RequestParam String ledgerId) {
        ledgerService.deleteLedger(ledgerId);
        return Response.success(null);
    }
}
