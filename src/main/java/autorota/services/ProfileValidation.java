package autorota.services;

import autorota.domain.UserInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class ProfileValidation implements Validator {

    // https://howtodoinjava.com/regex/java-regex-validate-email-address/
    Pattern emailRegex = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    Pattern numberRegex = Pattern.compile("^(?:0|\\+?44)(?:\\d\\s?){9,10}$");

    public boolean supports(Class clazz) {
        return UserInfo.class.equals(clazz);
    }


    public void validate(Object target, Errors errors) {


        UserInfo user = (UserInfo) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Email Address should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Password should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "confirmPassword.empty", "Confirm Password should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNum", "phoneNum.empty", "Phone Number should not be empty");

        String email = user.getEmail();
        if (!email.equals("") && !emailRegex.matcher(email).matches()) {
            errors.rejectValue("email", "email.invalid", "This email address is not valid");
        }
        //Check email is already sign uped


        //Password
        String password = user.getPassword();
        if (!password.equals("") && password.length() < 6) {
            errors.rejectValue("password", "password.length", "Password needs to be at least 6 characters long");
        }

        String confirmedPassword = user.getConfirmPassword();
        if (!confirmedPassword.equals("") && !confirmedPassword.equals(password)) {
            errors.rejectValue("confirmPassword", "confirmPassword.matching", "Passwords are not matching");
        }

        String phoneNum = user.getPhoneNum();
        if (!phoneNum.equals("") && !numberRegex.matcher(phoneNum).matches()) {
            errors.rejectValue("phoneNum", "phoneNum.areaCode", "This phone number is not valid");


        }

    }

}
