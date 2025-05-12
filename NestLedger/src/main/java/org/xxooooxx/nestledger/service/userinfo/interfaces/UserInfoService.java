/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.userinfo.interfaces;

import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

public interface UserInfoService {
    UserInfoGetResponse getUserInfoById(String id);
}
