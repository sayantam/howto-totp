package me.sayantam.howto.totp;

import me.sayantam.howto.totp.algo.HmacSha1Hash;
import me.sayantam.howto.totp.algo.NumericTotp;
import me.sayantam.howto.totp.core.CryptoHashStrategy;
import me.sayantam.howto.totp.core.TotpStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class TotpClient {

    @Value("${TOTP_SERVER_HOST:localhost:8080}")
    private String totpServerHost;

    @Bean
    public CryptoHashStrategy getHashStrategy() {
        return new HmacSha1Hash();
    }

    @Bean
    public TotpStrategy getTotpStrategy() {
        return new NumericTotp();
    }

    @Bean
    public ClientApp getClientApp() {
        return new ClientApp(totpServerHost);
    }

    private final static Logger logger = LoggerFactory.getLogger(TotpClient.class);

    public static void main(String[] args) throws Exception {
        var ctx = new AnnotationConfigApplicationContext(TotpClient.class);
        var client = ctx.getBean(ClientApp.class);
        client.register();
        for (var i = 0; i < 5; i++) {
            final var ok = client.authenticate();
            if (ok) {
                logger.info("Client auth ok");
            } else {
                logger.error("Client auth failed");
            }
            if (i > 0) Thread.sleep(ClientApp.DEFAULT_STEP_DURATION.plus(Duration.ofSeconds(5)).toMillis());
        }
    }
}
