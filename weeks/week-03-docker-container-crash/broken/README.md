# Broken State

The Docker container fails to start because a required environment variable is missing.

During startup the application checks for the `DB_HOST` variable.  
Since the variable is not provided, the application exits with an error.

Symptoms observed:

- Container exits immediately
- Docker reports `Exited (1)`
- Error message visible in container logs
