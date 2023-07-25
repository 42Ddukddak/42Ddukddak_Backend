package com.ddukddak.backend.chat;

import com.ddukddak.backend.chat.dto.ChatMessageDTO;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomService;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoomService;
import com.ddukddak.backend.user.UserService;
import com.ddukddak.backend.utils.Define;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final PublicChatRoomService publicChatRoomService;
    private final PrivateChatRoomService privateChatRoomService;

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

    /*
    * public쪽으로 메세지 전달
    * saveContents 이후 convert 태워서 바로 창에 띄우도록 전달
    * saveContents에서는 각각의 Storage 생성, db에 저장하도록.
    * */
    @MessageMapping(value = "/chat/message/public")
    public void publicMessage(@RequestBody ChatMessageDTO message) {
        log.info("msg is ...!!!" + message.getMessage());

        publicChatRoomService.saveContents(message.getSender(), message.getMessage());
        template.convertAndSend("/sub/chat/public/" + Define.PUBLIC_CHAT_ROOM_ID, message);
    }

    @MessageMapping(value = "chat/message/private")
    public void privateMessage(@RequestBody ChatMessageDTO message) {
        log.info("i'm in private msg.... : " + message.getMessage());

        privateChatRoomService.saveContents(message.getSender(), message.getMessage(), message.getRoomId());
        template.convertAndSend("/sub/chat/room/1", message);

    }


//    @MessageMapping(value = "/chat/message/private")
//    public void privateMessage(@RequestBody ChatMessageDTO message) {
//        log.info(message.getMessage());
//        privateChatRoomService.saveContents(message.getSender(), message.getMessage(), message.getRoomId());
//        template.convertAndSend("/sub/chat/room/" + privateChatRoomService.getRoomId(), message);
//    }
}

