package com.url.shortener.controller;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.exception.UrlExpiredException;
import com.url.shortener.exception.UrlNotProvidedException;
import com.url.shortener.model.TTLUnit;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.model.UrlShorteningInfoResponse;
import com.url.shortener.service.AliasHandlerService;
import com.url.shortener.service.ShortenedUrlGeneratorService;
import com.url.shortener.service.TtlCheckerService;
import com.url.shortener.service.UrlHandlerService;
import com.url.shortener.util.AliasExistenceCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Slf4j
public class UrlHandlerController {

    @Autowired
    private AliasExistenceCheckUtil aliasExistenceCheckUtil;

    @Autowired
    private TtlCheckerService ttlCheckerService;

    private ShortenedUrlGeneratorService shortenedUrlGeneratorService;
    private UrlHandlerService urlHandlerService;
    private AliasHandlerService aliasHandlerService;

    public UrlHandlerController(UrlHandlerService urlHandlerService, AliasHandlerService aliasHandlerService, ShortenedUrlGeneratorService shortenedUrlGeneratorService) {
        this.urlHandlerService = urlHandlerService;
        this.aliasHandlerService = aliasHandlerService;
        this.shortenedUrlGeneratorService = shortenedUrlGeneratorService;
    }

    @PostMapping(value = "/shortenUrl")
    public ResponseEntity<UrlShorteningInfoResponse> createShortenUrl(HttpServletRequest request, @RequestBody UrlShorteningInfoRequest urlShorteningInfoRequest) {

        if (urlShorteningInfoRequest.getUrl() == null || urlShorteningInfoRequest.getUrl().isEmpty()) {
            throw new UrlNotProvidedException("Url not provided. Please provide a valid url");
        }

        final String alias = urlShorteningInfoRequest.getAlias();

        if (alias != null) {
            if (!aliasExistenceCheckUtil.checkIfAliasExists(alias)) {
                aliasExistenceCheckUtil.addAliasToFilter(alias);
                log.info("createShortenUrl::Alias : {} can be used", alias);
                final AliasEntity savedAliasInfo = aliasHandlerService.saveAlias(aliasHandlerService.mapAlias(urlShorteningInfoRequest));
                urlShorteningInfoRequest.setAlias(savedAliasInfo.getAlias());
            } else {
                log.warn("createShortenUrl::Alias : {} already used", alias);
                return ResponseEntity.status(HttpStatus.IM_USED).build();
            }
        } else {
            log.info("createShortenUrl::No alias provided. Hence it will be auto-generated.");
        }


        final ShortUrlInfoEntity savedShortUrlInfo = urlHandlerService.saveUrl(urlHandlerService.mapUrl(urlShorteningInfoRequest));

        final String baseUrl = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");

        final String shortenedUrl = shortenedUrlGeneratorService.generateUrl(baseUrl, savedShortUrlInfo.getShortCode());

        log.info("createShortenUrl::shortenedUrl : {}", shortenedUrl);

        final UrlShorteningInfoResponse response = responseMapper(urlShorteningInfoRequest, shortenedUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{shortCode}")
    public ResponseEntity<Void> redirectShortCodeToLongUrl(HttpServletRequest request, @PathVariable("shortCode") String shortCode) {
        log.info("redirectShortCodeToLongUrl::shortCode provided : {}", shortCode);
        Optional<ShortUrlInfoEntity> urlInfoByShortCode = urlHandlerService.findUrlInfoByShortCode(shortCode);

        if (!urlInfoByShortCode.isPresent()) {
            log.warn("redirectShortCodeToLongUrl::Requested shortCode {} not present", shortCode);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ShortUrlInfoEntity shortUrlInfoEntity = urlInfoByShortCode.get();
        if (!ttlCheckerService.isValid(shortUrlInfoEntity.getCreationTime(), TTLUnit.valueOf(shortUrlInfoEntity.getTtlUnit()), shortUrlInfoEntity.getTtl())) {
            log.warn("redirectShortCodeToLongUrl::Requested shortCode {} is expired", shortCode);
            throw new UrlExpiredException(String.format("Provided shortCode expired [%s] , please recreate", shortUrlInfoEntity.getShortCode()));
        }

        final String longUrl = urlInfoByShortCode.get().getLongUrl();
        log.info("redirectShortCodeToLongUrl::shortCode : {} --> longUrl : {}", shortCode, longUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
    }

    public void setAliasExistenceCheckUtil(AliasExistenceCheckUtil aliasExistenceCheckUtil) {
        this.aliasExistenceCheckUtil = aliasExistenceCheckUtil;
    }

    public void setTtlCheckerService(TtlCheckerService ttlCheckerService) {
        this.ttlCheckerService = ttlCheckerService;
    }

    private UrlShorteningInfoResponse responseMapper(UrlShorteningInfoRequest request, String shortenedUrl) {
        final UrlShorteningInfoResponse response = UrlShorteningInfoResponse.builder()
                .actualUrl(request.getUrl())
                .createdAt(LocalDateTime.now())
                .shortenedUrl(shortenedUrl)
                .validTill(ttlCheckerService.validTill(LocalDateTime.now(), request.getTtlUnit(), request.getTtl()))
                .build();


        return response;
    }
}
