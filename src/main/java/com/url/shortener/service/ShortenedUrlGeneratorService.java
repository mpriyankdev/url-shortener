package com.url.shortener.service;

public interface ShortenedUrlGeneratorService {

    String generateUrl(String baseUrl, String shortCode);
}
