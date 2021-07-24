package com.url.shortener.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SHORT_URL_INFO")
public class ShortUrlInfoEntity {

    @Id
    @Column(name = "SHORT_CODE")
    private String shortCode;
    @Lob
    @Column(name = "LONG_URL")
    private String longUrl;
    @Column(name = "CREATION_TIME")
    private LocalDateTime creationTime;
    @Column(name = "TTL")
    private int ttl;
    @Column(name = "IS_DELETED")
    private boolean isDeleted;

}