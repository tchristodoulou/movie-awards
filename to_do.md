#  TODO

- Writing ITs
- Security Token
- Error handling for not found in request out / db
- Endpoint for posting reviews
- Endpoint for getting top ten box office movies
- complete documentation
- .env file for docker values

# Improvements

- Add Swagger to document the API outside the md files
- Add a cache to the api (or potentially outsource it to redis) (see scale.md for details)
- The security would need to be improved to be production ready - a secrets management system for
db password and API key from OMDb so that they aren't in plaintext. These could then be loaded into
the .env file