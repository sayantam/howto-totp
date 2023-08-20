package me.sayantam.howto.totp.domain;

import java.time.Duration;

public record Otp(String otp, long stepDurationSeconds) {

    public Duration stepDuration() {
        return Duration.ofSeconds(stepDurationSeconds());
    }
}
