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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.userinfo.interfaces.UserInfoService;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/get-user-by-uid")
    public Response<UserInfoGetResponse> getUserInfoByUid(@RequestParam(value = "uid") String uid) {
        return Response.success(userInfoService.getUserInfoById(uid));
    }

    @PostMapping("/get-multiple-user-info")
    public Response<List<UserInfoGetResponse>> getMultipleUserInfo(@RequestBody Map<String, List<String>> body) {
        if (!body.containsKey("uids")) {
            throw new CustomException(CustomExceptionEnum.MISSING_PARAMETER);
        }
        List<String> uids = body.get("uids");
        return Response.success(userInfoService.getMultipleUserInfoById(uids));
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

    @PatchMapping("/change-quick-log-ledger")
    public Response<UserInfoGetResponse> changeQuickLogLedger(@RequestParam String ledgerId) {
        String uid = UserContext.getUid();
        return Response.success(userInfoService.changeQuickLogLedger(uid, ledgerId));
    }

    @Transactional
    @DeleteMapping("/delete")
    public Response<UserInfoGetResponse> deleteUserInfo() throws FirebaseAuthException {
        String uid = UserContext.getUid();

        // Delete user data from Firebase
        FirebaseAuth.getInstance().deleteUser(uid);
        return Response.success(userInfoService.deleteUserInfo(uid));
    }

    @PostMapping("/upload-avatar")
    public Response<UserInfoGetResponse> uploadAvatar(
            @RequestParam("avatar") MultipartFile file
    ) throws IOException, IllegalAccessException {
        String uid = UserContext.getUid();
        return Response.success(userInfoService.uploadAvatar(uid, file));
    }

    @GetMapping("/avatar")
    public ResponseEntity<InputStreamResource> getAvatar(@RequestParam(value = "uid") String uid) throws IOException {
        String path = "C:\\Users\\ediet\\Documents\\Codes\\NestLedgerDB\\files\\avatar.jpg";
        UserInfoGetResponse userInfoGetResponse = userInfoService.getUserInfoById(uid);
        if (!userInfoGetResponse.getAvatar().isBlank()) {
            path = userInfoGetResponse.getAvatar();
        }

        // Check if the file exists
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            throw new CustomException(CustomExceptionEnum.USER_AVATAR_NOT_FOUND);
        }

        FileInputStream inputStream = new FileInputStream(path);
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(inputStream.available())
                .header("Content-Disposition", "attachment; filename=\"" + path + "\"")
                .body(resource);
    }
}
