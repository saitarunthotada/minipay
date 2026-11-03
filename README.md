# MiniPay

Order management backend for INR payments, built with Spring Boot and hexagonal (ports and adapters) architecture. Amounts are stored in paise to avoid floating-point issues.

The order management foundation is complete. Payment gateway functionality is still pending (see [Project status](#project-status)).

## Project status

**Foundation complete.** Order management is in place; payment gateway functionality is still pending.

| Area | Status |
|---|---|
| Order management | Complete |
| Payment domain | Next |
| Razorpay integration | Pending |
| Idempotency and retries | Pending |
| Webhooks and reconciliation | Pending |

## Tech stack

- Java 21
- Spring Boot 4.1.2 (Web MVC, Data JPA, Validation, Actuator)
- PostgreSQL 16
- Flyway for database migrations
- Testcontainers for integration tests
- Lombok
- Maven (wrapper included)

## Architecture

The `order` module follows ports and adapters, so the domain has no dependency on Spring or the database.

```
com.minipay
├── order
│   ├── domain                 # Order, OrderStatus (business rules)
│   ├── port
│   │   ├── in                 # CreateOrderUseCase, GetOrderUseCase
│   │   └── out                # OrderRepository
│   ├── application            # CreateOrderService, GetOrderService
│   └── adapter
│       ├── in/web             # OrderController, request/response DTOs
│       └── out/persistence    # JPA entity, Spring Data repo, adapter
└── shared
    └── exception              # GlobalExceptionHandler
```

## Order rules

- Description: 1 to 255 characters, not blank
- Amount: at least 100 paise (Rs. 1.00)
- Currency: INR only
- Status lifecycle values: `CREATED`, `PAYMENT_PENDING`, `PAID`, `FAILED`
- A new order starts as `CREATED`; only a `CREATED` order can move to `PAYMENT_PENDING`

The same rules are enforced again by database constraints in the Flyway migration.

## Getting started

### Prerequisites

- JDK 21
- Docker (for local PostgreSQL and for running tests)

### 1. Start PostgreSQL

```bash
docker compose up -d
```

This starts PostgreSQL 16 on `localhost:5432` with database `minipay`.

> The credentials in `compose.yml` and `application.yml` are for local development only.

### 2. Run the application

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw spring-boot:run
```

Flyway creates the `orders` table automatically on startup. The app runs on `http://localhost:8080`.

### 3. Run the tests

```bash
./mvnw clean test
```

Docker must be running. The integration tests start a throwaway PostgreSQL container through Testcontainers, so they don't touch your local database.

## API

### Create an order

`POST /api/orders`

```json
{
  "description": "Order for 2 items",
  "amountInPaise": 10000
}
```

Response `201 Created`:

```json
{
  "id": "89d2cf82-ad0f-45ea-a82c-056bbeedb489",
  "description": "Order for 2 items",
  "amountInPaise": 10000,
  "currency": "INR",
  "status": "CREATED",
  "createdAt": "2026-11-03T07:18:29.191342300Z",
  "updatedAt": "2026-11-03T07:18:29.191342300Z"
}
```

Invalid input returns `400 Bad Request` with a `status` field and error details.

### Get an order

`GET /api/orders/{id}`

Returns `200 OK` with the same body as above.

### Example with curl

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"description":"Test order","amountInPaise":10000}'
```

## Timezone note

The PostgreSQL JDBC driver sends the JVM timezone to the server, and some Postgres images reject the legacy name `Asia/Calcutta`. The project avoids this by setting `-Duser.timezone=Asia/Kolkata` for both `spring-boot:run` and the Surefire test JVM in `pom.xml`. If you run the app from your IDE, add the same VM option.

## Roadmap

1. Payment domain (next)
2. Razorpay integration
3. Idempotency and retries
4. Webhooks and reconciliation

## License

No license specified yet.