/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/26.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.utility;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom(); // 安全的隨機數產生器
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateToken() {
        byte[] randomBytes = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes); // 可安全用於 URL
    }
}
