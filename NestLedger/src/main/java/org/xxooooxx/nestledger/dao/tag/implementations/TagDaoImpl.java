/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.tag.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.tag.interfaces.TagDao;
import org.xxooooxx.nestledger.to.TagDB;
import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;

@Component
public class TagDaoImpl implements TagDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public TagDB createTag(TagCreateRequestData data) {
        TagDB tagDB = new TagDB();
        tagDB.setLabel(data.getLabel());
        tagDB.setColor(data.getColor());
        tagDB.setLedgerId(data.getLedgerId());
        tagDB.setUsingCount(0);
        tagDB.setVersion(data.getVersion());
        return mongoTemplate.insert(tagDB);
    }
}
