package com.ddukddak.backend.chat;

import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Storage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Long id;

    private String intraId;
    private String message;
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private PublicChatRoom publicChatRoom;

    public Storage(String intraId, String message, LocalDateTime time){
        this.intraId = intraId;
        this.message = message;
        this.time = time;
    }
}
