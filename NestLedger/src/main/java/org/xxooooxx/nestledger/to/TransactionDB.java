/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.to;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document("transactions")
public class TransactionDB {
    @Id
    private String _id;
    private String title = "";
    private String note = "";
    private BigDecimal money = BigDecimal.ZERO;
    private Date date;
    private String type;
    private String userId;
    private String tagId;
    private String ledgerId;
    private Integer version;
}
