package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.service.UrlHandlerService;
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
public class ShortCodeExistenceCheckUtil {

    @Value("${url.shortener.short-code-check.offset:1000}")
    private int offset;

    @Autowired
    private UrlHandlerService urlHandlerService;

    public ShortCodeExistenceCheckUtil(UrlHandlerService urlHandlerService) {
        this.urlHandlerService = urlHandlerService;
    }

    private BloomFilter<String> filter;

    @PostConstruct
    public void init() {

        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), urlHandlerService.countShortCodes() + offset);

        urlHandlerService.getAllShortUrlInfo().stream().forEach(ele -> filter.put(ele.getShortCode()));
        log.info("Initialised Bloom-Filter to check membership of shortCodes");

    }

    public void addShortCode(String shortCode) {
        filter.put(shortCode);
    }

    public Boolean checkIfShortCodeExists(String shortCode) {
        boolean isPresent = filter.mightContain(shortCode);
        log.info("ShortCodeExistenceCheckUtil.checkIfShortCodeExists::isPresent={}", isPresent);
        if (isPresent) {
            // check the database once just to be sure
            log.info("Checking the database , just to be sure for shortCode : {}", shortCode);
            return urlHandlerService.findUrlInfoByShortCode(shortCode).isPresent();
        }
        return isPresent;
    }

    public void setUrlHandlerService(UrlHandlerService urlHandlerService) {
        this.urlHandlerService = urlHandlerService;
    }

    public void setFilter(BloomFilter<String> filter) {
        this.filter = filter;
    }
}
