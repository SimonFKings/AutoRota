package autorota.controller;

import autorota.domain.Business;
import autorota.domain.UserInfo;
import autorota.repository.BusinessRepository;
import autorota.repository.UserRepository;
import autorota.services.SettingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/settings")
public class SettingsController {


    @Autowired
    UserRepository userRepo;

    @Autowired
    BusinessRepository bussRepo;

    ControllerHelper controllerHelper = new ControllerHelper();


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String settingsPage(Model model) {
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        Business business = loggedInUser.getBusiness();

        model.addAttribute("business", business);
        controllerHelper.modelCheckAdmin(model, loggedInUser);


        return "settings";

    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveSettings(Model model, @ModelAttribute("business") Business business, BindingResult results) {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        SettingValidation settingValidation = new SettingValidation();
        settingValidation.validate(business, results);

        if (results.hasErrors()) {

        } else {

            Business b = loggedInUser.getBusiness();
            b.setName(business.getName());
            b.setCity(business.getCity());
            b.setCountry(business.getCountry());

            bussRepo.save(b);

            model.addAttribute("success", true);
        }

        controllerHelper.modelCheckAdmin(model, loggedInUser);


        return "settings";
    }

}
