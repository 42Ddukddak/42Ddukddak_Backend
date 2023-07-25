package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.ChatMessageDTO;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class PrivateChatroomController {

    private final UserService userService;
    private final ChatTableService tableService;
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

    //@RequestParam(name = "roomName") String roomName, @RequestParam(name = "login") String userName
    @PostMapping("/ddukddak")
    public PrivateRoomInfo createDdukddak(@RequestBody PrivateRoomInfo message) throws Exception{
        log.info(message.getHost());
        Long roomId = userService.createPrivateChatRoom(message.getHost(), message.getRoomName());

//        return new ResponseEntity(new PrivateRoomInfo(roomId, roomName, userName, 15, 1), HttpStatus.OK);
        return new PrivateRoomInfo(roomId, message.getRoomName(), message.getHost(), 0);
    }

//    @GetMapping("/ddukddak")
//    public void joinMaster() {
//        Long roomId = tableService.
//    }
//
//    }

}