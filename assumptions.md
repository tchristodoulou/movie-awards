# Assumptions

- The CSV data is as expected. Other than the year which I've trimmed, all the other data is 
entered as given. Because of this I have askew database fields with large maximum values for the 
movie-awards table
- I've assumed that the request to OMDb will always work. I've mentioned in the TODO that this would
be addressed with retry or timeout functionality in a live system
- In terms of the docker compose, it's ran on an Intel mac, rather than an M1, so there might be 
compatibility issues to be resolved
- I've assumed that our API takes a name and year, we then send this to OMDb to get the movie details
back in full. This full detail is then matched on the database nominee column. If nothing is found 
against OMDb, it's a 404, if it exists in OMDb it's a valid movie, so we can check against our db.
  - If it's not in the DB as nominated for best picture, we still return a 200 successful but add 
  not nominated as the additional info field



