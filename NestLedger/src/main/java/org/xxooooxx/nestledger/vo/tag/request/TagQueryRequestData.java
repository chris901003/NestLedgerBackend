/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.tag.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagQueryRequestData {
    @NotBlank
    private String ledgerId;
    private String search;
    private String tagId;
    private Integer page = 1;
    private Integer limit = 100;
}
