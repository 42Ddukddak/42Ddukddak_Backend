package com.ddukddak.backend.chat.publicChatRoom;

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
    @JoinColumn(name = "public_room_id")
    private PublicChatRoom publicChatRoom;

    public Storage(String intraId, String message){
        this.intraId = intraId;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}
