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

    @Transactional
    public Long join(User user){
        PublicChatRoom publicChatRoom = user.getPublicChatRoom();
        publicChatRoom.getUsers().add(user);
        chatRoomRepository.save(publicChatRoom);
        return publicChatRoom.getId();
    }

    @Transactional
    public PublicChatRoom findOne(Long id){
        PublicChatRoom publicChatRoom = chatRoomRepository.findOne(id);
        if (publicChatRoom == null) {
            publicChatRoom = this.create();
        }
        return publicChatRoom;
    }

    @Transactional
    public PublicChatRoom create(){
        PublicChatRoom publicChatRoom = new PublicChatRoom();
//        publicChatRoom.setId(Define.PUBLIC_CHAT_ROOM_ID);
        chatRoomRepository.save(publicChatRoom);
        return publicChatRoom;
    }

    @Transactional // 이부분 transactional 안걸려도 될듯.. reposit에서 저장 갈기니까
    public void saveContents(String userName, String contents, LocalDateTime time) {
        PublicChatRoom pub = chatRoomRepository.findOne(Define.PUBLIC_CHAT_ROOM_ID);
        Storage storage = new Storage(userName, contents, time);
//
//        if (pub.getStorages().size() >= Define.MAX_COUNT) {
//            pub.setStorages(new ArrayList<>()); // 특정 개수를
//
//        }
        pub.getStorages().add(storage);
        chatRoomRepository.save(pub);
    }
}
