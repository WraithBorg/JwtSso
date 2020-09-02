package com.jsso.wt.mno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jsso.wt")
public class MnoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MnoApplication.class, args);
    }
}
