package com.url.shortener.service.impl;

import com.url.shortener.model.TTLUnit;
import com.url.shortener.service.TtlCheckerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TtlCheckerServiceImpl implements TtlCheckerService {

    @Override
    public Boolean isValid(LocalDateTime startTime, TTLUnit ttlUnit, int ttl) {

        String name = ttlUnit.name();
        log.info("TtlCheckerServiceImpl :: TTL is : {} {}", ttl, name);
        return addTime(startTime, ttlUnit, ttl).isAfter(LocalDateTime.now());
    }

    @Override
    public LocalDateTime validTill(LocalDateTime startTime, TTLUnit ttlUnit, int ttl) {

        return addTime(startTime, ttlUnit, ttl);
    }

    private LocalDateTime addTime(LocalDateTime startTime, TTLUnit ttlUnit, int ttl) {

        final String name = ttlUnit.name();
        switch (name) {
            case "DAYS":
                return startTime.plus(Duration.ofDays(ttl));
            case "HOURS":
                return startTime.plus(Duration.ofHours(ttl));
            case "MINUTES":
                return startTime.plus(Duration.ofMinutes(ttl));
            case "SECONDS":
                return startTime.plus(Duration.ofSeconds(ttl));
            default:
                return LocalDateTime.MAX;
        }

    }
}
