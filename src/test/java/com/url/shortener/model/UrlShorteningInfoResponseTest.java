package com.url.shortener.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlShorteningInfoResponseTest {

    @Test
    public void urlShorteningInfoResponseTest() {
        UrlShorteningInfoResponse response = UrlShorteningInfoResponse.builder().shortenedUrl("https://localhost:9090/g.gl").build();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("https://localhost:9090/g.gl", response.getShortenedUrl());
        Assertions.assertNull(response.getActualUrl());
        Assertions.assertNull(response.getCreatedAt());
        Assertions.assertNull(response.getOptionalInfo());
        Assertions.assertNull(response.getValidTill());
    }

}