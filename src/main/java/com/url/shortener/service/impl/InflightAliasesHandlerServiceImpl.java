package com.url.shortener.service.impl;

import com.url.shortener.service.InflightAliasesHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InflightAliasesHandlerServiceImpl implements InflightAliasesHandlerService {

    @Autowired
    @Qualifier(value = "inflightAliases")
    private Set<String> inflightAliases;


    @Override
    public void addInflightAlias(String alias) {
        inflightAliases.add(alias);
    }
}
