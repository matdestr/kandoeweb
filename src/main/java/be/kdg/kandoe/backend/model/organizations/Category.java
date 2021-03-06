package be.kdg.kandoe.backend.model.organizations;

import be.kdg.kandoe.backend.model.cards.CardDetails;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * A Category is part of one organization
 * Consists of a name and description
 * Has a list of tags, cards and topics
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"organization", "tags", "cards", "topics"})
public class Category {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int categoryId;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(optional = false)
    private Organization organization;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;
    
    //@OneToMany(targetEntity = CardDetails.class, fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
    @OneToMany(targetEntity = CardDetails.class, fetch = FetchType.EAGER, mappedBy = "category"/*, cascade = CascadeType.ALL*/) // mappedBy is needed to prevent unique key violation!
    private Set<CardDetails> cards;

    @OneToMany
    private List<Topic> topics;
}
