# api-cuentas
docker build -t api-cuentas .


docker run --name redis-banco -p 6379:6379 -d redis:latest