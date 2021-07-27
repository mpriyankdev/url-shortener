package com.url.shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.url.shortener.model.TTLUnit;
import com.url.shortener.model.UrlShorteningInfoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UrlHandlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Short url when unique alias is provided")
    public void testShortUrl() throws Exception {

        UrlShorteningInfoRequest request = UrlShorteningInfoRequest.builder().url("http://google.com").alias("xyz").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl", is("http://localhost/xyz")))
        ;
    }

    @Test
    @DisplayName("Short url when duplicate alias is provided")
    public void testShortUrlDuplicateAlias() throws Exception {

        UrlShorteningInfoRequest req = UrlShorteningInfoRequest.builder().url("http://google.com").alias("xyz1").build();
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(req))
        ).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(req))
        ).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult1.getResponse().getStatus());
        Assertions.assertEquals(HttpStatus.IM_USED.value(), mvcResult2.getResponse().getStatus());
    }

    @Test
    @DisplayName("Short url when no alias is provided")
    public void testShortUrlWithoutAlias() throws Exception {

        UrlShorteningInfoRequest req1 = UrlShorteningInfoRequest.builder().url("http://google.com").build();
        UrlShorteningInfoRequest req2 = UrlShorteningInfoRequest.builder().url("http://gmail.com").build();
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(req1))
        ).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(req2))
        ).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult1.getResponse().getStatus());
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult2.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test redirect from short-code to long url")
    public void testRedirectShortCodeToLongUrl() throws Exception {
        UrlShorteningInfoRequest req1 = UrlShorteningInfoRequest.builder().url("http://google.com").alias("g.gl").build();
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(req1))
        ).andReturn();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/g.gl").contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assertions.assertEquals(HttpStatus.FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test redirect when providing invalid short-code")
    public void testRedirectWithInvalidShortCode() throws Exception {
        UrlShorteningInfoRequest req1 = UrlShorteningInfoRequest.builder().url("http://google.com").alias("g.gl").build();
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(req1))
        ).andReturn();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ggl").contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test url expired exception")
    public void testUrlExpiredException() throws Exception {
        UrlShorteningInfoRequest request = UrlShorteningInfoRequest.builder().url("https://google.com").ttl(-10).ttlUnit(TTLUnit.SECONDS).alias("ggl").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))).andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ggl").contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test url not provided")
    public void testUrlNotProvided() throws Exception {

        UrlShorteningInfoRequest request = UrlShorteningInfoRequest.builder().ttl(-10).ttlUnit(TTLUnit.SECONDS).alias("ggl").build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))).andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

    }


}