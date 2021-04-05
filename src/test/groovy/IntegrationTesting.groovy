import autorota.AutorotaApplication
import autorota.SecurityConfig
import autorota.WebConfig
import autorota.controller.AuthenticationController
import autorota.controller.AvailabilityController
import autorota.controller.RotaController
import autorota.domain.*
import autorota.repository.BusinessRepository
import autorota.repository.HolidayRepository
import autorota.repository.RoleRepository
import autorota.repository.RotaRepository
import autorota.repository.ShiftRepository
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [AutorotaApplication.class, SecurityConfig.class,
        AuthenticationController.class, WebConfig.class, CustomUserDetailsService.class, AvailabilityController.class, RotaController.class])
@AutoConfigureMockMvc(secure = true)
class IntegrationTesting extends Specification {

    @Autowired
    WebApplicationContext wac

    @Autowired
    MockMvc mockMvc
    ResultActions result

    @MockBean
    UserRepository userRepo

    @MockBean
    RotaRepository rotaRepo

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
    ShiftRepository shiftRepo


    @MockBean
    BookingValidation bookingValidation


    void setup() {
        MockitoAnnotations.initMocks(this)


    }


    @Test
    def "viewPageTesting"() {

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
        rota.setYear(2019)
        rota.setWeekNum(40)
        List<Day> dayList = new ArrayList<>()
        Day day = new Day()
        List<Shift> shiftList = new ArrayList<>()
        Shift shift = new Shift()
        shift.setUser(userInfo)
        shiftList.add(shift)
        day.setShiftList(shiftList)
        dayList.add(day)
        rota.setDaysList(dayList)


        List<Rota> rotaList = new ArrayList<>()
        rotaList.add(rota)
        Mockito.when(userRepo.findById(Mockito.anyInt())).thenReturn(userInfo)
        Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userInfo)
        Mockito.when(bussRepo.findById(Mockito.anyInt())).thenReturn(business)
        Mockito.when(holidayRepo.findById(Mockito.anyInt())).thenReturn(holiday)
        Mockito.when(roleRepo.findAll()).thenReturn(new ArrayList<Role>())
        Mockito.when(userRepo.findAll()).thenReturn(new ArrayList<UserInfo>())
        Mockito.when(rotaRepo.findAll()).thenReturn((List<Rota>) rotaList)
        Mockito.when(shiftRepo.findAll()).thenReturn(shiftList)

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build()


        when:
        if (method == 'get') {
            if (url == 'holiday/approved') {
                result = this.mockMvc.perform(get("/$url").param("holidayId", "10").secure(true).with(user(email).roles(role)))
            } else {

                result = this.mockMvc.perform(get("/$url").secure(true).with(user(email).roles(role)))
            }
        } else {

            if (url == 'booking/new') {
                result = this.mockMvc.perform(post("/$url").param("startTime", "10:10")
                        .param("endTime", "11:30").param("customer", "Simon").
                        secure(true).with(user(email).roles(role)))

            } else if (url == 'holiday/new') {
                result = this.mockMvc.perform(post("/$url").param("startDateS", "20/05/2020")
                        .param("endDateS", "25/10/2020").
                        secure(true).with(user(email).roles(role)))

            } else if (url == 'register/newUser') {
                result = this.mockMvc.perform(post("/$url").param("forename", "John")
                        .param("lastname", "Cena").param("jobTitle", "Waiter").
                        param("email", "cena@test.com").param("age", "23").
                        param("salary", "10.50").
                        secure(true).with(user(email).roles(role)))


            } else if (url == 'settings/save') {
                result = this.mockMvc.perform(post("/$url").param("city", "London")
                        .secure(true).with(user(email).roles(role)))

            } else if (url == 'rota/add') {
                result = this.mockMvc.perform(post("/$url").param("daysList[0].shiftList[0].shiftStart", "HOL")
                        .param("daysList[0].shiftList[0].shiftEnd", "HOL").param('daysList[0].shiftList[0].user.id', "1")
                        .param("daysList[0].date", "20/10/2020").secure(true).with(user(email).roles(role)))


            } else if (url == 'rota/edited') {
                result = this.mockMvc.perform(post("/$url").param("daysList[0].shiftList[0].shiftStart", "SICK")
                        .param("daysList[0].shiftList[0].shiftEnd", "SICK").param('daysList[0].shiftList[0].user.id', "1")
                        .param("daysList[0].date", "20/10/2020").secure(true).with(user(email).roles(role)))


            } else if (url == 'my-profile/edit') {
                result = this.mockMvc.perform(post("/$url").param("email", "gomes@test.com")
                        .param("password", "password").param("confirmPassword", "password")
                        .param("phoneNum", "07956044554")
                        .secure(true).with(user(email).roles(role)))


            } else {
                result = this.mockMvc.perform(post("/$url").secure(true).with(user(email).roles(role)))
            }

        }

