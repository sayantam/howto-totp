package me.sayantam.howto.totp.algo;

import me.sayantam.howto.totp.core.CryptoHashStrategy;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class HmacSha1Hash extends CryptoHashStrategy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected String doGenerateHash(String sharedKey, Duration changeInterval) {
        final var algo = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, sharedKey);
        final String value = String.valueOf(getTimeAtStepBoundary(changeInterval));
        return algo.hmacHex(value);
    }

    private long getTimeAtStepBoundary(Duration changeInterval) {
        final var interval = changeInterval.getSeconds();
        logger.debug("interval: {}", interval);
        final var ldt = LocalDateTime.now();
        final var ts = ldt.toEpochSecond(ZoneOffset.UTC);
        logger.debug("ts: {}", ts);
        final var remainder = ts % interval;
        logger.debug("remainder: {}", remainder);
        final var tsb = ts - remainder;
        logger.debug("tsb: {}", tsb);
        return tsb;
    }
}
