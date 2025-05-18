package org.xxooooxx.nestledger.service.tag.interfaces;

import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;
import org.xxooooxx.nestledger.vo.tag.response.TagGetResponseData;

public interface TagService {
    TagGetResponseData createTag(TagCreateRequestData data);
}
