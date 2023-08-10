package com.ddukddak.backend.report;

import com.ddukddak.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReportedMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reported_message_id")
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static ReportedMessage create(String message, User user) {
        ReportedMessage reportedMessage = new ReportedMessage();
        reportedMessage.setMessage(message);
        reportedMessage.setUser(user);

        return reportedMessage;
    }
}
