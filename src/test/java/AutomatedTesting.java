import autorota.controller.ControllerHelper;
import autorota.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
public class AutomatedTesting {

    UserInfo user;
    Role role;
    Booking booking;
    Availability availability;
    Business business;

    ControllerHelper controllerHelper;


    @Before
    public void setup() {

        business = new Business();
        business.setName("Manchester United Cafe");
        business.setCity("Manchester");
        business.setCountry("GB");

        user = new UserInfo();

        role = new Role();
        role.setRole("Supervisor");
        user.setRole(role);


        availability = new Availability(user, 1, 1, 1, -1, 0, 0, 1);


        booking = new Booking();
        booking.setBusiness(business);
        booking.setStartTime("18:00");
        booking.setEndTime("23:00");
        booking.setCustomer("Lionel Messi");

        controllerHelper = new ControllerHelper();


    }

    @Test
    public void userSetForename() {
        user.setForename("Marcus");
        assertThat("", user, hasProperty("forename", equalTo("Marcus")));
    }

    @Test
    public void userSetRole() {

        user.setRole(role);

        assertThat(user, hasProperty("role", equalTo(role)));
        assertThat(role.getRole(), equalTo(user.getRole().getRole()));
    }

    @Test
    public void userSetAvailability() {

        user.setAvailability(availability);
        assertThat(availability.getFri(), equalTo(user.getAvailability().getFri()));
    }


    @Test
    public void noOfBookings() {

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        business.setBookings(bookings);
        assertThat(business.getBookings().size(), equalTo(1));

    }


    @Test
    public void checkBankHolidayTest() throws IOException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        assert(controllerHelper.checkHoliday(calendar, "GB").equals("New Year's Day"));


    }

    @Test
    public void checkBankHolidayTest2() throws IOException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 25);

        assertThat(controllerHelper.checkHoliday(calendar, "GB"),equalTo("First Day of Hanukkah"));


    }

    @Test
    public void checkBankHolidayTest3() throws IOException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2025);
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 18);

        assertThat(controllerHelper.checkHoliday(calendar, "GB"), equalTo("Orthodox Good Friday"));

    }


    @Test
    public void checkDateOnDays() throws IOException {

        List<Day> dayList = controllerHelper.setDatesInDayList(4, 2020, "GB");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);

        assertThat(dayList.get(2).getDate().getYear(), equalTo(calendar.getTime().getYear()));

    }


    @Test
    public void checkDateOnDays2() throws IOException {

        List<Day> dayList = controllerHelper.setDatesInDayList(9, 2019, "GB");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 2);

        assertThat(dayList.get(5).getDate().getMonth(), equalTo(calendar.getTime().getMonth()));

    }


    @Test
    public void checkDateOnDays3() throws IOException {

        List<Day> dayList = controllerHelper.setDatesInDayList(15, 2018, "GB");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 3, 9);

        assertThat(dayList.get(0).getDate().getDate(), equalTo(calendar.getTime().getDate()));

    }


}
