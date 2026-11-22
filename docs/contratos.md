# Contratos entre servicios (catálogo)

Todos los endpoints `/api/**` exigen `Authorization: Bearer <JWT>`. Los errores usan `application/problem+json` con el campo `code`.

## Que expone catálogo

| Método y ruta | Respuesta |
|---|---|
| `GET /api/categories` | lista de `CategoriaResponse` |
| `GET /api/professionals` | lista de `ProfesionalResponse` (filtros opcionales) |
| `GET /api/professionals/{id}/agenda` | `AgendaResponse`, 404 si no existe |
| `POST /api/sync/full` | fuerza una sincronización completa |
| `GET /api/sync/status` | versión actual sincronizada |

```json
// AgendaResponse
{ "professional": { "id": 1, "categoryId": 2, "firstName": "Ana", "lastName": "Gil", "enabled": true,
                    "createdAt": "…", "updatedAt": "…" },
  "weeklySchedules": [ { "id": 7, "professionalId": 1, "dayOfWeek": "MONDAY", "startTime": "09:00",
                         "endTime": "13:00", "slotDurationMinutes": 30, "enabled": true } ] }
```

Quien lo consume es `turnos` (`CatalogRestAdapter`), que ignora campos desconocidos para tolerar cambios compatibles.

## Que consume de la cátedra

| Canal | Uso |
|---|---|
| REST `CATEDRA_API_URL` con JWT técnico | snapshot completo y cambios desde una versión |
| Redis (claves `catedra:sync:*`) | versión actual y lista de cambios |
| Kafka, topic `KAFKA_CATALOG_TOPIC` | evento `CatalogUpdated` que dispara la sincronización |

## Códigos de error

`BusinessException` con `ErrorKind`: `VALIDATION`→400, `NOT_FOUND`→404, `CONFLICT`→409, `UNAVAILABLE`→503.
