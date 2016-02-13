package integrationtest.OAuth;

import be.kdg.kandoe.backend.model.oauth.OAuthClientDetails;
import be.kdg.kandoe.backend.service.api.OAuthClientDetailsService;
import be.kdg.kandoe.frontend.config.RootContextConfig;
import be.kdg.kandoe.frontend.config.WebContextConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootContextConfig.class, WebContextConfig.class, OAuth2TestConfig.class})
@WebAppConfiguration
@Transactional
public class ITTestOAuthEndpoints {
    @Autowired
    WebApplicationContext context;

    /*@Autowired
    private FilterChainProxy springSecurityFilterChain;*/

    /*@Autowired
    private OAuthClientDetailsService oAuthClientDetailsService;*/

    private MockMvc mvc;

    @Before
    public void setUp() {
        // Debug info - TODO : Remove
        /*if (context == null)
            System.out.println("WebApplicationContext is null !!");
        else
            System.out.println("WebApplicationContext initialized");
        
        if (springSecurityFilterChain == null)
            System.out.println("FilterChainProxy is null !!");
        else
            System.out.println("FilterChainProxy initialized");*/
        // TODO : Remove Debug info
        
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context)
                //.addFilter(springSecurityFilterChain)
                //.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
        
        /*OAuthClientDetails clientDetails = new OAuthClientDetails("test");
        clientDetails.setAuthorizedGrandTypes("password", "refresh_token");
        clientDetails.setAuthorities("ROLE_TEST_CLIENT");
        clientDetails.setScopes("read");
        clientDetails.setSecret("secret");

        oAuthClientDetailsService.addClientsDetails(clientDetails);*/
    }

    @Test
    public void testNotProtectedMethod() throws Exception {
        mvc.perform(get("/test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    public void testAuthorizedMethodAsUnauthorized() throws Exception {
        /*mvc.perform(get("/test/auth")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());*/
    }


}
