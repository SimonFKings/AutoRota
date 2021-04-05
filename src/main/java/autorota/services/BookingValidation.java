package autorota.services;

import autorota.domain.Booking;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class BookingValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Booking.class.equals(clazz);
    }

    Pattern nameAndJobRegex = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");

    @Override
    public void validate(Object target, Errors errors) {

        Booking booking = (Booking) target;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer", "customer.empty", "Customer name should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startTime", "startTime.empty", "Start time of booking should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endTime", "endTime.empty", "End time of booking should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty", "Description of the of booking should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateString", "dateString.empty", "Date of the of booking should not be empty");

        if (!booking.getStartTime().equals("") && !booking.getEndTime().equals("")) {
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();


            try {
                startCal.setTime(dateFormat.parse(booking.getStartTime()));
                endCal.setTime(dateFormat.parse(booking.getEndTime()));


                if (!startCal.before(endCal)) {
                    errors.rejectValue("startTime", "startTime.orderWrong", "The start time of the booking needs to be before the end time");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (!booking.getCustomer().equals("") && !nameAndJobRegex.matcher(booking.getCustomer()).matches()) {
            errors.rejectValue("customer", "customer.invalid", "Please enter a valid customer name");

        }

    }

}
