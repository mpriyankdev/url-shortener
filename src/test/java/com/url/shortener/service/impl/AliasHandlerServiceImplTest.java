package com.url.shortener.service.impl;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.repository.AliasHandlerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

class AliasHandlerServiceImplTest {

    @InjectMocks
    private AliasHandlerServiceImpl aliasHandlerService;

    @Mock
    private AliasHandlerRepository aliasHandlerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        AliasEntity xyzAlias = AliasEntity.builder().alias("xyz").ttl(10).createdAt(LocalDateTime.MAX).build();
        AliasEntity abcAlias = AliasEntity.builder().alias("abc").ttl(10).createdAt(LocalDateTime.MAX).build();
        Mockito.when(aliasHandlerRepository.findAll()).thenReturn(Arrays.asList(xyzAlias, abcAlias));
        Mockito.when(aliasHandlerRepository.save(xyzAlias)).thenReturn(xyzAlias);
        Mockito.when(aliasHandlerRepository.count()).thenReturn(2l);
        Mockito.when(aliasHandlerRepository.findById("xyz")).thenReturn(Optional.of(xyzAlias));
    }

    @Test
    @DisplayName("Get all aliases")
    public void getAllAliasesTest() {
        Assertions.assertEquals(2, aliasHandlerService.getAllAliases().size());
    }

    @Test
    @DisplayName("Count all aliases")
    public void countAliasesTest() {
        Assertions.assertEquals(2, aliasHandlerService.countAliases());
    }

    @Test
    @DisplayName("Test save alias")
    public void saveAliasTest() {
        AliasEntity xyzAlias = AliasEntity.builder().alias("xyz").ttl(10).createdAt(LocalDateTime.MAX).build();
        AliasEntity aliasEntity = aliasHandlerService.saveAlias(xyzAlias);
        Assertions.assertEquals("xyz", aliasEntity.getAlias());
    }

    @Test
    @DisplayName("Find alias by aliasName")
    public void findAliasByAliasNameTest() {
        Optional<AliasEntity> aliasEntity = aliasHandlerService.findAliasInfoByAliasName("xyz");
        Assertions.assertEquals("xyz", aliasEntity.get().getAlias());
    }

}