package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateMessage;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class PrivateChatroomController {

    private final UserService userService;
    private final ChatTableService tableService;
    private final PrivateChatRoomService privateChatRoomService;
    private final ChatTableService chatTableService;
    private final SimpMessagingTemplate template;
    private int person;

    @GetMapping("/roomList")
    public List<PrivateRoomInfo> showRoomList() {

        return tableService.getAllRoomInfo();
    }

    //새로운 방 만들기
    @PostMapping("/ddukddak")
    public PrivateRoomInfo createDdukddak(@RequestBody PrivateRoomInfo message) throws Exception{
        log.info(message.getLogin());
        Long tableId = userService.createPrivateChatRoom(message.getLogin(), message.getRoomName());

        return new PrivateRoomInfo(tableId, message.getRoomName(), message.getLogin(), 15L,1);
    }

    /*
    * 방의 db 정보, 방 내부의 userList 처리 방식 고민..
    * 들어잇는 db의 정보를 제거하고, convertAndSendToUser 방식으로 보내는건?
    * */
    @PostMapping("/private/{id}/leave")
    public void leaveRoom(@PathVariable Long id) {
//        ChatTable room = chatTableService.findOne(id);
        chatTableService.remove(id);
        log.info("in leave post" + HttpStatus.OK);
        template.convertAndSend("/sub/chat/room/" + id, HttpStatus.OK);
    }

    //만들어진 방에 들어가기
    @GetMapping("/private/{id}")
    public List<UniformDTO> showOneRoomInfo(@PathVariable Long id) {
        log.info("selected room : " + id.toString());
        return chatTableService.getMessageInfo(id);
    }
}

