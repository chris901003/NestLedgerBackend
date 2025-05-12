package org.xxooooxx.nestledger.dao.userinfo.interfaces;

import org.xxooooxx.nestledger.to.UserInfoDB;

public interface UserInfoDao {
    /**
     * Get user info by id
     *
     * @param id user id
     * @return UserInfoDB
     */
    UserInfoDB getUserInfoById(String id);

    /**
     * Get user info by user id
     *
     * @param id user id
     * @return UserInfoDB
     */
    UserInfoDB createUserInfo(String id);
}
