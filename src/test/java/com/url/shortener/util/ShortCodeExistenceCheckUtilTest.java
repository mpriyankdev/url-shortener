package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.service.impl.UrlHandlerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;

class ShortCodeExistenceCheckUtilTest {

    @InjectMocks
    private ShortCodeExistenceCheckUtil shortCodeExistenceCheckUtil;

    @Mock
    private UrlHandlerServiceImpl urlHandlerService;

    private BloomFilter<String> filter;

    @BeforeEach
    public void setup() {

        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), 10);
        MockitoAnnotations.openMocks(this);

        ShortUrlInfoEntity testShortUrl = ShortUrlInfoEntity.builder().longUrl("https://google.com").shortCode("gg.l").ttl(10).isDeleted(Boolean.FALSE).creationTime(LocalDateTime.MAX).build();

        Mockito.when(urlHandlerService.countShortCodes()).thenReturn(10l);
        Mockito.when(urlHandlerService.getAllShortUrlInfo()).thenReturn(Arrays.asList(testShortUrl));
        shortCodeExistenceCheckUtil.setFilter(filter);

    }

    @Test
    public void testInitialization() {
        shortCodeExistenceCheckUtil.init();
    }

    @Test
    public void addShortCodeToFilterTest() {
        shortCodeExistenceCheckUtil.addShortCode("abc");
    }

    @Test
    public void checkIfShortCodeExistsTest() {
        shortCodeExistenceCheckUtil.addShortCode("abcd");
        Assertions.assertTrue(shortCodeExistenceCheckUtil.checkIfShortCodeExists("abcd"));
    }


}