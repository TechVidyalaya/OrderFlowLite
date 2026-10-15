# OrderFlow Lite — Hibernate N+1 (broken demo)

A deliberately small Spring Boot project for TechVidyalaya's **"Hibernate N+1 Problem — Find It and Fix It with Real Code"** video.

> **This branch is intentionally inefficient for teaching. Do not copy this data-access pattern into production.**

## Requirements

- Java 21
- Maven 3.9+ (or a compatible recent Maven release)
- No PostgreSQL or Docker required: the demo uses an in-memory H2 database.

## Run

```bash
mvn spring-boot:run
```

In another terminal:

```bash
curl http://localhost:8080/api/demo/orders/n-plus-one
```

Expected response shape:

```json
{
  "orders": [
    {"orderId": 1, "itemCount": 1}
  ],
  "sqlStatements": 11
}
```

The `orders` array actually contains **10 orders**; the response above shortens it for readability. Under a single sequential local request after startup, expect **11 statements**: 1 SELECT for all orders and 10 SELECTs to initialize each lazy `items` collection.

## Why N+1?

`OrderRepository.findAll()` performs one SELECT. Each `order.getItems().size()` then initializes an uninitialized lazy collection with a separate SELECT. Hibernate is configured with `default_batch_fetch_size: 0` to prevent automatic batch fetching from hiding the example.

Read `org.hibernate.SQL` output to observe those selects. The `sqlStatements` number comes from Hibernate statistics. **Statistics are global** and reset at the start of this service call, so run one request at a time; concurrent requests can make the count inaccurate.

## Teaching sequence

1. Show the API returning order summaries with `sqlStatements: 11`.
2. Inspect `CustomerOrder.items` and point out `FetchType.LAZY`.
3. Explain the 1 + N SQL statements using the Hibernate SQL output.
4. Identify the lazy access inside `OrderQueryService.listOrdersBroken()`.
5. In a later optimized version, fetch required order items deliberately via a join fetch or entity graph, and verify the SQL count. Do **not** switch every association to `EAGER`.

## Scope

This first branch intentionally contains just Product, CustomerOrder, and OrderItem entities, seed data, and **one** demo endpoint. There is no authentication, payment gateway, Redis, frontend, Docker, or full CRUD layer.
