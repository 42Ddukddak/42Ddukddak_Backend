package com.ddukddak.backend.service;

import com.ddukddak.backend.domain.OauthToken;
import com.ddukddak.backend.domain.User42Info;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {
    private final ObjectMapper om = new ObjectMapper();
    private final RestTemplate rt = new RestTemplate();
    HttpHeaders headers;
    HttpEntity<MultiValueMap<String, String>> req;
    MultiValueMap<String, String> params;
    ResponseEntity<String> res;

    public OauthToken getOauthToken(String code){
        req = req42TokenHeader(code);
        res = resPostApi(req, req42TokenUri());
        return readOauthToken(res.getBody());
    }
    public User42Info get42SeoulInfo(String token){
        req = req42ApiHeader(token);
        res = resGetApi(req, req42UserUri());
        return readUser42Info(res.getBody());
    }
    public HttpEntity<MultiValueMap<String, String>> req42TokenHeader(String code){
       headers = new HttpHeaders();
       params = new LinkedMultiValueMap<>();
       headers.set("Content-type", "Application/x-www-form-urlencoded;charset=utf-8");

       params.add("grant_type", "authorization_code");
       params.add("client_id", "u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727");
       params.add("client_secret", "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");
       params.add("code", code);
       params.add("redirect_uri", "http://localhost:8080/api/auth/callback");

       return new HttpEntity<>(params, headers);
    }

    public HttpEntity<MultiValueMap<String, String>> req42ApiHeader(String token) {
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Accept", "application/json;charset=utf-8");
        params = new LinkedMultiValueMap<>();
        return new HttpEntity<>(params, headers);
    }

    public ResponseEntity<String> resPostApi(HttpEntity<MultiValueMap<String, String>> req, URI uri){
        return rt.exchange(uri.toString(),
                HttpMethod.POST,
                req,
                String.class);
    }
    public ResponseEntity<String> resGetApi(HttpEntity<MultiValueMap<String, String>> req, URI uri){
        return rt.exchange(uri.toString(),
                HttpMethod.GET,
                req,
                String.class);
    }
    public URI req42TokenUri(){
        return UriComponentsBuilder.fromHttpUrl("https://api.intra.42.fr/oauth/token")
                .build()
                .toUri();
    }
    public URI req42UserUri(){
        return UriComponentsBuilder.newInstance().scheme("https://api.intra.42.fr").path("/v2/me")
                .build()
                .toUri();
    }
    public OauthToken readOauthToken(String body){
        OauthToken oauthToken = null;
        try {
            oauthToken = om.readValue(body, OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return oauthToken;
    }

    public User42Info readUser42Info(String body){
        User42Info user42Info = null;
        try{
            user42Info = om.readValue(body, User42Info.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user42Info;
    }
}
