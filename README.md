# **Birth Chart API Calculator**

## PRÉ REQUISITOS
Para inicializar o projeto será necessária a instalaçao das seguintes ferramentas na sua máquina:
 - Docker

## START NA APLICAÇAO
Primeiro, clone o meu repositório
```
git clone https://github.com/andressa-raffler/Astrology.git
```

Após clonar o projeto, navege até a pasta astrology (pasta que contem o arquivo docker-compose.yml) e suba o container do docker com o comando: 
```
docker-compose up -d
```

O comando deverá rodar uma instancia do postgres com os seguintes dados de acesso:
```
URL: jdbc:postgresql://db:5432/postgres
Username: postgres
Password: postgres

```
Os dados acima podem ser alterados conforme preferencia, para isso, basta alterar o arquivo docker-compose.yml

O docker também irá rodar uma imagem do NGINX para o frontend que ficará disponível na seguinte URL:
```
http://localhost:8081/
```



A seguinte página deverá ser exibida:

![img.png](img.png)

Crie entao um novo usuário para posteriormente poder logar na aplicaçao
Após logar será possível criar, visualizar, editar e deletar mapas astrais

![img_1.png](img_1.png)

![img_2.png](img_2.png)



# Projeto desenvolvido com as seguintes tecnologias:

## Backend:
  - Java;
  - SpringBoot;
  - Postgres SQL;
  - Docker;
  - REST API;
  - MapStruct;

## Frontend:
  - JavaScript;
  - HTML;
  - CSS;
