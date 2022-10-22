package com.vault.hashicorp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

@Configuration
@PropertySource(value = { "application-vault.yml" })
@Import(value = EnvironmentVaultConfiguration.class)
public class VaultEnvironmentConfig {
}
