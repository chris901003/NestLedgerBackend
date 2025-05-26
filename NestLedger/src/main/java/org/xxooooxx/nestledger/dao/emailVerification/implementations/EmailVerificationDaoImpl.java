/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/26.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.emailVerification.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.emailVerification.interfaces.EmailVerificationDao;
import org.xxooooxx.nestledger.to.EmailVerificationDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EmailVerificationDaoImpl implements EmailVerificationDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private EmailVerificationDB createEmailVerification(String uid) {
        EmailVerificationDB emailVerificationDB = new EmailVerificationDB();
        emailVerificationDB.setUid(uid);
        emailVerificationDB.setVerificationHistory(new ArrayList<>());
        return mongoTemplate.insert(emailVerificationDB);
    }

    @Override
    public EmailVerificationDB getEmailVerificationByUid(String uid) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("uid").is(uid)), EmailVerificationDB.class
        );
    }

    @Override
    public void updateEmailVerification(
            String uid, String token, String emailAddress, Date expireAt, List<Date> verificationHistory
    ) {
        EmailVerificationDB emailVerificationDB = mongoTemplate.findOne(
                Query.query(Criteria.where("uid").is(uid)), EmailVerificationDB.class
        );
        if (emailVerificationDB == null) {
            emailVerificationDB = createEmailVerification(uid);
        }

        emailVerificationDB.setToken(token);
        emailVerificationDB.setEmailAddress(emailAddress);
        emailVerificationDB.setExpireAt(expireAt);
        emailVerificationDB.setVerificationHistory(verificationHistory);
        mongoTemplate.save(emailVerificationDB);
    }
}
