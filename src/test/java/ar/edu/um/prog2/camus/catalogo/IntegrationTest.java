package ar.edu.um.prog2.camus.catalogo;

import ar.edu.um.prog2.camus.catalogo.config.AsyncSyncConfiguration;
import ar.edu.um.prog2.camus.catalogo.config.DatabaseTestcontainer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        CatalogoApp.class,
        AsyncSyncConfiguration.class,
        ar.edu.um.prog2.camus.catalogo.config.JacksonHibernateConfiguration.class,
        DatabaseTestcontainer.class,
    }
)
public @interface IntegrationTest {}
