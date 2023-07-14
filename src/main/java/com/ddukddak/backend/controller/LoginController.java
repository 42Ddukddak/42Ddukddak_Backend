package com.ddukddak.backend.controller;

import com.ddukddak.backend.domain.OauthToken;
import com.ddukddak.backend.domain.User42Info;
import com.ddukddak.backend.repository.TokenRepository;
import com.ddukddak.backend.service.ApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final ApiService apiService;
    private final TokenRepository tokenRepository;
    private HttpSession httpSession;

    @GetMapping("/42login")
    public RedirectView goLogin(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://api.intra.42.fr/oauth/authorize?client_id=u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fapi%2Fauth%2Fcallback&response_type=code");
        System.out.println("hello");
        return redirectView;
    }

    @GetMapping("api/auth/callback")
    public String login(HttpServletRequest req, @RequestParam("code") String code){
        System.out.println(code);
        OauthToken oauthToken = apiService.getOauthToken(code);
        System.out.println("1111111111111");
        User42Info user42Info = apiService.get42SeoulInfo(oauthToken.getAccessToken());
        System.out.println("aaaaaaaaaaaaaaaaaa");
        tokenRepository.saveRefreshToken(user42Info.getLogin(), oauthToken);
        httpSession = req.getSession();
        httpSession.setAttribute("name", user42Info.getLogin());
        return "http://localhost:3000";
    }

}