        then:
        result.andExpect(status().isOk())
                .andExpect(view().name(viewName));

        where:
        id | method | url                                        | email               | role       | viewName
        1  | 'get'  | 'home'                                     | 'ronaldo@test.com'  | 'EMPLOYEE' | 'homePage'
        2  | 'get'  | 'availability'                             | 'messi@test.com'    | 'EMPLOYEE' | 'availability'
        3  | 'post' | 'availability/add'                         | 'neymar@test.com'   | 'EMPLOYEE' | 'availability'
        4  | 'get'  | 'booking/'                                 | 'siralex@test.com'  | 'ADMIN'    | 'bookings'
        5  | 'post' | 'booking/new'                              | 'young@test.com'    | 'ADMIN'    | 'bookings'
        6  | 'get'  | 'holiday/'                                 | 'keane@test.com'    | 'EMPLOYEE' | 'holidays'
        7  | 'post' | 'holiday/new'                              | 'lingard@test.com'  | 'EMPLOYEE' | 'holidays'
        8  | 'get'  | 'holiday/all'                              | 'james@test.com'    | 'ADMIN'    | 'allholidays'
        9  | 'get'  | 'holiday/approved'                         | 'fred@test.com'     | 'ADMIN'    | 'allholidays'
        10 | 'get'  | 'my-profile/'                              | 'smalling@test.com' | 'EMPLOYEE' | 'profilePage'
        27 | 'post' | 'my-profile/edit'                          | 'gomes@test.com'    | 'EMPLOYEE' | 'profilePage'
        11 | 'get'  | 'register/'                                | 'remero@test.com'   | 'ADMIN'    | 'registerPage'
        12 | 'post' | 'register/newUser'                         | 'ramous@test.com'   | 'ADMIN'    | 'registerPage'
        13 | 'get'  | 'settings/'                                | 'kante@test.com'    | 'ADMIN'    | 'settings'
        14 | 'post' | 'settings/save'                            | 'umtiti@test.com'   | 'ADMIN'    | 'settings'
        15 | 'get'  | 'rota/today'                               | 'grealish@test.com' | 'EMPLOYEE' | 'viewRota'
        16 | 'get'  | 'rota/?weekNum=10&year=2021&create=Create' | 'iwobi@test.com'    | 'ADMIN'    | 'createRota'
        17 | 'post' | 'rota/add'                                 | 'carrick@test.com'  | 'ADMIN'    | 'viewRota'
        18 | 'get'  | 'rota/?weekNum=40&year=2019&edit=Edit'     | 'park@test.com'     | 'ADMIN'    | 'editRota'
        19 | 'post' | 'rota/edited'                              | 'vidic@test.com'    | 'ADMIN'    | 'viewRota'
        20 | 'get'  | 'rota/?weekNum=40&year=2019&view=View'     | 'nani@test.com'     | 'EMPLOYEE' | 'viewRota'


    }


}