package com.ddukddak.backend.chat.privateChatRoom;

import jakarta.persistence.*;

@Entity
public class PrivateStorage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_storage_id")
    Long id;

    String contents;

    String intraId;

    private ChatTable chatTable;
}
