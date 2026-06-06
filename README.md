# 📚 API de Gerenciamento de Biblioteca v2.0

Evolução da [API de Biblioteca v1.0](https://github.com/rick-rocha/api-biblioteca-spring), agora com arquitetura em camadas, sistema completo de locações, controle de estoque automático e tratamento de erros padronizado.

Este projeto representa uma evolução significativa em relação à versão anterior, incorporando boas práticas de desenvolvimento back-end como separação de responsabilidades, DTOs, validações e regras de negócio robustas.

---

## ✨ Funcionalidades

### Livros
- CRUD completo de livros
- Suporte a múltiplos autores via `@ElementCollection`
- Controle de estoque por quantidade de exemplares
- Status automático: `ESTOQUE`, `LOCADO`, `INDISPONIVEL`
- Busca avançada combinada por múltiplos filtros simultâneos (título, categoria, autor, ano, status)

### Usuários
- Cadastro de usuários com validação de CPF e e-mail
- Bloqueio de duplicatas de CPF e e-mail
- CRUD completo de usuários

### Locações
- Registro de locação com prazo em dias, taxa de locação e taxa de atraso
- Bloqueio automático de locação quando não há exemplares disponíveis
- Atualização automática do estoque ao locar e devolver
- Registro de devolução com cálculo automático de multa por atraso
- Listagem de locações em aberto, em atraso e por usuário

---

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura em 3 camadas:

```
Controller → Service → Repository
```

- **Controller** — recebe as requisições HTTP e delega ao Service
- **Service** — contém todas as regras de negócio
- **Repository** — responsável pela comunicação com o banco de dados

Outros padrões aplicados:
- **DTOs** — separação entre o que a API recebe, processa e retorna
- **Enum** — controle de status do livro com valores fixos e tipados
- **GlobalExceptionHandler** — tratamento centralizado de erros com respostas padronizadas
- **Specification** — busca combinada e dinâmica com JPA Criteria API

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4 (Web, Data JPA, Validation)
- PostgreSQL
- Hibernate
- Lombok
- Maven
- Postman

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java JDK 17 ou superior
- PostgreSQL instalado e rodando
- Maven

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/rick-rocha/api-biblioteca-v2.git
```

2. Crie o banco de dados no PostgreSQL:
```sql
CREATE DATABASE bibliotecadb;
```

3. Configure as variáveis de ambiente ou edite o `application.properties`:
```properties
DB_URL=jdbc:postgresql://localhost:5432/bibliotecadb
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

4. Execute o projeto:
```bash
./mvnw spring-boot:run
```

O Hibernate criará as tabelas automaticamente ao subir a aplicação.

---

## 📡 Endpoints

### Livros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/livros` | Lista todos os livros |
| GET | `/livros/{id}` | Busca livro por ID |
| GET | `/livros/busca?titulo=&categoria=&autor=&anoPublicacao=&status=` | Busca combinada |
| POST | `/livros` | Cadastra novo livro |
| PUT | `/livros/{id}` | Atualiza livro |
| DELETE | `/livros/{id}` | Remove livro |

### Usuários
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios` | Lista todos os usuários |
| GET | `/usuarios/{id}` | Busca usuário por ID |
| POST | `/usuarios` | Cadastra novo usuário |
| PUT | `/usuarios/{id}` | Atualiza usuário |
| DELETE | `/usuarios/{id}` | Remove usuário |

### Locações
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/locacoes` | Lista todas as locações |
| GET | `/locacoes/em-aberto` | Lista locações em aberto |
| GET | `/locacoes/em-atraso` | Lista locações em atraso |
| GET | `/locacoes/usuario/{id}` | Lista locações por usuário |
| POST | `/locacoes` | Registra nova locação |
| PATCH | `/locacoes/{id}/devolver` | Registra devolução |

---

## 📂 Estrutura do Projeto

```
src/main/java/com/biblioteca/api_biblioteca/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── enums/
├── exception/
└── specification/
```

---

## 🔗 Versão Anterior

Este projeto é a evolução direta da [API de Biblioteca v1.0](https://github.com/rick-rocha/api-biblioteca-spring), que continha o CRUD básico de livros. A v2.0 foi construída aplicando boas práticas de arquitetura e novas funcionalidades de negócio.
