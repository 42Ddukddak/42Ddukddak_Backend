package com.ddukddak.backend.chat;

import com.ddukddak.backend.chat.dto.ChatMessageDTO;
import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableService;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoomService;
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
    private final PublicChatRoomService publicChatRoomService;
    private final ChatTableService chatTableService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message) {
        message.setMessage(message.getLogin() + " 님이 입장 하셨습니다");
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

        publicChatRoomService.saveContents(message.getLogin(), message.getMessage());
        template.convertAndSend("/sub/chat/public/" + Define.PUBLIC_CHAT_ROOM_ID, message);
    }

    /*
    * sender, roomid, message -> 8개 정형화 해서 보내기...
    * */
    @MessageMapping(value = "chat/message/private")
    public void UniformDTO(@RequestBody ChatMessageDTO message) {
        log.info("i'm in private msg.... : " + message.getMessage());
        log.info("sender is..." + message.getSender());

        //한번에 너무 많은 길이 validation 필요~
        chatTableService.saveContents(message.getSender(), message.getMessage(), message.getRoomId());
        ChatTable table = chatTableService.findOne(message.getRoomId());
        UniformDTO res = chatTableService.create(table.getPrivateChatRoom().getId(), message.getSender(), message.getMessage(), 5);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), res);
    }

}

