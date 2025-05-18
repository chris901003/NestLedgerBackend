/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
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
import org.xxooooxx.nestledger.service.ledgerInvite.interfaces.LedgerInviteService;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteGetRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.response.LedgerInviteGetResponseData;

import java.util.List;

@RequestMapping("/v1/ledger-invite")
@RestController
public class LedgerInviteAPIController {

    @Autowired
    private LedgerInviteService ledgerInviteService;

    @PostMapping("/create")
    public Response<LedgerInviteGetResponseData> createLedgerInvite(
            @RequestBody @Valid LedgerInviteCreateRequestData data
    ) {
        LedgerInviteGetResponseData response = ledgerInviteService.createLedgerInvite(data);
        return Response.success(response);
    }

    @GetMapping("/get")
    public Response<List<LedgerInviteGetResponseData>> getLedgerInvite(
            @Valid LedgerInviteGetRequestData data
    ) {
        List<LedgerInviteGetResponseData> response = ledgerInviteService.getLedgerInvite(data);
        return Response.success(response);
    }
}
