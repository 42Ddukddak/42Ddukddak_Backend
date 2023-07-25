package com.ddukddak.backend.chat.privateChatRoom;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

@Entity
public class PrivateStorage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_storage_id")
    Long id;

    String contents;

    String intraId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_table_id")
    private ChatTable chatTable;
}
