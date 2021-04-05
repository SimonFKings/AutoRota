package autorota

import autorota.controller.AuthenticationController
import autorota.controller.AvailabilityController
import autorota.domain.*
import autorota.repository.BusinessRepository
import autorota.repository.HolidayRepository
import autorota.repository.RoleRepository
import autorota.repository.UserRepository
import autorota.services.BookingValidation
import autorota.services.CustomUserDetailsService
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.security.web.FilterChainProxy
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes=[AutorotaApplication.class,SecurityConfig.class,
        AuthenticationController.class, WebConfig.class, CustomUserDetailsService.class, AvailabilityController.class])
@AutoConfigureMockMvc(secure=true)
 public class SecurityTest extends Specification {

    @Autowired
    WebApplicationContext wac

        @Autowired
     MockMvc mockMvc
      ResultActions result

//    @InjectMocks
//    AuthenticationController authenticationController;

@MockBean
UserRepository userRepo

    @InjectMocks
    CustomUserDetailsService userService;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @MockBean
     BusinessRepository bussRepo

    @MockBean
     RoleRepository roleRepo

    @MockBean
    HolidayRepository holidayRepo


    @MockBean
    BookingValidation bookingValidation


     void setup() {
         MockitoAnnotations.initMocks(this);

     }

    @Test
    def "authenticationTest"() {
        given:
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        when:""
        result = this.mockMvc.perform(get("home").with(user("/$username").roles("/$role")))
        then:
        result.andExpect(status().is(302))
                .andExpect(authenticated().withUsername("/$username").withRoles("/$role"))

        where:
        id| username | role
        1 | 'martial@test.com' | "ADMIN"
        2 | 'rashford@test.com' | "EMPLOYEE"


    }

    @Test
    def "unAuthenticationTest"() {
        given:
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        when: ""
        result = this.mockMvc.perform(formLogin().user("email","/$username").
                password("password","/$password"))
        then:
        result.andExpect(unauthenticated())

        where:
        id | username | password
        3 | "maguire@test.com" | "invalidPassword"
        4 | "wan-bissaka@test.com" | "password"
        5 | "shaw@test.com" | " "

    }

    @Test
    def "authorizationTest"(){
        given:
        UserInfo userInfo = new UserInfo();
        Business business = new Business()
        business.setName("Test Business")
        business.setCity("London")
        business.setCountry("GB")
        business.setBookings(new ArrayList<Booking>())
        userInfo.setBusiness(business)

        Role myRole = new Role();
        myRole.setRole("Test role")
        userInfo.setRole(myRole)

        Availability availability = new Availability()
        userInfo.setAvailability(availability)

        Holiday holiday = new Holiday();
        holiday.setStartDateS("11/06/2020")
        holiday.setEndDateS("20/06/2020")

        Rota rota = new Rota();
        List<Day> dayList = new ArrayList<>();
        Day day = new Day();
        List<Shift> shiftList = new ArrayList<>();
        Shift shift = new Shift();
        shiftList.add(shift)
        day.setShiftList(shiftList)
        dayList.add(day);
        rota.setDaysList();


        Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userInfo)
        Mockito.when(bussRepo.findById(Mockito.anyInt())).thenReturn(business)
        Mockito.when(holidayRepo.findById(Mockito.anyInt())).thenReturn(holiday)
        Mockito.when(roleRepo.findAll()).thenReturn(new ArrayList<Role>())
        Mockito.when(userRepo.findAll()).thenReturn(new ArrayList<UserInfo>())


        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        when: "I perform a request #url"
        if(method=='get') {
            if(url == 'holiday/approved'){
                result = this.mockMvc.perform(get("/$url").param("holidayId" , "10").secure(true).with(user(email).roles(role)))
            }else if (url == 'rota/?weekNum=19&year=2020&create=Create') {
                result = this.mockMvc.perform(get("/$url").
                secure(true).with(user(email).roles(role)))


            }else {
                    result = this.mockMvc.perform(get("/$url").secure(true).with(user(email).roles(role)))
                }

        }else {

            //Its a post method
            if (url == 'booking/new') {
                result = this.mockMvc.perform(post("/$url").param("startTime", "10:10")
                        .param("endTime", "11:30").param("customer", "Simon").
                        secure(true).with(user(email).roles(role)))
            }else if (url == 'holiday/new') {
                result = this.mockMvc.perform(post("/$url").param("startDateS", "20/05/2020")
                        .param("endDateS", "25/10/2020").
                        secure(true).with(user(email).roles(role)))

            }else if(url == 'register/newUser'){
                result = this.mockMvc.perform(post("/$url").param("forename", "John")
                        .param("lastname", "Cena").param("jobTitle", "Waiter").
                        param("email", "cena@test.com").param("age", "23").
                        param("salary","10.50").
                        secure(true).with(user(email).roles(role)))

            }else if(url == 'settings/save'){
                result = this.mockMvc.perform(post("/$url").param("city", "London")
                        .secure(true).with(user(email).roles(role)))

            }else if(url == 'rota/add'){
                result = this.mockMvc.perform(post("/$url")
                        .secure(true).with(user(email).roles(role)))
            }else
                result = this.mockMvc.perform(post("/$url").secure(true).with(user(email).roles(role)))
        }
        then:
        result.andExpect(status().is(statusCode))
                .andExpect(authenticated().withUsername(email).withRoles(role))


                where:
        id | method | url | email | role | statusCode
        6 | 'get' | 'home' | 'pogba@test.com' | "ADMIN" | 200
        7 | 'get' | 'home' | 'martial@test.com' | "EMPLOYEE" | 200
        8 | 'get' | 'availability' | 'tuanzebe@test.com' | "ADMIN" | 200
        9 | 'get' | 'availability' | "ole@test.com" | 'EMPLOYEE' | 200
        10 | 'post' | 'availability/add' | 'rooney@test.com' | 'ADMIN' | 200
        11 | 'post' | 'availability/add' | 'ronaldo@test.com' | 'EMPLOYEE' | 200
        12 | 'get' | 'booking/' | 'siralex@test.com' | 'ADMIN' | 200
        13 | 'get' | 'booking/' | 'LVG@test.com' | 'EMPLOYEE' | 403
        14 | 'post' | 'booking/new' | 'young@test.com' | 'ADMIN' | 200
        15 | 'post' | 'booking/new' | 'valencia@test.com' | 'EMPLOYEE' | 403
        16 | 'get' | 'holiday/' | 'keane@test.com' | 'ADMIN' | 200
        17 | 'get' | 'holiday/' | 'stam@test.com' | 'EMPLOYEE' | 200
        18 | 'post' | 'holiday/new' | 'lingard@test.com' | 'ADMIN' | 200
        19 | 'post' | 'holiday/new' | 'lindelof@test.com' | 'EMPLOYEE' || 200
        20 | 'get' | 'holiday/all' | 'james@test.com' | 'ADMIN' | 200
        21 | 'get' | 'holiday/all' | 'mcTominay@test.com' | 'EMPLOYEE' | 403
        22 | 'get' | 'holiday/approved' | 'fred@test.com' | 'ADMIN' | 200
        23 | 'get' | 'holiday/approved' | 'jones@test.com' | 'EMPLOYEE' | 403
        24 | 'get' | 'my-profile/' | 'smalling@test.com' | 'ADMIN' | 200
        25 | 'get' | 'my-profile/' | 'chong@test.com' | 'EMPLOYEE' | 200
//        26 | 'post' | 'my-profile/edit' | 'gomes@test.com' | 'ADMIN' | 200
//        27 | 'post' | 'my-profile/edit' | 'garner@test.com' | 'EMPLOYEE' | 200
        27 | 'get' | 'register/' | 'remero@test.com' | 'ADMIN' | 200
        28 | 'get' | 'register/' | 'henderson@test.com' | 'EMPLOYEE' | 403
        29 | 'post' | 'register/newUser' | 'ramous@test.com' | 'ADMIN' | 200
        30 | 'post' | 'register/newUser' | 'pique@test.com' | 'EMPLOYEE' | 403
        31 | 'get' | 'settings/' | 'kante@test.com' | 'ADMIN' | 200
        32 | 'get' | 'settings/' | 'matuidi@test.com' | 'EMPLOYEE' | 403
        33 | 'post' | 'settings/save' | 'umtiti@test.com' | 'ADMIN' | 200
        34 | 'post' | 'settings/save' | 'sancho@test.com' | 'EMPLOYEE' | 403
        35 | 'get' | 'rota/today' | 'grealish@test.com' | 'ADMIN' | 200
        36 | 'get' | 'rota/today' | 'maddison@test.com' | 'EMPLOYEE' | 200
        37 | 'get' | 'rota/?weekNum=19&year=2020&create=Create' | 'iwobi@test.com' | 'ADMIN' | 200
        38 | 'get' | 'rota/?weekNum=19&year=2020&create=Create' | 'ighalo@test.com' | 'EMPLOYEE' | 403
        39 | 'post' | 'rota/add' | 'carrick@test.com' | 'ADMIN' | 200
        40 | 'post' | 'rota/add' | 'fletcher@test.com' |'EMPLOYEE' | 403
        41 | 'get' | 'rota/?weekNum=19&year=2020&edit=Edit' | 'park@test.com' | 'ADMIN' | 200
        42 | 'get' | 'rota/?weekNum=19&year=2020&edit=Edit' | 'ferdinand@test.com' | 'EMPLOYEE' |403
        43 | 'post' | 'rota/edited' | 'vidic@test.com' | 'ADMIN' | 200
        44 | 'post' | 'rota/edited' | 'evra@test.com' | 'EMPLOYEE' | 403
        45 | 'get' | 'rota/?weekNum=19&year=2020&view=View' | 'nani@test.com' | 'ADMIN' | 200
        46 | 'get' | 'rota/?weekNum=19&year=2020&view=View' | 'faboi@test.com' | 'EMPLOYEE' | 200


//        29 | 'post' | 'register/newUser'

//        20 | 'get' | 'rota/create' | 'darmian@@ttest.com' | 'EMPLOYEE' | 403






    }
