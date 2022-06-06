#  TODO

- Security Token - Idea was to have a bean with a fixed username and generated password which is 
created on startup. Adding a new controller specifically for get "/authorisation" which would return
the username and password as plaintext.
  - This could then be added to the header on the getBestPicture and saveReview requests, and an 
  annotation created using spring security to apply to each endpoint. This would take a 
  HttpServletRequest to read the headers and then check these against the generated values.
  - If it's a match, allow the request, otherwise a 401 would be returned

- Endpoint for getting top ten box office movies

# Improvements

- Add Swagger to document the API outside the md files
- Add a cache to the api (or potentially outsource it to redis) (see scale.md for details)
- The security would need to be improved to be production ready - a secrets management system for
db password and API key from OMDb so that they aren't in plaintext. These could then be loaded into
the .env file
- Better handling of errors with callouts to OMDb, maybe implement a retry function / max timeout 
/ error handing response object that can deserialise to instead of checking field null
- Added some tests around the repositories for basic save / load to check they are configured right,
test for findByNomineeAndMovieYearAndCategory (all done using SpringBootTest and H2)
- Add better error handling to the bad request when doing a post request to /movie-awards/review + 
ITs for error handling on bad request post request