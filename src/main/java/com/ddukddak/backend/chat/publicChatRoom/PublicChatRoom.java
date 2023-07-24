package com.ddukddak.backend.chat.publicChatRoom;

import com.ddukddak.backend.chat.Storage;
import com.ddukddak.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter @Setter
public class PublicChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "public_room_id")
    private Long id;

    private String publicContents;

    @OneToMany(mappedBy = "publicChatRoom")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "publicChatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Storage> storages = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setPublicChatRoom(this);
    }

    public void addStorage(Storage storage) {
        storages.add(storage);
        storage.setPublicChatRoom(this);
    }
}
