package autorota.controller;

import autorota.domain.Holiday;
import autorota.domain.UserInfo;
import autorota.repository.HolidayRepository;
import autorota.repository.UserRepository;
import autorota.services.HolidayValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/holiday")
public class HolidayController {


    @Autowired
    UserRepository userRepo;

    @Autowired
    HolidayRepository holidayRepo;

    ControllerHelper controllerHelper = new ControllerHelper();


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String holidayPage(Model model) {
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        model.addAttribute("holidayList", loggedInUser.getHolidays());
        model.addAttribute("holiday", new Holiday());

        controllerHelper.modelCheckAdmin(model, loggedInUser);

        return "holidays";


    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newHoliday(Model model, @ModelAttribute("holiday") Holiday holiday, BindingResult results) {
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        HolidayValidation holidayValidation = new HolidayValidation();
        holidayValidation.validate(holiday, results);

        if (results.hasErrors()) {

        } else {

            holiday.setAccepted(false);
            holiday.setUser(loggedInUser);
            try {
                holiday.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(holiday.getStartDateS()));
                holiday.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(holiday.getEndDateS()));

            } catch (ParseException e) {
                e.printStackTrace();
            }


            holidayRepo.save(holiday);


            model.addAttribute("holiday", new Holiday());
        }

        model.addAttribute("holidayList", loggedInUser.getHolidays());

        controllerHelper.modelCheckAdmin(model, loggedInUser);

        return "holidays";
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allHolidays(Model model) {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);


        List<Holiday> allHolidays = (List<Holiday>) holidayRepo.findAll();
        model.addAttribute("holidayList", allHolidays);
        controllerHelper.modelCheckAdmin(model, loggedInUser);

        return "allholidays";
    }


    @RequestMapping(value = "/approved", method = RequestMethod.GET)
    public String approveHoliday(Model model, int holidayId) {
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);


        Holiday holiday = holidayRepo.findById(holidayId);
        holiday.setAccepted(true);
        holidayRepo.save(holiday);
        List<Holiday> allHolidays = (List<Holiday>) holidayRepo.findAll();

        model.addAttribute("holidayList", allHolidays);
        model.addAttribute("holiday", new Holiday());

        controllerHelper.modelCheckAdmin(model, loggedInUser);

        return "allholidays";

    }
}
