package com.url.shortener.service;

import com.url.shortener.model.TTLUnit;

import java.time.LocalDateTime;

public interface TtlCheckerService {

    Boolean isValid(LocalDateTime startTime, TTLUnit ttlUnit, int ttl);

    LocalDateTime validTill(LocalDateTime startTime, TTLUnit ttlUnit, int ttl);
}
