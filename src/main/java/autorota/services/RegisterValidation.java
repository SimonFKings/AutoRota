package autorota.services;

import autorota.domain.UserInfo;
import autorota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class RegisterValidation implements Validator {

    UserRepository userRepo;


    @Autowired
    public RegisterValidation(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public boolean supports(Class clazz) {
        return UserInfo.class.equals(clazz);
    }


    // https://howtodoinjava.com/regex/java-regex-validate-email-address/
    Pattern emailRegex = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    Pattern nameAndJobRegex = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
    Pattern numberRegex = Pattern.compile("^(?:0|\\+?44)(?:\\d\\s?){9,10}$");

    @Override
    public void validate(Object target, Errors errors) {

        UserInfo user = (UserInfo) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "forename", "forename.empty", "Forename should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "lastname.empty", "Last name should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobTitle", "jobTitle.empty", "Job Title should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "dob.empty", "Date of Birth should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNum", "phoneNum.empty", "Phone Number should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Email Address should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "salary.empty", "Salary should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userType", "userType.empty", "Please select a role for this user");


        String firstName = user.getForename();
        if (!firstName.equals("") && !nameAndJobRegex.matcher(firstName).matches()) {
            errors.rejectValue("forename", "forename.invalid", "Please enter a valid forename");

        }

        String lastname = user.getLastname();
        if (!lastname.equals("") && !nameAndJobRegex.matcher(lastname).matches()) {
            errors.rejectValue("lastname", "lastname.invalid", "Please enter a valid Surname");

        }

        String job = user.getJobTitle();
        if (!job.equals("") && !nameAndJobRegex.matcher(job).matches()) {
            errors.rejectValue("jobTitle", "jobTitle.invalid", "Please enter a valid Job Title");

        }

        double salaryDouble = 0.0;
        String salary = user.getSalary();
        if (!salary.equals("")) {
            try {
                salaryDouble = Double.valueOf(salary);
            } catch (NumberFormatException e) {
                errors.rejectValue("salary", "salary.format", "Please enter a valid salary");
            }
            if (salaryDouble < 0) {
                errors.rejectValue("salary", "salary.minus", "Salary should not be minus");

            }

        }

        String phoneNum = user.getPhoneNum();
        if (!phoneNum.equals("") && !numberRegex.matcher(phoneNum).matches()) {
            errors.rejectValue("phoneNum", "phoneNum.areaCode", "This phone number is not valid");

        }


        String email = user.getEmail();
        if (!email.equals("") && !emailRegex.matcher(email).matches()) {
            errors.rejectValue("email", "email.invalid", "This email address is not valid");
        }

        List<UserInfo> userList = (List<UserInfo>) userRepo.findAll();

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(email)) {
                errors.rejectValue("email", "email.exists", "This email address is already registered");

            }

        }


        if (user.getDob() != null) {
            LocalDate dob = user.getDob();

            LocalDate today = LocalDate.now();
            Period period = Period.between(dob, today);
            if (period.getYears() < 13) {
                errors.rejectValue("dob", "dob.Age", "Age should be at least 13 ");
            }

        }


    }
}
