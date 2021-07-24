package com.url.shortener.mapper;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.service.ShortCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlShorteningRequestToShortUrlInfoEntityMapper implements Converter<UrlShorteningInfoRequest, ShortUrlInfoEntity> {

    @Autowired
    private ShortCodeGeneratorService shortCodeGeneratorService;

    @Override
    public ShortUrlInfoEntity convert(UrlShorteningInfoRequest urlShorteningInfoRequest) {
        return ShortUrlInfoEntity.builder()
                .shortCode(shortCodeGeneratorService.generate(5))
                .creationTime(LocalDateTime.now())
                .longUrl(urlShorteningInfoRequest.getUrl())
                .ttl(urlShorteningInfoRequest.getTtl())
                .isDeleted(Boolean.FALSE)
                .build();
    }
}
