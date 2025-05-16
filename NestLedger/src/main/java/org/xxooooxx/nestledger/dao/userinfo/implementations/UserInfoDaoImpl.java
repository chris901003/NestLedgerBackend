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
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

import java.lang.reflect.Field;
import java.util.List;


@Component
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserInfoDB getUserInfoById(String id) {
        UserInfoDB userInfoDB = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), UserInfoDB.class);
        if (userInfoDB == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        return userInfoDB;
    }

    @Override
    public List<UserInfoDB> getMultipleUserInfoById(List<String> ids) {
        Query query = new Query(Criteria.where("id").in(ids));
        return mongoTemplate.find(query, UserInfoDB.class);
    }

    @Override
    public UserInfoDB getUserInfoByEmail(String email) {
        UserInfoDB userInfoDB = mongoTemplate.findOne(
                new Query(Criteria.where("emailAddress").is(email)), UserInfoDB.class
        );
        if (userInfoDB == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        return userInfoDB;
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

        if (!update.getUpdateObject().isEmpty()) {
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(query, update, options, UserInfoDB.class);
        }
        return null;
    }
}
