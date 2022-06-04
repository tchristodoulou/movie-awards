# How to run

The project contains a dockerfile, docker compose and pom.xml to build with maven.

## Building and starting the API

*note: the api is set to run on port 8080 and mysql is set to run on port 3306, these ports must be 
available before trying to run the below steps* 

- The project can be found as a public repo in the below location. You should be able to clone or 
download the project to your local machine.

``https://github.com/tchristodoulou/movie-awards``

- Once done, open command line and change directory to ```<your git folder>/movie-awards```, then run
``mvn clean install`` to build the jar for the api.

- You can then run the dockerfile with ``docker build -t movie-awards .`` which will build a docker 
image for the api.

- Before running check the values in ``docker-compose/.env`` are set correctly, and you can run 
``docker-compose up`` to create an instance of mysql and movie-awards

- As part of start up, a flyway migrations will run (detailed in src/main/resources/db.migration) which
will create our movie awards table and run insert statements generated from the provided csv file

- The API should now be started and ready to use. Instructions on how to use are in ``how_to_test.md``



