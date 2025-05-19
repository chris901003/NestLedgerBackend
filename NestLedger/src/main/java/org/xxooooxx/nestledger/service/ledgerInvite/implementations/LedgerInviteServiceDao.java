/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.ledgerInvite.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.ledgerInvite.interfaces.LedgerInviteDao;
import org.xxooooxx.nestledger.dao.userinfo.interfaces.UserInfoDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.ledgerInvite.interfaces.LedgerInviteService;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.to.LedgerInviteDB;
import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.ledger.request.LedgerUpdateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteGetRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.response.LedgerInviteGetResponseData;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

import java.util.List;

@Service
public class LedgerInviteServiceDao implements LedgerInviteService {

    @Autowired
    private LedgerInviteDao ledgerInviteDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public LedgerInviteGetResponseData createLedgerInvite(LedgerInviteCreateRequestData data) {
        LedgerDB ledgerDB = ledgerDao.getLedger(data.getLedgerId());
        UserInfoDB userInfoDB = userInfoDao.getUserInfoById(data.getReceiveUserId());
        if (ledgerDB == null) {
            throw new CustomException(CustomExceptionEnum.LEDGER_NOT_FOUND);
        }
        if (!ledgerDB.getUserIds().contains(data.getSendUserId())) {
            throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_CREATE_LEDGER_INVITE);
        }
        if (ledgerDB.getUserIds().contains(data.getReceiveUserId())) {
            throw new CustomException(CustomExceptionEnum.ALREADY_IN_LEDGER);
        }
        if (userInfoDB == null) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_NOT_FOUND);
        }
        if (userInfoDB.getIsDelete()) {
            throw new CustomException(CustomExceptionEnum.USER_INFO_HAS_BEEN_DELETED);
        }
        LedgerInviteDB ledgerInviteDB = ledgerInviteDao.createLedgerInvite(data);
        return new LedgerInviteGetResponseData(ledgerInviteDB);
    }

    @Override
    public List<LedgerInviteGetResponseData> getLedgerInvite(LedgerInviteGetRequestData data) {
        if (data.getLedgerId() == null && data.getReceiveUserId() == null) {
            throw new CustomException(CustomExceptionEnum.INVALID_LEDGER_INVITE_GET);
        }
        List<LedgerInviteDB> ledgerInviteDBList = ledgerInviteDao.getLedgerInvite(data);
        return ledgerInviteDBList.stream().map(LedgerInviteGetResponseData::new).toList();
    }

    @Override
    @Transactional
    public void deleteLedgerInvite(String inviteId, boolean accept) {
        LedgerInviteDB ledgerInviteDB = ledgerInviteDao.getLedgerInviteById(inviteId);
        if (ledgerInviteDB == null) {
            throw new CustomException(CustomExceptionEnum.LEDGER_INVITE_NOT_FOUND);
        }

        String uid = UserContext.getUid();
        ledgerInviteDao.deleteLedgerInvite(inviteId);
        if (accept) {
            if (!ledgerInviteDB.getReceiveUserId().equals(uid)) {
                throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_ACCEPT_LEDGER_INVITE);
            }
            // Update ledger
            LedgerDB ledgerDB = ledgerDao.ledgerUserJoin(
                    ledgerInviteDB.getReceiveUserId(), ledgerInviteDB.getLedgerId()
            );

            // Update receiver user info
            UserInfoDB userInfoDB = userInfoDao.userJoinLedger(
                    ledgerInviteDB.getReceiveUserId(), ledgerInviteDB.getLedgerId()
            );
        }
    }
}
