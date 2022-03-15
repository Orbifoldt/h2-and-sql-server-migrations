# h2-and-sql-server-migrations Project
To start this project such that it uses MS SQL server I added a [`docker-compose.yml`](docker-compose.yml) file. Initially you need to create a database, so first get access to `sqlcmd` inside the container as follows:
```bash
docker-compose up
docker exec -it sql-server-db "bash"
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "NiCe_pAsSword123*"
```
Then create the database by executing below:
```sql
CREATE DATABASE myDB;
GO;
```
After this you can uncomment the mssql settings in the [`application.yml`](src/main/resources/application.yml). You can also run the integration test using this database, but you best comment out the tests that use DBUnit, i.e. those annotated by `@DataSet`. Be aware that the test data will get persisted.

To see the content of the DB simply run this query:
```sql
USE myDB;
SELECT * FROM myTable;
GO;
```



## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

(project generated with https://code.quarkus.io/?a=h2-and-sql-server-migrations&e=kotlin&e=hibernate-orm-panache-kotlin&e=jdbc-mssql&e=jdbc-h2&e=hibernate-orm-panache&e=resteasy&e=resteasy-jackson&e=flyway&e=config-yaml)