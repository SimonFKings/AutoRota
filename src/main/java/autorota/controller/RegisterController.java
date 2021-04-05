package autorota.controller;

import autorota.domain.Availability;
import autorota.domain.Business;
import autorota.domain.UserInfo;
import autorota.repository.BusinessRepository;
import autorota.repository.RoleRepository;
import autorota.repository.UserRepository;
import autorota.services.RegisterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;

    @Autowired
    BusinessRepository bussRepo;

    ControllerHelper controllerHelper = new ControllerHelper();


    BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registerForm(Model model) {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);


        List<String> roleList = controllerHelper.getRoleList(roleRepo);

        model.addAttribute("user", new UserInfo());
        model.addAttribute("roleList", roleList);


        controllerHelper.modelCheckAdmin(model, loggedInUser);


        return "registerPage";
    }


    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") UserInfo user, Model model, BindingResult result) throws IOException {

        RegisterValidation registerValidation = new RegisterValidation(userRepo);
        registerValidation.validate(user, result);
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo loggedInUser = userRepo.findByEmail(authUser.getUsername());

        List<String> roleList = controllerHelper.getRoleList(roleRepo);


        if (result.hasErrors()) {

            model.addAttribute("error", true);

        } else {


            Business business = loggedInUser.getBusiness();

            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";
            char[] characters = AlphaNumericString.toCharArray();

            StringBuilder sb = new StringBuilder();

            int length = AlphaNumericString.length();
            for (int i = 0; i < 5; i++) {

                Random r = new Random();
                int randomNum = r.nextInt(((length - 1) - 0) + 1) + 0;
                sb.append(characters[randomNum]);
            }
            String generatedPassword = sb.toString();

            model.addAttribute("password", generatedPassword);

            user.setRole(roleRepo.findByRole(user.getUserType()));
            user.setPassword(pe.encode(generatedPassword));
            user.setConfirmPassword(pe.encode(generatedPassword));
            user.setAvailability(new Availability());
            user.setBusiness(business);
            userRepo.save(user);

            File file = new File("./data/trainingSet.arff");
            if (file.exists()) {
                ArffLoader loader = new ArffLoader();
                loader.setSource(file);
                Instances dataSet = loader.getDataSet();

                dataSet.setClassIndex(dataSet.numAttributes() - 1);

                FastVector attributes;
                FastVector userIDValues;
                FastVector dateOfMonthValues;
                FastVector dayOfWeekValues;
                FastVector bankHolidayValues;
                FastVector shiftHoursValues;

                Instances data;

                // 1. set up attributes
                attributes = new FastVector();

                userIDValues = new FastVector();
                List<UserInfo> userList = (List<UserInfo>) userRepo.findAll();
                for (int j = 0; j < userList.size(); j++) {
                    userIDValues.addElement(String.valueOf(userList.get(j).getId()));
                }

                attributes.addElement(new Attribute("user", userIDValues));
                // - numeric
                dateOfMonthValues = new FastVector();
                for (int i = 0; i < 31; i++) {
                    dateOfMonthValues.addElement(String.valueOf(i + 1));

                }

                attributes.addElement(new Attribute("date", dateOfMonthValues));
                // - nominal


                dayOfWeekValues = new FastVector();
                dayOfWeekValues.addElement("mon");
                dayOfWeekValues.addElement("tue");
                dayOfWeekValues.addElement("wed");
                dayOfWeekValues.addElement("thu");
                dayOfWeekValues.addElement("fri");
                dayOfWeekValues.addElement("sat");
                dayOfWeekValues.addElement("sun");


                attributes.addElement(new Attribute("day", dayOfWeekValues));

                attributes.addElement(new Attribute("temp"));

                attributes.addElement(new Attribute("pop"));

                List<String> shiftTypesList = business.getShiftTypes();


                bankHolidayValues = new FastVector();

                bankHolidayValues.addElement("true");
                bankHolidayValues.addElement("false");

                attributes.addElement(new Attribute("bankHoliday", bankHolidayValues));


                attributes.addElement(new Attribute("bookings"));


                shiftHoursValues = new FastVector();
                for (int i = 0; i < shiftTypesList.size(); i++) {
                    shiftHoursValues.addElement(shiftTypesList.get(i));
                }

                attributes.addElement(new Attribute("shiftTimes", shiftHoursValues));

                data = new Instances("Predict the shift each user will do", attributes, 0);

                data.addAll(dataSet);

                ArffSaver saver = new ArffSaver();
                saver.setInstances(data);
                saver.setFile(file);
                saver.writeBatch();




            }
            model.addAttribute("error", false);
            model.addAttribute("firstname", user.getForename());
            model.addAttribute("password", generatedPassword);
        }
        model.addAttribute("roleList", roleList);

        controllerHelper.modelCheckAdmin(model, loggedInUser);


        return "registerPage";
    }


}
