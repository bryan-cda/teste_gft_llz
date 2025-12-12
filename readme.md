# 🧩 Desafio – Microserviços de Pessoa e Boleto (Java + Spring Boot)

Este repositório implementa o desafio prático envolvendo dois microserviços Java com **Spring Boot 3**, **Java 17**, **PostgreSQL**, comunicação via **OpenFeign**, testes unitários, geração de **PDF com JasperReports** e **scheduler** para atualização automática de boletos vencidos.

O projeto foi desenvolvido com foco em clareza, separação de responsabilidades, alto nível de qualidade arquitetural e facilidade de execução.


# 🏗 Arquitetura Geral

O sistema é composto por dois microserviços independentes:

## **2️⃣ boleto-service**

Responsável por:

- Cadastro, consulta, pagamento e exclusão de boletos  
- Validações completas de pagamento  
- Atualização automática de boletos vencidos via **Scheduler diário (01:00)**  
- Ordenação de boletos por data de vencimento  


## Comunicação entre microserviços

O `pessoa-service` se comunica com o `boleto-service` via HTTP usando **Spring Cloud OpenFeign**:

pessoa-service → boleto-service


Cada serviço possui seu próprio banco **PostgreSQL**, isolado e configurado via Docker.

# 📁 Estrutura do Repositório

```
.
├── docker-compose.yml
├── README.md  ← (este arquivo)
├── pessoa-service/
│   ├── pom.xml
│   ├── Dockerfile
│   ├── src/
│   │   ├── main/java/...
│   │   ├── test/java/...
│   │   └── resources/reports/pessoas.jrxml
└── boleto-service/
├── pom.xml
├── Dockerfile
└── src/
├── main/java/...
└── test/java/...


#Tecnologias Utilizadas

### **Backend**
- Java 17  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- Spring Validation  
- Spring Cloud OpenFeign  
- Spring Scheduling  

### **Infraestrutura**
- Docker  
- Docker Compose  
- PostgreSQL  

### **Documentação**
- Springdoc OpenAPI (Swagger UI)

### **Testes**
- JUnit 5  
- Mockito  
- Spring Boot Test  
- WebMvcTest  

### **PDF**
- JasperReports  

# 📜 Regras de Negócio


# 👤 Pessoa (pessoa-service)
