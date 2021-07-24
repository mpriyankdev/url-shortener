package com.url.shortener.service.impl;

import com.url.shortener.service.ShortCodeGeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShortCodeGeneratorServiceImpl implements ShortCodeGeneratorService {

    @Override
    public String generate(int length) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
