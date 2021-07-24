package com.url.shortener.service.impl;

import com.url.shortener.service.ShortenedUrlGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class ShortenedUrlGeneratorServiceImpl implements ShortenedUrlGeneratorService {
    @Override
    public String generateUrl(String baseUrl, String shortCode) {
        String shortenedUrl = baseUrl.concat("/").concat(shortCode);
        System.out.println(shortenedUrl);
        return shortenedUrl;
    }
}
