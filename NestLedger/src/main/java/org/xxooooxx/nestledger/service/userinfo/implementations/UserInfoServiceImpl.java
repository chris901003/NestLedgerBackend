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
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.service.userinfo.interfaces.UserInfoService;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerCreate;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Override
    public UserInfoGetResponse createUserInfoIfNeeded(String id) {
        UserInfoDB existingUserInfo = userInfoDao.getUserInfoById(id);
        if (existingUserInfo != null) {
            log.info("User info already exists: {}", id);
            return new UserInfoGetResponse(existingUserInfo);
        } else {
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

    private UserInfoDB createUserInfo(String id) {
        return userInfoDao.createUserInfo(id);
    }
}
