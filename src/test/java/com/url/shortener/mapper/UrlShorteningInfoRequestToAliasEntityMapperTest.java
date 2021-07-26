package com.url.shortener.mapper;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlShorteningInfoRequestToAliasEntityMapperTest {

    private UrlShorteningInfoRequestToAliasEntityMapper mapper;


    @Test
    public void urlShorteningInfoRequestToAliasEntityMapperTest() {

        mapper = new UrlShorteningInfoRequestToAliasEntityMapper();
        UrlShorteningInfoRequest request = UrlShorteningInfoRequest.builder().url("https://google.com").alias("xyz").ttl(10).build();
        AliasEntity converted = mapper.convert(request);
        Assertions.assertNotNull(converted);

    }

}