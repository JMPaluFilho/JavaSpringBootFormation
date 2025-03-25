This code was developed as part of the "Java and Spring Boot Formation" course available on Alura, which can be viewed at the following link:\
[Java and Spring Boot Formation](https://cursos.alura.com.br/formacao-spring-boot-3)

In order to run the project, you must install and configure MySQL, as explained in this article:\
[MySQL Download and installation](https://www.alura.com.br/artigos/mysql-do-download-e-instalacao-ate-sua-primeira-tabela?srsltid=AfmBOoqMlVyzJyEmrfd82dwQqjhE1x1hPssgW7Gm6d8UHozPK5C34Mkv)

After installation and configuration, create two schemes, one named clinic_api and another named clinic_api_test:\
CREATE DATABASE clinic_api;\
CREATE DATABASE clinic_api_test;

Subsequently, insert the necessary environment variables into the runtime configuration of the ApiApplication:\
DATASOURCE_LOGIN: your MySQL login\
DATASOURCE_PASSWORD: your MySQL password\
DATASOURCE_URL: jdbc:mysql://localhost/clinic_api\
DATASOURCE_URL_TEST: jdbc:mysql://localhost/clinic_api_test\
JWT_SECRET: your JWT secret

These same variables are also required in the Maven install and package commands. You can create a run configuration for this purpose, for example:\
mvn clean install -DDATASOURCE_LOGIN=your MySQL login -DDATASOURCE_PASSWORD=your MySQL password -DDATASOURCE_URL=jdbc:mysql://localhost/clinic_api -DDATASOURCE_URL_TEST=jdbc:mysql://localhost/clinic_api_test -DJWT_SECRET=your JWT secret\

Once everything is configured, simply run the ApiApplication, and Flyway will perform the necessary migrations.
