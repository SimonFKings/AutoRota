package autorota.controller;

import autorota.domain.UserInfo;
import autorota.repository.RoleRepository;
import autorota.repository.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private String userName;
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;

    ControllerHelper controllerHelper = new ControllerHelper();


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() {

        return "loginPage";
    }


    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        this.userName = (String) event.getAuthentication().getPrincipal();
    }


    @RequestMapping(value = "/error-login", method = RequestMethod.GET)
    public String invalidLogin(Model model) {


        model.addAttribute("error", true);
        return "loginPage";


    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String successLogin(Model model) {

        UserInfo user = controllerHelper.getLoggedInUser(userRepo);


        model.addAttribute("user", user.getForename());
        model.addAttribute("business", user.getBusiness().getName());


        controllerHelper.modelCheckAdmin(model, user);

        return "homePage";


    }


    @RequestMapping(value = "/user-logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        model.addAttribute("logout", true);
        return "loginPage";
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String error() {

        return "accessDenied";
    }

}







