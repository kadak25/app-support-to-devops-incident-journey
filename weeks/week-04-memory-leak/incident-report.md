## Investigation

- Container was exiting unexpectedly after a short period.
- Checked container status using `docker ps -a` → Exited (137).
- Observed increasing memory usage via `docker stats`.
- Inspected logs using `docker logs`.

---

## Root Cause

The application continuously allocated memory in an infinite loop, causing a memory leak.

Due to the memory limit (`-m 100m`), the container exceeded its limit and was killed by the system (OOM Kill, exit code 137).

---

## Resolution

- Stopped and removed the failing container.
- Fixed the application logic to avoid unbounded memory allocation.
- Rebuilt and restarted the container.

---

## Prevention / Follow-up

- Always define memory limits for containers.
- Monitor memory usage (`docker stats` or metrics systems).
- Avoid unbounded data structures in application code.
- Implement alerting for abnormal memory growth.

---

## Evidence

- Memory usage spike
![Memory](./screenshots/01-docker-stats-memory-growing.png)

- Container killed (Exited 137)
![OOM](./screenshots/02-docker-ps-exited-137.png)

- Logs before crash
![Logs](./screenshots/03-docker-logs-chunks.png)

- Fixed container running stable
![Fixed](./screenshots/04-fixed-memory-stable.png)

---

## Advanced Analysis (Week 04)

### Exit Code 137

Exit 137 indicates that the container was terminated by the system (SIGKILL), typically due to an Out Of Memory (OOM) condition.

### Timeline

- T+00s → Container started
- T+10s → Memory usage increasing
- T+50s → Memory limit reached
- T+50s → OOM Kill triggered
- T+55s → Container exited (137)

### Impact

- Container crashed → service unavailable
- No data loss (stateless)
- No alerting mechanism in place
