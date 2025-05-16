/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.to;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;

@Document("ledgers")
@Data
public class LedgerDB {
    @Id
    private String _id;

    private String title = "";

    private ArrayList<String> userIds = new ArrayList<>();

    private BigDecimal totalIncome = BigDecimal.ZERO;

    private BigDecimal totalExpense = BigDecimal.ZERO;

    private Integer version = 1;
}
