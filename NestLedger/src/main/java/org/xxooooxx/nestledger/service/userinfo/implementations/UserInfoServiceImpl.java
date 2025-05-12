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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.service.userinfo.interfaces.UserInfoService;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.userinfo.response.UserInfoGetResponse;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfoGetResponse getUserInfoById(String id) {
        UserInfoDB userInfoDB = userInfoDao.getUserInfoById(id);
        return new UserInfoGetResponse(userInfoDB);
    }
}
