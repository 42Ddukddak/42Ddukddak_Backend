package com.ddukddak.backend.controller;

import com.ddukddak.backend.domain.OauthToken;
import com.ddukddak.backend.domain.User42Info;
import com.ddukddak.backend.repository.TokenRepository;
import com.ddukddak.backend.service.ApiService;
import com.ddukddak.backend.utils.Define;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final ApiService apiService;
    private final TokenRepository tokenRepository;
    private HttpSession httpSession;

    @GetMapping("api/42login")
    public String goLogin(HttpServletRequest req) {
        httpSession = req.getSession(false);
        if (httpSession != null)
            return "redirect:" + Define.DOMAIN;
        return "redirect:" + Define.REDIRECT_URI;
    }

    @PostMapping ("api/auth/42login")
    @ResponseBody
    public ResponseEntity login(HttpServletResponse res, HttpServletRequest req, @RequestParam(name = "code") String code) {
        OauthToken oauthToken = apiService.getOauthToken(code);
        User42Info user42Info = apiService.get42SeoulInfo(oauthToken.getAccess_token());

        String key = tokenRepository.saveRefreshToken(user42Info.getLogin(), oauthToken);
        httpSession = req.getSession();
        httpSession.setAttribute("name", user42Info.getLogin());

        Cookie cookie = new Cookie("key", key);
        cookie.setMaxAge(50*120);
        cookie.setPath("/");

        res.addCookie(cookie);

        return new ResponseEntity(HttpStatus.OK);
    }
}
