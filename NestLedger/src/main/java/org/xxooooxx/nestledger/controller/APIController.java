package org.xxooooxx.nestledger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xxooooxx.nestledger.common.Response;

@Controller
public class APIController {

    @RequestMapping("/hello")
    @ResponseBody
    public Response<String> hello() {
        return Response.success("Hello world!");
    }
}
