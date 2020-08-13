package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ProductRestful {
    public static void main(String[] args) {
        SpringApplication.run(ProductRestful.class, args);
    }

}
