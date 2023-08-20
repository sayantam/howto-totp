package me.sayantam.howto.totp.core;

public abstract class SharedSecretStrategy {

    protected abstract String doGenerateSecretKey();

    public String generateSecretKey() {
        return doGenerateSecretKey();
    }
}
