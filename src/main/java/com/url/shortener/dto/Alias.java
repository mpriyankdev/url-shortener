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
public class Alias {

    private String alias;
    private LocalDateTime createdAt;
    private int ttl;
    private String ttlUnit;
}


