package me.sayantam.howto.totp.algo;

import me.sayantam.howto.totp.core.TotpStrategy;

public class NumericTotp extends TotpStrategy {

    @Override
    protected String doGenerateTotp(String sha) {
        return "12345";
    }
}
