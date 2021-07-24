package com.url.shortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class TestController {

    @GetMapping(value = "/r")
    public ResponseEntity<Void> redirect() {

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://www.google.com/search?q=irish+time+now&sxsrf=ALeKk01o6i1oxMcrHHPvy4EP_Ml8-KVoCg%3A1627064430220&source=hp&ei=bgj7YNScCtOf4-EPrJK4EA&iflsig=AINFCbYAAAAAYPsWfk0bRXZrvc6XfQyAxLxFyCpaP6Dm&oq=&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJzIHCCMQ6gIQJ1AAWABghPoVaAFwAHgAgAEAiAEAkgEAmAEAqgEHZ3dzLXdperABCg&sclient=gws-wiz")).build();
    }

    // Post method to accept the long-url , alias-optionally and TTL if needed.
    // Perform the ID generation and store the url and return the shortnedUrl.
    // Now when user gives us the shortUrl , check if url exists , if it is valid , then redirect to the actual url , otherwise not-found


}
