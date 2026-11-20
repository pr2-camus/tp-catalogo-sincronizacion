<!-- evaluacion:inicio -->
# Evaluación — pr2-camus/tp-catalogo-sincronizacion

> Documento **generado automáticamente** por la cátedra de Programación II (UM): RepoSense + análisis de calidad de **7 dimensiones**. **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas) + build: es una criba para orientar la revisión, **no una nota**. La **documentación** del repositorio no se evalúa.
> Generado: 2026-11-20.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad | **54/100** |
| Tipo de repositorio | `backend` |
| Build (`mvnw verify` / `gradlew check`) | OK |
| Código (LOC) | 5,483 |
| Archivos de código | 167 |
| Clases | 145 |
| Código dentro de clases (%POO) | 75% |
| Funciones fuera de clases | 0 |
| Duplicación (puerta) | 9.1% |
| Duplicación lógica | 9.8% |
| Duplicación de boilerplate | 1.8% |
| Cobertura | 53% |
| Tope duro aplicado a D1 | `POO_BAJO` → D1 ≤ 60 |

> Este informe **describe** lo que el análisis midió con los criterios ya establecidos de la materia. No es una nota.

## 2. Semáforo de aprobación

Criterios de la cátedra (`CRITERIOS-CALIDAD.md`): **Aprobar** se juega con el perfil `estandar`; **destacar**, con el `exigente` (meta de excelencia).

| Semáforo | Resultado |
|---|---|
| Aprobar (`estandar`) | **no cumple** — 4/11 |
| Destacar (`exigente`) | **no cumple** — 3/11 |

**Para aprobar, falta cumplir:** SOLID; Hexagonal; Duplicación; Cobertura

**Para destacar, falta cumplir:** SOLID; POO; Hexagonal; Duplicación; Cobertura

## 3. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| zoecamus | 1 | 0 | 0 | 0 | 11 | 0% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis (que mira el estado de `main`)._

## 4. Dimensiones evaluadas

Cada dimensión indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el puntaje queda por debajo de la referencia, se agregan preguntas para **investigar la causa** (no hay recetas).

| Dimensión | Peso | Puntaje |
|:--|--:|--:|
| **D1** Diseño OO, SOLID y patrones (20%) | 20% | 4 |
| **D2** Arquitectura hexagonal — backend (15%) | 15% | 0 |
| **D3** Frontend KMP: MVVM y buenas prácticas (10%) | 10% | N/A |
| **D4** Integración y contrato (10%) | 10% | 80 |
| **D5** Seguridad y robustez (10%) | 10% | 100 |
| **D6** Pruebas y cobertura (20%) | 20% | 76 |
| **D7** Proceso y entrega (15%) | 15% | 100 |

### D1. Diseño OO, SOLID y patrones (20%)

- **Qué mide:** Métricas orientadas a objetos (clases, métodos, campos, cohesión, acoplamiento, herencia) y proxies de SOLID, más patrones de diseño.
- **Por qué importa:** El eje de la materia es el buen uso de la POO y su arquitectura: un diseño con clases claras y poco acopladas es más fácil de entender, probar y cambiar.
- **Tu resultado:** **4/100**
- **Para investigar la causa:**
    - ¿Se pensó el modelo de clases antes de codificar o se agrupó después?
    - ¿Qué clases concentran muchas responsabilidades a la vez?
    - ¿Los colaboradores llegan como parámetros o se crean por dentro?

### D2. Arquitectura hexagonal — backend (15%)

- **Qué mide:** Separación en capas (dominio, puertos de entrada/salida, adaptadores) y dirección de las dependencias del backend.
- **Por qué importa:** Aislar el dominio de la infraestructura permite cambiar la base de datos, el framework o el transporte sin tocar las reglas de negocio.
- **Tu resultado:** **0/100**
- **Para investigar la causa:**
    - ¿El dominio importa framework o detalles de persistencia?
    - ¿Dónde vive la lógica de negocio frente a los controladores?
    - ¿Se definieron puertos para los servicios que se consumen?

### D4. Integración y contrato (10%)

- **Qué mide:** Cumplimiento del contrato de integración de la cátedra (formato de errores, paginación, autenticación, endpoints esperados).
- **Por qué importa:** Los servicios deben poder integrarse entre sí y con la app sin sorpresas; el contrato es lo que hacen cumplir las pruebas de integración.
- **Tu resultado:** **80/100**

### D5. Seguridad y robustez (10%)

