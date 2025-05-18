/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.xxooooxx.nestledger.annotations.ColorAnnotation;

@Data
@Document("tags")
public class TagDB {
    @Id
    private String _id;

    @NotBlank
    private String label;

    @ColorAnnotation
    private String color;

    @NotBlank
    private String ledgerId;

    @NotNull
    private Integer usingCount = 0;

    @NotNull
    private Integer version;
}
