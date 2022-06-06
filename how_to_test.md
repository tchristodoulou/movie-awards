# How to test

There are unit tests written for each class and manual testing can be done by using the documentation
below. There are also full API tests covering the whole project in 
src/test/java/api/MovieAwardsApplicationIT

## Using the API

base-url: http://localhost:8080/movie-awards

## Get best picture (/best-picture)

Requires two parameters to be provided, name and year. For movies with multiple words plus can be 
used rather than spaces

``/best-picture?name=slumdog&year=2008``

``/best-picture?name=harry+potter&year=2009``


### Successful with movie in db

Request

``http://localhost:8080/movie-awards/best-picture?name=slumdog&year=2008``

Response

``200 OK``

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

### Successful with movie not in db (not nominated)

Request

``http://localhost:8080/movie-awards/best-picture?name=harry+potter&year=2009``

Response

``200 OK``

```
{
  "id": null,
  "year": "2009",
  "category": "Best Picture",
  "nominee": "Harry Potter and the Half-Blood Prince",
  "additionalInfo": "Not nominated for Best Picture",
  "won": "NO"
}
```

### Movie not found in OMDb

Request

``http://localhost:8080/movie-awards/best-picture?name=bad+name&year=1990``

Response

``404 NOT FOUND``

```
No title [bad name] found for year [1990]
```

## Save review (/review)

Requires a json request body where all parameters are mandatory.

``http://localhost:8080/movie-awards/review``

Example request body

```
{
  "movieYear" : "2022",
  "title" : "test movie title",
  "reviewScore" : 75
}
```

- title is a mandatory String and must be less than 256 characters
- movieYear is a mandatory String and must be exactly 4 characters
- reviewScore is a mandatory Integer and must be between 0 and 100

### Successful save review

Request

``http://localhost:8080/movie-awards/review``

Request body

```
{
  "movieYear" : "2022",
  "title" : "test movie title",
  "reviewScore" : 75
}
```

Response

``201 CREATED``

```
{
  "id": "43a168aa-f4b7-4f28-a9eb-c810f69b27cf",
  "movieYear": "2022",
  "title": "test movie title",
  "reviewScore": 75
}
```

### Bad request 

Request

``http://localhost:8080/movie-awards/review``

Request body

```
{
  "movieYear" : "",
  "title" : "test movie title",
  "reviewScore" : 75
}
```

Response

``400 BAD REQUEST``

```
{
  "timestamp": "2022-06-06T09:36:52.095+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/movie-awards/review"
}
```
