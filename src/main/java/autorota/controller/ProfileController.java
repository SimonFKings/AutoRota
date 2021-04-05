package autorota.controller;


import autorota.domain.UserInfo;
import autorota.repository.UserRepository;
import autorota.services.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
@RequestMapping("/my-profile")
public class ProfileController {


    @Autowired
    UserRepository userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

    ControllerHelper controllerHelper = new ControllerHelper();


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String profilePage(Model model) {

        UserInfo userInfo = controllerHelper.getLoggedInUser(userRepo);

        model.addAttribute("user", userInfo);

        controllerHelper.modelProfilePage(model, userInfo);
        controllerHelper.modelCheckAdmin(model, userInfo);

        return "profilePage";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editProfile(Model model, @ModelAttribute("user") UserInfo user, BindingResult result) {
        UserInfo userInfo = controllerHelper.getLoggedInUser(userRepo);


        ProfileValidation profileValidation = new ProfileValidation();
        profileValidation.validate(user, result);

        if (result.hasErrors()) {

            controllerHelper.modelProfilePage(model, userInfo);
            controllerHelper.modelCheckAdmin(model, userInfo);

            return "profilePage";

        } else {

            userInfo.setPhoneNum(user.getPhoneNum());
            userInfo.setEmail(user.getEmail());
            userInfo.setPassword(pe.encode(user.getPassword()));
            userInfo.setConfirmPassword(pe.encode(user.getConfirmPassword()));
            userInfo.setPasswordChange(true);
            userRepo.save(userInfo);

            Collection<SimpleGrantedAuthority> nowAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getPassword(), nowAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            model.addAttribute("user", userInfo);
            model.addAttribute("success", true);
            controllerHelper.modelProfilePage(model, userInfo);
            controllerHelper.modelCheckAdmin(model, userInfo);
            return "profilePage";

        }
    }

}


