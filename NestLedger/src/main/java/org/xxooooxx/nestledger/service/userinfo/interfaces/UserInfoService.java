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

import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

import java.util.List;

public interface UserInfoService {
    UserInfoGetResponse createUserInfoIfNeeded(String id);
    UserInfoGetResponse getUserInfoById(String id);
    List<UserInfoGetResponse> getMultipleUserInfoById(List<String> ids);
    UserInfoGetResponse getUserInfoByEmail(String email);
    UserInfoGetResponse updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException;
    UserInfoGetResponse deleteUserInfo(String id);
}
