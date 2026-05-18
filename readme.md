# 🚗 Ford Competitive Intelligence — CP3 SOA

**CP3 - Arquitetura Orientada a Serviços | Turma: 3ESPX**

**Desenvolvido por:**
- Carlos Henrique — RM558003
- Mauricio Alves — RM556214
- Ian Monteiro — RM558652
- Bruno Silva — RM550416
- João Hoffmann — RM550763

---

## 📌 Contexto de Implantação

O **Ford Competitive Intelligence** é uma API desenvolvida como parte do **Desafio 1 — Inteligência Competitiva Automotiva** do programa Ford FIAP 2026.

O mercado automotivo exige compreensão rápida de como os veículos concorrentes se posicionam em termos de especificações técnicas e preços. Hoje, esse processo é manual, demorado e sujeito a inconsistências. Este sistema oferece uma solução padronizada para cadastro, consulta e comparação de especificações técnicas de veículos.

---

## ❗ Problemas que o sistema resolve

| Problema | Como o sistema resolve |
|---|---|
| Especificações técnicas em formatos inconsistentes | Estrutura padronizada com atributo, valor e unidade |
| Comparação manual e demorada entre concorrentes | Endpoint dedicado que compara dois veículos lado a lado |
| Dados dispersos em planilhas | Persistência centralizada em banco Oracle |
| Falta de integração com outros sistemas | API REST + SOAP para consumo por qualquer plataforma |
| Atributos ausentes não identificados | Campos ausentes retornam "Não disponível" na comparação |

---

## 🧱 Estrutura do Projeto

```
ford-competitive-intel/
│
├── pom.xml
├── README.md
│
└── src/
    ├── main/
    │   ├── java/br/com/fiap/ford/
    │   │   ├── FordApplication.java
    │   │   ├── config/
    │   │   │   └── CxfConfig.java              → Configuração Apache CXF (SOAP)
    │   │   ├── controller/
    │   │   │   └── VeiculoController.java       → REST API (@RestController)
    │   │   ├── dto/
    │   │   │   ├── VeiculoDTO.java              → Request, Response, Resumo
    │   │   │   ├── EspecificacaoDTO.java        → Request, Response
    │   │   │   └── ComparacaoDTO.java           → Resultado da comparação
    │   │   ├── exception/
    │   │   │   ├── GlobalExceptionHandler.java  → Tratamento centralizado de erros
    │   │   │   ├── VeiculoNaoEncontradoException.java
    │   │   │   ├── VeiculoDuplicadoException.java
    │   │   │   └── EspecificacaoDuplicadaException.java
    │   │   ├── model/
    │   │   │   ├── Veiculo.java                 → Entidade JPA
    │   │   │   └── Especificacao.java           → Entidade JPA
    │   │   ├── repository/
    │   │   │   ├── VeiculoRepository.java       → Spring Data JPA
    │   │   │   └── EspecificacaoRepository.java → Spring Data JPA
    │   │   ├── service/
    │   │   │   ├── IVeiculoService.java         → Contrato de negócio
    │   │   │   └── VeiculoService.java          → Implementação
    │   │   └── soap/
    │   │       └── VeiculoSoapService.java      → WebService SOAP (@WebService)
    │   │
    │   └── resources/
    │       ├── application.properties           → Config Oracle + Flyway
    │       └── db/migration/
    │           ├── V1__create_veiculos.sql      → Tabela veiculos
    │           ├── V2__create_especificacoes.sql → Tabela especificacoes
    │           └── V3__seed_ford_ranger_raptor.sql → Dados iniciais
```

---

## ⚙️ Dependências Maven

| Dependência | Função |
|---|---|
| `spring-boot-starter-web` | REST API com Spring MVC |
| `spring-boot-starter-data-jpa` | Persistência com Oracle via Hibernate |
| `spring-boot-starter-validation` | Validação de entrada com Bean Validation |
| `ojdbc11` | Driver JDBC Oracle |
| `flyway-database-oracle` | Controle de migrações do banco |
| `cxf-spring-boot-starter-jaxws` | WebService SOAP com Apache CXF |
| `lombok` | Redução de boilerplate |

---

## 🔧 Como executar

### Pré-requisitos
- Java 21
- Maven
- Oracle Database (local ou em nuvem)

### 1. Configurar o banco Oracle

Edite `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=ford_user
spring.datasource.password=ford_pass
```

