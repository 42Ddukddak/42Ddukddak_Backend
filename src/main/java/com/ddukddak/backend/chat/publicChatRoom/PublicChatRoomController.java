package com.ddukddak.backend.chat.publicChatRoom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class PublicChatRoomController {

    @GetMapping("/ws")
    public String chatGET() {
        log.info("@ChatController, chatGET");

        return "redirect:/";
    }
}
