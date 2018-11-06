package no.fint.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 * Adding first class support for Swarm and Kubernetes Secrets in Spring Boot configuration
 * and property source hierarchy. This way any Spring Boot applications can read properties
 * from Docker Swarm/Kubernetes secrets.
 *
 * <h3>Implementation Details</h3>
 * Currently we only support Swarm style secrets file. By default when Swarm manager exposes a
 * secret to a service, it would be mounted under {@code /run/secrets/{secret_name}} file. With
 * this arrangement, each file in the {@code /run/secrets} directory would be the property name
 * and its content would be the property value.
 *
 * <a href="https://docs.docker.com/engine/swarm/secrets/">Swarm Secrets</a>
 *
 * @see EnvironmentPostProcessor
 * @see SystemEnvironmentPropertySource
 *
 * @author Ali Dehghani
 */
public class ContainerSecretsAwareEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(ContainerSecretsAwareEnvironmentPostProcessor.class);

    /**
     * This property allows to customize the default {@code /run/secrets/} directory for
     * container property lookups.
     */
    private static final String SECRETS_DIR_PROPERTY = "container.secrets-dir";

    /**
     * The default directory inside the container to lookup for secrets that pushed by
     * the container scheduler.
     */
    private static final String DEFAULT_SECRET_DIR = "/run/secrets/";

    /**
     * Scans the secrets directory and for each file found there:
     * <pre>
     *     for each_file in secrets_dir:
     *         addProperty(filename, fileContent)
     * </pre>
     *
     * @param environment The Spring environment to customize
     * @param application The Spring Boot bootstrapper
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String secretsDir = environment.getProperty(SECRETS_DIR_PROPERTY);
        if (secretsDir == null || secretsDir.trim().isEmpty()) secretsDir = DEFAULT_SECRET_DIR;

        log.info("About to read container secrets from {}", secretsDir);
        Map<String, Object> readProperties = readFromSharedSecrets(secretsDir);

        if (!readProperties.isEmpty()) {
            log.info("Adding {} secrets from container scheduler", readProperties.size());

            SystemEnvironmentPropertySource propertySource =
                    new SystemEnvironmentPropertySource("container-secrets", readProperties);

            // A property with lowest possible priority
            environment.getPropertySources().addLast(propertySource);
        }
    }

    /**
     * Scans the {@code secretsDir} directory and for each file, add a property named after the
     * file paired with its content as the property value.
     *
     * @param secretsDir Represents the secrets directory
     * @return Map of property names and property values.
     */
    private Map<String, Object> readFromSharedSecrets(String secretsDir) {
        Path directory;
        try {
            directory = Paths.get(secretsDir);
        } catch (Exception e) {
            log.warn("The provided container secrets directory was invalid: {}", secretsDir);
            return Collections.emptyMap();
        }

        if (!Files.exists(directory)) {
            log.warn("The provided container secrets directory not found: {}", secretsDir);
            return Collections.emptyMap();
        }

        try {
            return Files.list(directory)
                    .filter(this::isFile)
                    .collect(toMap(this::filename, this::content));
        } catch (Exception e) {
            log.warn("Failed to read secrets from secrets directory", e);
            return Collections.emptyMap();
        }
    }

    /**
     * @param path The path to inspect
     * @return Is the given {@code path} represents a file?
     */
    private boolean isFile(Path path) {
        return path.toFile().isFile();
    }

    /**
     * @param path The path to extract its filename
     * @return The filename
     */
    private String filename(Path path) {
        return path.getFileName().toString();
    }

    /**
     * @param path Represents a path to a file
     * @return The corresponding file content
     */
    private String content(Path path) {
        try {
            return Files.lines(path).filter(line -> !line.trim().isEmpty()).collect(joining());
        } catch (IOException e) {
            log.warn("Failed to read the secret file", e);
            return "";
        }
    }
}