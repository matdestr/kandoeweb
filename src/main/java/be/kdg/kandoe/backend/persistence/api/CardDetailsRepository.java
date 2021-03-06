package be.kdg.kandoe.backend.persistence.api;

import be.kdg.kandoe.backend.model.cards.CardDetails;
import be.kdg.kandoe.backend.model.organizations.Category;
import be.kdg.kandoe.backend.model.organizations.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Proxy interface for the CRUD Repository for the {@link CardDetails} model
 */
public interface CardDetailsRepository extends JpaRepository<CardDetails, Integer> {
    @Query("SELECT cd FROM CardDetails cd JOIN cd.topics t WHERE t.topicId = (:topicId)")
    Set<CardDetails> findCardDetailsByTopicId(@Param("topicId") int topicId);

    @Query("SELECT cd FROM CardDetails cd INNER JOIN cd.category c WHERE c.categoryId = (:categoryId)")
    Set<CardDetails> findCardDetailsByCategoryId(@Param("categoryId") int categoryId);

    //@Query("SELECT cd FROM CardDetails cd JOIN cd.category cat WHERE cat = (:category) AND LOWER(cd.text) = LOWER(:text)")
    @Query("SELECT cd FROM CardDetails cd JOIN cd.category cat WHERE cat = (:category) AND LOWER(cd.text) = LOWER(:text)")
    CardDetails findCardDetailsByCategoryAndText(@Param("category") Category category, @Param("text") String text);

    @Query("SELECT cd FROM CardDetails cd JOIN cd.topics t WHERE t = (:topic) AND LOWER(cd.text) = LOWER(:text)")
    CardDetails findCardDetailsByTopicAndText(@Param("topic") Topic topic, @Param("text") String text);
}
