package com.url.shortener.util;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.repository.AliasHandlerRepository;
import com.url.shortener.repository.UrlHandlerRepository;
import com.url.shortener.service.AliasHandlerService;
import com.url.shortener.service.UrlHandlerService;
import com.url.shortener.service.impl.AliasHandlerServiceImpl;
import com.url.shortener.service.impl.UrlHandlerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;

class AliasExistenceCheckUtilTest {

    private UrlHandlerService urlHandlerService;
    private AliasHandlerService aliasHandlerService;
    private ShortUrlInfoEntity shortUrlInfoEntity;
    private AliasExistenceCheckUtil aliasExistenceCheckUtil;

    @Mock
    private UrlHandlerRepository urlHandlerRepository;
    @Mock
    AliasHandlerRepository aliasHandlerRepository;

    @BeforeEach
    public void init() {
        urlHandlerService = new UrlHandlerServiceImpl(urlHandlerRepository);
        shortUrlInfoEntity = ShortUrlInfoEntity.builder().longUrl("https://google.com").shortCode("ggl").creationTime(LocalDateTime.now()).isDeleted(Boolean.FALSE).build();
        Mockito.when(urlHandlerRepository.save(shortUrlInfoEntity)).thenReturn(shortUrlInfoEntity);

        urlHandlerService.saveUrl(shortUrlInfoEntity);
        aliasExistenceCheckUtil = new AliasExistenceCheckUtil();
        aliasHandlerService = new AliasHandlerServiceImpl(aliasHandlerRepository);
        aliasExistenceCheckUtil.setAliasHandlerService(aliasHandlerService);
    }

    @Test
    public void test() {
        aliasExistenceCheckUtil.init();
        aliasExistenceCheckUtil.checkIfAliasExists("ggl");
        Assertions.assertTrue(aliasExistenceCheckUtil.checkIfAliasExists("ggl"));
    }

}