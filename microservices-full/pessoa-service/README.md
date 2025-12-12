# Pessoa Service

Microserviço responsável pelo cadastro e gerenciamento de pessoas.

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Validation
- Spring Cloud OpenFeign
- PostgreSQL
- JasperReports
- Springdoc OpenAPI (Swagger)

## Endpoints principais

- `POST /pessoas`  
  Cria uma nova pessoa.

- `GET /pessoas`  
  Lista todas as pessoas.

- `GET /pessoas/{id}`  
  Busca pessoa por ID e traz também seus boletos via OpenFeign.

- `PUT /pessoas/{id}`  
  Atualiza os dados da pessoa.

- `DELETE /pessoas/{id}`  
  Exclui pessoa se não houver boletos pendentes.

- `GET /pessoas/relatorio`  
  Gera um PDF com todas as pessoas cadastradas.

Documentação Swagger disponível em:  
`http://localhost:8080/swagger-ui.html`

## Como executar com Maven

```bash
mvn clean package
mvn spring-boot:run
```

## Testes

```bash
mvn test
```
