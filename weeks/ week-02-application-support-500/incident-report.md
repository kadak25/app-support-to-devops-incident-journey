
# Week 02 – Application Support Incident: HTTP 500 on /broken

## Summary
A Spring Boot application returned HTTP 500 on a specific endpoint due to an unhandled runtime exception, simulating a production-like application error scenario.

## Impact
- Requests to `/broken` returned `HTTP 500 Internal Server Error`.
- Error responses exposed stack trace details (information disclosure risk).

## Detection
The issue was detected by reproducing the behavior using:
- `curl -i http://localhost:8080/broken`

## Investigation
- Verified the HTTP status code and endpoint path (`/broken`, 500).
- Used the stack trace to identify the source of the failure:
  `BrokenEndpointController.broken(...)` throwing a `RuntimeException`.

## Root Cause
An intentionally added endpoint (`/broken`) threw an unhandled `RuntimeException`, causing Spring Boot to return HTTP 500.

## Resolution
- Removed the intentionally broken controller so the endpoint no longer exists.
- Restarted the application and verified `/broken` no longer returns 500 (404 after removal).

## Prevention / Follow-up
- Disabled stack trace and message exposure in error responses:
  - `server.error.include-stacktrace=never`
  - `server.error.include-message=never`
- Treat debug endpoints as non-production and keep them out of the main branch unless protected.

## Evidence

### Broken endpoint (HTTP 500)
![500 response](screenshots/500-response-broken-endpoint.png)

### Post-fix verification
![Fixed endpoint](screenshots/fixed-broken-endpoint.png)

