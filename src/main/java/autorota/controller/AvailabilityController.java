package autorota.controller;


import autorota.domain.Availability;
import autorota.domain.UserInfo;
import autorota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    UserRepository userRepo;

    ControllerHelper controllerHelper = new ControllerHelper();


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String availabilityPage(Model model) {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        Availability loggedInAva = loggedInUser.getAvailability();

        model.addAttribute("availability", new Availability());
        return controllerHelper.modelAddDayAva(model, loggedInUser, loggedInAva);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAvailability(Model model, @ModelAttribute("availability") Availability availability) {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        Availability loggedInAva = loggedInUser.getAvailability();

        loggedInAva.setMon(availability.getMon());
        loggedInAva.setTue(availability.getTue());
        loggedInAva.setWed(availability.getWed());
        loggedInAva.setThu(availability.getThu());
        loggedInAva.setFri(availability.getFri());
        loggedInAva.setSat(availability.getSat());
        loggedInAva.setSun(availability.getSun());

        userRepo.save(loggedInUser);

        model.addAttribute("error", false);
        return controllerHelper.modelAddDayAva(model, loggedInUser, loggedInAva);
    }


}
