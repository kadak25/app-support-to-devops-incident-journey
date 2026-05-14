# Weaknesses

## No Observability Stack
There is no Prometheus, Grafana, or any metrics collection in place.
Every incident was discovered manually — by checking the browser,
running docker ps, or noticing the container was gone.
In production, incidents should be detected by alerts, not by users.

## No Centralized Logging
Logs live inside containers. If a container crashes and is removed,
the logs are gone. There is no log aggregation, no searchable history,
and no way to correlate events across services.

## No High Availability
Every service runs as a single container on a single host. One crash
means full downtime. There is no redundancy, no failover, and no
load balancing.

## Limited Orchestration Experience
All deployments use Docker Compose on a single VM. No Kubernetes,
no service mesh, no auto-scaling. This limits the complexity of
workloads that can be managed reliably.

## No Infrastructure as Code
The EC2 instance, security groups, and NGINX configuration were set
up manually. There is no Terraform, no Ansible, and no repeatable
infrastructure provisioning. Rebuilding the environment from scratch
requires manual steps.

## No Automated Testing in Pipeline
The CI/CD pipeline skips tests (`-DskipTests`). There is no
automated test gate before deployment. A broken build could reach
production without any code quality check.
