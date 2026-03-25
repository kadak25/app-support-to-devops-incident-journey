"""
BROKEN - Memory Leak Simulation
================================
Bu uygulama kasıtlı olarak sürekli RAM tüketiyor.
Her iterasyonda 10MB ekliyor, hiç bırakmıyor.


"""

import time

data = []

print("Starting memory leak simulation...")
print("RAM will grow until container is OOM-Killed.")
print("-" * 50)

counter = 0
while True:
    chunk = "A" * 10_000_000  # 10 MB per iteration
    data.append(chunk)
    counter += 1
    total_mb = counter * 10
    print(f"[+] Allocated chunk #{counter} | Total: ~{total_mb} MB in memory", flush=True)
    time.sleep(0.5)
```

**Commit message:**
```
add week-04 broken app.py - memory leak simulation
