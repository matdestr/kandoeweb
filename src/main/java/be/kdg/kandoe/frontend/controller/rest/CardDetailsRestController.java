package be.kdg.kandoe.frontend.controller.rest;

import be.kdg.kandoe.backend.model.cards.CardDetails;
import be.kdg.kandoe.backend.model.organizations.Category;
import be.kdg.kandoe.backend.model.organizations.Organization;
import be.kdg.kandoe.backend.model.organizations.Topic;
import be.kdg.kandoe.backend.model.sessions.Session;
import be.kdg.kandoe.backend.model.users.User;
import be.kdg.kandoe.backend.service.api.CardService;
import be.kdg.kandoe.backend.service.api.CategoryService;
import be.kdg.kandoe.backend.service.api.SessionService;
import be.kdg.kandoe.backend.service.api.TopicService;
import be.kdg.kandoe.frontend.controller.resources.cards.CardDetailsResource;
import be.kdg.kandoe.frontend.controller.resources.cards.CreateCardDetailsResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Rest controller that exposes {@link CardDetails} model
 */

@RestController
@RequestMapping("/api/carddetails")
public class CardDetailsRestController {
    private CardService cardService;
    private TopicService topicService;
    private CategoryService categoryService;
    private MapperFacade mapperFacade;

    @Autowired
    public CardDetailsRestController(CardService cardService, TopicService topicService,
                                     CategoryService categoryService,
                                     MapperFacade mapperFacade) {
        this.cardService = cardService;
        this.topicService = topicService;
        this.categoryService = categoryService;
        this.mapperFacade = mapperFacade;
    }

    @RequestMapping(value = "/topics/{topicId}", method = RequestMethod.GET)
    public ResponseEntity<List<CardDetailsResource>> getCardDetailsOfTopic(@AuthenticationPrincipal User user,
                                                                           @PathVariable("topicId") int topicId) {
        Organization organization = this.topicService.getTopicByTopicId(topicId).getCategory().getOrganization();

        if (!organization.isOrganizer(user)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Set<CardDetails> cardDetailsSet = cardService.getCardDetailsOfTopic(topicId);

        return new ResponseEntity<>(mapperFacade.mapAsList(cardDetailsSet, CardDetailsResource.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<List<CardDetailsResource>> getCardDetailsOfCategory(@AuthenticationPrincipal User user,
                                                                              @PathVariable("categoryId") int categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId);

        if (!category.getOrganization().isOrganizer(user))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Set<CardDetails> cardDetailsSet = cardService.getCardDetailsOfCategory(categoryId);

        return new ResponseEntity<>(mapperFacade.mapAsList(cardDetailsSet, CardDetailsResource.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/topics", method = RequestMethod.POST)
    public ResponseEntity<CardDetailsResource> addCardDetailsToTopic(@AuthenticationPrincipal User user,
                                                                     @RequestParam("topicId") int topicId,
                                                                     @RequestParam("cardDetailsId") int cardDetailsId) {
        Topic topic = this.topicService.getTopicByTopicId(topicId);
        
        if (topic == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        Organization organization = topic.getCategory().getOrganization();

        if (!organization.isOrganizer(user))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        CardDetails cardDetails = this.cardService.getCardDetailsById(cardDetailsId);
        
        if (cardDetails == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        cardDetails = this.cardService.addCardDetailsToTopic(topic, cardDetails);

        return new ResponseEntity<>(mapperFacade.map(cardDetails, CardDetailsResource.class), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<CardDetailsResource> createCardDetails(@AuthenticationPrincipal User user,
                                                                 @RequestParam("categoryId") int categoryId,
                                                                 @Valid @RequestBody CreateCardDetailsResource resource) {
        Category category = this.categoryService.getCategoryById(categoryId);
        Organization organization = category.getOrganization();

        if (!organization.isOrganizer(user))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        CardDetails cardDetails = mapperFacade.map(resource, CardDetails.class);
        cardDetails.setCreator(user);
        
        cardDetails = this.cardService.addCardDetailsToCategory(category, cardDetails);

        return new ResponseEntity<>(mapperFacade.map(cardDetails, CardDetailsResource.class), HttpStatus.CREATED);
    }
}
