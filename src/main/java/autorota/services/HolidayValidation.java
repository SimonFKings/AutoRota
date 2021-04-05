package autorota.services;


import autorota.domain.Holiday;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HolidayValidation implements Validator {

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public boolean supports(Class<?> clazz) {
        return Holiday.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Holiday holiday = (Holiday) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDateS", "startDateS.empty", "Start date of the holiday should no be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDateS", "endDateS.empty", "End date of the holiday should no be empty");


        if (!holiday.getStartDateS().equals("") && !holiday.getEndDateS().equals("")) {
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            try {
                startCal.setTime(dateFormat.parse(holiday.getStartDateS()));
                endCal.setTime(dateFormat.parse(holiday.getEndDateS()));

                if (endCal.before(startCal)) {
                    errors.rejectValue("startDateS", "startDateS.orderWrong", "The start date of the holiday needs to be before or on the same day as the end date");
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }


    }
}
