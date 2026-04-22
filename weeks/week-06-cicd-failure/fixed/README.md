# Fixed State

The GitHub Actions pipeline runs successfully end to end after each code push.

The workflow was updated with the correct Java version, valid Maven dependency management, the correct Dockerfile path, and properly configured DockerHub secrets. The Docker image is now built and pushed to DockerHub automatically on every push to `main`.

Changes applied:

- Java version updated from `8` to `17` in the workflow and `pom.xml`
- Explicit broken dependency version removed — now managed by Spring Boot BOM
- Dockerfile path corrected from `./infra/Dockerfile` to `./Dockerfile`
- `DOCKER_USERNAME` and `DOCKER_PASSWORD` secrets added in GitHub repository settings
