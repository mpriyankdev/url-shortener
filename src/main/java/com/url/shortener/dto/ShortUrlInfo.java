package com.url.shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlInfo {

    private String shortCode;
    private String longUrl;
    private LocalDateTime creationTime;
    private int ttl;
    private String ttlUnit;
    private boolean isDeleted;
}
