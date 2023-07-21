package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.ChatRoomDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Slf4j
public class PrivateChatroomController {

    private final PrivateChatRoomRepository repository;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms() {

        log.info("# All chat room");

        ModelAndView mv = new ModelAndView("chat/rooms");
        mv.addObject("list", repository.findAllRooms());

        return mv;
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, RedirectAttributes rttr) {

        log.info(" # Created Chat room!" + name);

        ChatRoomDTO room = repository.createChatRoomDTO(name);
        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name));
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(String roomId, Model model, Principal principal) {
        log.info("# get Chat Room, id : " + roomId);

        model.addAttribute("room", repository.findRoomByName(roomId));
    }
//    @GetMapping("/room")
//    public ModelAndView getRoom(@RequestParam(value = "roomId") String roomId) {
//        ModelAndView mv = new ModelAndView("chat/room");
//        mv.addObject("room", repository.findRoomByName(roomId));
//
//        return mv;
//    }


}
