# Broken State

The application suffers a full production meltdown caused by a cascade of four failures.

The Spring Boot application allocates 5MB of memory on every request via an unbounded static list that is never cleared. Combined with a JVM heap limit of 64MB, memory is exhausted within seconds of receiving traffic. The container is killed by the OOM Killer with Exit 137. Since no restart policy is defined, the container stays down permanently. NGINX continues running but cannot reach the backend, returning 502 Bad Gateway to all users. The CI/CD pipeline can push a new image but always tags it as `latest` with no versioning, making rollback impossible.

Symptoms observed:

- Memory usage grows continuously with each request
- Container killed by OOM Killer — Exit 137
- NGINX returns 502 Bad Gateway to all users
- Container stays down — no automatic recovery
- No previous version available to roll back to
