# INC-006: CI/CD Pipeline Failure

## Summary

The deployment pipeline failed after a code push to `main`. No Docker image was built and no release was delivered.

## Impact

The new application version could not be deployed. Development was blocked until the pipeline was restored.

## Root Cause

Four misconfigurations caused a cascading pipeline failure.

The workflow specified Java version `8`, which is incompatible with Spring Boot 3.x. This caused Maven to fail during compilation. The `pom.xml` also referenced a non-existent dependency version `99.99.99-BROKEN` for `spring-boot-starter-data-jpa`, which Maven could not resolve from any repository. The Docker build step referenced `./infra/Dockerfile`, a path that did not exist in the repository. Finally, the `DOCKER_USERNAME` and `DOCKER_PASSWORD` secrets were never configured in the GitHub repository settings, causing the DockerHub login step to fail with no credentials.

## Resolution

Java version was updated to `17` in both the workflow file and `pom.xml`. The explicit broken dependency version was removed and is now managed automatically by the Spring Boot parent BOM. The Dockerfile path was corrected to `./Dockerfile`. The DockerHub secrets were added under repository Settings and the pipeline was re-triggered successfully.

## Prevention

Validate workflow files locally before merging to `main`. Never hardcode Spring Boot sub-library versions — always let the parent BOM manage them. Run `docker build` locally before pushing pipeline changes. Add a secrets checklist to the pull request template so no pipeline change merges without verifying required secrets exist.
