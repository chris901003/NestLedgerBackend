/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.utility;


public class UserContext {
    private static final ThreadLocal<String> uidHolder = new ThreadLocal<>();

    public static void setUid(String uid) {
        uidHolder.set(uid);
    }

    public static String getUid() {
        return uidHolder.get();
    }

    public static void clear() {
        uidHolder.remove();
    }
}
