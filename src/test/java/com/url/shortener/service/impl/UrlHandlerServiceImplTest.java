package com.url.shortener.service.impl;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.mapper.UrlShorteningRequestToShortUrlInfoEntityMapper;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.repository.UrlHandlerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

class UrlHandlerServiceImplTest {

    @InjectMocks
    private UrlHandlerServiceImpl urlHandlerService;

    @Mock
    private UrlShorteningRequestToShortUrlInfoEntityMapper shortUrlInfoEntityMapper;

    @Mock
    private UrlHandlerRepository urlHandlerRepository;

    @Mock
    private ShortCodeGeneratorServiceImpl shortCodeGeneratorService;

    private ShortUrlInfoEntity shortUrlInfo1;
    private ShortUrlInfoEntity shortUrlInfo2;
    private UrlShorteningInfoRequest ggl;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        shortUrlInfo1 = ShortUrlInfoEntity.builder().longUrl("http://google.com").shortCode("xyz").creationTime(LocalDateTime.MAX).isDeleted(Boolean.FALSE).ttl(10).build();
        shortUrlInfo2 = ShortUrlInfoEntity.builder().longUrl("http://gmail.com").shortCode("abc").creationTime(LocalDateTime.MAX).isDeleted(Boolean.FALSE).ttl(10).build();
        Mockito.when(urlHandlerRepository.save(shortUrlInfo1)).thenReturn(shortUrlInfo1);
        Mockito.when(urlHandlerRepository.findAll()).thenReturn(Arrays.asList(shortUrlInfo1, shortUrlInfo2));
        Mockito.when(urlHandlerRepository.findById("xyz")).thenReturn(Optional.of(shortUrlInfo1));
        Mockito.when(urlHandlerRepository.count()).thenReturn(2l);

        ggl = UrlShorteningInfoRequest.builder().url("http://google.com").alias("xyz").ttl(10).build();
        Mockito.when(shortUrlInfoEntityMapper.convert(ggl)).thenReturn(shortUrlInfo1);

        Mockito.when(shortCodeGeneratorService.generate(3)).thenReturn("xyz");
    }

    @Test
    public void findUrlInfoByShortCodeTest() {
        Optional<ShortUrlInfoEntity> shortUrlInfoEntity = urlHandlerService.findUrlInfoByShortCode("xyz");
        Assertions.assertEquals("xyz", shortUrlInfoEntity.get().getShortCode());
    }

    @Test
    public void getAllShortUrlInfoTest() {
        Collection<ShortUrlInfoEntity> allShortUrlInfo = urlHandlerService.getAllShortUrlInfo();
        Assertions.assertEquals(2, allShortUrlInfo.size());
    }

    @Test
    public void countShortCodesTest() {
        long l = urlHandlerService.countShortCodes();
        Assertions.assertEquals(2l, l);
    }

    @Test
    public void saveUrlTest() {
        ShortUrlInfoEntity shortUrlInfoEntity = urlHandlerService.saveUrl(shortUrlInfo1);
        Assertions.assertEquals("xyz", shortUrlInfoEntity.getShortCode());
    }

}