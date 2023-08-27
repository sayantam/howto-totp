package me.sayantam.howto.totp.domain;

import java.time.Duration;

public record Otp(String otp, long step) {

    public Duration stepDuration() {
        return Duration.ofSeconds(step());
    }
}
