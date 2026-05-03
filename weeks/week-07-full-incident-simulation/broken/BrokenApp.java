package com.incident.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class IncidentApp {

    // BUG: Static list never cleared — grows forever, causes memory leak
    private static final List<byte[]> memoryLeak = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(IncidentApp.class, args);
    }

    @GetMapping("/")
    public String index() {
        // BUG: Each request allocates 5MB and never releases it
        memoryLeak.add(new byte[5 * 1024 * 1024]);
        return "OK - requests served: " + memoryLeak.size();
    }

    @GetMapping("/health")
    public String health() {
        // BUG: Health check also leaks memory
        memoryLeak.add(new byte[1 * 1024 * 1024]);
        return "UP";
    }
}
