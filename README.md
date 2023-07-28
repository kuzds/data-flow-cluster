# User Flow Cluster
Cluster contains 4 chained business services and Spring Cloud Eureka/Config services.   
User objects stored in the `new_user` table are passed throught chain and stored in the `ready_user` table.
Workflow:
![process.png](.assets/process.png)

## Technological stack for services:

`eureka-server`:
- Spring Cloud Eureka Server

`config-server`:
- Spring Cloud Config Server (using a file system as a configuration source)

`bare-db2web`:
- Spring Cloud Eureka/Config Client
- Openfeign REST client
- CXF Framework/CodegenPlugin
- PostgreSQL / Flyway / Spring Data JPA


`bare-web2rabbit`:
- Spring Cloud Eureka/Config Client
- Rest controller
- CXF Framework/CodegenPlugin
- RabbitMQ starter

`camel-rabbit2web`:
- Spring Cloud Eureka/Config Client
- Apache Camel
- RabbitMQ starter
- REST client
- CXF Framework/CodegenPlugin

`camel-web2db`:
- Spring Cloud Eureka/Config Client
- Apache Camel
- REST controller
- CXF Framework/CodegenPlugin
- ElSql

## Local development

1. Run RabbitMQ and PostgreSQL
2. `config-server`: set RabbitMQ and PostgreSQL properties in `/resources/configs/*.yaml` files
3. Run order `config-server` -> `eureka-server` -> `bare-web2rabbit` -> `bare-db2web` -> `camel-web2db` -> `camel-rabbit2web`

## Additional Info

`config-server`:  
Config Server REST endpoint: 
- GET localhost:8888/application/default
