package com.url.shortener.mapper;

import com.url.shortener.dto.ShortUrlInfo;
import com.url.shortener.entity.ShortUrlInfoEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlInfoEntityToShortUrlInfoMapper implements Converter<ShortUrlInfoEntity, ShortUrlInfo> {
    @Override
    public ShortUrlInfo convert(ShortUrlInfoEntity shortUrlInfoEntity) {

        return ShortUrlInfo.builder().creationTime(shortUrlInfoEntity.getCreationTime())
                .isDeleted(shortUrlInfoEntity.isDeleted())
                .longUrl(shortUrlInfoEntity.getLongUrl())
                .shortCode(shortUrlInfoEntity.getShortCode())
                .ttl(shortUrlInfoEntity.getTtl())
                .build();

    }
}
