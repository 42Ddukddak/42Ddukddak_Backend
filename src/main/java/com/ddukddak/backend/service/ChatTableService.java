package com.ddukddak.backend.service;

import com.ddukddak.backend.domain.ChatTable;
import com.ddukddak.backend.repository.ChatTableRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatTableService {

    private final ChatTableRepository chatTableRepository;

    @Transactional
    public Long join(ChatTable chatTable){
        chatTableRepository.save(chatTable);
        return chatTable.getId();
    }

    public ChatTable findOne(Long chatTableId){
        return chatTableRepository.findOne(chatTableId);
    }
}
