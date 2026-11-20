# Ferretería Los Maestros — Backend 2 (`ferreteria-api`)

Microservicio de negocio para la plataforma FullStack **Ferretería Los Maestros**, encargado de la gestión del catálogo (`Categorías` y `Productos`), validación de tokens JWT emitidos por `ferreteria-auth` y procesamiento transaccional de ventas (`Boletas` y `Detalle de Boleta`).

---

## 1. Tecnologías utilizadas
- **Java 21** & **Spring Boot 3.5**
- **Spring Security** + **JJWT** (validación de firma HMAC-SHA256, expiración y `issuer`)
- **Spring Data JPA** + **MySQL** (`ferreteria_negocio`)
- **Springdoc OpenAPI (Swagger UI con esquema Bearer JWT)**
- **Lombok**

---

## 2. Variables de Entorno y Configuración

Por seguridad, ninguna contraseña ni clave secreta se almacena en el repositorio. El microservicio lee sus credenciales desde variables de entorno:

| Variable de Entorno | Descripción | Valor / Ejemplo Local |
| :--- | :--- | :--- |
| `SERVER_PORT` | Puerto donde levanta el microservicio | `8082` |
| `DB_URL` | Cadena de conexión JDBC a MySQL | `jdbc:mysql://localhost:3306/ferreteria_negocio?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true` |
| `DB_USER` | Usuario de la base de datos MySQL | `ferreteria_app` |
| `DB_PASSWORD` | Contraseña de la base de datos MySQL | *(definida en el entorno local o `.env`)* |
| `JWT_SECRET` | Clave secreta Base64 (32 bytes) compartida con `ferreteria-auth` | `<clave-base64-compartida>` |
| `APP_CORS_ALLOWED_ORIGIN` | Origen permitido para peticiones desde React | `http://localhost:5173` |

---

## 3. URLs del Servicio y Documentación
- **Base URL Local:** `http://localhost:8082`
- **Swagger UI:** `http://localhost:8082/swagger-ui/index.html`
- **OpenAPI JSON:** `http://localhost:8082/v3/api-docs`

### Endpoints Principales (`/api/v1`)
- **Productos (`/api/v1/productos`):**
  - `GET /api/v1/productos` — Listado público (acepta filtro opcional `?categoriaId=`).
  - `GET /api/v1/productos/{id}` — Detalle público de producto.
  - `POST`, `PUT`, `DELETE` — Gestión de inventario (solo rol `ADMIN`).
- **Categorías (`/api/v1/categorias`):**
  - `GET /api/v1/categorias` — Listado público de categorías.
  - `POST`, `PUT`, `DELETE` — Administración de categorías (solo rol `ADMIN`).
- **Boletas (`/api/v1/boletas`):**
  - `POST /api/v1/boletas` — Emisión transaccional de compra (`CLIENTE` / `ADMIN`), validación y descuento de stock.
  - `GET /api/v1/boletas` — Listado de órdenes (`ADMIN` y `VENDEDOR` ven todas; `CLIENTE` visualiza su historial).
  - `GET /api/v1/boletas/{id}` — Detalle de boleta con sus ítems.

---

## 4. Ejecución Local (PowerShell)

```powershell
$env:DB_USER="ferreteria_app"
$env:DB_PASSWORD="tu_password_mysql"
$env:JWT_SECRET="<misma_clave_base64_de_ferreteria_auth>"
.\mvnw.cmd spring-boot:run