- **Qué mide:** Credenciales embebidas, consultas SQL por concatenación, y presencia de config de entorno; aislamiento de secretos.
- **Por qué importa:** Un secreto en el repositorio o una consulta construida por concatenación son riesgos concretos, no hipotéticos.
- **Tu resultado:** **100/100**

### D6. Pruebas y cobertura (20%)

- **Qué mide:** Cantidad y resultado de pruebas y cobertura de línea (JaCoCo/Kover, Surefire/Failsafe) cuando hay build.
- **Por qué importa:** Las pruebas son la red de seguridad para refactorizar e integrar; la cobertura acota las zonas sin verificar.
- **Tu resultado:** **76/100**

### D7. Proceso y entrega (15%)

- **Qué mide:** Artefactos verificables de proceso/entrega: `compose`, `.env.example`, CI (`.github/workflows/`), `CODEOWNERS` y wrapper de build (`mvnw`/`gradlew`). **No** puntúa documentación.
- **Por qué importa:** Un proyecto que compila, se levanta y se valida solo es más fácil de revisar y de integrar en equipo.
- **Tu resultado:** **100/100**

## 5. Señales detectadas

El análisis encontró las siguientes señales, con el archivo y la línea donde se originan cuando están disponibles:

**Archivos con más señales**

| Archivo | Señales |
|---|---:|
| `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java` | 10 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java` | 10 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java` | 10 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java` | 7 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java` | 6 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/security/SecurityUtils.java` | 3 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/management/SecurityMetersService.java` | 3 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/config/SecurityConfiguration.java` | 3 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/CatalogoApp.java` | 2 |
| `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/RunFullSyncUseCaseImpl.java` | 2 |

