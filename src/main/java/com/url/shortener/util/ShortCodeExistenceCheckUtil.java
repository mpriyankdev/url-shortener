package com.url.shortener.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.url.shortener.service.UrlHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

@Component
public class ShortCodeExistenceCheckUtil {

    @Value("${url.shortener.short-code-check.offset:1000}")
    private int offset;

    @Autowired
    private UrlHandlerService urlHandlerService;

    private BloomFilter<String> filter;

    @PostConstruct
    public void init() {

        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), urlHandlerService.countShortCodes() + offset);

        urlHandlerService.getAllShortUrlInfo().stream().forEach(ele -> filter.put(ele.getShortCode()));

    }

    public void addShortCode(String shortCode) {
        filter.put(shortCode);
    }

    public Boolean checkIfShortCodeExists(String shortCode) {
        boolean b = filter.mightContain(shortCode);
        return b;
    }
}
