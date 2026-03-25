# Broken State

The Docker container fails to start because the application continuously allocates memory without releasing it.

Each iteration appends a 10MB chunk to a list that is never cleared.
Since memory is never freed, usage grows until the container hits its limit.

Symptoms observed:

- Memory usage grows continuously
- Container is killed by the OOM Killer
- Docker reports `Exited (137)`
- No error in logs — process is forcefully terminated
