# User Flow Cluster
This cluster contains 4 chained business services. Workflow:
![process.png](.assets/process.png)

## Technological stack for services:
Spring Cloud + Spring Admin + Spring Cloud Config  

`eureka-server`:
- Spring Cloud Eureka Server

`config-server`:
- Spring Cloud Config Server (using a file system as a configuration source)

`bare-db2web`:
- Spring Cloud Eureka/Config Client
- SOAP producer
- Feign REST client

`bare-web2rabbit`:
- Spring Cloud Eureka/Config Client
- SOAP consumer

`camel-rabbit2web`:
- Spring Cloud Eureka/Config Client
- Camel

`camel-web2db`:
- Spring Cloud Eureka/Config Client
- Camel

## Local development

1. `config-server`: set RabbitMQ and PostgreSQL properties in `/resources/configs/*.yaml` files

## Additional Info

`config-server`:  
Config Server REST endpoints: 
- localhost:8888/bare-db2web/default
- localhost:8888/bare-web2rabbit/default
- localhost:8888/camel-rabbit2web/default
- localhost:8888/camel-web2db/default