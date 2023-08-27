package me.sayantam.howto.totp;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sayantam.howto.totp.core.CryptoHashStrategy;
import me.sayantam.howto.totp.core.TotpStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class ClientApp {

    public final static Duration DEFAULT_STEP_DURATION = Duration.ofSeconds(60);

    @Autowired
    private CryptoHashStrategy hashStrategy;

    @Autowired
    private TotpStrategy totpStrategy;

    private String sharedKey;

    private final String totpServerHost;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ClientApp(String totpServerHost) {
        this.totpServerHost = totpServerHost;
    }

    public void register() {
        final var client = HttpClient.newHttpClient();
        final var req = HttpRequest.newBuilder()
                .uri(URI.create("http://" + totpServerHost + "/totp/shared-secret"))
                .build();
        try {
            final var res = client.send(req, HttpResponse.BodyHandlers.ofString());
            final var mapper = new ObjectMapper();
            final var resObj = mapper.readValue(res.body(), Map.class);
            this.sharedKey = String.valueOf(resObj.get("key"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticate() {
        final var sha = hashStrategy.generateCryptoHash(this.sharedKey, DEFAULT_STEP_DURATION);
        logger.debug("sha: {}", sha);
        final var totp = totpStrategy.generateTotp(sha);
        logger.info("totp: {}", totp);
        final var client = HttpClient.newHttpClient();
        final var mapper = new ObjectMapper();
        try {
            String resBody = mapper.writeValueAsString(Map.of("otp", totp, "step",
                    DEFAULT_STEP_DURATION.getSeconds()));
            final var req = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + totpServerHost + "/authenticate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(resBody))
                    .build();
            final var res = client.send(req, HttpResponse.BodyHandlers.ofString());
            return (res.statusCode() == 200);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
