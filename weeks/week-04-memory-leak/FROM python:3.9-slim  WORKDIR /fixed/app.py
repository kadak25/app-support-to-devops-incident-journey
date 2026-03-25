"""
FIXED - Memory-Safe Application
================================
Aynı "iş" yapılıyor ama bellek güvenli şekilde yönetiliyor.

Fix'ler:
1. data listesine süresiz ekleme YOK
2. Geçici veri işlendikten sonra siliniyor
3. Bellek kullanımı sabit kalıyor
"""

import time
import gc

print("Starting memory-safe application...")
print("RAM usage will stay stable.")
print("-" * 50)

counter = 0
while True:
    chunk = "A" * 10_000_000  # 10 MB işle
    result = len(chunk)        

    del chunk
    gc.collect()

    counter += 1
    print(f"[ok] Processed chunk #{counter} | Memory stable (chunk freed after use)", flush=True)
    time.sleep(1)
