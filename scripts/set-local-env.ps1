$env:ACCOUNT_DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=AccountServiceDb;encrypt=true;trustServerCertificate=true"
$env:ACCOUNT_DB_USERNAME = "sa"
$env:ACCOUNT_DB_PASSWORD = "Hiep2004"

$env:ORCHID_DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=OrchidServiceDb;encrypt=true;trustServerCertificate=true"
$env:ORCHID_DB_USERNAME = "sa"
$env:ORCHID_DB_PASSWORD = "Hiep2004"

$env:ORDER_DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=OrderServiceDb;encrypt=true;trustServerCertificate=true"
$env:ORDER_DB_USERNAME = "sa"
$env:ORDER_DB_PASSWORD = "Hiep2004"

$env:EUREKA_DEFAULT_ZONE = "http://localhost:9001/eureka/"

Write-Host "Local environment variables loaded for DemoMicroservice."
