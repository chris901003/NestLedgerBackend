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
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

import java.lang.reflect.Field;


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
        UserInfoDB userInfoDB = new UserInfoDB();
        userInfoDB.setId(id);
        userInfoDB.setTimeZone(8);
        userInfoDB.setImageQuality(1.0);
        userInfoDB.setIsDelete(false);
        userInfoDB.setVersion(1);
        return mongoTemplate.insert(userInfoDB);
    }

    @Override
    public UserInfoDB updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException {
        Query query = new Query(Criteria.where("id").is(data.getId()));
        Update update = new Update();

        for (Field field: data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(data);
            if (value != null) {
                update.set(field.getName(), value);
            }
        }

        if (!update.getUpdateObject().isEmpty()) {
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(query, update, options, UserInfoDB.class);
        }
        return null;
    }
}
