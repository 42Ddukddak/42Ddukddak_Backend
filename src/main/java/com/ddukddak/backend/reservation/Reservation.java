package com.ddukddak.backend.reservation;

import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import com.ddukddak.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String chatRoomName;

    private String reservationTime;

    @Column(name = "private_chat_room_id")
    private Long Private_chat_room_id;

    public static Reservation createReservation(User user, String title, Long private_chat_room_id, String time) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setChatRoomName(title);
        reservation.status = ReservationStatus.RESERVE;
        reservation.setReservationTime(time);
        reservation.setPrivate_chat_room_id(private_chat_room_id);
        user.getReservations().add(reservation);
        return reservation;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCEL;
    }
}
