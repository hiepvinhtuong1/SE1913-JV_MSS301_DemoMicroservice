param(
    [string]$ContainerName = "sqlserver-db",
    [string]$Username = "sa",
    [string]$Password = "Hiep2004"
)

$databases = @(
    "AccountServiceDb",
    "OrchidServiceDb",
    "OrderServiceDb"
)

$sql = ($databases | ForEach-Object {
    "IF DB_ID(N'$_') IS NULL CREATE DATABASE [$_];"
}) -join " "

$sqlcmdPaths = @(
    "/opt/mssql-tools18/bin/sqlcmd",
    "/opt/mssql-tools/bin/sqlcmd"
)

foreach ($sqlcmdPath in $sqlcmdPaths) {
    docker exec $ContainerName test -x $sqlcmdPath *> $null

    if ($LASTEXITCODE -eq 0) {
        $arguments = @("exec", $ContainerName, $sqlcmdPath, "-S", "localhost", "-U", $Username, "-P", $Password, "-Q", $sql)

        if ($sqlcmdPath -like "*tools18*") {
            $arguments += "-C"
        }

        docker @arguments

        if ($LASTEXITCODE -ne 0) {
            exit $LASTEXITCODE
        }

        Write-Host "SQL Server databases are ready: $($databases -join ', ')."
        exit 0
    }
}

Write-Error "sqlcmd was not found in container '$ContainerName'."
exit 1
