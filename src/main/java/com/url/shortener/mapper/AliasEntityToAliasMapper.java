package com.url.shortener.mapper;

import com.url.shortener.dto.Alias;
import com.url.shortener.entity.AliasEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AliasEntityToAliasMapper implements Converter<AliasEntity, Alias> {
    @Override
    public Alias convert(AliasEntity aliasEntity) {
        return Alias.builder().alias(aliasEntity.getAlias())
                .createdAt(aliasEntity.getCreatedAt())
                .ttl(aliasEntity.getTtl())
                .ttlUnit(aliasEntity.getTtlUnit())
                .build();
    }
}
