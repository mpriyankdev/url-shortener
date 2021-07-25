package com.url.shortener.mapper;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.service.ShortCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlShorteningRequestToShortUrlInfoEntityMapper implements Converter<UrlShorteningInfoRequest, ShortUrlInfoEntity> {

    @Value("${url.shortener.short-code.length:8}")
    private int shortCodeLength;

    @Autowired
    private ShortCodeGeneratorService shortCodeGeneratorService;

    @Override
    public ShortUrlInfoEntity convert(UrlShorteningInfoRequest urlShorteningInfoRequest) {
        return ShortUrlInfoEntity.builder()
                .shortCode((urlShorteningInfoRequest.getAlias() != null) ? urlShorteningInfoRequest.getAlias() : shortCodeGeneratorService.generate(shortCodeLength))
                .creationTime(LocalDateTime.now())
                .longUrl(urlShorteningInfoRequest.getUrl())
                .ttl(urlShorteningInfoRequest.getTtl())
                .isDeleted(Boolean.FALSE)
                .build();
    }
}
