package com.url.shortener.service.impl;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.mapper.UrlShorteningInfoRequestToAliasEntityMapper;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.repository.AliasHandlerRepository;
import com.url.shortener.service.AliasHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class AliasHandlerServiceImpl implements AliasHandlerService {
    @Autowired
    private UrlShorteningInfoRequestToAliasEntityMapper aliasEntityMapper;

    private AliasHandlerRepository aliasHandlerRepository;

    public AliasHandlerServiceImpl(AliasHandlerRepository aliasHandlerRepository) {
        this.aliasHandlerRepository = aliasHandlerRepository;
    }


    @Override
    public Optional<AliasEntity> findAliasInfoByAliasName(String aliasName) {
        log.info("AliasHandlerServiceImpl.findAliasInfoByAliasName::aliasName={}", aliasName);
        return aliasHandlerRepository.findById(aliasName);
    }

    @Override
    public Collection<AliasEntity> getAllAliases() {
        return aliasHandlerRepository.findAll();
    }

    @Override
    public long countAliases() {
        return aliasHandlerRepository.count();
    }

    @Override
    public AliasEntity saveAlias(AliasEntity alias) {
        log.debug("AliasHandlerServiceImpl.saveAlias::alias={}", alias);
        return aliasHandlerRepository.save(alias);
    }

    @Override
    public AliasEntity mapAlias(UrlShorteningInfoRequest request) {
        log.debug("AliasHandlerServiceImpl.mapAlias::request={}", request);
        return aliasEntityMapper.convert(request);
    }

    public void setAliasEntityMapper(UrlShorteningInfoRequestToAliasEntityMapper aliasEntityMapper) {
        this.aliasEntityMapper = aliasEntityMapper;
    }
}
