# Projedata

## Descrição do Projeto
O Projedata é um sistema completo para gerenciamento de produtos e matérias-primas, permitindo:
- Cadastro, edição e remoção de produtos.
- Controle de matérias-primas associadas a cada produto.
- Cálculo da quantidade máxima de produtos que podem ser produzidos com base no estoque de matérias-primas.
- Visualização de produtos produzíveis com preço unitário e total.

O sistema possui **frontend em React** e **backend em Spring Boot**, com integração via API REST.

## Tecnologias Utilizadas
- **Frontend:** React, CSS, Axios
- **Backend:** Java, Spring Boot, Spring Data JPA, PostgreSQL
- **Controle de versão:** Git, GitHub

## Estrutura do Projeto

## Funcionalidades Principais
- CRUD de produtos e matérias-primas.
- Associação de matérias-primas a produtos com quantidade necessária.
- Cálculo de quantidade máxima produzível por produto.
- Listagem de produtos produzíveis com detalhes de preço e quantidade.

## Como Executar
### Backend
1. Configure o banco de dados PostgreSQL.
2. Ajuste `application.properties` com suas credenciais.
3. Execute a aplicação Spring Boot:
```bash
cd backend
./mvnw spring-boot:run

cd frontend
npm install
npm start

O frontend será executado em http://localhost:3000 e se comunicará com o backend em http://localhost:8080.

Observações

Este projeto foi estruturado para manter backend e frontend separados, mas versionados no mesmo repositório.

Utilize a API REST para futuras integrações.

