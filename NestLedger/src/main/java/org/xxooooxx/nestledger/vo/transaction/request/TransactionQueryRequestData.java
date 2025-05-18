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
import lombok.Data;

import java.util.Date;

@Data
public class TransactionQueryRequestData {
    @NotBlank
    private String ledgerId;

    private String search;
    private Date startDate;
    private Date endDate;
    private String tagId;
    private String type;
    private String userId;
    private Integer sortOrder = 1;
    private Integer page = 1;
    private Integer limit = 100;
}
