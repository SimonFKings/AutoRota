package autorota

import autorota.controller.AuthenticationController
import autorota.repository.BusinessRepository
import autorota.repository.RoleRepository
import autorota.repository.UserRepository
import autorota.services.CustomUserDetailsService
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@ContextConfiguration
@WebMvcTest(controllers = [AuthenticationController.class])
class AuthenticationTesting extends Specification {

    @Autowired
    WebApplicationContext wac


    @Autowired
    MockMvc mockMvc
    ResultActions result

    @MockBean
    UserRepository userRepo

    @InjectMocks
    CustomUserDetailsService userService;

    @MockBean
    BusinessRepository bussRepo

    @MockBean
    RoleRepository roleRepo

    @Before
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Unroll
    def "test #testId: Request #request returns #viewName"() {
        given: "the context controller is set up"
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()


        when: "I do a get #request "
        result = this.mockMvc.perform(get("/$request"))

        then: "I should view the #viewName"
        result
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));

        where:
        testId | request         | viewName
        1      | 'login'         | 'loginPage'
        2      | 'access-denied' | 'accessDenied'
        3      | 'error-login'   | 'loginPage'
    }


    @Unroll
    def "test2 #testId: Request #request returns #viewName"() {
        given: "the context controller is set up"
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build()


        when: "I do a get #request "
        result = this.mockMvc.perform(logout('/user-logout'))


        then: "I should view the #viewName"
        result
//                .andExpect(view().name('loginPage'))
                .andExpect(unauthenticated())


    }


}