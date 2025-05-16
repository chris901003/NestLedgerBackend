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
public class TransactionUpdateRequestData {
    @NotBlank
    private String _id;

    private String title;
    private String note;
    private Integer money;
    private Date date;
    private String type;
    private String tagId;
    private Integer version;
}
