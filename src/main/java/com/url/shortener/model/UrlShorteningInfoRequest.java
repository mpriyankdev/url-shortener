package com.url.shortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShorteningInfoRequest {
    private String url;
    private String alias;
    private int ttl;

}