//    def "authentication-#testId"() {
//        when:
//        result = this.mockMvc.perform(formLogin().user("login",username).password("password",password))
//
//
//        then:
//        result.andExpect(unauthenticated())
//
//        where:
//        testId | username | password | role
//        3 | "admin" | "admin" | "PREMIUM"
//        4 | "invalid" | "invalid" | "PREMIUM"
//
//
//
//
//    }
//
//
//    def "authorization-#testId"() {
//        when: "I perform a get #request"
//        if(method=='get')
//            result = this.mockMvc.perform(get("/$requestUrl").secure( true ).with(user(username).roles(role)))
//        else
//            result = this.mockMvc.perform(post("/$requestUrl").secure( true ).with(user(username).roles(role)))
//
//        then:
//        result.andExpect(status().is(statusCode))
//                .andExpect(authenticated().withUsername(username).withRoles(role))
//
//
//        where:
//        testId | method | requestUrl | username | role | statusCode
//        5 | 'get' | 'upload' | 'admin' | "ADMIN" | 200
//        6 | 'get' | 'upload' | 'employee' | "EMPLOYEE" | 403
//        7 | 'get' | 'viewUsers' | 'admin' | "ADMIN" | 200
//        8 | 'get' | 'viewUsers' | 'admin' | "EMPLOYEE" | 200
//
//
//
//
//
//
//
//
//
//    }
//
//}



}


