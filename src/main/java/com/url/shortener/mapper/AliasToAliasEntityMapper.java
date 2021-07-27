package com.url.shortener.mapper;

import com.url.shortener.dto.Alias;
import com.url.shortener.entity.AliasEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AliasToAliasEntityMapper implements Converter<Alias, AliasEntity> {
    @Override
    public AliasEntity convert(Alias alias) {
        return AliasEntity.builder().alias(alias.getAlias())
                .createdAt(alias.getCreatedAt())
                .ttl(alias.getTtl())
                .build();
    }
}
