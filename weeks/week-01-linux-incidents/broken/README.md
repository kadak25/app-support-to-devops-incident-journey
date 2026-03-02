# Broken State

- `broken-app.service` runs an infinite loop causing CPU hog
- Service starts automatically and consumes high CPU
- Detected via `htop` and `systemctl status`

This represents the system state before mitigation.
