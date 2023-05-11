# **Birth Chart API Calculator**

## REQUIREMENTS
Before starting the project, make sure you have the following tools installed on your computer:

- Docker
- Java 17
- Maven 4.0

These tools are essential for initializing and running the application. If any of these requirements are not met, 
the project may not run correctly.

To ensure the smooth execution of the project, we recommend verifying the version of each tool before proceeding with 
the installation. This will help to prevent any potential compatibility issues and ensure that the application runs smoothly.

## STARTING THE APPLICATION
To get started, first clone the repository:

```
git clone https://github.com/andressa-raffler/Astrology.git
```

After cloning the repository, you will need to build the project locally. Navigate to the astrology folder 
(the folder containing the docker-compose.yml file) and run the following command:
```
mvn clean install
```

Once the build is complete, the target folder will be created. With Docker running, start the project containers using 
the command:
```
docker-compose up -d
```
This command will run a Postgres instance with the following access data:
```
URL: jdbc:postgresql://db:5432/postgres
Username: postgres
Password: postgres
```
The above data can be changed according to your preference. To do so, simply modify the docker-compose.yml file.

Docker will also run an NGINX image for the frontend, which will be available at the following URL:
```
http://localhost:8081/
```
Now, navigate to the target folder and run the Java command to start the backend:
```
cd target
java -jar astrology.jar
```

When you access localhost, the following page should be displayed:

![img.png](images/img.png)

You can then create a new user to be able to log in to the application later. Once logged in, it will be possible to 
create, view, edit, and delete birth charts.

![img_1.png](images/img_1.png)

![img_2.png](images/img_2.png)



# TECHNOLOGIES

## Backend:
  - Java 17;
  - SpringBoot;
  - Docker;
  - REST API;
  - MapStruct;
  - Mockito;
  - JUnit;
  - Hibernate;
  - JsonWebToken;
  - Spring Security;
  - Lombok.

## Frontend:
  - JavaScript;
  - HTML;
  - CSS;

## BD:
  - Tests: H2
  - Repository: Postgres
