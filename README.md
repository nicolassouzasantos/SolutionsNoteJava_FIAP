# Solutions Note

## Visão geral
O Solutions Note é uma aplicação Spring Boot que centraliza o cadastro e o acompanhamento de veículos estacionados em pátios parceiros. O sistema expõe APIs REST para administrar pátios, operadores e automóveis, além de uma interface web construída com Spring MVC e Thymeleaf para operações de CRUD sobre o acervo de automóveis. A aplicação utiliza Spring Data JPA com Oracle Database, validação com Jakarta Bean Validation e migrations Flyway para manter o esquema consistente.

## Sumário
- [Pré-requisitos](#pré-requisitos)
- [Configuração do banco de dados](#configuração-do-banco-de-dados)
- [Execução local](#execução-local)
- [Execução com Docker](#execução-com-docker)
- [APIs REST](#apis-rest)
  - [Pátios](#pátios)
  - [Operadores](#operadores)
  - [Automóveis](#automóveis)
  - [Tratamento de erros](#tratamento-de-erros)
- [Interface web](#interface-web)
- [Testes automatizados](#testes-automatizados)
- [Manutenção e contribuição](#manutenção-e-contribuição)
- [Licença](#licença)
- [Autores](#autores)

## Pré-requisitos
Antes de executar o projeto, garanta que você possui:
- **Java 17** – definido em [`pom.xml`](pom.xml) e requerido tanto para build quanto para execução.
- **Maven 3.9+** ou o wrapper incluído (`./mvnw`).
- **Acesso a um Oracle Database** (local ou remoto) com credenciais válidas.
- **Docker 24+** e **Docker Compose** (opcional, para empacotamento/execução em containers).

## Configuração do banco de dados
As configurações padrão residem em [`src/main/resources/application.properties`](src/main/resources/application.properties) e apontam para o ambiente acadêmico da FIAP. Para executar em outro banco Oracle, sobrescreva as propriedades com variáveis de ambiente ou argumentos de linha de comando:

```bash
export SPRING_DATASOURCE_URL="jdbc:oracle:thin:@host:1521/NOME_DO_SERVICO"
export SPRING_DATASOURCE_USERNAME="usuario"
export SPRING_DATASOURCE_PASSWORD="senha"
./mvnw spring-boot:run
```

Outras opções úteis:
- `SPRING_JPA_SHOW_SQL=false` para suprimir logs SQL.
- `SPRING_FLYWAY_BASELINE_ON_MIGRATE=true` caso esteja inicializando em um schema existente.

### Migrations Flyway
O projeto traz duas migrations (pasta [`src/main/resources/db/migration`](src/main/resources/db/migration)):
- `V1__criar_tabelas_basicas.sql` cria as tabelas `PATIO`, `AUTOMOVEL` e `OPERADOR`, além dos índices necessários.
- `V2__seed_inicial.sql` popula o banco com pátios, operadores e automóveis de exemplo.

As migrations são aplicadas automaticamente ao subir a aplicação quando o usuário configurado possui permissões de DDL.

## Execução local
1. Clone o repositório e acesse o diretório do projeto.
2. Compile e execute diretamente com o Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   A aplicação ficará disponível em `http://localhost:8080`.
3. Para gerar o artefato:
   ```bash
   ./mvnw clean package
   java -jar target/note-0.0.1-SNAPSHOT.jar
   ```

## Execução com Docker
### Build e run diretos
```bash
docker build -t solutions-note .
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:oracle:thin:@host:1521/NOME_DO_SERVICO" \
  -e SPRING_DATASOURCE_USERNAME="usuario" \
  -e SPRING_DATASOURCE_PASSWORD="senha" \
  solutions-note
```

### Docker Compose
O arquivo [`docker-compose.yaml`](docker-compose.yaml) constrói a imagem localmente e publica a porta 8080:
```bash
docker compose up --build
```
Ajuste as variáveis de ambiente dentro do compose ou via arquivo `.env` para conectar ao seu banco Oracle.

## APIs REST
Todas as respostas são em JSON e utilizam validação automática das entidades.

### Pátios
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/patios` | Cria um pátio. Campos aceitos: `nome` (obrigatório) e `endereco`. |
| `GET`  | `/patios` | Lista todos os pátios cadastrados. |

### Operadores
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/operadores` | Cria um operador com `nome`, `login` e `senha` obrigatórios. |
| `GET`  | `/operadores` | Lista todos os operadores. |

### Automóveis
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/automoveis` | Cadastra um automóvel. Campos obrigatórios: `placa`, `chassi`, `tipo` e `patioId`. Campos opcionais: `cor`, `localizacaoNoPatio`, `comentarios`. |
| `GET`  | `/automoveis` | Lista automóveis com suporte a paginação (`page`, `size`) e ordenação (`sort`). Aceita o parâmetro opcional `tipo` para filtrar por categoria. |

### Tratamento de erros
Validações de campo incorretas retornam `400 Bad Request` com a lista de erros de validação. Erros inesperados são tratados pelo [`GlobalExceptionHandler`](src/main/java/br/com/solutionsnote/note/exception/GlobalExceptionHandler.java), retornando uma mensagem amigável e o `HTTP status` apropriado.

## Interface web
A interface MVC está disponível em `http://localhost:8080/automoveis-ui` e é composta por:
- Listagem de automóveis com ordenação por placa.
- Formulário de criação e edição com validação servidor/cliente.
- Para testes na página de login utilize:
Login: admin
Senha: admin123
- Ações de exclusão com feedback visual via flash messages.

Os templates residem em [`src/main/resources/templates/automoveis`](src/main/resources/templates/automoveis) e utilizam fragmentos comuns definidos em [`templates/fragments/layout.html`](src/main/resources/templates/fragments/layout.html). As opções de pátio são carregadas dinamicamente a partir do repositório, garantindo consistência entre a interface e as APIs.

## Testes automatizados
Execute os testes com:
```bash
./mvnw test
```

## Licença
Este é um projeto acadêmico sem licença definida. Caso deseje reutilizar, consulte os autores.

## Autores
- Nicolas Souza dos Santos - rm555571
- Oscar Arias Neto - rm556936
- Julia Martins Rebelles - rm554516
