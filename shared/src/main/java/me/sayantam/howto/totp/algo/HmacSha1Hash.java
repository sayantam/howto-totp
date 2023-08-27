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
    protected String doGenerateHash(String sharedKey, Duration stepDuration) {
        final var algo = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, sharedKey);
        final String value = String.valueOf(getTimeAtStepBoundary(stepDuration));
        return algo.hmacHex(value);
    }

    private long getTimeAtStepBoundary(Duration stepDuration) {
        final var boundary = stepDuration.getSeconds();
        logger.debug("boundary: {}", boundary);
        final var ldt = LocalDateTime.now();
        final var ts = ldt.toEpochSecond(ZoneOffset.UTC);
        logger.debug("ts: {}", ts);
        final var diff = ts % boundary;
        logger.debug("diff: {}", diff);
        final var tsb = ts - diff;
        logger.debug("tsb: {}", tsb);
        return tsb;
    }
}
