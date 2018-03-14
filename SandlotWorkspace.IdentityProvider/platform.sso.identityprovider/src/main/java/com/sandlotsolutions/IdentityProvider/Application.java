package com.sandlotsolutions.IdentityProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import com.sandlotsolutions.IdentityProvider.Model.LDAPConnectionManager;

@SpringBootApplication
@EnableConfigurationProperties(LDAPConnectionManager.class)
@ImportResource("spring-config.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
