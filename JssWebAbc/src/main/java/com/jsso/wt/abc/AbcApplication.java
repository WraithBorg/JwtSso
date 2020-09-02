package com.jsso.wt.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jsso.wt")
public class AbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(AbcApplication.class, args);
    }
}
