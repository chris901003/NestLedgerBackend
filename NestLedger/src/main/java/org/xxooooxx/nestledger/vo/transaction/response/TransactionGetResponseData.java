/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.transaction.response;

import lombok.Data;
import org.xxooooxx.nestledger.to.TransactionDB;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionGetResponseData {
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

    public TransactionGetResponseData(TransactionDB transactionDB) {
        this._id = transactionDB.get_id();
        this.title = transactionDB.getTitle();
        this.note = transactionDB.getNote();
        this.money = transactionDB.getMoney();
        this.date = transactionDB.getDate();
        this.type = transactionDB.getType();
        this.userId = transactionDB.getUserId();
        this.tagId = transactionDB.getTagId();
        this.ledgerId = transactionDB.getLedgerId();
        this.version = transactionDB.getVersion();
    }
}
