# Incident Report — Week 04
## INC-004: OOM Kill — Container Memory Leak

---

| Field            | Value                                      |
|------------------|--------------------------------------------|
| **Incident ID**  | INC-004                                    |
| **Date**         | 2026-03-17                                 |
| **Severity**     | P2 — High                                  |
| **Status**       | ✅ Resolved                                |
| **Duration**     | ~5 minutes from start to OOM Kill          |
| **Reported By**  | `docker ps -a` → Exited (137)              |
| **Resolved By**  | Mustafa Kadak                              |
| **Environment**  | Windows / Docker Desktop                   |

---

## 1. Executive Summary

A containerized Python application was silently consuming memory without releasing it — a classic memory leak pattern. The Linux kernel's OOM Killer terminated the process once the container hit its memory limit, resulting in exit code **137** (SIGKILL). The issue was identified via `docker stats` and `docker ps -a`, root-caused through log analysis, and resolved by fixing the application's memory management.

---

## 2. Timeline

| Time  | Event |
|-------|-------|
| T+00s | Container started: `docker run -m 100m --name mem-leak-container mem-leak` |
| T+10s | `docker stats` shows MEM growing: 10MB → 30MB → 60MB |
| T+50s | Memory reaches 100MB limit — OOM Killer fires |
| T+50s | Container exits with code **137** |
| T+55s | `docker ps -a` → `Exited (137)` confirmed |
| T+60s | `docker logs` → last allocated chunk visible, then silence |
| T+90s | Root cause identified: unbounded list growing in memory |
| T+120s| Fixed app built and verified: memory stays stable at ~13MiB |

---

## 3. Root Cause Analysis

### What is Exit Code 137?
```
Exit 137 = 128 + 9
         = base + SIGKILL signal
```

When a container exceeds its memory limit (`-m 100m`), the OOM Killer sends `SIGKILL` to the process. The process cannot catch or ignore this signal — it is immediately terminated.

### The Leak
```python
# BROKEN — data listesi sürekli büyüyor, hiç temizlenmiyor
data = []

while True:
    data.append("A" * 10_000_000)  # 10MB ekleniyor
    # Python GC bunu temizleyemez — aktif referans var
```

Every iteration appends 10MB to a list that is never freed. Memory grows linearly until the OS intervenes.

### Why This Happens in Production

| Scenario | Example |
|----------|---------|
| Unbounded cache | `cache[key] = value` — cache hiç temizlenmiyor |
| DB result accumulation | `results += db.query(...)` loop içinde |
| Event listeners not removed | GUI/event-driven uygulamalarda |
| Log buffers | Flush edilmeyen in-memory log buffer |

---

## 4. Diagnosis Commands Used
```bash
# Container'ın canlı memory kullanımını izle
docker stats mem-leak-container

# Container'ın durumunu kontrol et
docker ps -a

# Son logları gör
docker logs mem-leak-container
```

---

## 5. Impact

| Category     | Impact |
|--------------|--------|
| Availability | Container crashed — service unavailable |
| Data loss    | None (stateless app) |
| Detectability | Only visible via `docker ps -a` — no alerting |
| Blast radius | Single container, isolated |

---

## 6. Resolution

### Fix 1: Application Level (Root Fix)
```python
# FIXED — chunk işlendikten sonra serbest bırakılıyor
import gc

while True:
    chunk = "A" * 10_000_000  # lokal değişken
    result = len(chunk)        # kullan
    del chunk                  # referansı sil
    gc.collect()               # GC'yi tetikle
    time.sleep(1)
```

### Fix 2: Infrastructure Level (Defense in Depth)
```bash
# Memory limit koyarak çalıştır
docker run -m 100m --name mem-leak-fixed mem-leak-fixed
```

---

## 7. Verification
```bash
docker build -t mem-leak-fixed ./fixed
docker run -m 100m --name mem-leak-fixed mem-leak-fixed
docker stats mem-leak-fixed
# Sonuç: 13.91MiB / 100MiB — sabit, artmıyor ✅
```

---

## 8. Key Learnings

### Exit Codes Cheat Sheet

| Code | Anlam |
|------|-------|
| `0`   | Normal çıkış |
| `1`   | Genel hata |
| `137` | OOM Kill (SIGKILL — kernel tarafından) |
| `139` | Segmentation fault |
| `143` | Graceful stop (SIGTERM) |

### Docker Memory Flags
```bash
docker run -m 100m                  # max RAM: 100 megabyte
docker run -m 100m --memory-swap 100m  # swap'ı da kapat
```

---

## 9. Evidence

| File | Açıklama |
|------|----------|
| `broken/app.py` | Memory leak olan uygulama |
| `broken/Dockerfile` | Build dosyası |
| `fixed/app.py` | Belleği düzgün yöneten uygulama |
| `fixed/Dockerfile` | Build dosyası |
| `screenshots/01-docker-stats-memory-growing.png` | RAM %99.62 artışı |
| `screenshots/02-docker-ps-exited-137.png` | OOM Kill kanıtı |
| `screenshots/03-docker-logs-chunks.png` | Son log çıktısı |
| `screenshots/04-fixed-memory-stable.png` | 13.91MiB sabit |

---

## 10. Repo Progression
```
Week 01 → Linux process / systemd issues
Week 02 → Application 500 errors
Week 03 → Docker container crash
Week 04 → Resource management / OOM Kill   ← BU HAFTA
Week 05 → Cloud Operations (AWS)
```

> Bu haftadan itibaren gerçek DevOps / SRE thinking başlıyor:
> Sadece "ne bozuk?" değil, "neden bozuk, nasıl önlerim?" sorusu soruyoruz.

---

*Report authored by: Mustafa Kadak | App Support → DevOps Journey | Week 04*
