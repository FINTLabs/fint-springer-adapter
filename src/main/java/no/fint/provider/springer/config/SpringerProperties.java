package no.fint.provider.springer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "fint.springer")
public class SpringerProperties {
    /**
     * Whether to clean the database before loading initial data on startup
     */
    private boolean cleanOnStartup = false;
}
