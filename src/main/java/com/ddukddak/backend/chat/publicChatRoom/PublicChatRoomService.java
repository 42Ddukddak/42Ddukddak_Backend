package com.ddukddak.backend.chat.publicChatRoom;

import com.ddukddak.backend.chat.Storage;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.utils.Define;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicChatRoomService {

    public final PublicChatRoomRepository chatRoomRepository;

    public Long join(User user){
        PublicChatRoom publicChatRoom = chatRoomRepository.findOne(1006L);
        publicChatRoom.addUser(user);
        chatRoomRepository.save(publicChatRoom);
        return publicChatRoom.getId();
    }
    public PublicChatRoom findOne(Long id){
        PublicChatRoom publicChatRoom = chatRoomRepository.findOne(id);
        if (publicChatRoom == null) {
            publicChatRoom = this.create();
        }
        return publicChatRoom;
    }

    public PublicChatRoom create(){
        PublicChatRoom publicChatRoom = new PublicChatRoom();
        chatRoomRepository.save(publicChatRoom);
        return publicChatRoom;
    }

    public void saveContents(String userName, String contents, LocalDateTime time) {
        PublicChatRoom pub = chatRoomRepository.findOne(1006L);
        Storage storage = new Storage(userName, contents, time);
//
//        if (pub.getStorages().size() >= Define.MAX_COUNT) {
//            pub.setStorages(new ArrayList<>()); // 특정 개수를
//
//        }
        pub.addStorage(storage);
        chatRoomRepository.save(pub);
    }
}
