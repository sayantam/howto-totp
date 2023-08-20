package me.sayantam.howto.totp.algo;

import me.sayantam.howto.totp.core.SharedSecretStrategy;
import org.springframework.stereotype.Component;

public class DemoSecret extends SharedSecretStrategy {

    @Override
    protected String doGenerateSecretKey() {
        return "f286e967dcd9c103c312685d85da1d06";
    }
}
