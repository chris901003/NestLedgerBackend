/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.userinfo.interfaces.UserInfoService;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

@RestController
@RequestMapping("/v1/user")
public class UserInfoAPIController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/login")
    public Response<UserInfoGetResponse> login() {
        String uid = UserContext.getUid();
        return Response.success(userInfoService.createUserInfoIfNeeded(uid));
    }

    @GetMapping("/get")
    public Response<UserInfoGetResponse> getUserInfo() {
        String uid = UserContext.getUid();
        return Response.success(userInfoService.getUserInfoById(uid));
    }

    @GetMapping("/get-user-by-email")
    public Response<UserInfoGetResponse> getUserInfoByEmail(
            @RequestParam(value = "email") String email
    ) {
        return Response.success(userInfoService.getUserInfoByEmail(email));
    }

    @PatchMapping("/update")
    public Response<UserInfoGetResponse> updateUserInfo(@RequestBody @Valid UserInfoUpdateRequestData data)
            throws IllegalAccessException {
        String uid = UserContext.getUid();
        if (!uid.equals(data.getId())) {
            throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_UPDATE_USER_INFO);
        }
        return Response.success(userInfoService.updateUserInfo(data));
    }
}
