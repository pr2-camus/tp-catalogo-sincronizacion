package ar.edu.um.prog2.camus.catalogo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({
    "ar.edu.um.prog2.camus.catalogo.repository",
    "ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository",
    "ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository",
})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {}
