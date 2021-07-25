package com.url.shortener.service.impl;

import com.url.shortener.service.ShortenedUrlGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class ShortenedUrlGeneratorServiceImpl implements ShortenedUrlGeneratorService {
    @Override
    public String generateUrl(String baseUrl, String shortCode) {
        String shortenedUrl;
        if (baseUrl.endsWith("/")) {
            shortenedUrl = baseUrl.concat(shortCode);
        } else {
            shortenedUrl = baseUrl.concat("/").concat(shortCode);
        }
        return shortenedUrl;
    }
}
