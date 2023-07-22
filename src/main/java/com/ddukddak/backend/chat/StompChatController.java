package com.ddukddak.backend.chat;

import com.ddukddak.backend.chat.dto.ChatMessageDTO;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoomService;
import com.ddukddak.backend.user.UserService;
import com.ddukddak.backend.utils.Define;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final PublicChatRoomService publicChatRoomService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message) {
        message.setMessage(message.getSender() + " 님이 입장 하셨습니다");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(@RequestBody ChatMessageDTO message) {
        log.info(message.getMessage());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message/public")
    public void publicMessage(@RequestBody ChatMessageDTO message) {
        template.convertAndSend("/sub/chat/`public" + Define.PUBLIC_ROOM_ID, message);
    }
//
//    @MessageMapping(value = "/chat/message/private")
//    public void privateMessage(@RequestBody ChatMessageDTO message) {
//        template.convertAndSend("/sub/chat/room" + privateChatRoomService.getRoomId(), message);
//    }
}

