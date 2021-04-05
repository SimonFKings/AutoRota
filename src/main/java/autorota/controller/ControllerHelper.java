package autorota.controller;

import autorota.domain.*;
import autorota.repository.RoleRepository;
import autorota.repository.UserRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ControllerHelper {


    //Check whether there is a bank holiday for a date in a given country
    public String checkHoliday(Calendar date, String country) throws IOException {

        //Call calendarific API for a given country and calendar year
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://calendarific.com/api/v2/holidays?" +
                        "&api_key=d0c03d9b5f2491baa750884f94b145000fc3ef3a&country=" +
                        country +
                        "&year=" +
                        date.get(Calendar.YEAR))
                .get()
                .build();
        Response response = client.newCall(request).execute();
        JSONObject myObject = new JSONObject(response.body().string());
        try {
            JSONObject responseJSON = myObject.getJSONObject("response");
            JSONArray holidays = responseJSON.getJSONArray("holidays");
            JSONObject holiday;

            //For each holiday the API gives back:
            for (int i = 0; i < holidays.length(); i++) {
                holiday = holidays.getJSONObject(i);
                JSONObject dateJSON = holiday.getJSONObject("date");
                JSONObject datetime = dateJSON.getJSONObject("datetime");

                //Get the day, month and year of the holiday
                int day = datetime.getInt("day");
                int month = datetime.getInt("month");
                int year = datetime.getInt("year");

                //Check if it matches with the date we are checking
                if (date.get(Calendar.DAY_OF_MONTH) == day && date.get(Calendar.MONTH) + 1 == month && date.get(Calendar.YEAR) == year) {
                    String holidayName = holiday.getString("name");
                    return holidayName;
                }
            }

        } catch (JSONException e) {

        }
        return "noHoliday";


    }


    //Add the dates to each day of the week to appear on rota and send to JSP
    public void modelAddDays(List<Day> dayList, Model model) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (dayList.size() == 7) {
            model.addAttribute("mon", sdf.format(dayList.get(0).getDate()));
            model.addAttribute("tue", sdf.format(dayList.get(1).getDate()));
            model.addAttribute("wed", sdf.format(dayList.get(2).getDate()));
            model.addAttribute("thu", sdf.format(dayList.get(3).getDate()));
            model.addAttribute("fri", sdf.format(dayList.get(4).getDate()));
            model.addAttribute("sat", sdf.format(dayList.get(5).getDate()));
            model.addAttribute("sun", sdf.format(dayList.get(6).getDate()));
        }
    }


    //Send the rota, whether a rota exist or not and if the user is an admin to JSP
    public void modelReturnRota(Model model, boolean validRota, Rota rota, UserInfo user) {

        model.addAttribute("rota", rota);
        model.addAttribute("validRota", validRota);

        modelCheckAdmin(model, user);


    }

    //Set the actual date for each day object based on the week and year
    public List<Day> setDatesInDayList(int weekNum, int year, String country) throws IOException {

        List<Day> dayList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        //For each day of the week create a day object
        for (int i = 0; i < 7; i++) {
            Day day = new Day(cal.getTime());
            //Check if there is a bank holiday
            if (checkHoliday(cal, country).equals("noHoliday")) {
                day.setBankHoliday(false);
            } else {
                day.setBankHoliday(true);
                day.setBankHolidayTitle(checkHoliday(cal, country));
            }
            dayList.add(day);
            //Check next day
            cal.add(Calendar.DATE, 1);
        }

        return dayList;

    }

    public void getTimeOptions(List<String> shiftTimes) {
        //Get list for shift hours
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int startDate = calendar.get(Calendar.DATE);
        while (calendar.get(Calendar.DATE) == startDate) {
            //Create a list of different shift times in 30 minute intervals ( 19:30, 20:00, 20:30...)
            shiftTimes.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 30);

        }
    }

    //Get the user that is logged in
    public UserInfo getLoggedInUser(UserRepository userRepo) {
        UserInfo userInfo = userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userInfo;
    }


    public String modelAddDayAva(Model model, UserInfo loggedInUser, Availability loggedInAva) {
        model.addAttribute("monday", loggedInAva.getMon());
        model.addAttribute("tuesday", loggedInAva.getTue());
        model.addAttribute("wednesday", loggedInAva.getWed());
        model.addAttribute("thursday", loggedInAva.getThu());
        model.addAttribute("friday", loggedInAva.getFri());
        model.addAttribute("saturday", loggedInAva.getSat());
        model.addAttribute("sunday", loggedInAva.getSun());

        modelCheckAdmin(model, loggedInUser);

        return "availability";
    }


    public void modelCheckAdmin(Model model, UserInfo loggedInUser) {

        if (loggedInUser.getRole().getRole().equals("ADMIN")) {
            model.addAttribute("admin", true);

        } else {
            model.addAttribute("admin", false);
        }


    }

    public void modelProfilePage(Model model, UserInfo user) {
        model.addAttribute("firstname", user.getForename());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("jobTitle", user.getJobTitle());
        model.addAttribute("dob", user.getDob());
        model.addAttribute("salary", user.getSalary());
    }

    public List<String> getRoleList(RoleRepository roleRepo) {

        List<Role> list = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        roleRepo.findAll().forEach(list::add);

        for (Role r : list) {
            roleList.add(r.getRole());
        }


        return roleList;

    }
}
