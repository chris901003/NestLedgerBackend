package org.xxooooxx.nestledger.dao.userinfo.interfaces;

import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

import java.util.List;

public interface UserInfoDao {
    UserInfoDB getUserInfoById(String id);

    UserInfoDB getUserInfoByEmail(String email);

    List<UserInfoDB> getMultipleUserInfoById(List<String> ids);

    UserInfoDB createUserInfo(String id);

    UserInfoDB updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException;

    void updateUserEmail(String id, String email);

    UserInfoDB changeQuickLogLedger(String uid, String ledgerId);

    UserInfoDB userJoinLedger(String uid, String ledgerId);

    UserInfoDB userLeaveLedger(String uid, String ledgerId);

    UserInfoDB deleteUserInfo(String id);
}
