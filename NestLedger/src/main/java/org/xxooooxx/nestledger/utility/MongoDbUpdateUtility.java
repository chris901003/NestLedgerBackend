/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.utility;

import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;

public class MongoDbUpdateUtility {
    static public <T> Update getFullUpdate(T data) throws IllegalAccessException {
        Update update = new Update();
        // 使用反射獲取所有的內容，達成資料更新
        // step1: 透過 data.getClass() 獲取 class 自解碼物件
        // step2: 透過 getDeclaredFields() 獲取所有的欄位包含 private 聲明的變數
        for (Field field: data.getClass().getDeclaredFields()) {
            // step3: 透過 setAccessible(true) 暫時關閉權限檢查，使我們可以使用 private 欄位
            field.setAccessible(true);
            // step4: 透過 field.get(data) 獲取該欄位的值
            Object value = field.get(data);
            if (value != null) {
                // step5: 透過 getName() 獲取欄位名稱，由於我們的欄位名稱與資料庫的欄位名稱相同，所以可以直接使用
                update.set(field.getName(), value);
            }
        }
        return update;
    }
}
