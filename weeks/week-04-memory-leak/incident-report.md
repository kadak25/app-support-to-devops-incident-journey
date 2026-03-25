# Week 04 – Container OOM Kill Incident

## Summary

A containerized Python application continuously allocated memory without releasing it, causing the container to be killed by the OOM Killer with exit code 137.

---

## Investigation

* Observed that the container was not staying in a running state.
* Checked container status using `docker ps -a`.
* Identified exit code 137 indicating OOM Kill.
* Inspected memory usage using `docker stats` to confirm RAM growth.
* Reviewed logs using `docker logs` to see last allocated chunks.

---

## Root Cause

The application appended 10MB chunks to a list in an infinite loop without ever releasing memory. Python's garbage collector could not free the memory because an active reference kept it alive. Once the container reached its 100MB memory limit, the OOM Killer sent SIGKILL and terminated the process.

---

## Resolution

* Fixed the application to delete each chunk after use and call `gc.collect()`.
* Rebuilt the image and restarted the container with the same memory limit.
* Verified that memory usage stayed stable at ~13MiB.

---

## Prevention / Follow-up

* Always set memory limits on containers in production.
* Avoid appending to unbounded lists in long-running processes.
* Use `docker stats` regularly to monitor container resource usage.
* Add alerting for containers that exit unexpectedly.

---

## Evidence

* Memory usage growing — RAM at 99.62% before OOM Kill
  [![docker stats memory growing](https://github.com/kadak25/app-support-to-devops-incident-journey/raw/main/weeks/week-04-memory-leak/screenshots/01-docker-stats-memory-growing.png)](https://github.com/kadak25/app-support-to-devops-incident-journey/blob/main/weeks/week-04-memory-leak/screenshots/01-docker-stats-memory-growing.png)

* Container exited with code 137 — OOM Kill confirmed
  [![docker ps exited 137](https://github.com/kadak25/app-support-to-devops-incident-journey/raw/main/weeks/week-04-memory-leak/screenshots/02-docker-ps-exited-137.png)](https://github.com/kadak25/app-support-to-devops-incident-journey/blob/main/weeks/week-04-memory-leak/screenshots/02-docker-ps-exited-137.png)

* Application logs showing memory chunks allocated until crash
  [![docker logs chunks](https://github.com/kadak25/app-support-to-devops-incident-journey/raw/main/weeks/week-04-memory-leak/screenshots/03-docker-logs-chunks.png)](https://github.com/kadak25/app-support-to-devops-incident-journey/blob/main/weeks/week-04-memory-leak/screenshots/03-docker-logs-chunks.png)

* Fixed application — memory stable at 13.91MiB
  [![fixed memory stable](https://github.com/kadak25/app-support-to-devops-incident-journey/raw/main/weeks/week-04-memory-leak/screenshots/04-fixed-memory-stable.png)](https://github.com/kadak25/app-support-to-devops-incident-journey/blob/main/weeks/week-04-memory-leak/screenshots/04-fixed-memory-stable.png)
