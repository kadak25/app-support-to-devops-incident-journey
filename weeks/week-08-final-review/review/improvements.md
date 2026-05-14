# Improvements

Next planned improvements to bring this system closer to production grade.

## Observability — Prometheus + Grafana
Add metrics collection to the Spring Boot application via
`spring-boot-starter-actuator` and `micrometer-registry-prometheus`.
Deploy Prometheus to scrape metrics and Grafana to visualize them.
Set up dashboards for JVM heap, request rate, error rate, and
container restarts.

## Alerting
Configure Grafana alerts or Alertmanager to send notifications
when memory exceeds 80%, when a container restarts unexpectedly,
or when error rate spikes above a threshold.

## Centralized Logging — Loki or ELK
Ship container logs to a central store using Promtail + Loki or
Filebeat + Elasticsearch. This ensures logs survive container
restarts and can be searched across services.

## Container Orchestration — Kubernetes
Migrate from Docker Compose to Kubernetes. Use Deployments for
rolling updates, Services for stable networking, and HorizontalPodAutoscaler
for traffic-based scaling. This removes single-point-of-failure risk.

## Infrastructure as Code — Terraform
Define EC2 instances, security groups, and networking in Terraform.
This makes the environment reproducible, version-controlled, and
deployable in minutes rather than hours.

## Automated Testing in Pipeline
Re-enable unit tests in the CI/CD pipeline. Add integration tests
and a coverage gate. Only promote images that pass all tests.

## Blue/Green Deployment
Implement a blue/green deployment strategy so new releases can
be validated before traffic is switched over. This eliminates
downtime during deployments and makes rollback instant.
