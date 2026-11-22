# Modelo de datos (catálogo)

Esquema creado por Liquibase (`liquibase/changelog/tablas_catalogo.xml` ejecuta `liquibase/sql/catalogo_tablas.sql`).

```mermaid
erDiagram
  category ||--o{ professional : tiene
  professional ||--o{ weekly_schedule : atiende
  category { bigint id PK
    varchar name
    text description
    boolean enabled
    timestamptz created_at
    timestamptz updated_at }
  professional { bigint id PK
    bigint category_id FK
    varchar first_name
    varchar last_name
    boolean enabled }
  weekly_schedule { bigint id PK
    bigint professional_id FK
    varchar day_of_week
    varchar start_time
    varchar end_time
    int slot_duration_minutes
    boolean enabled }
  sync_state { bigint id PK "siempre 1"
    bigint current_version
    timestamptz updated_at }
  processed_event { varchar event_id PK
    timestamptz processed_at }
```

- Los `id` de `category`, `professional` y `weekly_schedule` **vienen de la cátedra** (no se generan acá), para poder aplicar cambios por id.
- `sync_state` tiene una sola fila (`id = 1`) con la última versión aplicada.
- `processed_event` guarda los eventos ya procesados (idempotencia).
- Las tablas de usuarios (`jhi_user`, `jhi_authority`) son las de JHipster.
