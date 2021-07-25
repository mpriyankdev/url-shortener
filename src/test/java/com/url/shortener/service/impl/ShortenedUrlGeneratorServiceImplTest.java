package com.url.shortener.service.impl;

import com.url.shortener.service.ShortenedUrlGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShortenedUrlGeneratorServiceImplTest {


    private ShortenedUrlGeneratorService shortenedUrlGeneratorService = new ShortenedUrlGeneratorServiceImpl();

    @Test
    @DisplayName("Generating test url with baseUrl and shortCode")
    public void generateUrlTest() {
        String url1 = shortenedUrlGeneratorService.generateUrl("http://localhost:9090/", "xyz");
        String url2 = shortenedUrlGeneratorService.generateUrl("http://localhost:9090", "xyz");

        Assertions.assertEquals("http://localhost:9090/xyz", url1);
        Assertions.assertEquals("http://localhost:9090/xyz", url2);
    }

}