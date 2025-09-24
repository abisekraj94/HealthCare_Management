package com.healthcare.mgnt.controller.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class HealthController {
    @GetMapping("/health")
    public Map<String, String> health() {
        return Collections.singletonMap("status", "UP");
    }
}
