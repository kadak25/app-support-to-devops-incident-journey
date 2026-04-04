# Broken State

NGINX reverse proxy returns 502 Bad Gateway because the backend 
container is unavailable.

Symptoms:
- 502 Bad Gateway on HTTP request
- NGINX running, backend container down
- docker ps -a shows Exited (137)
