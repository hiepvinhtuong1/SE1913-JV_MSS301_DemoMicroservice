# Orchid Store Microservices Demo

Demo for Chapter 02: a simple orchid store built with three Spring Boot microservices.

## Services

| Service | Port | Responsibility |
| --- | --- | --- |
| AccountService | 8081 | Manages accounts and roles |
| OrchidService | 8082 | Manages orchid catalog and categories |
| OrderService | 8080 | Creates and lists orders, using OpenFeign to call the other services |

## Databases

Each service uses its own SQL Server database:

- `AccountServiceDb`
- `OrchidServiceDb`
- `OrderServiceDb`

Default connection values are in each service's `application.properties`. Override them with environment variables if needed:

```powershell
$env:ACCOUNT_DB_URL="jdbc:sqlserver://localhost:1433;databaseName=AccountServiceDb;encrypt=true;trustServerCertificate=true"
$env:ACCOUNT_DB_USERNAME="sa"
$env:ACCOUNT_DB_PASSWORD="YourStrong!Passw0rd"

$env:ORCHID_DB_URL="jdbc:sqlserver://localhost:1433;databaseName=OrchidServiceDb;encrypt=true;trustServerCertificate=true"
$env:ORCHID_DB_USERNAME="sa"
$env:ORCHID_DB_PASSWORD="YourStrong!Passw0rd"

$env:ORDER_DB_URL="jdbc:sqlserver://localhost:1433;databaseName=OrderServiceDb;encrypt=true;trustServerCertificate=true"
$env:ORDER_DB_USERNAME="sa"
$env:ORDER_DB_PASSWORD="YourStrong!Passw0rd"
```

## Build

```powershell
mvn test
```

## Run

Start the services in this order:

```powershell
mvn spring-boot:run -pl account-service
mvn spring-boot:run -pl orchid-service
mvn spring-boot:run -pl order-service
```

## Demo Requests

```powershell
Invoke-RestMethod http://localhost:8081/api/accounts/1
Invoke-RestMethod http://localhost:8082/api/orchids/1
```

Create an order:

```powershell
$body = @{
  accountId = 1
  items = @(
    @{ orchidId = 1; quantity = 2 },
    @{ orchidId = 2; quantity = 1 }
  )
} | ConvertTo-Json -Depth 5

Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/orders -ContentType "application/json" -Body $body
```

List orders:

```powershell
Invoke-RestMethod http://localhost:8080/api/orders
```
