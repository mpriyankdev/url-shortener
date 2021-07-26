package com.url.shortener.mapper;

import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlShorteningRequestToShortUrlInfoEntityMapperTest {

    private UrlShorteningRequestToShortUrlInfoEntityMapper mapper;

    @Test
    public void urlShorteningRequestToShortUrlInfoEntityMapperTest() {

        mapper = new UrlShorteningRequestToShortUrlInfoEntityMapper();
        UrlShorteningInfoRequest request = UrlShorteningInfoRequest.builder().url("https://google.com").alias("xyz").ttl(10).build();
        ShortUrlInfoEntity converted = mapper.convert(request);
        Assertions.assertNotNull(converted);

    }

}