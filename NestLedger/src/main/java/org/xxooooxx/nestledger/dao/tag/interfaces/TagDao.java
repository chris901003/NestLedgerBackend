/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.dao.tag.interfaces;

import org.xxooooxx.nestledger.to.TagDB;
import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagQueryRequestData;

import java.util.List;

public interface TagDao {
    TagDB createTag(TagCreateRequestData data);
    TagDB getTag(String id);
    List<TagDB> queryTags(TagQueryRequestData data);
    void incrementTagUsingCount(String id, int count);
}
