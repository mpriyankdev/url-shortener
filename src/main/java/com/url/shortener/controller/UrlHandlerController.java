package com.url.shortener.controller;

import com.url.shortener.entity.AliasEntity;
import com.url.shortener.entity.ShortUrlInfoEntity;
import com.url.shortener.model.UrlShorteningInfoRequest;
import com.url.shortener.model.UrlShorteningInfoResponse;
import com.url.shortener.service.AliasHandlerService;
import com.url.shortener.service.InflightAliasesHandlerService;
import com.url.shortener.service.ShortenedUrlGeneratorService;
import com.url.shortener.service.UrlHandlerService;
import com.url.shortener.util.AliasExistenceCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class UrlHandlerController {

    @Autowired
    private AliasExistenceCheckUtil aliasExistenceCheckUtil;

    @Autowired
    private InflightAliasesHandlerService inflightAliasesHandlerService;

    @Autowired
    private ShortenedUrlGeneratorService shortenedUrlGeneratorService;


    private UrlHandlerService urlHandlerService;
    private AliasHandlerService aliasHandlerService;

    public UrlHandlerController(UrlHandlerService urlHandlerService, AliasHandlerService aliasHandlerService) {
        this.urlHandlerService = urlHandlerService;
        this.aliasHandlerService = aliasHandlerService;
    }

    @PostMapping(value = "/shortenedUrl")
    public ResponseEntity<UrlShorteningInfoResponse> createShortenUrl(HttpServletRequest request, @RequestBody UrlShorteningInfoRequest urlShorteningInfoRequest) {

        final String alias = urlShorteningInfoRequest.getAlias();

        final boolean b = (alias != null) ? aliasExistenceCheckUtil.checkIfAliasExists(alias) : false; //always check for false using bloom-filter

        if (!b && alias != null) {
            inflightAliasesHandlerService.addInflightAlias(alias);
        }

        final AliasEntity savedAliasInfo = aliasHandlerService.saveAlias(aliasHandlerService.mapAlias(urlShorteningInfoRequest));
        final ShortUrlInfoEntity savedShortUrlInfo = urlHandlerService.saveUrl(urlHandlerService.mapUrl(urlShorteningInfoRequest));

        String baseUrl = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");

        final String shortenedUrl = shortenedUrlGeneratorService.generateUrl(baseUrl, savedShortUrlInfo.getShortCode());

        final UrlShorteningInfoResponse response = UrlShorteningInfoResponse.builder().actualUrl(urlShorteningInfoRequest.getUrl()).createdAt(LocalDateTime.now()).shortenedUrl(shortenedUrl).validTill(LocalDateTime.MAX).optionalInfo("no optional info").build();


        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{shortCode}")
    public ResponseEntity<Void> redirect(HttpServletRequest request, @PathVariable("shortCode") String shortCode) {
        System.out.println(shortCode);
        Optional<ShortUrlInfoEntity> urlInfoByShortCode = urlHandlerService.findUrlInfoByShortCode(shortCode);

        if (!urlInfoByShortCode.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String longUrl = urlInfoByShortCode.get().getLongUrl();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
    }


}
