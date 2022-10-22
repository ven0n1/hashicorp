package com.vault.hashicorp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("datamart")
@Getter
@Setter
public class DatamartProperties {
    String url;
    String username;
    String password;
}
