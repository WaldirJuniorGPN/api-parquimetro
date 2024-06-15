# Sistema de Gestão de Parquímetros

## Visão Geral

Este projeto tem como objetivo desenvolver um sistema eficiente e confiável para a gestão de parquímetros em uma cidade movimentada. A solução será construída utilizando o framework Spring, com a linguagem Java (versão 17). O sistema armazenará os dados no banco de dados PostgreSQL, que será executado em um contêiner Docker. O gerenciamento das dependências será realizado com o Maven.

## Estrutura do Projeto

- **Framework:** Spring
- **Linguagem:** Java 17
- **Banco de Dados:** PostgreSQL (Dockerizado)
- **Gerenciador de Dependências:** Maven
- **Containerização:** Docker

## Funcionalidades

1. **Gerenciamento do Tempo de Estacionamento:**
   - Registro do tempo de estacionamento dos veículos.
   - Cálculo dos valores devidos pelo uso dos parquímetros.

2. **Armazenamento de Informações:**
   - Gravação confiável dos dados no banco de dados PostgreSQL.
   - Leitura eficiente dos dados para fins de fiscalização.

3. **Desempenho e Confiabilidade:**
   - Melhoria no desempenho de gravação e leitura dos dados.
   - Redução de atrasos no processamento, proporcionando uma melhor experiência tanto para os motoristas quanto para os agentes de fiscalização.

## Requisitos

- **Java 17**
- **Docker**
- **Maven**
- **PostgreSQL**

## Instruções de Configuração

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados em sua máquina:
- JDK 17
- Docker
- Maven

### Passo a Passo

1. **Clone o Repositório:**

   ```bash
   git clone https://github.com/WaldirJuniorGPN/api-parquimetro.git
   cd sistema-de-parquimetros
   ```

2. **Configuração do Banco de Dados com Docker:**

   - Navegue até o diretório do projeto e encontre o arquivo `Dockerfile` e o arquivo `docker-compose.yml`.
   - Execute o comando abaixo para subir o contêiner do PostgreSQL:

     ```bash
     docker-compose up -d
     ```

3. **Configuração do Maven:**

   - Navegue até o diretório do projeto e execute o comando abaixo para baixar as dependências e compilar o projeto:

     ```bash
     mvn clean install
     ```

4. **Executar a Aplicação:**

   - Com o PostgreSQL rodando em um contêiner Docker, execute a aplicação Spring:

     ```bash
     mvn spring-boot:run
     ```

## Estrutura de Diretórios

```plaintext
sistema-de-parquimetros/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Contribuição

1. Faça um Fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`).
4. Faça push para a branch (`git push origin feature/nova-feature`).
5. Abra um Pull Request.



---

Agradecemos por contribuir para a melhoria do sistema de gestão de parquímetros e esperamos que esta solução beneficie a todos os usuários da cidade!
