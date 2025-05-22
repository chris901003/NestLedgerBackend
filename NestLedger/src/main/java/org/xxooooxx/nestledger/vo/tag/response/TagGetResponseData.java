/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.tag.response;

import lombok.Data;
import org.xxooooxx.nestledger.to.TagDB;

@Data
public class TagGetResponseData {
    private String _id;
    private String label;
    private String color;
    private String ledgerId;
    private Integer usingCount;
    private Integer version;

    public TagGetResponseData(TagDB data) {
        this._id = data.get_id();
        this.label = data.getLabel();
        this.color = data.getColor();
        this.ledgerId = data.getLedgerId();
        this.usingCount = data.getUsingCount();
        this.version = data.getVersion();
    }
}
