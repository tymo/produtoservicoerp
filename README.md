# Desafio Java - ERP - Denis Metzner 1

# PROVA NÍVEL III

# ProductServiceERP

## Como executar

1. **Pré-requisitos:** Java 11+, Maven, PostgreSQL.
2. Configure o banco em `src/main/resources/application.properties`.
3. Para compilar e rodar:
   ```sh
   mvn clean package
   mvn spring-boot:run
   ```
   Acesse os endpoins abaixo para listar os dados de exemplo:
   http://localhost:8080/produtoservico
   http://localhost:8080/itempedido
   http://localhost:8080/pedido

## Testes automatizados

Execute:

```sh
mvn test
```

Todos os testes cobrem CRUD, regras de negócio e validações.

## Funcionalidades

- Cadastro de Produto/Serviço, Pedido e Itens de Pedido (CRUD, filtros, paginação)
- Validação de regras de negócio (desconto, estoque, produto ativo, etc)
- Mensagens de erro amigáveis e tratamento global de exceções
