package org.xxooooxx.nestledger.dao.userinfo.interfaces;

import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

import java.util.List;

public interface UserInfoDao {
    /**
     * Get user info by id
     *
     * @param id user id
     * @return UserInfoDB
     */
    UserInfoDB getUserInfoById(String id);

    UserInfoDB getUserInfoByEmail(String email);

    List<UserInfoDB> getMultipleUserInfoById(List<String> ids);

    /**
     * Get user info by user id
     *
     * @param id user id
     * @return UserInfoDB
     */
    UserInfoDB createUserInfo(String id);

    UserInfoDB updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException;

    UserInfoDB changeQuickLogLedger(String uid, String ledgerId);

    UserInfoDB userJoinLedger(String uid, String ledgerId);

    UserInfoDB userLeaveLedger(String uid, String ledgerId);

    UserInfoDB deleteUserInfo(String id);
}
