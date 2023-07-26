package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.ChatMessageDTO;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class PrivateChatroomController {

    private final UserService userService;
    private final ChatTableService tableService;
    private final PrivateChatRoomService privateChatRoomService;
    //채팅방 목록 조회
//    @GetMapping(value = "/rooms")
//    public ModelAndView rooms() {
//
//        log.info("# All chat room");
//
//        ModelAndView mv = new ModelAndView("chat/rooms");
//        mv.addObject("list", repository.findAllRooms());
//
//        return mv;
//    }

    //채팅방 개설
//    @PostMapping(value = "/room")
//    public String create(@RequestParam String name, RedirectAttributes rttr) {
//
//        log.info(" # Created Chat room!" + name);
//
//        ChatRoomDTO room = repository.createChatRoomDTO(name);
//        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name));
//        return "redirect:/chat/rooms";
//    }

    //채팅방 조회
//    @GetMapping("/room")
//    public void getRoom(String roomId, Model model, Principal principal) {
//        log.info("# get Chat Room, id : " + roomId);
//
//        model.addAttribute("room", repository.findRoomByName(roomId));
//    }
    @GetMapping("/roomList")
    public List<PrivateRoomInfo> showRoomList() {
        return tableService.getAllRoomInfo();
    }

    @PostMapping("/ddukddak")
    public PrivateRoomInfo createDdukddak(@RequestBody PrivateRoomInfo message) throws Exception{
        log.info(message.getLogin());
        Long tableId = userService.createPrivateChatRoom(message.getLogin(), message.getRoomName());

        return new PrivateRoomInfo(tableId, message.getRoomName(), message.getLogin(), 15L,1);
    }

//    @GetMapping("/ddukddak")
//    public void joinMaster() {
//        Long roomId = tableService.
//    }
//
//    }

}