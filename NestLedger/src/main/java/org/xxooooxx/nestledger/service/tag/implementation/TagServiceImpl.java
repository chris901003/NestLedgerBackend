/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.service.tag.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxooooxx.nestledger.dao.ledger.interfaces.LedgerDao;
import org.xxooooxx.nestledger.dao.tag.interfaces.TagDao;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.service.tag.interfaces.TagService;
import org.xxooooxx.nestledger.to.LedgerDB;
import org.xxooooxx.nestledger.to.TagDB;
import org.xxooooxx.nestledger.utility.UserContext;
import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagQueryRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagUpdateRequestData;
import org.xxooooxx.nestledger.vo.tag.response.TagGetResponseData;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private LedgerDao ledgerDao;

    public TagGetResponseData createTag(TagCreateRequestData data) {
        checkOperationValid(data.getLedgerId());
        TagDB tagDB = tagDao.createTag(data);
        return new TagGetResponseData(tagDB);
    }

    public TagGetResponseData getTag(String id) {
        TagDB tagDB = tagDao.getTag(id);
        if (tagDB == null) {
            throw new CustomException(CustomExceptionEnum.TAG_NOT_FOUND);
        }
        checkOperationValid(tagDB.getLedgerId());
        return new TagGetResponseData(tagDB);
    }

    public List<TagGetResponseData> queryTags(TagQueryRequestData data) {
        if (data.getLedgerId() != null) {
            checkOperationValid(data.getLedgerId());
        }
        List<TagDB> tagDBs = tagDao.queryTags(data);
        return tagDBs.stream()
                .map(TagGetResponseData::new)
                .toList();
    }

    public TagGetResponseData updateTag(TagUpdateRequestData data) {
        TagDB tagDB = tagDao.getTag(data.get_id());
        if (tagDB == null) {
            throw new CustomException(CustomExceptionEnum.TAG_NOT_FOUND);
        }
        checkOperationValid(tagDB.getLedgerId());

        try {
            tagDB = tagDao.updateTag(data);
        } catch (IllegalAccessException e) {
            throw new CustomException(CustomExceptionEnum.TAG_UPDATE_FAILED);
        }
        return new TagGetResponseData(tagDB);
    }

    public void deleteTag(String tagId) {
        TagDB tagDB = tagDao.getTag(tagId);
        if (tagDB == null) {
            throw new CustomException(CustomExceptionEnum.TAG_NOT_FOUND);
        }
        if (tagDB.getUsingCount() > 0) {
            throw new CustomException(CustomExceptionEnum.TAG_STILL_IN_USE);
        }
        checkOperationValid(tagDB.getLedgerId());
        tagDao.deleteTag(tagId);
    }

    private void checkOperationValid(String ledgerId) {
        LedgerDB ledger = ledgerDao.getLedger(ledgerId);
        if (ledger == null) {
            throw new CustomException(CustomExceptionEnum.TAG_REFERENCE_LEDGER_NOT_FOUND);
        }

        String userId = UserContext.getUid();
        if (!ledger.getUserIds().contains(userId)) {
            throw new CustomException(CustomExceptionEnum.UNAUTHORIZED_OPERATION_TAG);
        }
    }
}
