package com.incident.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class IncidentApp {

    // FIX: Simple counter instead of unbounded list — no memory leak
    private static final AtomicLong requestCount = new AtomicLong(0);

    public static void main(String[] args) {
        SpringApplication.run(IncidentApp.class, args);
    }

    @GetMapping("/")
    public String index() {
        // FIX: No memory allocation per request
        return "OK - requests served: " + requestCount.incrementAndGet();
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
