# Broken State

The application container fails to start due to a missing required environment variable.

During container startup the application expects a database host configuration, but the variable is not provided.

As a result the container exits immediately and enters a restart loop.

Symptoms:

- Container repeatedly restarting
- Application not reachable
- Error visible in container logs

This represents the system state before the fix.
