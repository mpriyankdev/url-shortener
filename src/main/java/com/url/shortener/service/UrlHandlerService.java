package com.url.shortener.service;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;

import java.util.Collection;
import java.util.Optional;

public interface UrlHandlerService {

    Optional<ShortUrlInfoEntity> findUrlInfoByShortCode(String shortCode);

    Collection<ShortUrlInfoEntity> getAllShortUrlInfo();

    long countShortCodes();

    ShortUrlInfoEntity saveUrl(ShortUrlInfoEntity shortUrlInfo);

    ShortUrlInfoEntity mapUrl(UrlShorteningInfoRequest request);
}