### 2. Criar o usuário Oracle (execute como SYSDBA)
```sql
CREATE USER ford_user IDENTIFIED BY ford_pass;
GRANT CONNECT, RESOURCE TO ford_user;
GRANT UNLIMITED TABLESPACE TO ford_user;
```

### 3. Compilar e subir
```bash
mvn clean install
mvn spring-boot:run
```

O Flyway criará automaticamente as tabelas e inserirá os dados iniciais.

### 4. Verificar endpoints
```
REST: http://localhost:8080/api/veiculos
SOAP: http://localhost:8080/soap/veiculo?wsdl
```

---

## 🗂️ Regras de Negócio

- ✅ Combinação marca + modelo + versão + ano deve ser única
- ✅ Cada veículo pode ter apenas uma especificação por atributo
- ✅ Atributos ausentes na comparação retornam "Não disponível"
- ✅ Exclusão de veículo remove automaticamente suas especificações (CASCADE)
- ✅ Dados iniciais incluem Ford Ranger Raptor 2025 (caso de validação Ford)

---

## 🌐 API REST — Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/veiculos` | Lista todos os veículos |
| `GET` | `/api/veiculos/{id}` | Detalhes de um veículo com specs |
| `GET` | `/api/veiculos/buscar?marca=&modelo=&versao=&ano=` | Busca por filtro exato |
| `GET` | `/api/veiculos/pesquisar?termo=` | Busca por termo livre |
| `POST` | `/api/veiculos` | Cadastra novo veículo |
| `POST` | `/api/veiculos/{id}/especificacoes` | Adiciona especificação |
| `GET` | `/api/veiculos/{id}/especificacoes` | Lista especificações |
| `GET` | `/api/veiculos/comparar?veiculoAId=&veiculoBId=` | Compara dois veículos |
| `DELETE` | `/api/veiculos/{id}` | Remove veículo |

---

## 🧼 SOAP — Operações

| Operação | Descrição |
|---|---|
| `listarVeiculos()` | Lista todos os veículos |
| `buscarVeiculoPorId(id)` | Busca por ID |
| `buscarVeiculoPorFiltro(marca, modelo, versao, ano)` | Busca por filtro |
| `cadastrarVeiculo(marca, modelo, versao, ano, categoria)` | Cadastra veículo |
| `adicionarEspecificacao(veiculoId, atributo, valor, unidade, categoria)` | Adiciona spec |
| `listarEspecificacoes(veiculoId)` | Lista specs do veículo |
| `compararVeiculos(veiculoAId, veiculoBId)` | Compara dois veículos |

---

## ✅ Boas Práticas Aplicadas

| Prática | Descrição |
|---|---|
| **REST + SOAP** | Dois protocolos para máxima interoperabilidade |
| **Arquitetura em camadas** | controller, service, repository, model, dto, exception |
| **DTOs separados** | Desacoplamento entre camada de API e domínio |
| **Spring Data JPA** | Repositórios declarativos sem SQL manual |
| **Flyway** | Migrações versionadas e rastreáveis do banco |
| **Bean Validation** | Validação automática nas entradas REST |
| **GlobalExceptionHandler** | Respostas de erro padronizadas em JSON |
| **Interface de serviço** | `IVeiculoService` desacopla SOAP e REST da implementação |
| **Transações** | `@Transactional` nas operações de escrita |
| **Logs estruturados** | Lombok `@Slf4j` em todas as camadas |

---

## 🔄 Arquitetura do Sistema

```
Cliente (REST / SOAP / Postman)
            ↓
┌─────────────────────────────────┐
│  VeiculoController (REST)       │ → /api/veiculos/**
│  VeiculoSoapService (SOAP/CXF)  │ → /soap/veiculo
└─────────────┬───────────────────┘
              ↓
       IVeiculoService
              ↓
       VeiculoService
      ↙              ↘
VeiculoRepository  EspecificacaoRepository
      ↓                    ↓
         Oracle Database
         (Flyway migrations)
```

---

## 🚀 Próximas Features

- [ ] Autenticação JWT para proteção dos endpoints
- [ ] Importação em lote de especificações via CSV/Excel
- [ ] Histórico de alterações de especificações
- [ ] Relatório de comparação exportável em PDF
- [ ] Integração com API de precificação de veículos
- [ ] Cache Redis para comparações frequentes

---

## 👨‍💻 Tecnologias

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA + Hibernate
- Oracle Database (ojdbc11)
- Flyway
- Apache CXF 4.0.4 (SOAP)
- Lombok
- Maven