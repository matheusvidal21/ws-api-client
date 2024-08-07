# ws-api-client

API RESTful para atender as demandas do cliente.

## Sumário

- [Sobre](#sobre)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Uso](#uso)
- [Endpoints](#endpoints)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Sobre

O projeto `ws-api-client` é uma API RESTful desenvolvida para gerenciar autenticações, pagamentos, tipos de assinatura e usuários. Ele utiliza diversas tecnologias e frameworks para fornecer uma solução completa e eficiente.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.1
- Maven
- Docker
- MySQL
- MongoDB
- Redis
- JWT (Json Web Token)
- Flyway

## Instalação

### Pré-requisitos

- Docker e Docker Compose
- Java 17+
- Maven

### Passos

1. Clone o repositório:
   
    ```bash
    git clone https://github.com/seu-usuario/ws-api-client.git
    cd ws-api-client
    ```
3. Configure o Docker Compose para iniciar os serviços necessários (MySQL, MongoDB, Redis):
   
    ```
   bash docker-compose up -d
    ```
4. Compile e construa o projeto com Maven:
   
    ```
    bash mvn clean install
   ```
6. Inicie a aplicação:
   
    ```
   bash mvn spring-boot:run
    ```

## Uso

A API fornece vários endpoints para gerenciar autenticações, pagamentos, tipos de assinatura e usuários.

### Exemplos de Requisições

#### Autenticação

```bash
POST /auth
```
Body:
```json
{
  "username": "seu-usuario",
  "password": "sua-senha"
}
```
#### Enviar Código de Recuperação

```bash
POST /auth/recovery-code/send
```
Body:
```json
{
  "email": "seu-email@example.com"
}
```
## Endpoints

### Autenticação

- `POST /auth` - Autenticar usuário e obter token JWT.
- `POST /auth/recovery-code/send` - Enviar código de recuperação para o e-mail.
- `GET /auth/recovery-code` - Verificar se o código de recuperação é válido.
- `PATCH /auth/recovery-code/password` - Atualizar senha utilizando o código de recuperação.

### Pagamento

- `POST /payment/process` - Processar pagamento.

### Tipo de Assinatura

- `GET /subscription-type` - Listar todos os tipos de assinatura.
- `GET /subscription-type/{id}` - Obter tipo de assinatura por ID.
- `POST /subscription-type` - Criar novo tipo de assinatura.
- `PUT /subscription-type/{id}` - Atualizar tipo de assinatura.
- `DELETE /subscription-type/{id}` - Deletar tipo de assinatura.

### Usuário

- `POST /users\` - Criar novo usuário.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests. Para grandes mudanças, por favor abra uma issue primeiro para discutir o que você gostaria de mudar.

1. Fork o repositório
2. Crie sua feature branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request
