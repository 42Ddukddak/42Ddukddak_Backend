package com.ddukddak.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String chatRoomName;

    private LocalDateTime reservationTime;

    public static Reservation createReservation(User user, String title) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setChatRoomName(title);
        reservation.status = ReservationStatus.RESERVE;
        reservation.setReservationTime(LocalDateTime.now());
        return reservation;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCEL;
    }
}
