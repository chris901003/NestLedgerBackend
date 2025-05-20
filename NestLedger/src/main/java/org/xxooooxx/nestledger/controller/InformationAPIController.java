/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/20.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxooooxx.nestledger.common.Response;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/information")
public class InformationAPIController {

    @GetMapping("/basic")
    public Response<Map<String, String>> getBasicInformation() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("author", "Zephyr-Huang");
        map.put("contactUs", "service@xxooooxx.org");
        map.put("copyright", "Copyright © 2025 Zephyr-Huang.");
        return Response.success(map);
    }
}
