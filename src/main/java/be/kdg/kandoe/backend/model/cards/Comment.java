package be.kdg.kandoe.backend.model.cards;

import be.kdg.kandoe.backend.model.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A comment on a CardDetail by a user
 * Consist of a message and datetime
 */

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    //@Setter(AccessLevel.NONE)
    private int commentId;

    @OneToOne
    private User user;

    private LocalDateTime datePosted;

    @Column(nullable = false)
    private String message;
}
