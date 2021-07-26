package com.url.shortener.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlShorteningInfoRequestTest {

    @Test
    public void urlShorteningInfoRequestTest() {

        UrlShorteningInfoRequest request = UrlShorteningInfoRequest.builder().url("https://google.com").build();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("https://google.com", request.getUrl());
        Assertions.assertNull(request.getAlias());
        Assertions.assertEquals(0, request.getTtl());

    }

}