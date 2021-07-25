package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.service.AliasHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

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

    }

    public void addAliasToFilter(String alias) {
        filter.put(alias);
    }

    public Boolean checkIfAliasExists(String alias) {
        boolean b = filter.mightContain(alias);
        System.out.println(b);
        return b;
    }
}
