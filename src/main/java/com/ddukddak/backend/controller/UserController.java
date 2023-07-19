package com.ddukddak.backend.controller;

import com.ddukddak.backend.repository.UserRepository;
import com.ddukddak.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String mainPage() {

        return "/";
    }
}
