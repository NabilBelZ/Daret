package org.gestion.daret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DaretApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaretApplication.class, args);
    }

}
