/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/16.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.userinfo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UserInfoUpdateRequestData {
    @NotBlank(message = "Id 不可為空白")
    private String id;
    private String userName;
    @Email(message = "電子郵件格式錯誤")
    private String emailAddress;
    private String avatar;
    private Integer timeZone;
    private Double imageQuality;
    private ArrayList<String> ledgerIds;
    private Boolean isDelete;
    private Integer version;
}
