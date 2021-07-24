package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.service.AliasHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

@Component
@DependsOnDatabaseInitialization
public class AliasExistenceCheckUtil {

    @Autowired
    private AliasHandlerService aliasHandlerService;

    private BloomFilter<String> filter;

    @PostConstruct
    public void init() {

        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), aliasHandlerService.countAliases());

        aliasHandlerService.getAllAliases().stream().forEach(ele -> filter.put(ele.getAlias()));

    }

    public Boolean checkIfAliasExists(String alias) {
        boolean b = filter.mightContain(alias);
        System.out.println(b);
        return b;
    }
}
