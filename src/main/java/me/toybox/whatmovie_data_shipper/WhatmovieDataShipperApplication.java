package me.toybox.whatmovie_data_shipper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhatmovieDataShipperApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WhatmovieDataShipperApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
