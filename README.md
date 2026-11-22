# tp-catalogo-sincronizacion

## Qué es

Servicio de **catálogo** (puerto 8081): mantiene una copia local de categorías, profesionales y horarios de la cátedra y la expone a `turnos`.

## Cómo ejecutarlo

```bash
cp .env.example .env      # completar valores (no se versiona)
docker compose up -d catalogo-db
./mvnw                    # perfil dev, http://localhost:8081
```

Variables: ver `.env.example`. Requiere VPN/ZeroTier para llegar a la cátedra (Kafka, Redis, API).

## Documentación

1. [Arquitectura](docs/arquitectura.md)
2. [Contratos entre servicios](docs/contratos.md)
3. [Modelo de datos](docs/modelo-de-datos.md)
4. [Sincronización, idempotencia y recuperación](docs/sincronizacion.md)
5. [Seguridad](docs/seguridad.md)
6. [Pruebas](docs/pruebas.md)

<!-- calidad:inicio -->

![Calidad](https://img.shields.io/badge/Calidad-54%2F100-yellow) ![Aprobar](https://img.shields.io/badge/Aprobar-no-red) ![Destacar](https://img.shields.io/badge/Destacar-no-lightgrey) ![Build](https://img.shields.io/badge/Build-OK-green) ![Duplicación](https://img.shields.io/badge/Duplicaci%C3%B3n-9.1%25-orange) ![%POO](https://img.shields.io/badge/%25POO-75%25-yellow) ![Sueltas](https://img.shields.io/badge/Sueltas-0-green) ![D1](https://img.shields.io/badge/D1-4-red) ![D2](https://img.shields.io/badge/D2-0-red) ![D4](https://img.shields.io/badge/D4-80-green) ![D5](https://img.shields.io/badge/D5-100-green) ![D6](https://img.shields.io/badge/D6-76-yellow) ![D7](https://img.shields.io/badge/D7-100-green)

**Calidad (heurístico, 7 dimensiones):** índice **54/100** · build **OK** · aprobar **no (4/11)** · destacar **no**
Detalle completo en [`docs/EVALUACION.md`](docs/EVALUACION.md).
<!-- calidad:fin -->
