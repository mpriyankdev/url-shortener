package com.url.shortener.mapper;

import com.url.shortener.dto.ShortUrlInfo;
import com.url.shortener.entity.ShortUrlInfoEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlInfoToShortUrlInfoEntityMapper implements Converter<ShortUrlInfo, ShortUrlInfoEntity> {
    @Override
    public ShortUrlInfoEntity convert(ShortUrlInfo shortUrlInfo) {

        return ShortUrlInfoEntity.builder().longUrl(shortUrlInfo.getLongUrl())
                .creationTime(shortUrlInfo.getCreationTime())
                .isDeleted(shortUrlInfo.isDeleted())
                .ttl(shortUrlInfo.getTtl())
                .shortCode(shortUrlInfo.getShortCode())
                .ttlUnit(shortUrlInfo.getTtlUnit())
                .build();

    }
}
