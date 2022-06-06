# Solution explanation

## Architecture
For the solution I've opted for Java 11, Spring-boot and Maven. These are all fairly common tools
that are used across different companies.

I've included a dockerfile and docker-compose implementation using a .env, the idea being that when
deploying this to non-local environments we could simple override the environment values, and it 
allows us to easily spin up the service for testing, along with dependent services.

## CSV - Reader vs DB

I used https://www.convertcsv.com/csv-to-sql.htm to convert the csv into insert statements, 
editing the data slightly (removing the Nth terms for years), while leaving some data untouched 
(not an exercise in cleaning data). I then used a flyway migration to create and populate a table
on API start up. I felt given the data in the CSV is fairly static, this was a sensible approach 
over using the CSVReader library or something similar. Using the DB I could simply write a Spring
data jpa interface instead of reading the csv line by line and having to filter on text matching.

## Movie Awards and Movie Review Tables

I created a second table for the reviews rather than adding an extra column. I felt that these two areas
are separate parts of the business - yes they are both linked with movies, however a movie being 
nominated for an award has no correlation with how a movie could be reviewed - critically bad movies 
can be reviewed well by audiences






