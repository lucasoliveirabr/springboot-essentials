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
- 🛢️ MySQL (MariaDB) and H2 Database
- 💤 Hibernate
- 🧪 JUnit and Mockito
- 🔄 MapStruct
- 📄 SpringDoc OpenAPI (Swagger)

## <a name="start">🚀 Run the project</a>

Follow these steps to set up the project locally on your machine.

**Prerequisites**

Make sure you have the following installed on your machine:

- [Java 17](https://www.azul.com/downloads/?version=java-17-lts#zulu)
- [A Java IDE, such as IntelliJ](https://www.jetbrains.com/idea/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [A test API software, such as Postman](https://www.postman.com/downloads/)
- [Git](https://git-scm.com/)


**Cloning the repository**

```bash
git clone https://github.com/lucasoliveirabr/springboot-essentials.git
cd springboot-essentials
```

**Starting the database**

```bash
docker-compose up
```

**Running the Project**

Run the `SpringbootEssentialsApplication` class in the IDE.

**Running Unit Testing and Integration Testing**

```bash
mvn test -P all-tests
```