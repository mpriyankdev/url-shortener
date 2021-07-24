package com.url.shortener.service;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;

import java.util.Collection;
import java.util.Optional;

public interface AliasHandlerService {
    Optional<AliasEntity> findAliasInfoByAliasName(String aliasName);

    Collection<AliasEntity> getAllAliases();

    long countAliases();

    AliasEntity saveAlias(AliasEntity alias);

    AliasEntity mapAlias(UrlShorteningInfoRequest request);

}
