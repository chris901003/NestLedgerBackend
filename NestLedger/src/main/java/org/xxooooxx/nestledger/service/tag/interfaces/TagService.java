package org.xxooooxx.nestledger.service.tag.interfaces;

import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagQueryRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagUpdateRequestData;
import org.xxooooxx.nestledger.vo.tag.response.TagGetResponseData;

import java.util.List;

public interface TagService {
    TagGetResponseData createTag(TagCreateRequestData data);
    TagGetResponseData getTag(String id);
    List<TagGetResponseData> queryTags(TagQueryRequestData data);
    TagGetResponseData updateTag(TagUpdateRequestData data);
    void deleteTag(String tagId);
}
