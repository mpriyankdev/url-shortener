package com.url.shortener.service.impl;

import com.url.shortener.service.ShortCodeGeneratorService;
import com.url.shortener.util.ShortCodeExistenceCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ShortCodeGeneratorServiceImpl implements ShortCodeGeneratorService {

    @Autowired
    private ShortCodeExistenceCheckUtil shortCodeExistenceCheckUtil;

    @Override
    public String generate(int length) {
        UUID uuid = UUID.randomUUID();
        String longId = uuid.toString().replaceAll("-", "");
        String shortCode = null;
        for (int i = 0; i < longId.length() - length - 1; i++) {
            shortCode = longId.substring(i, i + length);
            if (!shortCodeExistenceCheckUtil.checkIfShortCodeExists(shortCode)) {
                return shortCode;
            }
        }
        log.info("ShortCodeGeneratorServiceImpl.generate::shortCode generated : {} with length : {}", shortCode, length);
        return shortCode;

    }

    public void setShortCodeExistenceCheckUtil(ShortCodeExistenceCheckUtil shortCodeExistenceCheckUtil) {
        this.shortCodeExistenceCheckUtil = shortCodeExistenceCheckUtil;
    }
}
