package com.nttdata.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Aplicación principal del microservicio Account Service
 */
@SpringBootApplication(scanBasePackages = {
    "com.nttdata.account",
    "com.nttdata.common",
    "com.nttdata.shared"
})
@EnableKafka
public class AccountServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
