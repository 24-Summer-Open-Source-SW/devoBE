package me.jaykim.devo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevoApplication {
    public static void main(String[] args){
        SpringApplication.run(DevoApplication.class, args);
    }
}
