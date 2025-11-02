package com.skillswapplatform.user.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "users")
public class UserProperties {

    // users.defaultUser.username
    // users.defaultUser.password
    private DefaultUser defaultUser;
    // users.testProperty
    private String testProperty;

    @Data
    public static class DefaultUser {

        private String username;

        private String password;
    }

//    @PostConstruct
//    public void test() {
//        System.out.println();
//    }
}