**DIP/concretos** (26)
- clase 'CatalogoApp' muy acoplada (CBO=24) — `src/main/java/ar/edu/um/prog2/camus/catalogo/CatalogoApp.java:24`
- clase 'LoggingAspect' muy acoplada (CBO=24) — `src/main/java/ar/edu/um/prog2/camus/catalogo/aop/logging/LoggingAspect.java:22`
- clase 'CatalogoService' muy acoplada (CBO=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/application/service/CatalogoService.java:15`
- clase 'CatalogoRepositoryAdapter' muy acoplada (CBO=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/adapter/CatalogoRepositoryAdapter.java:16`
- clase 'JpaProfessionalRepository' muy acoplada (CBO=14) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/repository/JpaProfessionalRepository.java:9`
- clase 'ProfessionalController' muy acoplada (CBO=11) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/web/controller/ProfessionalController.java:21`
- clase 'CatalogoDtoMapper' muy acoplada (CBO=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/web/mapper/CatalogoDtoMapper.java:14`
- clase 'CatalogoDtoMapper' instancia 4 concretos (CBO=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/web/mapper/CatalogoDtoMapper.java:14`
- clase 'AsyncConfiguration' muy acoplada (CBO=12) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/AsyncConfiguration.java:22`
- clase 'AsyncConfiguration' instancia 3 concretos (CBO=12) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/AsyncConfiguration.java:22`
- clase 'CRLFLogConverter' muy acoplada (CBO=16) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/CRLFLogConverter.java:23`
- clase 'LiquibaseConfiguration' muy acoplada (CBO=18) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/LiquibaseConfiguration.java:22`
- clase 'SecurityConfiguration' muy acoplada (CBO=18) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/SecurityConfiguration.java:23`
- clase 'SecurityConfiguration' instancia 4 concretos (CBO=18) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/SecurityConfiguration.java:23`
- clase 'SecurityJwtConfiguration' muy acoplada (CBO=19) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/SecurityJwtConfiguration.java:21`
- clase 'WebConfigurer' muy acoplada (CBO=13) — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/WebConfigurer.java:21`
- clase 'SecurityUtils' muy acoplada (CBO=15) — `src/main/java/ar/edu/um/prog2/camus/catalogo/security/SecurityUtils.java:18`
- clase 'RunFullSyncUseCaseImpl' muy acoplada (CBO=12) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/RunFullSyncUseCaseImpl.java:14`
- clase 'SincronizarAlArrancarUseCaseImpl' muy acoplada (CBO=9) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SincronizarAlArrancarUseCaseImpl.java:12`
- clase 'SyncToLatestUseCaseImpl' muy acoplada (CBO=24) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:23`
- clase 'SyncToLatestUseCaseImpl' instancia 4 concretos (CBO=24) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:23`
- clase 'SnapshotRestAdapter' muy acoplada (CBO=11) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:16`
- clase 'SnapshotRestAdapter' instancia 4 concretos (CBO=11) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:16`
- clase 'CatalogUpdatedListener' muy acoplada (CBO=9) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/messaging/CatalogUpdatedListener.java:15`
- clase 'BadRequestAlertException' muy acoplada (CBO=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/BadRequestAlertException.java:11`
- clase 'ExceptionTranslator' muy acoplada (CBO=75) — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:51`

**SRP/responsabilidad** (48)
- método 'CatalogoApp.logApplicationStartup' de 35 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/CatalogoApp.java:74`
- clase 'LoggingAspect' grande (LOC=100, métodos=15, campos=1) — `src/main/java/ar/edu/um/prog2/camus/catalogo/aop/logging/LoggingAspect.java:22`
- método 'BuscarProfesionalesUseCaseImpl.buscar' con complejidad 6 — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/application/usecases/BuscarProfesionalesUseCaseImpl.java:22`
- clase 'CategoriaProfesional' grande (LOC=45, métodos=13, campos=6) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/domain/model/CategoriaProfesional.java:5`
- clase 'CategoriaProfesional' con baja cohesión (8%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/domain/model/CategoriaProfesional.java:5`
- clase 'HorarioSemanal' grande (LOC=66, métodos=19, campos=9) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/domain/model/HorarioSemanal.java:5`
- clase 'HorarioSemanal' con baja cohesión (5%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/domain/model/HorarioSemanal.java:5`
- clase 'Profesional' grande (LOC=52, métodos=15, campos=7) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/domain/model/Profesional.java:5`
- clase 'Profesional' con baja cohesión (7%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/domain/model/Profesional.java:5`
- clase 'CatalogoRepositoryAdapter' grande (LOC=70, métodos=13, campos=4) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/adapter/CatalogoRepositoryAdapter.java:16`
- clase 'CategoryEntity' grande (LOC=50, métodos=13, campos=6) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:8`
- clase 'CategoryEntity' con baja cohesión (8%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:8`
- clase 'ProfessionalEntity' grande (LOC=59, métodos=15, campos=7) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/ProfessionalEntity.java:8`
- clase 'ProfessionalEntity' con baja cohesión (7%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/ProfessionalEntity.java:8`
- clase 'SyncStateEntity' con baja cohesión (14%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/SyncStateEntity.java:8`
- clase 'WeeklyScheduleEntity' grande (LOC=75, métodos=19, campos=9) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:8`
- clase 'WeeklyScheduleEntity' con baja cohesión (5%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:8`
- método 'CategoriaResponse.CategoriaResponse' con 6 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/web/dto/CategoriaResponse.java:5`
- método 'HorarioSemanalResponse.HorarioSemanalResponse' con 9 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/web/dto/HorarioSemanalResponse.java:5`
- método 'ProfesionalResponse.ProfesionalResponse' con 7 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/web/dto/ProfesionalResponse.java:5`
- método 'LiquibaseConfiguration.liquibase' de 50 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/LiquibaseConfiguration.java:33`
- método 'SecurityConfiguration.filterChain' de 37 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/config/SecurityConfiguration.java:32`
- clase 'AbstractAuditingEntity' con baja cohesión (14%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:23`
- clase 'SecurityMetersService' grande (LOC=35, métodos=6, campos=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/management/SecurityMetersService.java:8`
- clase 'SecurityMetersService' con baja cohesión (27%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/management/SecurityMetersService.java:8`
- clase 'SecurityUtils' grande (LOC=84, métodos=10, campos=2) — `src/main/java/ar/edu/um/prog2/camus/catalogo/security/SecurityUtils.java:18`
- método 'SyncToLatestUseCaseImpl.SyncToLatestUseCaseImpl' con 5 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:33`
- método 'SyncToLatestUseCaseImpl.sincronizarAlDia' de 34 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:48`
- método 'SyncToLatestUseCaseImpl.sincronizarAlDia' con complejidad 6 — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:48`
- método 'SyncToLatestUseCaseImpl.aplicarVersion' de 37 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:88`
- método 'SyncToLatestUseCaseImpl.aplicarVersion' con complejidad 8 — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/SyncToLatestUseCaseImpl.java:88`
- clase 'Snapshot' con baja cohesión (11%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/domain/model/Snapshot.java:8`
- clase 'SnapshotRestAdapter' grande (LOC=100, métodos=16, campos=1) — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:16`
- método 'SnapshotRestAdapter.mapToDomain' de 54 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:34`
- método 'SnapshotRestAdapter.CategoriaDto' con 6 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:104`
- método 'CategoriaDto.CategoriaDto' con 6 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:104`
- método 'SnapshotRestAdapter.ProfesionalDto' con 7 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:107`
- método 'ProfesionalDto.ProfesionalDto' con 7 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:107`
- método 'SnapshotRestAdapter.HorarioDto' con 9 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:118`
- método 'HorarioDto.HorarioDto' con 9 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/infrastructure/client/SnapshotRestAdapter.java:118`
- clase 'ExceptionTranslator' grande (LOC=219, métodos=26, campos=8) — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:51`
- clase 'ExceptionTranslator' con baja cohesión (1%) — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:51`
- método 'ExceptionTranslator.handleExceptionInternal' con 5 parámetros — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:80`
- método 'ExceptionTranslator.customizeProblem' de 30 líneas — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:108`
- método 'ExceptionTranslator.customizeProblem' con complejidad 10 — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:108`
- método 'ExceptionTranslator.getCustomizedErrorDetails' con complejidad 6 — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:218`
- método 'ExceptionTranslator.getMappedStatus' con complejidad 6 — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:228`
- método 'ExceptionTranslator.resolveCode' con complejidad 13 — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:283`

**LSP/overrides** (1)
- 'RunFullSyncUseCaseImpl.sincronizarIncremental' vacío o 'no implementado' — `src/main/java/ar/edu/um/prog2/camus/catalogo/sync/application/usecases/RunFullSyncUseCaseImpl.java:52`

**OCP/type-switch** (2)
- clase 'SecurityUtils' con 6 type-checks — `src/main/java/ar/edu/um/prog2/camus/catalogo/security/SecurityUtils.java:18`
- clase 'ExceptionTranslator' con 26 type-checks — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ExceptionTranslator.java:51`

**?** (1)
- clase 'ProfesionalNoEncontradoException' con herencia profunda (DIT=2) — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/application/exception/ProfesionalNoEncontradoException.java:6`

**POO** (2)
- clase 'SecurityMetersService' con 4 campos públicos (encapsulación) — `src/main/java/ar/edu/um/prog2/camus/catalogo/management/SecurityMetersService.java:8`
- clase 'ErrorConstants' con 5 campos públicos (encapsulación) — `src/main/java/ar/edu/um/prog2/camus/catalogo/web/rest/errors/ErrorConstants.java:5`

**duplicación** (6)
- duplicación lógica: 9.1% · boilerplate: 1.8%
- bloque de 8 líneas repetido 7 veces en src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/SyncStateEntity.java:35, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:101, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:67 — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/SyncStateEntity.java:35`
- bloque de 8 líneas repetido 6 veces en src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:87, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:53, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/ProfessionalEntity.java:65 — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:87`
- bloque de 8 líneas repetido 6 veces en src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:91, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:57, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/ProfessionalEntity.java:69 — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:91`
- bloque de 8 líneas repetido 6 veces en src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:95, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:61, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/ProfessionalEntity.java:73 — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:95`
- bloque de 8 líneas repetido 6 veces en src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:99, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/CategoryEntity.java:65, src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/ProfessionalEntity.java:77 — `src/main/java/ar/edu/um/prog2/camus/catalogo/catalog/infrastructure/persistence/entity/WeeklyScheduleEntity.java:99`

**hexagonal** (9)
- domain importa framework/infra: import com.fasterxml.jackson.annotation.JsonIgnoreProperties; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:3`
- domain importa framework/infra: import jakarta.persistence.Column; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:4`
- domain importa framework/infra: import jakarta.persistence.EntityListeners; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:5`
- domain importa framework/infra: import jakarta.persistence.MappedSuperclass; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:6`
- domain importa framework/infra: import org.springframework.data.annotation.CreatedBy; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:10`
- domain importa framework/infra: import org.springframework.data.annotation.CreatedDate; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:11`
- domain importa framework/infra: import org.springframework.data.annotation.LastModifiedBy; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:12`
- domain importa framework/infra: import org.springframework.data.annotation.LastModifiedDate; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:13`
- domain importa framework/infra: import org.springframework.data.jpa.domain.support.AuditingEntityListener; — `src/main/java/ar/edu/um/prog2/camus/catalogo/domain/AbstractAuditingEntity.java:14`

## 6. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (28 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice de calidad a lo largo de los commits"
    x-axis ["eef580c", "e7aa464", "aae5a5e", "d5496e9", "70a6f86", "d8cb7d6", "7914c73", "54effa4", "060285a", "748e261", "5fcdc3d", "726c074", "be1fd7b", "0f85b21", "3231751", "bd975fa", "65c197c", "74983d0", "0b1bb14", "ef8b2c6", "c78da3e", "f560506", "32a034d", "6217b72", "e900d32", "ebb6c74", "3098d19", "d7c5a7d"]
    y-axis "Índice" 0 --> 100
    line [0, 15, 40, 40, 39, 39, 39, 39, 39, 39, 39, 44, 44, 44, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49]
```

| # | Fecha | Commit | Autor | LOC | Índice | %POO | Duplic. | Sueltas |
|---|---|---|---|---:|---:|---:|---:|---:|
| 1 | 2026-09-21 | `eef580c` | zoecamus | 0 | 0 | 0% | 0.0% | 0 |
| 2 | 2026-09-22 | `e7aa464` | Ing. Daniel Quinteros | 0 | 15 | 0% | 0.0% | 0 |
| 3 | 2026-10-23 | `aae5a5e` | zoecamus | 2,653 | 40 | 72% | 0.0% | 0 |
| 4 | 2026-10-23 | `d5496e9` | zoecamus | 3,024 | 40 | 72% | 3.1% | 0 |
| 5 | 2026-10-26 | `70a6f86` | zoecamus | 3,255 | 39 | 74% | 13.0% | 0 |
| 6 | 2026-10-27 | `d8cb7d6` | zoecamus | 3,389 | 39 | 77% | 12.4% | 0 |
| 7 | 2026-10-27 | `7914c73` | zoecamus | 3,627 | 39 | 76% | 11.6% | 0 |
| 8 | 2026-10-27 | `54effa4` | zoecamus | 3,698 | 39 | 76% | 11.4% | 0 |
| 9 | 2026-10-27 | `060285a` | zoecamus | 3,735 | 39 | 76% | 11.2% | 0 |
| 10 | 2026-10-27 | `748e261` | zoecamus | 3,898 | 39 | 77% | 10.7% | 0 |
| 11 | 2026-10-28 | `5fcdc3d` | zoecamus | 3,984 | 39 | 77% | 10.5% | 0 |
| 12 | 2026-10-28 | `726c074` | zoecamus | 4,089 | 44 | 77% | 10.2% | 0 |
| 13 | 2026-10-28 | `be1fd7b` | zoecamus | 4,157 | 44 | 77% | 10.0% | 0 |
| 14 | 2026-10-29 | `0f85b21` | zoecamus | 4,184 | 44 | 77% | 10.4% | 0 |
| 15 | 2026-10-29 | `3231751` | zoecamus | 4,184 | 49 | 77% | 10.4% | 0 |
| 16 | 2026-10-29 | `bd975fa` | zoecamus | 4,184 | 49 | 77% | 10.4% | 0 |
| 17 | 2026-10-29 | `65c197c` | zoecamus | 4,184 | 49 | 77% | 10.4% | 0 |
| 18 | 2026-10-29 | `74983d0` | zoecamus | 4,184 | 49 | 77% | 10.4% | 0 |
| 19 | 2026-11-03 | `0b1bb14` | zoecamus | 4,188 | 49 | 77% | 10.4% | 0 |
| 20 | 2026-11-03 | `ef8b2c6` | zoecamus | 4,188 | 49 | 77% | 10.4% | 0 |
| 21 | 2026-11-03 | `c78da3e` | zoecamus | 4,188 | 49 | 77% | 10.4% | 0 |
| 22 | 2026-11-03 | `f560506` | zoecamus | 4,188 | 49 | 77% | 10.4% | 0 |
| 23 | 2026-11-12 | `32a034d` | zoecamus | 4,227 | 49 | 77% | 10.3% | 0 |
| 24 | 2026-11-14 | `6217b72` | zoecamus | 5,116 | 49 | 75% | 10.3% | 0 |
| 25 | 2026-11-14 | `e900d32` | zoecamus | 5,158 | 49 | 75% | 10.2% | 0 |
| 26 | 2026-11-14 | `ebb6c74` | zoecamus | 5,158 | 49 | 75% | 10.2% | 0 |
| 27 | 2026-11-14 | `3098d19` | zoecamus | 5,394 | 49 | 74% | 9.8% | 0 |
| 28 | 2026-11-17 | `d7c5a7d` | zoecamus | 5,483 | 49 | 75% | 9.1% | 0 |

---
_Informe generado por la cátedra. Las dimensiones y umbrales corresponden a `calidad.py` (perfil `exigente`); el semáforo, a `CRITERIOS-CALIDAD.md`. La documentación del repositorio no se evalúa. Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
