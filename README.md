# Projeto Desafio Soft Design

Este projeto é uma solução que visa facilitar o gerenciamento e a participação em sessões de votação cooperativa. No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Nossa solução permite que os associados participem dessas sessões de votação de maneira eficiente e transparente.

A solução é uma REST API que é executada na nuvem e oferece uma série de funcionalidades para gerenciar e participar de sessões de votação.

## Ferramentas e Tecnologias Utilizadas

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html): A linguagem de programação usada para desenvolver o projeto.
- [Spring Boot 3.1.11](https://spring.io/projects/spring-boot): Framework usado para facilitar o setup e desenvolvimento de aplicações Spring.
- [IntelliJ IDEA 2024.1.1](https://www.jetbrains.com/idea/): IDE utilizada para o desenvolvimento do projeto.
- [Maven](https://maven.apache.org/): Ferramenta de gerenciamento de dependências e build.
- [Git](https://git-scm.com/): Sistema de controle de versão distribuído.
- [Postman](https://www.postman.com/): Ferramenta utilizada para testar as APIs desenvolvidas.
- [Flyway](https://flywaydb.org/): Ferramenta de migração de banco de dados.
- [RDS - PostgreSQL](https://aws.amazon.com/rds/postgresql/): Serviço de banco de dados relacional da AWS.
- [EC2 - Elastic Compute Cloud](https://aws.amazon.com/ec2/): Serviço web que fornece capacidade de computação segura e redimensionável na nuvem. É usado para hospedar a aplicação.
- [FIS - Fault Injection Simulator](https://aws.amazon.com/fis/): Serviço da AWS que ajuda a melhorar a resiliência de aplicativos simulando falhas e monitorando as respostas.
- [PuTTY](https://www.putty.org/): Um cliente de terminal livre e de código aberto, serial console e aplicativo de transferência de arquivos de rede. É usado para acessar servidores remotos através de SSH.
- [JUnit](https://junit.org/junit5/): Framework de testes para Java.
- [JPA](https://spring.io/projects/spring-data-jpa): Framework que facilita o mapeamento de objetos com tabelas de banco de dados.
- [Lombok](https://projectlombok.org/): Biblioteca Java que ajuda a reduzir a verbosidade do código.

## Funcionalidades

A API REST fornece as seguintes funcionalidades:

1. Criação de novas sessões de votação.
2. Participação em sessões de votação existentes.
3. Gerenciamento de votos dos associados.
4. Visualização dos resultados das sessões de votação.

## Endpoints
Todo o projeto está rodando em nuvem, com a seguinte URL:
```
http://ec2-18-231-116-58.sa-east-1.compute.amazonaws.com:8080
```
Dentro do projeto, há um arquivo chamado "DesafioSoftDesing.postman_collection.json", que pode ser importado no Postman para testar os endpoints. Além disso, documentei alguns endpoints em um arquivo swagger.yaml, o qual pode ser importado no site https://editor.swagger.io/ para visualizar a documentação da API ou na própria IDE.
## Tarefa Bônus 1 - Integração com sistemas externos

O link para a API do Heroku fornecido estava indisponível (não sei se foi um problema temporário ou se a API foi retirada do ar). Considerando que solucionar problemas faz parte do dia a dia de um desenvolvedor, usei a anotação @CPF do Hibernate Validator para validar o CPF do associado.

Criei um associado para poder continuar com o desafio. Além de validar o CPF, ele também possui um atributo para indicar se tem poder de voto e se está inativo.

## Tarefa Bônus 2 - Performance
Para garantir que a aplicação de votação se comporte de maneira performática em cenários com centenas de milhares de votos, podemos usar o AWS Fault Injection Simulator (FIS) para realizar testes de resiliência e performance.

Simulação de Alta Carga: Podemos usar AWS FIS para injetar cargas de trabalho simuladas na aplicação, emulando centenas de milhares de votos simultâneos. Isso ajuda a observar como a aplicação lida com alta demanda e identificar possíveis gargalos.

Injeção de Falhas Controladas: Com o AWS FIS, podemos simular falhas específicas, como latência de rede aumentada, indisponibilidade de instâncias ou falhas de banco de dados. Isso permite avaliar a robustez da aplicação sob condições adversas e assegurar que ela continua performática.

Monitoramento em Tempo Real: Podemos integrar o AWS FIS com o Amazon CloudWatch para monitorar métricas de performance em tempo real durante os experimentos. Isso inclui uso de CPU, memória, latência de resposta, taxa de erros.

## Tarefa Bônus 3 - Versionamento da API
Para versionar a aplicação de votação, eu usaria as seguintes práticas:
Escolher um Esquema de Versionamento: O Semantic Versioning é comum, com MAJOR.MINOR.PATCH indicando mudanças incompatíveis, novas funcionalidades compatíveis e correções de bugs.
Configure o Controle de Versão: Git para gerenciar as mudanças no código-fonte, inicializando um repositório, adicionando arquivos, fazendo commits e marcando versões com tags.
Automatize o Versionamento: Ferramentas de CI/CD podem automatizar o versionamento, baseando-se em convenções de commits, como Conventional Commits, para determinar a próxima versão.
Especifique a Versão no Build: Inclua a versão no build da aplicação, seja em um arquivo pom.xml para Maven.
Releases e Tags: Ao criar um novo lançamento, adicionar uma tag no repositório Git para marcar a versão.
Integração Continua e Deploy: Configurar pipelines de CI/CD para testar, versionar e fazer deploy automático da aplicação, garantindo um processo contínuo e eficiente.

