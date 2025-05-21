/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/20.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.exception;

import lombok.Getter;

public class AuthenticationException extends RuntimeException {
    @Getter
    final private Integer code;

    @Getter
    final private String message;

    public AuthenticationException(final Integer code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
