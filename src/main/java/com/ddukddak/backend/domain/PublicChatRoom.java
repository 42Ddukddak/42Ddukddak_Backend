package com.ddukddak.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class PublicChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "public_room_id")
    private Long id;

    private String publicContents;

    @OneToMany(mappedBy = "publicChatRoom")
    private List<User> users = new ArrayList<>();
}
