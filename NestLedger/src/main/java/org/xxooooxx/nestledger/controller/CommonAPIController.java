package org.xxooooxx.nestledger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxooooxx.nestledger.common.Response;

@RestController
public class CommonAPIController {
    @RequestMapping("/")
    public String root() {
        return "<html><head><title>Nest Ledger Backend API</title></head>" +
                "<body><h1>Welcome to Nest Ledger Backend API</h1>" +
                "<p>This is a simple API for Nest Ledger.</p></body></html>";
    }
}
