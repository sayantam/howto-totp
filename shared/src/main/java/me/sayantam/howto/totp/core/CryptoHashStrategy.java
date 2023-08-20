package me.sayantam.howto.totp.core;

import java.time.Duration;

public abstract class CryptoHashStrategy {

    protected abstract String doGenerateHash(String sharedKey);

    public String generateCryptoHash(String sharedKey, Duration stepDuration) {
        return doGenerateHash(sharedKey);
    }
}
