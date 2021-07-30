package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.entity.AliasEntity;
import com.url.shortener.service.impl.AliasHandlerServiceImpl;
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
import java.util.Optional;

class AliasExistenceCheckUtilTest {

    @InjectMocks
    private AliasExistenceCheckUtil aliasExistenceCheckUtil;

    @Mock
    private AliasHandlerServiceImpl aliasHandlerService;

    private BloomFilter<String> filter;


    @BeforeEach
    public void setup() {
        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), 10);
        MockitoAnnotations.openMocks(this);

        AliasEntity testAlias = AliasEntity.builder().alias("xyz").ttl(10).createdAt(LocalDateTime.MAX).build();

        Mockito.when(aliasHandlerService.countAliases()).thenReturn(10l);
        Mockito.when(aliasHandlerService.getAllAliases()).thenReturn(Arrays.asList(testAlias));
        Mockito.when(aliasHandlerService.findAliasInfoByAliasName("abcd")).thenReturn(Optional.of(testAlias));
        aliasExistenceCheckUtil.setFilter(filter);
    }

    @Test
    public void testInitialization() {
        aliasExistenceCheckUtil.init();
    }

    @Test
    public void addAliasToFilterTest() {
        aliasExistenceCheckUtil.addAliasToFilter("abc");
    }

    @Test
    public void checkIfAliasExistsTest() {
        aliasExistenceCheckUtil.addAliasToFilter("abcd");
        Assertions.assertTrue(aliasExistenceCheckUtil.checkIfAliasExists("abcd"));
    }


}