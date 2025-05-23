/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/18.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.service.tag.interfaces.TagService;
import org.xxooooxx.nestledger.vo.tag.request.TagCreateRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagQueryRequestData;
import org.xxooooxx.nestledger.vo.tag.request.TagUpdateRequestData;
import org.xxooooxx.nestledger.vo.tag.response.TagGetResponseData;

import java.util.List;

@RestController
@RequestMapping("/v1/tag")
public class TagAPIController {

    @Autowired
    private TagService tagService;

    @PostMapping("/create")
    public Response<TagGetResponseData> createTag(@RequestBody @Valid TagCreateRequestData data) {
        TagGetResponseData tag = tagService.createTag(data);
        return Response.success(tag);
    }

    @GetMapping("/get")
    public Response<TagGetResponseData> getTag(@RequestParam String tagId) {
        TagGetResponseData tag = tagService.getTag(tagId);
        return Response.success(tag);
    }

    @PostMapping("/query")
    public Response<List<TagGetResponseData>> queryTag(@RequestBody @Valid TagQueryRequestData data) {
        List<TagGetResponseData> tags = tagService.queryTags(data);
        return Response.success(tags);
    }

    @PatchMapping("/update")
    public Response<TagGetResponseData> updateTag(@RequestBody @Valid TagUpdateRequestData data) {
        TagGetResponseData tag = tagService.updateTag(data);
        return Response.success(tag);
    }

    @DeleteMapping("/delete")
    public Response<Void> deleteTag(@RequestParam String tagId) {
        tagService.deleteTag(tagId);
        return Response.success(null);
    }
}
