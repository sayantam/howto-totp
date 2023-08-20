package me.sayantam.howto.totp.core;

public abstract class TotpStrategy {

    protected abstract String doGenerateTotp(String sha);

    public String generateTotp(String sha) {
        return doGenerateTotp(sha);
    }
}
