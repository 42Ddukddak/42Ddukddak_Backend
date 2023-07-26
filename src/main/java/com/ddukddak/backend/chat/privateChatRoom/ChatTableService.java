package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableRepository;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomRepository;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatTableService {

    private final ChatTableRepository chatTableRepository;
    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;

    @Transactional
    public Long join(ChatTable chatTable){
        chatTableRepository.save(chatTable);
        return chatTable.getId();
    }

    public void create(User user, PrivateChatRoom privateChatRoom) {
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        user = userRepository.findOne(user.getId());

    }

    @Transactional
    public void saveContents(String sender, String message, String roomId) {
        ChatTable chatTable = chatTableRepository.findOne(Long.parseLong(roomId));
        PrivateStorage privateStorage = new PrivateStorage(sender, message, roomId);

        chatTable.addPrivateStorages(privateStorage);
//        chatTableRepository.save(chatTable);
        /*update
        * 한번 entity가 save가된 시점에서는 save를 재호출하기보다는
        * repo를 갈아끼우는 방식(set)을 추천한다고 하는데
        * 여기에선 그럼 AddPrivateStorage만 해주면 될까?
        * 막줄 Save에 대해....흠
        * */

    }

    public List<PrivateRoomInfo> getAllRoomInfo() {
        List<PrivateRoomInfo> result = new ArrayList<>();
        List<ChatTable> chatTables = chatTableRepository.findAll();

        for (ChatTable table : chatTables) {
            PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
            PrivateRoomInfo privateRoomInfo = new PrivateRoomInfo(table.getId(), privateChatRoom.getRoomName(),
                    table.getHost(), privateChatRoom.getCreateTime(), privateChatRoom.getParticipantsNum());
            result.add(privateRoomInfo);
        }
        return result;
    }

    public ChatTable findOne(Long chatTableId){
        return chatTableRepository.findOne(chatTableId);
    }
}
