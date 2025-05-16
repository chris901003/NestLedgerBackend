package org.xxooooxx.nestledger.dao.userinfo.interfaces;

import org.xxooooxx.nestledger.to.UserInfoDB;
import org.xxooooxx.nestledger.vo.userinfo.request.UserInfoUpdateRequestData;

public interface UserInfoDao {
    /**
     * Get user info by id
     *
     * @param id user id
     * @return UserInfoDB
     */
    UserInfoDB getUserInfoById(String id);

    UserInfoDB getUserInfoByEmail(String email);

    /**
     * Get user info by user id
     *
     * @param id user id
     * @return UserInfoDB
     */
    UserInfoDB createUserInfo(String id);

    UserInfoDB updateUserInfo(UserInfoUpdateRequestData data) throws IllegalAccessException;
}
