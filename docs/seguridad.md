# Decisiones de seguridad (catálogo)

- **Autenticación**: JWT firmado con HS512 (`JWT_SECRET`, base64). Comparte secreto con `turnos`, por eso un token emitido por turnos sirve en catálogo. Validez 24 h.
- **Autorización**: `/api/**` requiere usuario autenticado; `/api/admin/**` y `/management/**` (salvo `health`, `info`, `prometheus`) requieren `ROLE_ADMIN`.
- **Secretos fuera del repositorio**: contraseñas, JWT propio y JWT de la cátedra se leen de variables de entorno (`.env` está en `.gitignore`; solo se versiona `.env.example` sin valores).
- **JWT de la cátedra**: se usa solo del lado servidor, nunca se devuelve a clientes.
- **Sin sesión**: API *stateless*, sin cookies de autenticación, CSRF desactivado por eso.
- **Entradas**: la búsqueda usa consultas parametrizadas (JPQL con `:nombre`), sin concatenar texto.
- **Datos**: el catálogo es público dentro del sistema; no guarda datos personales de pacientes.
