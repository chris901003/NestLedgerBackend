package org.xxooooxx.nestledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NestLedgerApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(NestLedgerApplication.class, args);
        System.out.println("ioc = " + ioc);
    }
}
