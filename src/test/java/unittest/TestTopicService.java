package unittest;

import be.kdg.kandoe.backend.config.BackendContextConfig;
import be.kdg.kandoe.backend.model.organizations.Category;
import be.kdg.kandoe.backend.model.organizations.Organization;
import be.kdg.kandoe.backend.model.organizations.Topic;
import be.kdg.kandoe.backend.model.users.User;
import be.kdg.kandoe.backend.service.api.CategoryService;
import be.kdg.kandoe.backend.service.api.OrganizationService;
import be.kdg.kandoe.backend.service.api.TopicService;
import be.kdg.kandoe.backend.service.api.UserService;
import be.kdg.kandoe.backend.service.exceptions.TopicServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by thaneestevens on 20/02/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BackendContextConfig.class })
@Transactional // Automatically rollbacks after each test
public class TestTopicService {
    @Autowired
    private TopicService topicService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    private Organization organization1,organization2;

    private Category testCategory, testCategory2;

    private Topic testTopic;

    @Before
    public void setUp(){
        User user = new User("test-user", "test-password");
        userService.addUser(user);

        organization1 = new Organization("test-organization-1", userService.getUserByUsername(user.getUsername()));
        organization2 = new Organization("test-organization-2", userService.getUserByUsername(user.getUsername()));
        organizationService.addOrganization(organization1);
        organizationService.addOrganization(organization2);

        testCategory = new Category();
        testCategory.setName("test-category");
        testCategory.setDescription("This is a test category for test purposes only.");
        organization1 = organizationService.getOrganizationByName(organization1.getName());
        categoryService.addCategory(testCategory, organization1);

        testCategory2= new Category();
        testCategory.setName("test-category-2");
        testCategory.setDescription("This is a second test category for test purposes only.");
        organization2 = organizationService.getOrganizationByName(organization2.getName());
        categoryService.addCategory(testCategory2, organization2);

        testTopic = new Topic();
        testTopic.setName("test-topic");
        testTopic.setDescription("This is a test topic for test purposes only.");
        testTopic.setCategory(testCategory);
    }

    @Test
    public void testAddNewTopic(){
        topicService.addTopic(testTopic);
        Topic fetchedTopic = topicService.getTopicByName(testTopic.getName(),testTopic.getCategory());
        assertEquals(testTopic, fetchedTopic);
    }

    @Test (expected = TopicServiceException.class)
    public void testAddExistingTopicInSameCategory() throws TopicServiceException{
        topicService.addTopic(testTopic);
        Topic fetchedTopic = topicService.getTopicByName(testTopic.getName(), testTopic.getCategory());
        assertEquals(testTopic, fetchedTopic);

        Topic newTestTopic = new Topic();
        newTestTopic.setName(fetchedTopic.getName());
        newTestTopic.setCategory(fetchedTopic.getCategory());

        topicService.addTopic(newTestTopic);
    }

    @Test
    public void testAddExistingTopicInDifferentCategory() throws TopicServiceException {
        topicService.addTopic(testTopic);
        Topic fetchedTopic = topicService.getTopicByName(testTopic.getName(), testTopic.getCategory());
        assertEquals(testTopic, fetchedTopic);

        Topic newTestTopic = new Topic();
        newTestTopic.setName(fetchedTopic.getName());
        newTestTopic.setDescription(fetchedTopic.getDescription());
        newTestTopic.setCategory(categoryService.getCategoryByName(testCategory2.getName(),organization2));
        topicService.addTopic(newTestTopic);

        Topic fetchedNewTopic = topicService.getTopicByName(newTestTopic.getName(), newTestTopic.getCategory());
        assertEquals(newTestTopic, fetchedNewTopic);
    }


}