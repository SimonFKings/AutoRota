package autorota.services;

import autorota.domain.Business;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.io.IOException;

public class SettingValidation implements Validator {


    @Override
    public boolean supports(Class clazz) {
        return Business.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {


        Business business = (Business) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "The name of the business should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "city.empty", "The city the business is located in should not be empty");


        String city = business.getCity();
        String country = business.getCountry();

        if (!city.equals("")) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.weatherbit.io/v2.0/forecast/daily?city=" + city +
                            "&country=" + country + "&key=14feff36ef124987b24f35724dbb6f94")
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.code() == 204) {

                    errors.rejectValue("city", "city.invalid", "Please enter a valid city and country code.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
