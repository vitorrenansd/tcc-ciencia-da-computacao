# Serve-Me

> 🇧🇷 [Português](#português) | 🇺🇸 [English](#english)

---

## Português

Sistema de autoatendimento para restaurantes desenvolvido como Trabalho de Conclusão de Curso em Ciência da Computação pela Universidade do Sul de Santa Catarina (UNISUL).

Este repositório é uma continuação individual do projeto [serve-me](https://github.com/vitorrenansd/serve-me), iniciado anteriormente em grupo. Nesta etapa, o desenvolvimento é conduzido exclusivamente pelo autor, dando continuidade e expandindo as funcionalidades originais.

### Sobre o Sistema

O Serve-Me permite que clientes de um restaurante realizem pedidos diretamente pelo celular ou tablet da mesa, sem necessidade de interação inicial com garçons. O sistema conta com dois frontends distintos que consomem a mesma API backend.

**Frontend Cliente** — Interface simplificada para uso na mesa, onde o cliente navega pelo cardápio, seleciona produtos e envia o pedido.

**Painel Administrativo** — Interface completa para a equipe do restaurante, com gerenciamento de pedidos em tempo real, controle de cardápio, cadastro de produtos e categorias, configurações do estabelecimento e controle de caixa.

### Stack

| Camada | Tecnologia |
|---|---|
| Backend | Java 21 + Spring Boot 3 |
| Acesso a dados | JdbcTemplate |
| Banco de dados | H2 (em memória) |
| Frontend Cliente | React + Vite |
| Frontend Admin | React + Vite |
| Build Backend | Gradle (Kotlin DSL) |

### Estrutura do Repositório

```
/
├── backend/          API REST em Spring Boot
├── serveme-client/   Frontend de autoatendimento (porta 22800)
├── serveme-admin/    Painel administrativo (porta 22801)
└── start.bat         Script para subir o ambiente completo
```

### Como Executar

**Pré-requisitos:** Java 21, Node.js 18+, npm, curl

**1. Configure as variáveis de ambiente**

Crie um arquivo `.env` na raiz do `backend/` com:
```env
IMAGES_PATH=C:/serve-me/images/
IMAGES_BASE_URL=http://localhost:8080/images/
```

Crie um arquivo `.env` em `serveme-client/` e `serveme-admin/` com:
```env
VITE_API_BASE_URL=http://localhost:8080
```

**2. Instale as dependências dos frontends**
```bash
cd serveme-client && npm install
cd serveme-admin  && npm install
```

**3. Suba o ambiente completo**
```bash
start.bat
```

O script executa os testes do backend, compila, aguarda o backend responder no health check e então sobe os dois frontends automaticamente.

| Serviço | URL |
|---|---|
| Backend | http://localhost:8080 |
| Frontend Cliente | http://localhost:22800 |
| Painel Admin | http://localhost:22801 |

### Arquitetura

O backend segue arquitetura por features — cada funcionalidade contém suas próprias camadas de controller, service, repository, entity e DTO. O frontend administrativo espelha essa organização. O acesso ao banco é feito via `JdbcTemplate`, sem ORM, com controle direto sobre as queries SQL.

---

## English

A self-service ordering system for restaurants, developed as a Computer Science undergraduate thesis at the Universidade do Sul de Santa Catarina (UNISUL).

This repository is an individual continuation of the [serve-me](https://github.com/vitorrenansd/serve-me) project, originally built as a group effort. In this stage, development is carried out solely by the author, extending and improving the original features.

### About

Serve-Me allows restaurant customers to place orders directly from their phone or tablet at the table, without needing to interact with a waiter. The system has two separate frontends that consume the same backend API.

**Client Frontend** — A simplified interface for table use, where customers browse the menu, select items and submit their order.

**Admin Panel** — A full-featured interface for restaurant staff, including real-time order management, menu control, product and category registration, restaurant settings and cash shift control.

### Stack

| Layer | Technology |
|---|---|
| Backend | Java 21 + Spring Boot 3 |
| Data access | JdbcTemplate |
| Database | H2 (in-memory) |
| Client Frontend | React + Vite |
| Admin Frontend | React + Vite |
| Build | Gradle (Kotlin DSL) |

### Repository Structure

```
/
├── backend/          Spring Boot REST API
├── serveme-client/   Self-service frontend (port 22800)
├── serveme-admin/    Admin panel (port 22801)
└── start.bat         Script to start the full environment
```

### Running Locally

**Prerequisites:** Java 21, Node.js 18+, npm, curl

**1. Set up environment variables**

Create a `.env` file inside `backend/`:
```env
IMAGES_PATH=C:/serve-me/images/
IMAGES_BASE_URL=http://localhost:8080/images/
```

Create a `.env` file inside both `serveme-client/` and `serveme-admin/`:
```env
VITE_API_BASE_URL=http://localhost:8080
```

**2. Install frontend dependencies**
```bash
cd serveme-client && npm install
cd serveme-admin  && npm install
```

**3. Start the full environment**
```bash
start.bat
```

The script runs backend tests, builds the JAR, waits for the backend health check to pass, then starts both frontends automatically.

| Service | URL |
|---|---|
| Backend | http://localhost:8080 |
| Client Frontend | http://localhost:22800 |
| Admin Panel | http://localhost:22801 |

### Architecture

The backend follows a feature-based architecture — each feature contains its own controller, service, repository, entity and DTO layers. The admin frontend mirrors this organization. Data access is handled via `JdbcTemplate` without an ORM, giving full control over SQL queries.

---

*Universidade do Sul de Santa Catarina — UNISUL*