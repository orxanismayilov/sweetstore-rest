package com.orxan.sweetstorerest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SpringBootApplication
public class SweetstoreRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SweetstoreRestApplication.class, args);
    }

}
