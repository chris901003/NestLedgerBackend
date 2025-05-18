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

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.xxooooxx.nestledger.dao.tag.interfaces.TagDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.to.TagDB;
import org.xxooooxx.nestledger.utility.MongoDbUpdateUtility;
import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagQueryRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagUpdateRequestData;

import java.util.ArrayList;
import java.util.List;

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

    public TagDB getTag(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), TagDB.class);
    }

    public List<TagDB> queryTags(TagQueryRequestData data) {
        List<Criteria> criteria = new ArrayList<>();
        if (data.getLedgerId() != null) {
            criteria.add(Criteria.where("ledgerId").is(data.getLedgerId()));
        }
        if (data.getSearch() != null) {
            criteria.add(Criteria.where("label").regex(data.getSearch()));
        }
        if (data.getTagId() != null) {
            criteria.add(Criteria.where("_id").is(data.getTagId()));
        }

        if (!criteria.isEmpty()) {
            Query query = new Query(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            query.skip((long) (data.getPage() - 1) * data.getLimit());
            query.limit(data.getLimit());
            return mongoTemplate.find(query, TagDB.class);
        } else {
            return mongoTemplate.findAll(TagDB.class);
        }
    }

    public TagDB updateTag(TagUpdateRequestData data) throws IllegalAccessException {
        Query query = Query.query(Criteria.where("_id").is(data.get_id()));
        Update update = MongoDbUpdateUtility.getFullUpdate(data);

        if (!update.getUpdateObject().isEmpty()) {
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
            return mongoTemplate.findAndModify(query, update, options, TagDB.class);
        }
        return null;
    }

    public void incrementTagUsingCount(String id, int count) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update().inc("usingCount", count);
        UpdateResult result = mongoTemplate.updateFirst(query, update, TagDB.class);
        if (result.getModifiedCount() == 0) {
            throw new CustomException(CustomExceptionEnum.TAG_NOT_FOUND);
        }
    }
}
