/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/13.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.ledger.response;

import lombok.Data;
import org.xxooooxx.nestledger.to.LedgerDB;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class LedgerGetResponseData {
    private String _id;
    private String title;
    private ArrayList<String> userIds;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private Integer version;

    public LedgerGetResponseData(LedgerDB data) {
        this._id = data.get_id();
        this.title = data.getTitle();
        this.userIds = data.getUserIds();
        this.totalIncome = data.getTotalIncome();
        this.totalExpense = data.getTotalExpense();
        this.version = data.getVersion();
    }
}
