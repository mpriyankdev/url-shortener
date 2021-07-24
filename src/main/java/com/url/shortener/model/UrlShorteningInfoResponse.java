package com.url.shortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShorteningInfoResponse {

    private String actualUrl;
    private String shortenedUrl;
    private LocalDateTime createdAt;
    private LocalDateTime validTill;
    private String optionalInfo;

}
