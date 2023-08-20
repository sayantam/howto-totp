package me.sayantam.howto.totp.controller;

import me.sayantam.howto.totp.core.SharedSecretStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/totp")
public class TotpController {

    private SharedSecretStrategy sharedSecretStrategy;

    @Autowired
    public void setSharedSecretStrategy(SharedSecretStrategy sharedSecretStrategy) {
        this.sharedSecretStrategy = sharedSecretStrategy;
    }

    @GetMapping(path = "/shared-secret", produces = "application/json")
    public Map<String, String> fetchSharedSecret() {
        return Map.of("key", sharedSecretStrategy.generateSecretKey());
    }
}
