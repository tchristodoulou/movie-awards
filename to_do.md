#  TODO

- Security Token - could just have a "cache" which generates a random string for password, 
use endpoint to get it.

- Endpoint for getting top ten box office movies


- complete documentation
- how_to_run with security

- Diagrams
   ○ system-context-diagram – a system context diagram (UML, whiteboard
   jpg photo, 8-bit pixel art gif, a phone picture of a bar's paper napkin with a
   crayon diagram scrawled on it, anything is good as long as it's legible)
   ○ entity-diagram
   ○ component-diagram
   ○ class-diagram
   ○ data-flow-diagram
   ○ sequence-diagram

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