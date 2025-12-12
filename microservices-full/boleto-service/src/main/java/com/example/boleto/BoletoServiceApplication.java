package com.example.boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoletoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoletoServiceApplication.class, args);
    }
}
