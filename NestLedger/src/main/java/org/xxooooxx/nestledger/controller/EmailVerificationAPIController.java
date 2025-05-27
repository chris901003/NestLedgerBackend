/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/26.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.service.emailVerification.interfaces.EmailVerificationService;
import org.xxooooxx.nestledger.vo.emailVerification.response.EmailVerificationGetResponseData;

@RestController
@RequestMapping("/v1/email-verification")
public class EmailVerificationAPIController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping("/get")
    public Response<EmailVerificationGetResponseData> getEmailVerification() {
        return Response.success(emailVerificationService.getEmailVerification());
    }

    @GetMapping("/send")
    public Response<?> sendEmailVerification(@Parameter String emailAddress) {
        emailVerificationService.sendEmailVerification(emailAddress);
        return Response.success(null);
    }
}
