package me.sayantam.howto.totp;

import me.sayantam.howto.totp.algo.DemoSecret;
import me.sayantam.howto.totp.algo.HmacSha1Hash;
import me.sayantam.howto.totp.algo.NumericTotp;
import me.sayantam.howto.totp.core.CryptoHashStrategy;
import me.sayantam.howto.totp.core.SharedSecretStrategy;
import me.sayantam.howto.totp.core.TotpStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Configuration
public class TotpServer {

    @Bean
    public SharedSecretStrategy getSharedSecretStrategy() {
        return new DemoSecret();
    }

    @Bean
    public CryptoHashStrategy getHashStrategy() {
        return new HmacSha1Hash();
    }

    @Bean
    public TotpStrategy getTotpStrategy() {
        return new NumericTotp();
    }

    public static void main(String[] args) {
        SpringApplication.run(TotpServer.class, args);
    }

}
