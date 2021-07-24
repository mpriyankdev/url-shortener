package com.url.shortener.service.impl;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.mapper.UrlShorteningRequestToShortUrlInfoEntityMapper;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.repository.UrlHandlerRepository;
import com.url.shortener.service.UrlHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UrlHandlerServiceImpl implements UrlHandlerService {

    @Autowired
    private UrlShorteningRequestToShortUrlInfoEntityMapper shortUrlInfoEntityMapper;

    private UrlHandlerRepository urlHandlerRepository;

    public UrlHandlerServiceImpl(UrlHandlerRepository urlHandlerRepository) {
        this.urlHandlerRepository = urlHandlerRepository;
    }

    @Override
    public Optional<ShortUrlInfoEntity> findUrlInfoByShortCode(String shortCode) {
        return urlHandlerRepository.findById(shortCode);
    }

    @Override
    public Collection<ShortUrlInfoEntity> getAllShortUrlInfo() {
        return urlHandlerRepository.findAll();
    }

    @Override
    public ShortUrlInfoEntity saveUrl(ShortUrlInfoEntity shortUrlInfo) {
        return urlHandlerRepository.save(shortUrlInfo);
    }

    @Override
    public ShortUrlInfoEntity mapUrl(UrlShorteningInfoRequest request) {
        return shortUrlInfoEntityMapper.convert(request);
    }
}
