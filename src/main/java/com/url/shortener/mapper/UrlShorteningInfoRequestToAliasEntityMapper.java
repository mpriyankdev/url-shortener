package com.url.shortener.mapper;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UrlShorteningInfoRequestToAliasEntityMapper implements Converter<UrlShorteningInfoRequest, AliasEntity> {
    @Override
    public AliasEntity convert(UrlShorteningInfoRequest urlShorteningInfoRequest) {
        log.info("UrlShorteningInfoRequestToAliasEntityMapper.convert::Mapping UrlShorteningInfoRequest To AliasEntity");
        return AliasEntity.builder()
                .alias(urlShorteningInfoRequest.getAlias())
                .createdAt(LocalDateTime.now())
                .ttl(urlShorteningInfoRequest.getTtl())
                .build();
    }
}
