#  TODO

- Endpoint for posting reviews 
- ITs
- complete documentation

- Security Token - could just have a "cache" which generates a random string for password, 
use endpoint to get it.

- Endpoint for getting top ten box office movies

- solution.md – a short (min two lines, max half a page) description of the solution and explaining some design decisions
- how_to_test.md file explaining what needs to be done to use the service.
- assumptions.md – your assumptions when solving the challenge
- scale.md – a description of how it will scale when the number of
users/agents/consumers grows from 100 per day to 10000000 per day, and what changes would have to be made to keep the same quality of service\
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