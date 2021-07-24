package com.url.shortener.mapper;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlShorteningInfoRequestToAliasEntityMapper implements Converter<UrlShorteningInfoRequest, AliasEntity> {
    @Override
    public AliasEntity convert(UrlShorteningInfoRequest urlShorteningInfoRequest) {
        return AliasEntity.builder()
                .alias(urlShorteningInfoRequest.getAlias())
                .createdAt(LocalDateTime.now())
                .ttl(urlShorteningInfoRequest.getTtl())
                .build();
    }
}
