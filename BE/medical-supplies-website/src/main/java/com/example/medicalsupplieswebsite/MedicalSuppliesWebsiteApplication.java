package com.example.medicalsupplieswebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MedicalSuppliesWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalSuppliesWebsiteApplication.class, args);
    }
}
