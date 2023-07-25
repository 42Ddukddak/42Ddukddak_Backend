package com.ddukddak.backend.chat.publicChatRoom;

import com.ddukddak.backend.chat.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PublicChatRoomController {

    private final PublicChatRoomService publicChatRoomService;

    @GetMapping("/ws")
    public String chatGET() {
        log.info("@ChatController, chatGET");

        return "redirect:/";
    }

//    @PostMapping("/sub/chat/public") // 경로 나중에 수정
//    public ResponseEntity<String> saveMessage(@RequestBody ChatMessageDTO data) {
//        if (data.getMessage() != null && data.getSender() != null) {
//            publicChatRoomService.saveContents(data.getSender(), data.getMessage(), data.getTime());
//            return ResponseEntity.ok("메세지 저장 성공");
//        } else {
//            return ResponseEntity.badRequest().body("잘못된 메시지 데이터");
//        }
//    }
}
