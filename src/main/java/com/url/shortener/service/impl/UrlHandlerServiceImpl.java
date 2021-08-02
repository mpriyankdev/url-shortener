package com.url.shortener.service.impl;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.mapper.UrlShorteningRequestToShortUrlInfoEntityMapper;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.repository.UrlHandlerRepository;
import com.url.shortener.service.UrlHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class UrlHandlerServiceImpl implements UrlHandlerService {

    private UrlShorteningRequestToShortUrlInfoEntityMapper shortUrlInfoEntityMapper;

    private UrlHandlerRepository urlHandlerRepository;

    public UrlHandlerServiceImpl(UrlHandlerRepository urlHandlerRepository) {
        this.urlHandlerRepository = urlHandlerRepository;
    }

    @Override
    public Optional<ShortUrlInfoEntity> findUrlInfoByShortCode(String shortCode) {
        log.info("UrlHandlerServiceImpl.findUrlInfoByShortCode::shortCode={}", shortCode);
        return urlHandlerRepository.findById(shortCode);
    }

    @Override
    public Collection<ShortUrlInfoEntity> getAllShortUrlInfo() {
        return urlHandlerRepository.findAll();
    }

    @Override
    public long countShortCodes() {
        return urlHandlerRepository.count();
    }

    @Override
    public ShortUrlInfoEntity saveUrl(ShortUrlInfoEntity shortUrlInfo) {
        log.debug("UrlHandlerServiceImpl.saveUrl::shortUrlInfo={}", shortUrlInfo);
        return urlHandlerRepository.save(shortUrlInfo);
    }

    @Override
    public ShortUrlInfoEntity mapUrl(UrlShorteningInfoRequest request) {
        log.debug("UrlHandlerServiceImpl.mapUrl::urlShorteningInfoRequest={}", request);
        return shortUrlInfoEntityMapper.convert(request);
    }

    @Autowired
    public void setShortUrlInfoEntityMapper(UrlShorteningRequestToShortUrlInfoEntityMapper shortUrlInfoEntityMapper) {
        this.shortUrlInfoEntityMapper = shortUrlInfoEntityMapper;
    }
}
