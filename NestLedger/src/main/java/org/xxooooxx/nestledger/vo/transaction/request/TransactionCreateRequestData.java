/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.transaction.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionCreateRequestData {
    private String title = "";
    private String note = "";

    @NotNull
    private Integer money;

    @DateTimeFormat
    private Date date;

    @NotBlank
    private String type;

    @NotBlank
    private String userId;

    @NotBlank
    private String tagId;

    @NotBlank
    private String ledgerId;

    @NotNull
    private Integer version;
}
