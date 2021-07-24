package com.url.shortener.repository;

import com.url.shortener.entity.AliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AliasHandlerRepository extends JpaRepository<AliasEntity, String> {
}
