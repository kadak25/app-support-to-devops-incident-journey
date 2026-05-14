# Final Incident Summary

A complete record of all 7 incidents investigated and resolved
across the 8-week App Support → DevOps journey.

---

## INC-001 — Linux Service Failure
**Week:** 01 | **Severity:** Medium
**Symptom:** Service unavailable, process not responding
**Root Cause:** Misconfigured service, process not running after restart
**Resolution:** Diagnosed with `systemctl`, `ps`, `journalctl` — service restarted and enabled
**Key Learning:** Linux process management and service debugging fundamentals

---

## INC-002 — HTTP 500 Internal Server Error
**Week:** 02 | **Severity:** High
**Symptom:** All requests returning HTTP 500
**Root Cause:** Unhandled application exception in error path
**Resolution:** Identified via application logs, exception handled and tested
**Key Learning:** HTTP error code diagnosis, log-driven debugging

---

## INC-003 — Docker Runtime Failure
**Week:** 03 | **Severity:** High
**Symptom:** Container failing to start or exiting immediately
**Root Cause:** Missing image, wrong entrypoint, misconfigured environment
**Resolution:** Inspected with `docker logs`, `docker inspect` — Dockerfile corrected
**Key Learning:** Container lifecycle, image troubleshooting, entrypoint debugging

---

## INC-004 — OOM Kill / Memory Leak
**Week:** 04 | **Severity:** Critical
**Symptom:** Container killed repeatedly, Exit 137
**Root Cause:** Unbounded memory allocation per request, JVM heap too low
**Resolution:** Fixed allocation logic, raised heap limit, added memory constraints
**Key Learning:** JVM memory management, OOM Kill (SIGKILL), `docker stats`

---

## INC-005 — NGINX 502 Bad Gateway
**Week:** 05 | **Severity:** Critical
**Symptom:** All requests returning 502 Bad Gateway
**Root Cause:** Backend container down, NGINX had no target to proxy to
**Resolution:** Added `restart: always`, verified auto-recovery with `os.abort()`
**Key Learning:** Reverse proxy debugging, restart policies, NGINX configuration

---

## INC-006 — CI/CD Pipeline Failure
**Week:** 06 | **Severity:** High
**Symptom:** GitHub Actions pipeline failing on every push
**Root Cause:** Wrong Java version, invalid dependency, wrong Dockerfile path, missing secrets
**Resolution:** Fixed all four misconfigurations, added secrets to repository settings
**Key Learning:** GitHub Actions debugging, Maven dependency management, secrets management

---

## INC-007 — Full Production Meltdown
**Week:** 07 | **Severity:** Critical
**Symptom:** Memory leak → OOM Kill → 502 Bad Gateway → no rollback path
**Root Cause:** 5MB allocation per request, 64MB heap limit, no restart policy, only latest tag
**Resolution:** Fixed leak, raised heap, added restart + health check, added SHA versioning
**Key Learning:** Cascade failure analysis, end-to-end incident response, rollback strategy

---

## Pattern Analysis

Looking across all 7 incidents, three patterns repeat:

**1. Missing safeguards** — restart policies, heap limits, health checks.
Things that should always be configured but aren't until something breaks.

**2. Configuration errors** — wrong versions, wrong paths, missing secrets.
Small mistakes in config files that cascade into full outages.

**3. No observability** — every incident was discovered manually.
In a production environment, all of these would have been caught by alerts
before they caused user-facing impact.

---

*Mustafa Kadak | App Support → DevOps Journey | 2026*
