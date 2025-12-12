
---

```markdown
# 🧩 Desafio – Microserviços de Pessoa e Boleto (Java + Spring Boot)

Este repositório implementa o desafio prático envolvendo dois microserviços Java com **Spring Boot 3**, **Java 17**, **PostgreSQL**, comunicação via **OpenFeign**, testes unitários, geração de **PDF com JasperReports** e **scheduler** para atualização automática de boletos vencidos.

O projeto foi desenvolvido com foco em clareza, separação de responsabilidades, alto nível de qualidade arquitetural e facilidade de execução.

---

# 🏗 Arquitetura Geral

O sistema é composto por dois microserviços independentes:

---

## **2️⃣ boleto-service**

Responsável por:

- Cadastro, consulta, pagamento e exclusão de boletos  
- Validações completas de pagamento  
- Atualização automática de boletos vencidos via **Scheduler diário (01:00)**  
- Ordenação de boletos por data de vencimento  

---

## Comunicação entre microserviços

O `pessoa-service` se comunica com o `boleto-service` via HTTP usando **Spring Cloud OpenFeign**:

pessoa-service → boleto-service


Cada serviço possui seu próprio banco **PostgreSQL**, isolado e configurado via Docker.

---

# 📁 Estrutura do Repositório



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


---

# 🧪 Tecnologias Utilizadas

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

---

# 📜 Regras de Negócio

---

# 👤 Pessoa (pessoa-service)

### Campos

| Campo | Obrigatório | Observações |
|-------|-------------|-------------|
| nome | ✔️ | - |
| cpf | ✔️ | Apenas números, 11 dígitos, não duplicado |
| dataNascimento | ✔️ | Idade mínima: 18 anos |
| cep / logradouro / bairro / uf / cidade | ❌ | opcionais |




### Regras

- Não permitir cadastro com CPF duplicado  
- Não permitir CPF com caracteres não numéricos  
- Não permitir pessoa menor de 18 anos  
- Ao excluir pessoa:  
  → **Não pode ter boletos pendentes** (verificado via OpenFeign)

### Endpoints

- `POST /pessoas`  
- `GET /pessoas`  
- `GET /pessoas/{id}`  
- `PUT /pessoas/{id}`  
- `DELETE /pessoas/{id}`  
- `GET /pessoas/relatorio` → gera PDF

---

# 💸 Boleto (boleto-service)

### Campos

pessoaId

Obrigatório: ✔️

Regras: Deve referenciar uma pessoa existente (relacionamento)

valorDocumento

Obrigatório: ✔️

Regras: Deve ser maior que 0

dataVencimento

Obrigatório: ✔️

Regras: Deve ser maior ou igual à data atual

status

Obrigatório: ✔️

Regras: Valores permitidos → PENDENTE, PAGO, VENCIDO

valorPago

Obrigatório: ❌

Regras: Preenchido apenas em pagamento

dataPagamento

Obrigatório: ❌

Regras: Preenchida apenas em pagamento

### Pagamento

Só permitido quando:

- status == **PENDENTE**  
- valorPago == valorDocumento  
- dataPagamento == hoje  

Após pagamento:

- status = **PAGO**

### Exclusão

- Não permitir exclusão de boleto **PAGO**

### Scheduler

Executa diariamente às 01:00:

- Busca boletos PENDENTES vencidos  
- Atualiza para status **VENCIDO**

### Endpoints

- `POST /boletos`  
- `GET /boletos/pessoa/{pessoaId}`  
- `GET /boletos/{id}`  
- `POST /boletos/{id}/pagamento`  
- `DELETE /boletos/{id}`  

---

# 🚀 Como Executar com Docker Compose

Pré-requisitos:

- Docker  
- Docker Compose  
- Maven instalado

---

## 1️⃣ Gerar os JARs

```bash
mvn -f boleto-service/pom.xml clean package -DskipTests
mvn -f pessoa-service/pom.xml clean package -DskipTests
````

---

## 2️⃣ Subir toda a plataforma

```bash
docker-compose up --build
```

Isso irá inicializar:

* PostgreSQL (pessoa) → porta 5433
* PostgreSQL (boleto) → porta 5434
* boleto-service (8081)
* pessoa-service (8080)

---

# 🌐 Acesso aos Serviços

### Swagger UI

* Pessoa Service
  **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

* Boleto Service
  **[http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)**

---

# 📄 Relatório PDF (Pessoa)

Endpoint:

```
GET /pessoas/relatorio
```

Gera um PDF com:

* ID
* Nome
* CPF
* Data de Nascimento

Template Jasper (`pessoas.jrxml`) está em:

```
pessoa-service/src/main/resources/reports/
```

---

# 🧪 Testes Unitários

Ambos os microserviços possuem testes para:

### ✔️ Services

* Validações
* Regras de negócio

### ✔️ Controllers

* Testes com MockMvc
* Validação de status HTTP

Rodar:

```bash
cd pessoa-service
mvn test

cd boleto-service
mvn test
```

---

# 🔥 Critérios do Desafio (Atendidos)

* ✔️ Qualidade do código
* ✔️ Regras de negócio completas
* ✔️ Testes unitários
* ✔️ Documentação via Swagger
* ✔️ OpenFeign
* ✔️ Tratamento centralizado de erros
* ✔️ Uso correto dos verbos HTTP
* ✔️ Banco relacional
* ✔️ Scheduler
* ✔️ JasperReports
* ✔️ Dockerfile + docker-compose

---

# 👤 Autor

**Bryan Duarte**
📧 **[albuquerque.bry.n@gmail.com](mailto:albuquerque.bry.n@gmail.com)**
🐙 GitHub: **[https://github.com/bryan-cda](https://github.com/bryan-cda)**

```
