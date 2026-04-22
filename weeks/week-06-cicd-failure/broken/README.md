# Broken State

The GitHub Actions pipeline fails immediately after a code push to `main`.

The workflow is misconfigured with an unsupported Java version, a non-existent Maven dependency version, a wrong Dockerfile path, and missing DockerHub credentials. Each of these causes the pipeline to fail at a different stage, fully blocking deployment.

Symptoms observed:

- Pipeline triggers on push but fails within seconds
- Maven cannot resolve dependencies and exits with build failure
- Docker image is never built
- DockerHub push never executes
- No new release is delivered to any environment
