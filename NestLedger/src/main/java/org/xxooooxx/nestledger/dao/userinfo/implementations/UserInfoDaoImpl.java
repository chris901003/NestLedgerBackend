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
import org.xxooooxx.nestledger.utility.MongoDbUpdateUtility;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

import java.util.Collections;
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
        Update update = MongoDbUpdateUtility.getFullUpdate(data);

        if (!update.getUpdateObject().isEmpty()) {
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(query, update, options, UserInfoDB.class);
        }
        return null;
    }

    @Override
    public UserInfoDB changeQuickLogLedger(String uid, String ledgerId) {
        Query query = new Query(Criteria.where("id").is(uid));
        UserInfoDB userInfoDB = mongoTemplate.findOne(query, UserInfoDB.class);
        if (userInfoDB == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        int index = userInfoDB.getLedgerIds().indexOf(ledgerId);
        if (index != -1) {
            Collections.swap(userInfoDB.getLedgerIds(), 0, index);
            Query updateQuery = new Query(Criteria.where("id").is(uid));
            Update update = new Update().set("ledgerIds", userInfoDB.getLedgerIds());
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(updateQuery, update, options, UserInfoDB.class);
        } else {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
    }

    @Override
    public UserInfoDB userJoinLedger(String uid, String ledgerId) {
        Query query = new Query(Criteria.where("id").is(uid));
        Update update = new Update().addToSet("ledgerIds", ledgerId);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return mongoTemplate.findAndModify(query, update, options, UserInfoDB.class);
    }

    @Override
    public UserInfoDB userLeaveLedger(String uid, String ledgerId) {
        Query query = new Query(Criteria.where("id").is(uid));
        Update update = new Update().pull("ledgerIds", ledgerId);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return mongoTemplate.findAndModify(query, update, options, UserInfoDB.class);
    }

    @Override
    public UserInfoDB deleteUserInfo(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        UserInfoDB userInfoDB = mongoTemplate.findOne(query, UserInfoDB.class);
        if (userInfoDB == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        } else {
            userInfoDB.setIsDelete(true);
            return mongoTemplate.save(userInfoDB);
        }
    }
}
