package com.url.shortener.repository;

import com.url.shortener.entity.ShortUrlInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlHandlerRepository extends JpaRepository<ShortUrlInfoEntity, String> {
}
