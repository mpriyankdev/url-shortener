package com.url.shortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Configuration
public class AliasInflightRequestConfig {

    private Set<String> inflightAliases;

    @Bean(name = "inflightAliases")
    public Set<String> inflightAliases() {
        inflightAliases = new ConcurrentSkipListSet<>();
        return inflightAliases;
    }
}
