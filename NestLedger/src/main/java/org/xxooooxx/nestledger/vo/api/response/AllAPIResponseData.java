/*
 * =============================================================================
 * Created by Zephyr-Huang on 2025/5/15.
 * Copyright © 2025 Zephyr-Huang. All rights reserved.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * =============================================================================
 */
package org.xxooooxx.nestledger.vo.api.response;

import lombok.Data;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import org.xxooooxx.nestledger.controller.LedgerAPIController;
import org.xxooooxx.nestledger.controller.TagAPIController;
import org.xxooooxx.nestledger.controller.TransactionAPIController;
import org.xxooooxx.nestledger.controller.UserInfoAPIController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AllAPIResponseData {
    private ArrayList<APIResponseData> userAPI;
    private ArrayList<APIResponseData> ledgerAPI;
    private ArrayList<APIResponseData> tagAPI;
    private ArrayList<APIResponseData> transactionAPI;
    private ArrayList<APIResponseData> ledgerInviteAPI;

    public AllAPIResponseData() {
        // User API
        List<Class<?>> userInfoControllers = new ArrayList<>();
        userInfoControllers.add(UserInfoAPIController.class);
        this.userAPI = ExtractAPIController(userInfoControllers);

        // Ledger API
        List<Class<?>> ledgerControllers = new ArrayList<>();
        ledgerControllers.add(LedgerAPIController.class);
        this.ledgerAPI = ExtractAPIController(ledgerControllers);

        // Tag API
        List<Class<?>> tagControllers = new ArrayList<>();
        tagControllers.add(TagAPIController.class);
        this.tagAPI = ExtractAPIController(tagControllers);

        // Transaction API
        List<Class<?>> transactionControllers = new ArrayList<>();
        transactionControllers.add(TransactionAPIController.class);
        this.transactionAPI = ExtractAPIController(transactionControllers);

        // Ledger Invite API
        List<Class<?>> ledgerInviteControllers = new ArrayList<>();
        ledgerInviteControllers.add(LedgerAPIController.class);
        this.ledgerInviteAPI = ExtractAPIController(ledgerInviteControllers);
    }

    private ArrayList<APIResponseData> ExtractAPIController(List<Class<?>> controllers) {
        ArrayList<APIResponseData> result = new ArrayList<>();
        Map<Pair<String, String>, List<String>> apiMap = new HashMap<>();

        for (Class<?> controller: controllers) {
            RequestMapping classRequestMapping = controller.getAnnotation(RequestMapping.class);
            String basePath = classRequestMapping.value()[0];
            String version = basePath.substring(1, basePath.indexOf("/", 1));
            for (Method method: controller.getDeclaredMethods()) {
                for (Annotation annotation: method.getAnnotations()) {
                    if (annotation instanceof GetMapping getMapping) {
                        String path = basePath + getMapping.value()[0];
                        apiMap.put(Pair.of(path, "GET"), List.of(version));
                    } else if (annotation instanceof PostMapping) {
                        String path = basePath + ((PostMapping) annotation).value()[0];
                        apiMap.put(Pair.of(path, "POST"), List.of(version));
                    } else if (annotation instanceof PutMapping) {
                        String path = basePath + ((PutMapping) annotation).value()[0];
                        apiMap.put(Pair.of(path, "PUT"), List.of(version));
                    } else if (annotation instanceof PatchMapping) {
                        String path = basePath + ((PatchMapping) annotation).value()[0];
                        apiMap.put(Pair.of(path, "PATCH"), List.of(version));
                    } else if (annotation instanceof DeleteMapping) {
                        String path = basePath + ((DeleteMapping) annotation).value()[0];
                        apiMap.put(Pair.of(path, "DELETE"), List.of(version));
                    }
                }
            }
        }

        for (Map.Entry<Pair<String, String>, List<String>> entry : apiMap.entrySet()) {
            Pair<String, String> key = entry.getKey();
            String path = key.getFirst();
            String method = key.getSecond();
            List<String> versions = entry.getValue();
            result.add(new APIResponseData(path, method, versions.toArray(new String[0])));
        }
        return result;
    }
}
