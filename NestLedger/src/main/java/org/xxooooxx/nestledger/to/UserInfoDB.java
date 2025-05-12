/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/12.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.to;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document("userinfos")
@Data
public class UserInfoDB {
    @Id
    private String _id;
    private String id;
    private String userName;
    private String emailAddress;
    private String avatar;
    private Integer timeZone;
    private Double imageQuality;
    private ArrayList<String> ledgerIds;
    private Boolean isDelete;
    private Integer version;
}
