# API de Autenticação com JWT em Spring Boot

Este projeto é uma API RESTful desenvolvida com Spring Boot que implementa autenticação e autorização utilizando tokens JWT (JSON Web Token).

## 📋 Funcionalidades

- Registro de usuários
- Autenticação via JWT
- Gerenciamento de usuários (listar, buscar por ID, atualizar, excluir)
- Proteção de rotas usando Spring Security
- Persistência de dados com JPA/Hibernate
- Banco de dados H2 em memória para facilitar testes

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.4
- Spring Security
- JSON Web Token (JWT)
- Spring Data JPA
- H2 Database
- Maven

## 🏗️ Estrutura do Projeto

```
src/main/java/estudo/api/jwt/projeto/
├── config/                # Configurações do Spring
│   └── SecurityConfig.java
├── controller/            # Controladores REST
│   ├── AuthController.java
│   └── UserController.java
├── dto/                   # Objetos de Transferência de Dados
│   ├── AuthenticationRequestDTO.java
│   ├── AuthenticationResponseDTO.java
│   └── UserDTO.java
├── model/                 # Entidades JPA
│   └── UserModel.java
├── repository/            # Repositórios de dados
│   └── UserRepository.java
├── security/              # Classes relacionadas à segurança
│   ├── JwtRequestFilter.java
│   ├── JwtUtil.java
│   └── UserDetailsServiceImpl.java
├── service/               # Camada de serviço
│   └── UserService.java
└── ProjetoApplication.java  # Classe principal
```

## 🚀 Como Executar

### Pré-requisitos

- JDK 21
- Maven

### Passos para Execução

1. Clone o repositório
   ```
   git clone https://github.com/gustavokurtz/jwt-validation.git
   ```

2. Navegue até o diretório do projeto
   ```
   cd jwt-validation
   ```

3. Execute a aplicação
   ```
   ./mvnw spring-boot:run
   ```
   ou no Windows:
   ```
   mvnw.cmd spring-boot:run
   ```

4. A aplicação estará disponível em `http://localhost:8080`

## 🔒 Endpoints da API

### Autenticação

- **Registro de usuário**
  - POST `/api/users/register`
  - Body: `{ "nome": "usuario", "password": "senha123" }`

- **Login (obter token JWT)**
  - POST `/api/auth/login`
  - Body: `{ "nome": "usuario", "password": "senha123" }`
  - Resposta: `{ "jwt": "token..." }`

### Gerenciamento de Usuários (requer autenticação)

- **Listar todos os usuários**
  - GET `/api/users`
  - Header: `Authorization: Bearer [token]`

- **Buscar usuário por ID**
  - GET `/api/users/{id}`
  - Header: `Authorization: Bearer [token]`

- **Atualizar usuário**
  - PUT `/api/users/{id}`
  - Header: `Authorization: Bearer [token]`
  - Body: `{ "nome": "novoNome", "password": "novaSenha" }`

- **Excluir usuário**
  - DELETE `/api/users/{id}`
  - Header: `Authorization: Bearer [token]`

## 🔐 Fluxo de Autenticação

1. O usuário se registra fornecendo nome e senha
2. O usuário realiza login com suas credenciais
3. Ao realizar login bem-sucedido, o servidor gera um token JWT
4. O usuário inclui esse token no cabeçalho `Authorization` nas requisições subsequentes
5. O servidor valida o token e permite acesso aos recursos protegidos se o token for válido

## ⚙️ Configuração

As principais configurações podem ser ajustadas no arquivo `application.properties`:

```properties
# Configuração do JWT
jwt.secret=suachavesecreta
jwt.expiration=3600000  # 1 hora em milissegundos

# Configuração do banco de dados H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

## 🤝 Integração com Frontend

Para integrar esta API com uma aplicação frontend:

1. Implemente formulários de registro e login no frontend
2. Ao receber o token JWT do endpoint de login, armazene-o localmente (localStorage, sessionStorage)
3. Inclua o token em todas as requisições subsequentes no cabeçalho `Authorization: Bearer [token]`
4. Implemente lógica para redirecionar para a página de login quando o token expirar ou for inválido

## 🛠️ Melhorias Futuras

- Implementação de refresh tokens
- Adição de perfis de acesso (ROLE_USER, ROLE_ADMIN)
- Validação mais robusta de dados de entrada
- Implementação de logout (blacklist de tokens)
- Testes automatizados
- Documentação com Swagger/OpenAPI
