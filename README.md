# FINT Springer Adapter

FINT Springer Adapter serves data from a Mongo database.

## Adapter configuration

| Key                                   | Description                                     | Example                                                                           |
|---------------------------------------|-------------------------------------------------|-----------------------------------------------------------------------------------|
| `fint.adapter.organizations`          | List of orgIds the adapter handles.             | rogfk.no, vaf.no, ofk.no                                                          |
| `fint.adapter.endpoints.provider.XXX` | Url to the sse endpoint for provider XXX        | https://play-with-fint.felleskomponent.no/administrasjon/personal/provider/sse/%s |
| `spring.data.mongodb.uri`             | URI of Mongo server containing adapter database |                                                                                   |
| `spring.data.mongodb.database`        | Name of database                                |                                                                                   |

- **[SSE Configuration](https://github.com/FINTlibs/fint-sse#sse-configuration)**
- **[OAuth Configuration](https://github.com/FINTlibs/fint-sse#oauth-configuration)**

## Reseed information

To reseed an entity, run the following request three times within 10 seconds:: 

```bash
POST https://play-with-fint.felleskomponent.no/<component>/admin/cache/rebuild/<entity>
```
For example:

```bash
POST https://play-with-fint.felleskomponent.no/utdanning/elev/admin/cache/rebuild/elevforhold
```