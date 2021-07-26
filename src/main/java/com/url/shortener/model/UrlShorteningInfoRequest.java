package com.url.shortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShorteningInfoRequest {

    @NotBlank(message = "long url cannot be blank")
    private String url;
    private String alias;
    private int ttl;

}
