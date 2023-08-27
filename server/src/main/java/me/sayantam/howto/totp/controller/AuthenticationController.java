package me.sayantam.howto.totp.controller;

import me.sayantam.howto.totp.core.CryptoHashStrategy;
import me.sayantam.howto.totp.core.SharedSecretStrategy;
import me.sayantam.howto.totp.core.TotpStrategy;
import me.sayantam.howto.totp.domain.Otp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {

    private SharedSecretStrategy sharedSecretStrategy;
    private CryptoHashStrategy hashStrategy;
    private TotpStrategy totpStrategy;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setSharedSecretStrategy(SharedSecretStrategy sharedSecretStrategy) {
        this.sharedSecretStrategy = sharedSecretStrategy;
    }

    @Autowired
    public void setHashStrategy(CryptoHashStrategy hashStrategy) {
        this.hashStrategy = hashStrategy;
    }

    @Autowired
    public void setTotpStrategy(TotpStrategy totpStrategy) {
        this.totpStrategy = totpStrategy;
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<Map<String, Boolean>> authenticate(@RequestBody Otp clientOtp) {
        final var sharedSecret = sharedSecretStrategy.generateSecretKey();
        final var sha = hashStrategy.generateCryptoHash(sharedSecret, clientOtp.stepDuration());
        logger.debug("serverSha: {}", sha);
        final var serverOtp = totpStrategy.generateTotp(sha);
        logger.info("serverOtp: {}", serverOtp);
        if (serverOtp.equals(clientOtp.otp())) {
            return ResponseEntity.ok().body(Map.of("status", true));
        }

        return ResponseEntity.notFound().build();
    }
}
