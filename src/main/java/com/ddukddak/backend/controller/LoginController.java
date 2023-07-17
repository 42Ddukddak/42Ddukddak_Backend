package com.ddukddak.backend.controller;

import com.ddukddak.backend.domain.OauthToken;
import com.ddukddak.backend.domain.Token;
import com.ddukddak.backend.domain.User42Info;
import com.ddukddak.backend.repository.TokenRepository;
import com.ddukddak.backend.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("api/42login")
    public RedirectView goLogin(HttpServletRequest req){
        httpSession = req.getSession(false);
        if (httpSession != null)
        {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://localhost");
            return redirectView;
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://api.intra.42.fr/oauth/authorize?client_id=u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fapi%2Fauth%2Fcallback&response_type=code");
        return redirectView;
    }

    @GetMapping ("api/auth/callback")
    public RedirectView login(HttpServletRequest req, HttpServletResponse res, @RequestParam(name = "code") String code) {
        OauthToken oauthToken = apiService.getOauthToken(code);
        User42Info user42Info = apiService.get42SeoulInfo(oauthToken.getAccess_token());
        String key = tokenRepository.saveRefreshToken(user42Info.getLogin(), oauthToken);
        httpSession = req.getSession();
        httpSession.setAttribute("name", user42Info.getLogin());
        Cookie cookie = new Cookie("key", key);
        cookie.setMaxAge(50*120);
        cookie.setPath("/");
        res.addCookie(cookie);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost");
        return redirectView;
    }
}
