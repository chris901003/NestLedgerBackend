/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/15.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.api.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AllAPIResponseData {
    private ArrayList<APIResponseData> userAPI;
    private ArrayList<APIResponseData> ledgerAPI;

    public AllAPIResponseData() {
        // User API
        this.userAPI = new ArrayList<>();
        this.userAPI.add(new APIResponseData("/user/login", "GET", new String[]{"v1"}));
        this.userAPI.add(new APIResponseData("/user/get", "GET", new String[]{"v1"}));

        // Ledger API
        this.ledgerAPI = new ArrayList<>();
        this.ledgerAPI.add(new APIResponseData("/ledger/create", "POST", new String[]{"v1"}));
    }
}
