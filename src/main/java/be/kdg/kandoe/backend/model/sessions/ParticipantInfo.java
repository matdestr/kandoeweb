package be.kdg.kandoe.backend.model.sessions;

import be.kdg.kandoe.backend.model.users.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
public class ParticipantInfo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int id;
    
    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    private User participant;
    
    private boolean joined;
}