package com.url.shortener.mapper;

import com.url.shortener.dto.Alias;
import com.url.shortener.model.UrlShorteningInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UrlShorteningInfoRequestToAliasMapper implements Converter<UrlShorteningInfoRequest, Alias> {
    @Override
    public Alias convert(UrlShorteningInfoRequest urlShorteningInfoRequest) {
        log.info("UrlShorteningInfoRequestToAliasMapper.convert::Mapping UrlShorteningInfoRequest To Alias");
        return Alias.builder()
                .alias(urlShorteningInfoRequest.getAlias())
                .createdAt(LocalDateTime.now())
                .ttl(urlShorteningInfoRequest.getTtl())
                .build();
    }
}
