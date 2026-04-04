# Fixed State

Backend container configured with restart policy.
Simulates AWS Auto Recovery behavior.

Fix applied:
- restart: always added to docker-compose.yml
- Container auto-recovers after crash

Verification:
- Crash simulated via os.abort()
- Container restarted automatically
- No 502 errors observed
