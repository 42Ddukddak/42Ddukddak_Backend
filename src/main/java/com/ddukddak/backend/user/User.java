package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.ChatTable;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    private boolean banned;
    
    @Column(name = "login")
    private String intraId;
    
    private boolean master;
    
    private Long reportNumber;

    private LocalDateTime reportTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private PublicChatRoom publicChatRoom;

    @OneToMany(mappedBy = "user")
    private List<ChatTable> chatTables = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    public User(String userName) {
        this.banned = false;
        this.intraId = userName;
        this.master = false;
        this.reportNumber = 0L;
    }

    /*
        예약 잡은 횟수를 확인하는 로직
     */
    public int getReservationNumber() {
        int count = 0;
        for (Reservation reserved : reservations) {
            if (reserved.getStatus() == ReservationStatus.RESERVE) {
                count += 1;
            }
        }
        return count;
    }

    public List<String> getReservationRoomName() {
        List<String> result = new ArrayList<>();
        for (Reservation reserved : reservations){
            if (reserved.getStatus() == ReservationStatus.RESERVE) {
                result.add(reserved.getChatRoomName());
            }
        }
        return result;
    }
}
