/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.userinfo.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.userinfo.interfaces.UserInfoService;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreate;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

import java.util.List;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Transactional
    @Override
    public UserInfoGetResponse createUserInfoIfNeeded(String id) {
        try {
            UserInfoDB existingUserInfo = userInfoDao.getUserInfoById(id);
            log.info("User info already exists: {}", id);
            return new UserInfoGetResponse(existingUserInfo);
        } catch(CustomException e) {
            log.info("Creating new user info: {}", id);
            UserInfoDB newUserInfo = createUserInfo(id);

            // Create main ledger
            LedgerCreate ledgerCreate = new LedgerCreate();
            ledgerCreate.setTitle("[Main]:" + id);
            ledgerCreate.setUserId(id);
            ledgerCreate.setVersion(1);
            ledgerDao.createLedger(ledgerCreate);
            return new UserInfoGetResponse(newUserInfo);
        }
    }

    @Override
    public UserInfoGetResponse getUserInfoById(String id) {
        UserInfoDB userInfoDB = userInfoDao.getUserInfoById(id);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public List<UserInfoGetResponse> getMultipleUserInfoById(List<String> ids) {
        List<UserInfoDB> userInfoDBList = userInfoDao.getMultipleUserInfoById(ids);
        return userInfoDBList.stream()
                .map(UserInfoGetResponse::new)
                .toList();
    }

    @Override
    public UserInfoGetResponse getUserInfoByEmail(String email) {
        UserInfoDB userInfoDB = userInfoDao.getUserInfoByEmail(email);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public UserInfoGetResponse updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException {
        if (userInfoDao.getUserInfoById(data.getId()) == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        UserInfoDB userInfoDB = userInfoDao.updateUserInfo(data);
        return new UserInfoGetResponse(userInfoDB);
    }

    @Override
    public UserInfoGetResponse deleteUserInfo(String id) {
        UserInfoDB userInfoDB = userInfoDao.deleteUserInfo(id);
        return new UserInfoGetResponse(userInfoDB);
    }

    private UserInfoDB createUserInfo(String id) {
        return userInfoDao.createUserInfo(id);
    }
}
