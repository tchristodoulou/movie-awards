#  TODO

- Writing ITs
- Security Token
- Endpoint for posting reviews
- Endpoint for getting top ten box office movies
- complete documentation
- .env file for docker values
- Logging

# Improvements

- Add Swagger to document the API outside the md files
- Add a cache to the api (or potentially outsource it to redis) (see scale.md for details)
- The security would need to be improved to be production ready - a secrets management system for
db password and API key from OMDb so that they aren't in plaintext. These could then be loaded into
the .env file
- Better handling of errors with callouts to OMDb, maybe implement a retry function / max timeout / error handing response object that can deserialise to instead of checking field null