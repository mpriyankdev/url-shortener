package com.url.shortener.mapper;

import com.url.shortener.dto.ShortUrlInfo;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.service.ShortCodeGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UrlShorteningRequestToShortUrlInfoMapper implements Converter<UrlShorteningInfoRequest, ShortUrlInfo> {

    @Value("${url.shortener.short-code.length:8}")
    private int shortCodeLength;

    @Autowired
    private ShortCodeGeneratorService shortCodeGeneratorService;

    @Override
    public ShortUrlInfo convert(UrlShorteningInfoRequest urlShorteningInfoRequest) {
        log.info("UrlShorteningRequestToShortUrlInfoMapper.convert::Mapping UrlShorteningRequestToShortUrlInfo");
        return ShortUrlInfo.builder()
                .shortCode((urlShorteningInfoRequest.getAlias() != null) ? urlShorteningInfoRequest.getAlias() : shortCodeGeneratorService.generate(shortCodeLength))
                .creationTime(LocalDateTime.now())
                .longUrl(urlShorteningInfoRequest.getUrl())
                .ttl(urlShorteningInfoRequest.getTtl())
                .isDeleted(Boolean.FALSE)
                .ttlUnit(urlShorteningInfoRequest.getTtlUnit().name())
                .build();
    }
}
