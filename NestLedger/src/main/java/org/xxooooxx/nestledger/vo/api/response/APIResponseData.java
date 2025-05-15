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

@Data
public class APIResponseData {
    private String path;
    private String method;
    private String[] versions;

    public APIResponseData(String path, String method, String[] versions) {
        this.path = path;
        this.method = method;
        this.versions = versions;
    }
}
