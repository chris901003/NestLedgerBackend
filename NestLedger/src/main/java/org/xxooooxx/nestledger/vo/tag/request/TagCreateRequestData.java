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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.xxooooxx.nestledger.annotations.ColorAnnotation;

@Data
public class TagCreateRequestData {
    @NotBlank
    private String label;

    @ColorAnnotation
    private String color;

    @NotBlank
    private String ledgerId;

    @NotNull
    private Integer version;
}
