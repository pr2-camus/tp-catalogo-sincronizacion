package ar.edu.um.prog2.camus.catalogo.security.jwt;

import ar.edu.um.prog2.camus.catalogo.config.SecurityConfiguration;
import ar.edu.um.prog2.camus.catalogo.config.SecurityJwtConfiguration;
import ar.edu.um.prog2.camus.catalogo.config.WebConfigurer;
import ar.edu.um.prog2.camus.catalogo.management.SecurityMetersService;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import tech.jhipster.config.JHipsterProperties;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        JHipsterProperties.class,
        WebConfigurer.class,
        SecurityConfiguration.class,
        SecurityJwtConfiguration.class,
        SecurityMetersService.class,
        JwtAuthenticationTestUtils.class,
    }
)
public @interface AuthenticationIntegrationTest {}
