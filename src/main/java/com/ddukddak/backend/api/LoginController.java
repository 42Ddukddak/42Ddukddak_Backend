package com.ddukddak.backend.api;

import com.ddukddak.backend.api.dto.User42Info;
import com.ddukddak.backend.api.entity.OauthToken;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoomRepository;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoomService;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserService;
import com.ddukddak.backend.utils.Define;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final ApiService apiService;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final PublicChatRoomService publicChatRoomService;
    private final PublicChatRoomRepository repository;
    private HttpSession httpSession;

    @Operation(summary = "go login page", description = "로그인 페이지로 이동시키는 API")
    @GetMapping("api/42login")
    public String goLogin(HttpServletRequest req) {
        httpSession = req.getSession(false);
        if (httpSession != null)
            return "redirect:" + Define.DOMAIN;
        return "redirect:" + Define.REDIRECT_URI;
    }

    @Operation(summary = "login", description = "42api와 연결해 로그인 시키는 API",
            parameters = {
                @Parameter(name = "code", description = "42api 콜백 주소에서 제공하는 code", in = ParameterIn.QUERY)
            },
            responses = {
                @ApiResponse(responseCode = "200")
            }
    )
    @PostMapping ("api/auth/42login")
    @ResponseBody
    public ResponseEntity login(HttpServletResponse res, HttpServletRequest req, @RequestParam(name = "code") String code) {

        OauthToken oauthToken = apiService.getOauthToken(code);
        User42Info user42Info = apiService.get42SeoulInfo(oauthToken.getAccess_token());

        PublicChatRoom room = repository.findOne(1006L);
        if (room == null) {
//            room = publicChatRoomService.create();
            room = new PublicChatRoom();
        }
        User user = userService.create(user42Info.getLogin(), room);
        publicChatRoomService.join(user);


        String key = tokenRepository.saveRefreshToken(user42Info.getLogin(), oauthToken);
        httpSession = req.getSession();
        httpSession.setAttribute("name", user42Info.getLogin());

//        Cookie cookie = new Cookie("key", key);
        Cookie cookie = new Cookie("intraId", user42Info.getLogin());
        cookie.setMaxAge(50*120);
        cookie.setPath("/");

        res.addCookie(cookie);

        return new ResponseEntity(HttpStatus.OK);
    }
}
