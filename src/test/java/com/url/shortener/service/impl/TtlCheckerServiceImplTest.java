package com.url.shortener.service.impl;

import com.url.shortener.model.TTLUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;

class TtlCheckerServiceImplTest {

    @InjectMocks
    private TtlCheckerServiceImpl ttlCheckerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfTTLIsValid() {
        Assertions.assertTrue(ttlCheckerService.isValid(LocalDateTime.now(), TTLUnit.DAYS, 1));
        Assertions.assertTrue(ttlCheckerService.isValid(LocalDateTime.now(), TTLUnit.HOURS, 1));
        Assertions.assertTrue(ttlCheckerService.isValid(LocalDateTime.now(), TTLUnit.MINUTES, 1));
        Assertions.assertTrue(ttlCheckerService.isValid(LocalDateTime.now(), TTLUnit.SECONDS, 1));
        Assertions.assertFalse(ttlCheckerService.isValid(LocalDateTime.now(), TTLUnit.SECONDS, -1));
    }

    @Test
    public void testValidTill() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = ttlCheckerService.validTill(now, TTLUnit.DAYS, 1);
        Assertions.assertEquals(now.plus(Duration.ofDays(1)), localDateTime);
    }

}