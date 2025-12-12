# Boleto Service

Microserviço responsável pelo cadastro, consulta, pagamento e exclusão de boletos.

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Springdoc OpenAPI (Swagger)
- Scheduler (Spring Scheduling)

## Endpoints principais

- `POST /boletos`  
  Cria um novo boleto.

- `GET /boletos/pessoa/{pessoaId}`  
  Lista boletos de uma pessoa, ordenados por data de vencimento.

- `GET /boletos/{id}`  
  Busca boleto por ID.

- `POST /boletos/{id}/pagamento`  
  Realiza o pagamento do boleto (status precisa estar PENDENTE).

- `DELETE /boletos/{id}`  
  Exclui boleto (não permitido se status for PAGO).

Documentação Swagger:  
`http://localhost:8081/swagger-ui.html`

## Como executar com Maven

```bash
mvn clean package
mvn spring-boot:run
```

## Testes

```bash
mvn test
```
