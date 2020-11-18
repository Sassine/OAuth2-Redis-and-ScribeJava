# OAuth2 Usando Redis e ScribeJava 8
*Este é um projeto simples que concentra as principais autenticações OAuth2.0 com pouca configuração pelo yml e utilizando o Redis como banco para as sessions.* 

![Examplo da funcionalidade do projeto](https://i.imgur.com/cyIa5AM.jpg)
**Você precisará:**
-   Java 8 ou superior
-   Maven 3+
- Lombok
-   Redis Server ( veja abaixo como instalar )
> Instalando o Redis Server pelo Docker : 
> 1.  docker pull redis:3.2.5-alpine 
> 2. docker run -d -p 6379:6379 -i -t redis:3.2.5-alpine

**Config ClientId / SecretId** 
No arquivo application.yml contem todas as config de autenticação listados na imagem acima.

```bash
## Para rodar execute o seguinte comando ##
mvn clean spring-boot:run
```