package com.url.shortener.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ALIAS_INFO")
public class AliasEntity {

    @Id
    @Column(name = "ALIAS")
    private String alias;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "TTL")
    private int ttl;
}
