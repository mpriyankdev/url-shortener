package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.service.AliasHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

@Slf4j
@Component
@DependsOnDatabaseInitialization
public class AliasExistenceCheckUtil {

    @Value("${url.shortener.alias-check.offset:1000}")
    private int offset;

    @Autowired
    private AliasHandlerService aliasHandlerService;

    private BloomFilter<String> filter;

    @PostConstruct
    public void init() {

        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), aliasHandlerService.countAliases() + offset);

        aliasHandlerService.getAllAliases().stream().forEach(ele -> filter.put(ele.getAlias()));
        log.info("Initialised Bloom-Filter to check membership of aliases");

    }

    public void addAliasToFilter(String alias) {
        filter.put(alias);
    }

    public Boolean checkIfAliasExists(String alias) {
        boolean isPresent = filter.mightContain(alias);
        log.info("AliasExistenceCheckUtil.checkIfAliasExists::isPresent={}", isPresent);
        if (isPresent) {
            // check the database once just to be sure
            log.info("Checking the database , just to be sure for alias : {}", alias);
            return aliasHandlerService.findAliasInfoByAliasName(alias).isPresent();
        }
        return isPresent;
    }

    public void setAliasHandlerService(AliasHandlerService aliasHandlerService) {
        this.aliasHandlerService = aliasHandlerService;
    }

    public void setFilter(BloomFilter<String> filter) {
        this.filter = filter;
    }
}
