package com.ddukddak.backend.controller;

import com.ddukddak.backend.domain.OauthToken;
import com.ddukddak.backend.domain.User42Info;
import com.ddukddak.backend.repository.TokenRepository;
import com.ddukddak.backend.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@Slf4j

public class LoginController {
    private final ApiService apiService;
    private final TokenRepository tokenRepository;
    private HttpSession httpSession;

    @GetMapping("/42login")
    public RedirectView goLogin(HttpServletRequest req){
        httpSession = req.getSession(false);
        if (httpSession != null)
        {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("a");
            return redirectView;
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://api.intra.42.fr/oauth/authorize?client_id=u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fapi%2Fauth%2Fcallback&response_type=code");
        return redirectView;
    }

//    @RequestMapping(value = "api/auth/callback", method = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping ("api/auth/callback")
    public RedirectView login(HttpServletRequest req, @RequestParam(name = "code") String code) {
        OauthToken oauthToken = apiService.getOauthToken(code);
        User42Info user42Info = apiService.get42SeoulInfo(oauthToken.getAccess_token());
        tokenRepository.saveRefreshToken(user42Info.getLogin(), oauthToken);
        httpSession = req.getSession();
        httpSession.setAttribute("name", user42Info.getLogin());
        System.out.println(httpSession.getId());

//        return "redirect:http://localhost:3000";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:3000");
        return redirectView;
    }

//    @GetMapping ("api/auth/callback")
//    public String ttttt(@RequestParam(name = "code") String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-type", "Application/x-www-form-urlencoded;charset=utf-8");
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727");
//        body.add("client_secret", "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");
//        body.add("code", code);
//        body.add("redirect_uri", "http://localhost:8080/api/auth/callback");
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange("https://api.intra.42.fr/oauth/token", HttpMethod.POST, entity, String.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        OauthToken oauthToken = null;
//
//        try {
//            oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println("\n\n ======= test1 =======");
//        System.out.println("status : " + response.getStatusCode());
//        System.out.println("body : " + response.getBody());
//        return "success";
//    }


}
