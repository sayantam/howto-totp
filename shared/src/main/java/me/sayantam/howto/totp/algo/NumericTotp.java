package me.sayantam.howto.totp.algo;

import me.sayantam.howto.totp.core.TotpStrategy;

import java.util.Random;

public class NumericTotp extends TotpStrategy {

    @Override
    protected String doGenerateTotp(String sha) {
        final var rg = new Random();
        rg.setSeed(Long.parseLong(sha.substring(0, 5), 16));
        return String.valueOf(rg.nextInt(0, 999999));
    }
}
