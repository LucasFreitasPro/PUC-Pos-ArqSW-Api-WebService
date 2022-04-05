# Trabalho Final APIs e WebServices

## Professor

Vinicius Luiz do Amaral

## Alunos
Allan David Silva Onofre de Castro – 140609

Lucas Freitas Paula Pereira - 142768

Weslley Guilherme Lage - 139099

## Objetivo
Implementação prática de uma API REST.

## Premissas assumidas
- Focamos na aplicação dos conceitos RESTful;
- Objetivo é atingir o nível 3 de maturidade;
- Os nomes do Time e do Jogador são únicos;
- Um jogador só existe dentro de um time;
- Um campeonato existe quando relacionado a uma divisão;
- Uma partida só ocorre dentro de uma temporada.
- Campeonatos podem nomes iguais desde que sejam de divisões diferentes;
- Código 404 será retornado somente para chamadas HTTP GET. Com isso, para o caso de cadastro de um jogador em um time (HTTP POST) que não existe, o código retornado será 409.
- Chamadas HTTP DELETE sempre retornam código 204. Dessa forma, garantimos a característica idempotente;
- O verbo HTTP PUT não será utilizado, uma vez que as atualizações serão parciais. Para isso, utilizaremos o HTTP PATCH. Como não é idempotente diferentes códigos podem ser retornados;
- As chamadas HTTP POST para o sub recurso /events tem como código de retorno o 202 Accepted.

## Sobre Linguagem/Framework
Utilizada a linguagem de programação Java com Spring Framework. 

Foi utilizado o Spring Boot para construção dos Microsserviços, Spring Data JPA para acesso ao banco de dados e o Spring HATEOAS para a geração dos links, Spring Sleuth, Spring Wavefront.

## Sobre a Persistência
Utilizado o banco de dados relacional PostgreSQL.

## Apresentação
- 1 parte - Modelo Arquitetural: https://youtu.be/2dzdBKtCSyY
- 2 parte - Premissas e Evidências: https://youtu.be/rO18vWZe17o
