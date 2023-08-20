package me.sayantam.howto.totp.algo;

import me.sayantam.howto.totp.core.CryptoHashStrategy;

public class HmacSha1Hash extends CryptoHashStrategy {

    @Override
    protected String doGenerateHash(String sharedKey) {
        return "ABCD";
    }
}
