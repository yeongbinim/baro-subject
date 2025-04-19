package nbc.subjectbaro.domain.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    Secret secret,
    Token token
) {

    public record Secret(
        String key
    ) {

    }

    public record Token(
        String prefix,
        long expiration
    ) {

    }
}
