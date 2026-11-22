# Cómo correr las pruebas (catálogo)

Requisitos: Java 21 y Docker (los tests de integración usan Testcontainers con PostgreSQL).

```bash
./mvnw verify                 # compila, formatea, corre unitarios + integración + cobertura
./mvnw -Dtest=SyncServiceTest test   # una sola clase
```

| Tipo | Dónde | Qué cubre |
|---|---|---|
| Unitarios (`*Test`) | `src/test/java/.../catalog`, `.../sync` | casos de uso, adaptadores, mappers, controladores con puertos simulados |
| Integración (`*IT`) | `ExceptionTranslatorIT`, `TokenAuthenticationIT`, `HibernateTimeZoneIT` | seguridad JWT y base real |
| Arquitectura | `TechnicalStructureTest` (ArchUnit) | que ninguna capa viole las dependencias |

- La configuración de test (`src/test/resources/config/application.yml`) tiene valores fijos: no depende de variables de entorno.
- Cobertura: JaCoCo, informe en `target/site/jacoco`. SonarQube Cloud exige ≥ 80 % en código nuevo.
