# Fixed State

All four failure points in the production meltdown cascade have been resolved.

The memory leak was eliminated by replacing the unbounded static list with a simple atomic counter. No memory is allocated per request. The JVM heap limit was raised from 64MB to 256MB in the Dockerfile. The docker-compose configuration was updated with `restart: always` on both services, a memory limit of 512MB, and a health check so NGINX only routes traffic when the backend is genuinely ready. The NGINX configuration was updated with proxy timeouts and a clean 503 error page instead of a raw 502. The CI/CD pipeline now tags images with the commit SHA in addition to `latest`, making rollback possible at any time.

Changes applied:

- `IncidentApp.java` — removed unbounded memory allocation per request
- `Dockerfile` — JVM heap raised from `-Xmx64m` to `-Xmx256m`
- `docker-compose.yml` — added `restart: always`, `mem_limit: 512m`, and `healthcheck`
- `nginx.conf` — added proxy timeouts and 503 error handling
- `deploy.yml` — added SHA-based image tagging for rollback capability
