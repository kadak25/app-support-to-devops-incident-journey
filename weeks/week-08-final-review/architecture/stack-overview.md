# Stack Overview

## Component Breakdown

### Source Control
- **GitHub** — all weekly incident scenarios stored in a single monorepo
- Branch strategy: `main` for all weeks, each week in its own directory

### CI/CD Pipeline
- **GitHub Actions** — triggered on every push to `main`
- Pipeline stages: checkout → Java 17 setup → Maven build → Docker build → DockerHub push
- Image tagging: `latest` + commit SHA for rollback capability

### Build Tools
- **Maven 3** — dependency management and JAR packaging
- **Spring Boot 3.2** — application framework (Java 17)
- **Docker** — containerization

### Image Registry
- **DockerHub** — public image registry
- Images tagged by commit SHA: `username/incident-app:<sha>`

### Cloud Infrastructure
- **AWS EC2** — t2.micro, Ubuntu 22.04, us-east-1
- Security groups: port 22 (SSH), port 80 (HTTP)

### Reverse Proxy
- **NGINX** — listens on port 80, proxies to application on port 8080
- Config: proxy timeouts, health-aware routing, custom error pages

### Application Runtime
- **Docker container** — Spring Boot JAR inside eclipse-temurin:17-jre-alpine
- JVM heap: `-Xmx256m -Xms64m`
- Restart policy: `restart: always`
- Health check: HTTP GET `/health` every 10s

---

## Data Flow

```
User Request (HTTP :80)
        ↓
    NGINX proxy
        ↓
    App Container (:8080)
        ↓
    Spring Boot Handler
        ↓
    HTTP Response
```

---

## Known Limitations

| Component | Limitation |
|-----------|------------|
| NGINX | Single instance — no load balancing |
| Application | Single container — no horizontal scaling |
| Database | None — stateless application only |
| Monitoring | No metrics collection |
| Alerting | No notification on failure |
| Logging | Container logs only — not centralized |
