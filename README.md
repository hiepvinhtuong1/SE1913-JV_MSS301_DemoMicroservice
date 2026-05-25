# Orchid Store Microservices Demo

Demo for Chapter 03: a simple orchid store built with Spring Boot microservices, OpenFeign, and Eureka service discovery.

## Services

| Service | Port | Responsibility |
| --- | --- | --- |
| EurekaServer | 9001 | Service registry for microservices |
| AccountService | 8081 | Manages accounts and roles |
| OrchidService | 8082 | Manages orchid catalog and categories |
| OrderService | 8080 | Creates and lists orders, using OpenFeign to call services discovered through Eureka |

## Databases

Each business service uses its own SQL Server database:

- `AccountServiceDb`
- `OrchidServiceDb`
- `OrderServiceDb`

Default connection values are in each service's `application.properties`. Override them with environment variables if needed:

```powershell
$env:ACCOUNT_DB_URL="jdbc:sqlserver://localhost:1433;databaseName=AccountServiceDb;encrypt=true;trustServerCertificate=true"
$env:ACCOUNT_DB_USERNAME="sa"
$env:ACCOUNT_DB_PASSWORD="Hiep2004"

$env:ORCHID_DB_URL="jdbc:sqlserver://localhost:1433;databaseName=OrchidServiceDb;encrypt=true;trustServerCertificate=true"
$env:ORCHID_DB_USERNAME="sa"
$env:ORCHID_DB_PASSWORD="Hiep2004"

$env:ORDER_DB_URL="jdbc:sqlserver://localhost:1433;databaseName=OrderServiceDb;encrypt=true;trustServerCertificate=true"
$env:ORDER_DB_USERNAME="sa"
$env:ORDER_DB_PASSWORD="Hiep2004"
```

## Eureka

All services use this Eureka registry by default:

```properties
eureka.client.service-url.defaultZone=http://localhost:9001/eureka/
```

Override it when running against another registry:

```powershell
$env:EUREKA_DEFAULT_ZONE="http://localhost:9001/eureka/"
```

## Local Environment

Load local SQL Server and Eureka variables in PowerShell:

```powershell
.\scripts\set-local-env.ps1
```

Create the SQL Server databases after the Docker container is running:

```powershell
.\scripts\init-sqlserver-databases.ps1
```

For IntelliJ IDEA run configurations, select `.env.local` in the Environment variables file picker.

## Build

```powershell
mvn test
```

## Run

Start the services in this order:

```powershell
mvn spring-boot:run -pl eureka-server
mvn spring-boot:run -pl account-service
mvn spring-boot:run -pl orchid-service
mvn spring-boot:run -pl order-service
```

Open the Eureka dashboard:

```text
http://localhost:9001
```

## Demo Requests

```powershell
Invoke-RestMethod http://localhost:8081/api/accounts/1
Invoke-RestMethod http://localhost:8082/api/orchids/1
```

## Swagger UI

Open each service's Swagger UI after the service starts:

```text
AccountService: http://localhost:8081/swagger-ui/index.html
OrchidService:  http://localhost:8082/swagger-ui/index.html
OrderService:   http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON is available at `/v3/api-docs` for each service.

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
