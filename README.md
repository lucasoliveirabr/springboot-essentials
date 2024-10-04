<h1 style="font-size: 2em; font-weight: bold; margin: 0;">🍃 Spring Boot Essentials</h1>

Repository that contains practical knowledge about various Spring Framework projects.

## <a name="spring-projects">🌱 Spring projects</a>
- Spring Framework
- Spring Boot 3
- Spring Data JPA
- Spring Security

## <a name="technologies">⚙️ Project technologies</a>

- ☕ Java 17
- 🌱 Spring
- 🌶️ Lombok
- 🐋 Docker and jib
- 🛢️ MySQL (MariaDB) and H2 Database
- 🧪 JUnit and Mockito
- 🔄 MapStruct
- 📄 SpringDoc OpenAPI (Swagger)
- 📄 Actuator, Prometheus and Grafana

## <a name="installing-and-running">🚀 Installing and Running</a>

Follow these steps to set up the project locally on your machine.

**Prerequisites**

Make sure you have the following installed on your machine:

- [Java 17](https://www.azul.com/downloads/?version=java-17-lts#zulu)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [A test API software, such as Postman](https://www.postman.com/downloads/)
- [Git](https://git-scm.com/)


**Cloning the repository**

> ```bash
> git clone https://github.com/lucasoliveirabr/springboot-essentials.git
> cd springboot-essentials
> ```

**Starting the database**

> ```bash
> docker-compose up
> ```

**Running the Project**

> ```bash
> mvn spring-boot:run
> ```

- Open http://localhost:8080/animes in your browser.

**Running Unit Testing and Integration Testing**

> ```bash
> mvn test -P all-tests
> ```

**Extra: Push image to Docker Hub with jib**

> ```bash
> docker login
> ```

> ```bash
> mvn compile jib:build
> ```

## <a name="start">🌐 Endpoints</a>
- Main HTTP request methods
  - GET: Paginated anime search.
    - http://localhost:8080/animes/
    - http://localhost:8080/animes?size=10&sort=id,desc
  - GET: Search all animes.
    - http://localhost:8080/animes/all
  - GET: Search for an anime by ID.
    - http://localhost:8080/animes/{id}
  - POST: Insert a new anime.
    - http://localhost:8080/animes
  - PUT: Update an existing anime.
    - http://localhost:8080/animes
  - DELETE: Delete an anime.
    - http://localhost:8080/animes/{id}

- Other routes
  - SpringDoc OpenAPI (Swagger)
    - http://localhost:8080/swagger-ui/index.html
  - Actuator
    - http://localhost:8080/actuator
  - Prometheus
    - http://localhost:9090
  - Grafana
    - http://localhost:3000