/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.userinfo.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.to.UserInfoDB;


@Component
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserInfoDB getUserInfoById(String id) {
        String queryStr = String.format("{ \"id\": \"%s\" }", id);
        BasicQuery query = new BasicQuery(queryStr);
        return mongoTemplate.findOne(query, UserInfoDB.class);
    }

    @Override
    public UserInfoDB createUserInfo(String id) {
        return null;
    }
}
