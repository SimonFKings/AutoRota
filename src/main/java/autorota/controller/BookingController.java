package autorota.controller;


import autorota.domain.Booking;
import autorota.domain.Business;
import autorota.domain.Day;
import autorota.domain.UserInfo;
import autorota.repository.BookingRepository;
import autorota.repository.BusinessRepository;
import autorota.repository.DayRepository;
import autorota.repository.UserRepository;
import autorota.services.BookingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    BusinessRepository businessRepo;

    @Autowired
    BookingRepository bookingRepo;

    @Autowired
    DayRepository dayRepo;

    ControllerHelper controllerHelper = new ControllerHelper();


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String bookingPage(Model model) {


        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        Business business = businessRepo.findById(loggedInUser.getBusiness().getId());
        List<Booking> bookings = business.getBookings();

        List<String> bookingTimes = new ArrayList<>();

        //Get list for shift hours
        controllerHelper.getTimeOptions(bookingTimes);


        model.addAttribute("bookingList", bookings);
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookingTimes", bookingTimes);

        controllerHelper.modelCheckAdmin(model, loggedInUser);


        return "bookings";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newBooking(Model model, @ModelAttribute("booking") Booking booking, BindingResult results) throws ParseException {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        BookingValidation bookingValidation = new BookingValidation();
        bookingValidation.validate(booking, results);

        Business business = businessRepo.findById(loggedInUser.getBusiness().getId());
        List<Booking> bookings = business.getBookings();

        List<String> bookingTimes = new ArrayList<>();

        controllerHelper.getTimeOptions(bookingTimes);

        if (results.hasErrors()) {

        } else {
            booking.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(booking.getDateString()));
            booking.setBusiness(business);

            bookingRepo.save(booking);

            List<Day> dayList = (List<Day>) dayRepo.findAll();
            for (int i = 0; i < dayList.size(); i++) {

                //Check if an existing rota has a day that matches the new booking
                if (dayList.get(i).getDate().getDate() == booking.getDate().getDate() &&
                        dayList.get(i).getDate().getMonth() == booking.getDate().getMonth() &&
                        dayList.get(i).getDate().getYear() == booking.getDate().getYear()) {
                    dayList.get(i).setNoBookings(dayList.get(i).getNoBookings() + 1);
                }

            }
            dayRepo.save(dayList);
            model.addAttribute("booking", new Booking());
            model.addAttribute("success", true);

        }

        model.addAttribute("bookingList", bookings);
        model.addAttribute("bookingTimes", bookingTimes);

        if (loggedInUser.getRole().getRole().equals("ADMIN")) {
            model.addAttribute("admin", true);

        } else {
            model.addAttribute("admin", false);
        }

        return "bookings";

    }


}
