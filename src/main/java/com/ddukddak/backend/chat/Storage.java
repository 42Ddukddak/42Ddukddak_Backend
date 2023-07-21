package com.ddukddak.backend.chat;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Storage {

    private String contents;
}
