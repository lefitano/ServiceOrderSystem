# Criando o primeiro usuário ADMIN (seed manual)

A API **nunca** cria usuários `ADMIN`: nem o cadastro público de clientes, nem o `POST /usuarios`
(que é restrito a ADMIN e também recusa a função `ADMIN`). O primeiro administrador é inserido
direto no banco.

## 1. Gerar o comando SQL

Na pasta `barberflow`, no PowerShell:

```powershell
.\scripts\gerar-hash-admin.ps1 -Nome "Seu Nome" -Email "voce@barbearia.com"
```

A senha é pedida no terminal (não aparece na tela) e deve ter de 8 a 72 caracteres.
O script imprime um `INSERT` com o hash BCrypt, algo como:

```sql
INSERT INTO usuario (nome, email, senha_hash, funcao, criado_em)
VALUES ('Seu Nome', 'voce@barbearia.com', '$2a$10$...', 'ADMIN', NOW());
```

## 2. Executar no MySQL

Rode o `INSERT` gerado no banco `SOS` (MySQL Workbench ou cliente de linha de comando).

## 3. Entrar na API

```
POST /auth/login
{ "email": "voce@barbearia.com", "senha": "a senha escolhida" }
```

A resposta traz o `token`. Use-o nas demais chamadas:
`Authorization: Bearer <token>`.

## Configuração necessária (`.env`)

```
BARBERFLOW_DB_PASSWORD=...
BARBERFLOW_JWT_SECRET=...   # mínimo 32 caracteres; gere com: openssl rand -hex 32
```

Veja `.env.example`. O token expira em 60 minutos (`barberflow.jwt.expiration-minutes`).
