# Scaling

- scale.md – a description of how it will scale when the number of users/agents/consumers grows from
100 per day to 10000000 per day, and what changes would have to be made to keep the same quality of service\

- So the basic things of spinning up more instances of the service to handle more inbound requests 
would be the first thing to help with inbound traffic. You can use tools like load balancers to help
manage stress on the services
- To help with stress on the db, we could introduce a caching capability, either in the API or with 
redis, this can be used to cache things like the top 10 box office results, best picture winners for
certain years could be saved in a map
  - The 10 ten could be refreshed daily, or potentially a longer period depending on how often it's
  updated, meaning we'd only need to call out to OMBd once
  - The best picture winners could be stored in a map using the year as a key, this will never 
  change for previous years, so this map could be stored as part of the API, depending on how much 
  memory usage we want to allow, or we could refresh this cache daily, as we'd still benefit highly
- Our biggest bottleneck would be the calls out to OMDb, so minimising that with any tactics would 
be necessary for scaling, however I think the caching could solve most of that