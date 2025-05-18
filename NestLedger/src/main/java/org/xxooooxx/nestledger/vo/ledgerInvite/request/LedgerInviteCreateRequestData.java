/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.ledgerInvite.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LedgerInviteCreateRequestData {
    @NotBlank
    private String ledgerId;

    @NotBlank
    private String sendUserId;

    @NotBlank
    private String receiveUserId;

    @NotNull
    private Integer version;
}
