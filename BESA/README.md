# BESA

el proyecto puede ser abierto con intellij  
importar /RGWS-PwA/BESA/AgenteResPwA/ResPwAAgent/pom.xml  

## base de datos
también es necesario correr una instancia de postgres, para esto se recomienda utilizar docker  

docker-compose.yml
``` yaml
volumes:
  postgres_volume:

services:
  postgres:
    image: postgres
    container_name: postgres
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: postgres
    ports:
      - "5432:5432"
    expose:
      - "5432"
    volumes:
      - postgres_volume:/var/lib/postgresql/data/
```

ejecutar `docker-compose up`  

### crear database

`docker exec -it postgres psql -U postgres`

ya estando en postgres  
`create database res_pwadb;`
