/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.userinfo.response;

import lombok.Data;
import org.xxooooxx.nestledger.to.UserInfoDB;

import java.util.ArrayList;

@Data
public class UserInfoGetResponse {
    private String id;
    private String userName;
    private String emailAddress;
    private String avatar;
    private Integer timeZone;
    private Double imageQuality;
    private ArrayList<String> ledgerIds;
    private Boolean isDelete;
    private Integer version;

    public UserInfoGetResponse(UserInfoDB userInfoDB) {
        this.id = userInfoDB.getId();
        this.userName = userInfoDB.getUserName();
        this.emailAddress = userInfoDB.getEmailAddress();
        this.avatar = userInfoDB.getAvatar();
        this.timeZone = userInfoDB.getTimeZone();
        this.imageQuality = userInfoDB.getImageQuality();
        this.ledgerIds = userInfoDB.getLedgerIds();
        this.isDelete = userInfoDB.getIsDelete();
        this.version = userInfoDB.getVersion();
    }
}
