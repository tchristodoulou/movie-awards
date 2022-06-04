# How to test

There are unit tests written for each class and manual testing can be done by using the documentation
below.


## Using the API

base-url: http://localhost:8080/movie-awards

### Get best picture (/best-picture)

Requires two parameters to be provided, name and year. For movies with multiple words underscore can be 
used rather than spaces

``/best-picture?name=slumdog&year=2008``

``/best-picture?name=harry_potter&year=2009``

Response body

```
{
"id": "518d0fa0-df63-11ec-80cf-0242ac1d0002",
"year": "2008",
"category": "Best Picture",
"nominee": "Slumdog Millionaire",
"additionalInfo": "Christian Colson, Producer",
"won": "YES"
}
```




