package com.example.studentcoursedemoproject.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("storage")
public class StorageProperties {

    private final String filesDir = "/iwp-service";
    private final String profileImageDir = "profile-images";
    private final String host = "103.4.145.245";
    private final Integer port = 62233;
    private final String username = "IEIMS";
    private final String password = "IEIMS@dev#123";

}
