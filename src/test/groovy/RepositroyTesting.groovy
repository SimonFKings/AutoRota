import autorota.AutorotaApplication
import autorota.domain.*
import autorota.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs
import static org.junit.Assert.assertThat

@DataJpaTest
@SpringBootTest(classes = AutorotaApplication.class)
class RepositoryTest extends Specification {


    @Autowired
    BookingRepository bookingRepo

    @Autowired
    BusinessRepository bussRepo

    @Autowired
    DayRepository dayRepo

    @Autowired
    HolidayRepository holidayRepo

    @Autowired
    RoleRepository roleRepo

    @Autowired
    RotaRepository rotaRepo

    @Autowired
    ShiftRepository shiftRepo

    @Autowired
    UserRepository userRepo


     void setup() {


    }


    def " Adding a booking to the database-#testId"() {

        given:
        Booking booking = new Booking();
        booking.setId(id)
        booking.setStartTime("$startTime")
        booking.setEndTime("$endTime")
        booking.setCustomer("$customer")
        booking.setDateString("$date")
        booking.setDescription("$description")

        bookingRepo.save(booking)

        when:
        Booking bookingDB = bookingRepo.findById(id)

        then:
        assertThat(bookingDB, samePropertyValuesAs(booking))

        where:
        id | startTime | endTime | customer        | date         | description
        1  | "10:00"   | "18:00" | "Wayne Rooney"  | "25/06/2019" | "Rooney's wedding"
        2  | "17:00"   | "23:00" | "Ashley Young"  | "21/03/2020" | "Young's wife birthday"
        3  | "15:00"   | "21:00" | "Harry Maguire" | "10/01/2019" | "Harry's Wedding Anniversary"
    }

    def " Adding a business to the database-#testId"() {

        given:

        Business business = new Business()
        business.setId(id)
        business.setName("$name")
        business.setCity("$city")
        business.setCountry("$country")
        business.setShiftTypes(null)

        bussRepo.save(business)

        when:
        Business businessDB = bussRepo.findById(id)

        then:
        assertThat(businessDB, samePropertyValuesAs(business))

        where:
        id | name                         | city         | country
        1  | "Bob's Burger"               | "London"     | "GB"
        2  | "Charlies Chocolate Factory" | "Leicester"  | "GB"
        3  | "Simon's Cafe"               | "Manchester" | "GB"

    }

    def " Adding a day to the database-#testId"() {

        given:
        Day day = new Day();
        day.setId(id)
        day.setNoBookings(noBooking)
        day.setDate(new Date())
        day.setShiftList(null)
        dayRepo.save(day)

        when:
        Day dayDB = dayRepo.findById(id)

        then:
        assertThat(dayDB, samePropertyValuesAs(day))

        where:
        id | noBooking
        1  | 10
        2  | 3
        3  | 0


    }

    def " Adding a holiday to the database-#testId"() {

        given:
        Holiday holiday = new Holiday()
        holiday.setId(id)
        holiday.setStartDateS("$startDate")
        holiday.setEndDateS("$endDate")
        holiday.setAccepted(accepted)
        holidayRepo.save(holiday)

        when:
        Holiday holidayDB = holidayRepo.findById(id)

        then:
        assertThat(holidayDB, samePropertyValuesAs(holiday))

        where:
        id | startDate    | endDate      | accepted
        1  | "25/12/2020" | "31/12/2020" | true
        2  | "04/03/2018" | "16/03/2018" | true
        3  | "05/07/2019" | "17/07/2019" | false
    }

    def " Adding a role to the database-#testId"() {
        given:
        Role role = new Role();
        role.setId(id)
        role.setRole("$roleTitle")
        role.setUserRoles(null)
        roleRepo.save(role)

        when:
        Role roleDB = roleRepo.findById(id)

        then:
        assertThat(roleDB, samePropertyValuesAs(role))

        where:
        id | roleTitle
        1  | "ADMIN"
        2  | "USER"
        3  | "EMPLOYEE"

    }

    def " Adding a rota to the database-#testId"() {
        given:
        Rota rota = new Rota()
        rota.setId(id)
        rota.setWeekNum(weekNum)
        rota.setYear(year)
        rota.setDaysList(null)

        rotaRepo.save(rota)

        when:
        Rota rotaDB = rotaRepo.findById(id)

        then:
        assertThat(rotaDB, samePropertyValuesAs(rota))
        where:
        id | weekNum | year
        1  | 25      | 2018
        2  | 12      | 2019
        3  | 02      | 2001

    }

    def " Adding a shift to the database-#testId"() {
        given:

        Shift shift = new Shift()
        shift.setId(id)
        shift.setShiftStart("$startTime")
        shift.setShiftEnd("$endTime")
        shift.setOnHoliday(onHoliday)

        shiftRepo.save(shift)

        when:
        Shift shiftDB = shiftRepo.findById(id)

        then:
        assertThat(shiftDB, samePropertyValuesAs(shift))

        where:

        id | startTime | endTime | onHoliday
        1  | "10:00"   | "18:00" | false
        2  | "15:00"   | "22:00" | false
        3  | "HOL"     | "HOL"   | true

    }

    def " Adding a user to the database-#testId"() {

        given:
        UserInfo user = new UserInfo()
        user.setId(id)
        user.setForename("$forename")
        user.setLastname("$lastname")
        user.setEmail("$email")
        user.setPhoneNum("$phoneNum")
        user.setPassword("$password")
        user.setShiftList(null)
        user.setHolidays(null)
        userRepo.save(user)

        when:
        UserInfo userDB = userRepo.findById(id)

        then:
        assertThat(userDB, samePropertyValuesAs(user))

        where:
        id | forename  | lastname  | email            | phoneNum        | password
        1  | "James"   | "Madison" | "james@test.com" | "07956032341"   | "myPassword"
        2  | "Ji-Sung" | "Park"    | "park@test.com"  | "+447956323564" | "password"
        3  | "Wes"     | "Brown"   | "brown@test.com" | "02086763210"   | "123456"


    }




}



