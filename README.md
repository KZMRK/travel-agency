# TRAVEL AGENCY APPLICATION
## Run the application

Before starting the application you need to set environment variables 
that are responsible for setting up the app and the database.

Go to the `environments` folder and open `db.env` file. In the file you will see the variables:

```
POSTGRES_USER=<database user>
POSTGRES_PASSWORD=<database user password>
POSTGRES_DB=<database name>
```

Open `app.env`. In the file you will see the variables:

```
DB_URL=jdbc:postgresql://db:5432/<database name>
DB_USERNAME=<database user>
DB_PASSWORD=<database user password>
USER_EMAIL=<admin email adress>
USER_PASSWORD=<admin passpord>
INIT_MODE=<default never>
```

To run the app in the root folder in the command line type
```
docker-compose up
```
and run the command

[Click here to see the swagger page](http://localhost:8080/swagger-ui/index.html#/)
## Stop the application

In the same command prompt where the program is running, press `Ctrl+C`

Or you can use command:
```
docker-compose down
```