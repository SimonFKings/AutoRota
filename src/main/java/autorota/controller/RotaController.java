package autorota.controller;

import autorota.domain.*;
import autorota.repository.*;
import autorota.services.RotaValidation;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("rota")
@SessionAttributes("rota")
public class RotaController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RotaRepository rotaRepo;

    @Autowired
    ShiftRepository shiftRepo;

    @Autowired
    DayRepository dayRepo;

    @Autowired
    BusinessRepository bussRepo;


    ControllerHelper controllerHelper = new ControllerHelper();

    @ModelAttribute("rota")
    public Rota populateForm() {
        return new Rota();
    }


    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public String today(Model model) throws IOException {
        //Get the user that is logged into the system
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        //Get today's date week number and year
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.getWeekYear();

        //List of all rotas
        List<Rota> rotaList = (List<Rota>) rotaRepo.findAll();

        //If no rota has been created before
        if (rotaList.isEmpty()) {

            List<Day> dayList = controllerHelper.setDatesInDayList(week, year, loggedInUser.getBusiness().getCountry());
            controllerHelper.modelAddDays(dayList, model);
            controllerHelper.modelReturnRota(model, false, new Rota(week, year), loggedInUser);
            return "viewRota";

        }

        //Check if a rota exist from the requested week and year
        for (int i = 0; i < rotaList.size(); i++) {
            if (rotaList.get(i).getWeekNum() == week && rotaList.get(i).getYear() == year) {

                controllerHelper.modelAddDays(rotaList.get(i).getDaysList(), model);
                controllerHelper.modelReturnRota(model, true, rotaList.get(i), loggedInUser);

                return "viewRota";
            }
        }


        //Display a blank empty rota
        controllerHelper.modelReturnRota(model, false, new Rota(week, year), loggedInUser);
        return "viewRota";

    }


    @RequestMapping(value = "/", params = "view", method = RequestMethod.GET)
    public String viewRotaPage(Model model, Rota rota) throws IOException {

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);
        List<Rota> rotaList = (List<Rota>) rotaRepo.findAll();
        String country = loggedInUser.getBusiness().getCountry();

        //Get the date of each day
        List<Day> dayList = controllerHelper.setDatesInDayList(rota.getWeekNum(), rota.getYear(), country);


        if (rotaList.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "There is no rota for ");

            controllerHelper.modelAddDays(dayList, model);
            controllerHelper.modelReturnRota(model, false, new Rota(rota.getWeekNum(), rota.getYear()), loggedInUser);

            return "viewRota";

        }


        for (int i = 0; i < rotaList.size(); i++) {
            if (rotaList.get(i).getWeekNum() == rota.getWeekNum() && rotaList.get(i).getYear() == rota.getYear()) {

                controllerHelper.modelAddDays(dayList, model);
                controllerHelper.modelReturnRota(model, true, rotaList.get(i), loggedInUser);

                return "viewRota";
            }
        }


        //No rota exist for the given week and year
        model.addAttribute("error", true);
        model.addAttribute("message", "There is no rota for ");
        controllerHelper.modelAddDays(dayList, model);
        controllerHelper.modelReturnRota(model, false, new Rota(rota.getWeekNum(), rota.getYear()), loggedInUser);

        return "viewRota";


    }


    @RequestMapping(value = "/", params = "create", method = RequestMethod.GET)
    public String createRotaPage(Model model, @ModelAttribute("rota") Rota rota) throws Exception {

        List<Rota> rotaList = (List<Rota>) rotaRepo.findAll();
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        //List of all users
        List<UserInfo> list = (List<UserInfo>) userRepo.findAll();

        //Check if a rota already exist with the requested week and year
        for (int i = 0; i < rotaList.size(); i++) {
            if (rotaList.get(i).getWeekNum() == rota.getWeekNum() && rotaList.get(i).getYear() == rota.getYear()) {
                model.addAttribute("error", true);
                model.addAttribute("message", "There is already a rota for ");
                controllerHelper.modelAddDays(rotaList.get(i).getDaysList(), model);
                controllerHelper.modelReturnRota(model, true, rotaList.get(i), loggedInUser);

                return "viewRota";
            }
        }

        String country = loggedInUser.getBusiness().getCountry();
        String city = loggedInUser.getBusiness().getCity();

        List<String> shiftTimes = new ArrayList<>();
        shiftTimes.add("OFF");
        controllerHelper.getTimeOptions(shiftTimes);


        List<Day> dayList = controllerHelper.setDatesInDayList(rota.getWeekNum(), rota.getYear(), country);

        Rota newRota = new Rota(rota.getWeekNum(), rota.getYear());

        //Call WeatherBit API to get weather details for each date based on the country and city
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.weatherbit.io/v2.0/forecast/daily?city=" + city
                        + "&country=" + country + "&key=14feff36ef124987b24f35724dbb6f94")
                .get().build();
        Response response = client.newCall(request).execute();
        JSONObject myObject = new JSONObject(response.body().string());

        //List of all bookings the business has
        List<Booking> bookings = loggedInUser.getBusiness().getBookings();
        for (int j = 0; j < dayList.size(); j++) {

            try {

                JSONArray jsonArray = myObject.getJSONArray("data");
                //For each date and weather information(16 days including today's day)
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    JSONObject weather = (JSONObject) jsonObject.get("weather");

                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) jsonObject.get("valid_date"));

                    Date rotaDate = dayList.get(j).getDate();
                    //Check if the API given date matches one of the dates on the rota
                    if (rotaDate.getDate() == date.getDate() && rotaDate.getMonth() == date.getMonth() && rotaDate.getYear() == date.getYear()) {
                        //Set the probability of precipitation, highest temperature and weather description for each day
                        dayList.get(j).setPop((Integer) jsonObject.get("pop"));
                        dayList.get(j).setTemp((new Double(jsonObject.get("high_temp").toString())));
                        dayList.get(j).setWeatherDes((String) weather.get("description"));
                        dayList.get(j).setApiCalled(true);

                    }
                    //Check there are any bookings for the days in this weeks rota
                }
            } catch (JSONException e) {
                dayList.get(j).setApiCalled(false);


            }
            int noBookings = 0;
            for (int k = 0; k < bookings.size(); k++) {
                if (bookings.get(k).getDate().getDate() == dayList.get(j).getDate().getDate() &&
                        bookings.get(k).getDate().getMonth() == dayList.get(j).getDate().getMonth() &&
                        bookings.get(k).getDate().getYear() == dayList.get(j).getDate().getYear()) {
                    noBookings++;
                }
            }
            dayList.get(j).setNoBookings(noBookings);

        }

        //Add the modified days to the new rota
        for (int i = 0; i < 7; i++) {
            newRota.getDaysList().add(dayList.get(i));

        }

// Change to a for loop

        //A list of shifts for each day of the week
        List<Shift> monShifts = new ArrayList<>();
        List<Shift> tueShifts = new ArrayList<>();
        List<Shift> wedShifts = new ArrayList<>();
        List<Shift> thuShifts = new ArrayList<>();
        List<Shift> friShifts = new ArrayList<>();
        List<Shift> satShifts = new ArrayList<>();
        List<Shift> sunShifts = new ArrayList<>();


        //For every user on the rota
        for (int i = 0; i < list.size(); i++) {

            //Create a new shift for each day of the week
            monShifts.add(new Shift(list.get(i)));
            tueShifts.add(new Shift(list.get(i)));
            wedShifts.add(new Shift(list.get(i)));
            thuShifts.add(new Shift(list.get(i)));
            friShifts.add(new Shift(list.get(i)));
            satShifts.add(new Shift(list.get(i)));
            sunShifts.add(new Shift(list.get(i)));

            //For every holiday
            for (int j = 0; j < list.get(i).getHolidays().size(); j++) {
                Holiday hol = list.get(i).getHolidays().get(j);

                for (int k = 0; k < 7; k++) {
                    //Check if the holiday overlaps with any days in the rota
                    if ((hol.isAccepted()) && (hol.getStartDate().before(dayList.get(k).getDate())
                            && (hol.getEndDate().after(dayList.get(k).getDate()) || (hol.getEndDate().getDate() == dayList.get(k).getDate().getDate()
                            && hol.getEndDate().getYear() == dayList.get(k).getDate().getYear() && hol.getEndDate().getMonth() == dayList.get(k).getDate().getMonth())))) {

                        //Specify the shift for that day to be HOL
                        Shift shift = new Shift();
                        shift.setShiftStart("HOL");
                        shift.setShiftEnd("HOL");
                        shift.setUser(list.get(i));
                        shift.setOnHoliday(true);

                        // Check which day the shift is on and add to that days list
                        if (k == 0)
                            monShifts.set(i, shift);
                        else if (k == 1)
                            tueShifts.set(i, shift);
                        else if (k == 2)
                            wedShifts.set(i, shift);
                        else if (k == 3)
                            thuShifts.set(i, shift);
                        else if (k == 4)
                            friShifts.set(i, shift);
                        else if (k == 5)
                            satShifts.set(i, shift);
                        else if (k == 6)
                            sunShifts.set(i, shift);
                    }

                }


            }


        }

        //Store the shifts in the days for the rota
        newRota.getDaysList().get(0).setShiftList(monShifts);
        newRota.getDaysList().get(1).setShiftList(tueShifts);
        newRota.getDaysList().get(2).setShiftList(wedShifts);
        newRota.getDaysList().get(3).setShiftList(thuShifts);
        newRota.getDaysList().get(4).setShiftList(friShifts);
        newRota.getDaysList().get(5).setShiftList(satShifts);
        newRota.getDaysList().get(6).setShiftList(sunShifts);


        //Make predictions on shifts

        //https://waikato.github.io/weka-wiki/formats_and_processing/creating_arff_file/
        File file = new File("./data/trainingSet.arff");
        if (file.exists()) {

            //Get the training set
            ArffLoader loader = new ArffLoader();
            loader.setSource(file);
            Instances trainDataset = loader.getDataSet();

            //Set class index of the training set to the last attribute
            trainDataset.setClassIndex(trainDataset.numAttributes() - 1);

            //Use Naive Bayes to build a classifier model
            NaiveBayes nb = new NaiveBayes();
            nb.buildClassifier(trainDataset);


            FastVector attributes;
            FastVector userIDValues;
            FastVector dateOfMonthValues;
            FastVector dayOfWeekValues;
            FastVector bankHolidayValues;
            FastVector shiftHoursValues;
            Instances data;
            double[] values;

            //Set up attributes for unknown dataset
            attributes = new FastVector();

            userIDValues = new FastVector();


            //Set the userId of all users as an attribute
            List<UserInfo> userList = (List<UserInfo>) userRepo.findAll();
            for (int j = 0; j < userList.size(); j++) {
                userIDValues.addElement(String.valueOf(userList.get(j).getId()));
            }
            attributes.addElement(new Attribute("user", userIDValues));

            //Set the date of the month as an attribute
            dateOfMonthValues = new FastVector();
            for (int i = 0; i < 31; i++) {
                dateOfMonthValues.addElement(String.valueOf(i + 1));

            }
            attributes.addElement(new Attribute("date", dateOfMonthValues));

            //Set day of the week as an attribute
            dayOfWeekValues = new FastVector();

            dayOfWeekValues.addElement("mon");
            dayOfWeekValues.addElement("tue");
            dayOfWeekValues.addElement("wed");
            dayOfWeekValues.addElement("thu");
            dayOfWeekValues.addElement("fri");
            dayOfWeekValues.addElement("sat");
            dayOfWeekValues.addElement("sun");

            attributes.addElement(new Attribute("day", dayOfWeekValues));

            //Set the temperature as a numerical attribute
            attributes.addElement(new Attribute("temp"));

            //Set the percentage of perception as a numerical attribute
            attributes.addElement(new Attribute("pop"));


            //Set if it as a bank holiday as an attribute
            bankHolidayValues = new FastVector();

            bankHolidayValues.addElement("true");
            bankHolidayValues.addElement("false");
            attributes.addElement(new Attribute("bankHoliday", bankHolidayValues));


            //Set the number of bookings as an attribute
            attributes.addElement(new Attribute("bookings"));


            Business business = bussRepo.findById(loggedInUser.getBusiness().getId());
            List<String> shiftTypesList = business.getShiftTypes();

            //Set the different types of shifts the model will try to predict
            shiftHoursValues = new FastVector();
            for (int i = 0; i < shiftTypesList.size(); i++) {
                shiftHoursValues.addElement(shiftTypesList.get(i));
            }
            attributes.addElement(new Attribute("shiftTimes", shiftHoursValues));


            //Store the attributes in the data object
            data = new Instances("Predict the shift each user will do", attributes, 0);


            //Fill in the known  data

            for (int j = 0; j < dayList.size(); j++) {
                for (int i = 0; i < dayList.get(j).getShiftList().size(); i++) {
                    //For each day and each shift predict the shift times

                    values = new double[data.numAttributes()];

                    // User ID
                    values[0] = userIDValues.indexOf(String.valueOf(dayList.get(j).getShiftList().get(i).getUser().getId()));

                    // Date of the month
                    values[1] = dateOfMonthValues.indexOf(String.valueOf(dayList.get(j).getDate().getDate()));

                    // Day of the week
                    if (dayList.get(j).getDate().getDay() == 1) {
                        values[2] = dayOfWeekValues.indexOf("mon");

                    } else if (dayList.get(j).getDate().getDay() == 2) {
                        values[2] = dayOfWeekValues.indexOf("tue");


                    } else if (dayList.get(j).getDate().getDay() == 3) {
                        values[2] = dayOfWeekValues.indexOf("wed");
                    } else if (dayList.get(j).getDate().getDay() == 4) {
                        values[2] = dayOfWeekValues.indexOf("thu");


                    } else if (dayList.get(j).getDate().getDay() == 5) {

                        values[2] = dayOfWeekValues.indexOf("fri");

                    } else if (dayList.get(j).getDate().getDay() == 6) {
                        values[2] = dayOfWeekValues.indexOf("sat");


                    } else {
                        values[2] = dayOfWeekValues.indexOf("sun");


                    }

                    if (dayList.get(j).isApiCalled()) {

                        values[3] = dayList.get(j).getTemp();
                        values[4] = dayList.get(j).getPop();
                    }

                    values[5] = bankHolidayValues.indexOf(String.valueOf(dayList.get(j).isBankHoliday()));

                    values[6] = dayList.get(j).getNoBookings();


                    values[7] = 0;

                    Instance instance = new DenseInstance(1.0, values);
                    data.setClassIndex(data.numAttributes() - 1);

                    instance.setDataset(data);

                    // Set the shift times as unknown
                    instance.setClassMissing();

                    //Mark weather details as unknown if the API has not been called
                    if (!dayList.get(j).isApiCalled()) {
                        instance.setMissing(3);
                        instance.setMissing(4);
                    }
                    data.add(instance);


                }
            }

            //Save the data in the unknown dataset file
            ArffSaver saver2 = new ArffSaver();
            saver2.setInstances(data);
            saver2.setFile(new File("./data/unknownSet.arff"));
            saver2.writeBatch();

            //Load back the dataset
            ArffLoader loader2 = new ArffLoader();
            loader2.setSource(new File("./data/unknownSet.arff"));
            Instances predict = loader2.getDataSet();
            predict.setClassIndex(predict.numAttributes() - 1);


            //     https://github.com/nsadawi/WEKA-API/blob/master/src/ClassifyInstance.java

            //loop through the new dataset and make predictions
            for (int i = 0; i < predict.numInstances(); i++) {


                //Instance object of that we want to predict
                Instance newInst = predict.instance(i);

                //Use Naive Bayes to predict the class of the instance
                double predNB = nb.classifyInstance(newInst);


                String predString = predict.classAttribute().value((int) predNB);


                //Split the start and end time
                String[] strings = predString.split("-");
                String start = strings[0];
                String end = strings[1];

                //Put the predicited shift times in the rota
                for (int j = 0; j < newRota.getDaysList().size(); j++) {
                    List<Shift> shifts = newRota.getDaysList().get(j).getShiftList();

                    for (int k = 0; k < shifts.size(); k++) {
                        if (shifts.get(k).getUser().getId() == Integer.parseInt(newInst.stringValue(0)) &&
                                dayList.get(j).getDate().getDate() == Integer.parseInt(newInst.stringValue(1))) {

                            //If the user is not on holiday, predict the shift times
                            if (!shifts.get(k).getShiftStart().equals("HOL")) {
                                shifts.get(k).setShiftStart(start);
                                shifts.get(k).setShiftEnd(end);


                            }
                        }
                    }
                }

            }

            model.addAttribute("modelSummary", nb.toString());
        }


        model.addAttribute("userList", list);
        model.addAttribute("rota", newRota);
        model.addAttribute("shiftTimesList", shiftTimes);
        model.addAttribute("day", new Day());
        model.addAttribute("todayDate", new Date());
        controllerHelper.modelAddDays(dayList, model);


        return "createRota";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String newRota(@ModelAttribute("rota") Rota rota, Model model, BindingResult result, SessionStatus status) throws Exception {

        RotaValidation rotaValidation = new RotaValidation();
        rotaValidation.validate(rota, result);

        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        List<String> shiftTimes = new ArrayList<>();
        shiftTimes.add("OFF");
        controllerHelper.getTimeOptions(shiftTimes);

        List<UserInfo> list = (List<UserInfo>) userRepo.findAll();


        if (result.hasErrors()) {

            model.addAttribute("userList", list);
            model.addAttribute("shiftTimesList", shiftTimes);
            controllerHelper.modelAddDays(rota.getDaysList(), model);

            return "createRota";

        } else {

            List<Day> dayList = new ArrayList<>();
            List<Shift> shiftList = new ArrayList<>();


            for (int i = 0; i < rota.getDaysList().size(); i++) {
                Day day = new Day();
                day.setRota(rota);
                day.setDate(rota.getDaysList().get(i).getDate());
                day.setTemp(rota.getDaysList().get(i).getTemp());
                day.setPop((int) rota.getDaysList().get(i).getPop());
                day.setWeatherDes(rota.getDaysList().get(i).getWeatherDes());
                day.setApiCalled(rota.getDaysList().get(i).isApiCalled());
                day.setBankHoliday(rota.getDaysList().get(i).isBankHoliday());
                day.setBankHolidayTitle(rota.getDaysList().get(i).getBankHolidayTitle());
                day.setNoBookings(rota.getDaysList().get(i).getNoBookings());
                dayList.add(day);
            }

            for (int i = 0; i < dayList.size(); i++) {
                for (int j = 0; j < rota.getDaysList().get(i).getShiftList().size(); j++) {

                    UserInfo userInfo = userRepo.findById(rota.getDaysList().get(i).getShiftList().get(j).getUser().getId());
                    Shift shift = new Shift();
                    shift.setUser(userInfo);
                    shift.setShiftStart(rota.getDaysList().get(i).getShiftList().get(j).getShiftStart());
                    shift.setShiftEnd(rota.getDaysList().get(i).getShiftList().get(j).getShiftEnd());
                    shift.setDate(dayList.get(i));
                    shift.setOnHoliday(rota.getDaysList().get(i).getShiftList().get(j).isOnHoliday());
                    shiftList.add(shift);

                }


            }
            shiftRepo.save(shiftList);

            for (int i = 0; i < dayList.size(); i++) {
                for (int j = 0; j < shiftList.size(); j++) {
                    if (shiftList.get(j).getDate().equals(dayList.get(i))) {
                        dayList.get(i).getShiftList().add(shiftList.get(j));
                    }

                }

            }


            dayRepo.save(dayList);
            rota.setBusiness(loggedInUser.getBusiness());
            rotaRepo.save(rota);
            rota.setDaysList(dayList);


            Business business = bussRepo.findById(loggedInUser.getBusiness().getId());
            List<String> shiftTypes = business.getShiftTypes();

            //Add the new shift times to the list of known shifts
            for (int i = 0; i < shiftList.size(); i++) {
                if (!shiftTypes.contains(shiftList.get(i).getShiftStart() + "-" + shiftList.get(i).getShiftEnd())) {
                    if (!"HOL-HOL".equals(shiftList.get(i).getShiftStart() + "-" + shiftList.get(i).getShiftEnd()))
                        shiftTypes.add(shiftList.get(i).getShiftStart() + "-" + shiftList.get(i).getShiftEnd());

                }
            }
            bussRepo.save(business);


            controllerHelper.modelAddDays(dayList, model);
            controllerHelper.modelReturnRota(model, true, rota, loggedInUser);


            FastVector attributes;
            FastVector userIDValues;
            FastVector dateOfMonthValues;
            FastVector dayOfWeekValues;
            FastVector bankHolidayValues;
            FastVector shiftHoursValues;
            Instances data;
            double[] values;

            attributes = new FastVector();

            userIDValues = new FastVector();

            //Check the training set already exist
            File f = new File("./data/trainingSet.arff");
            Instances oldDataset = null;

            if (f.exists()) {
                ArffLoader loader = new ArffLoader();
                loader.setSource(new File("./data/trainingSet.arff"));
                oldDataset = loader.getDataSet();
            }

            List<UserInfo> userList = (List<UserInfo>) userRepo.findAll();
            for (int j = 0; j < userList.size(); j++) {
                userIDValues.addElement(String.valueOf(userList.get(j).getId()));
            }
            attributes.addElement(new Attribute("user", userIDValues));


            dateOfMonthValues = new FastVector();
            for (int i = 0; i < 31; i++) {
                dateOfMonthValues.addElement(String.valueOf(i + 1));
            }
            attributes.addElement(new Attribute("date", dateOfMonthValues));

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

            bankHolidayValues = new FastVector();
            bankHolidayValues.addElement("true");
            bankHolidayValues.addElement("false");
            attributes.addElement(new Attribute("bankHoliday", bankHolidayValues));

            attributes.addElement(new Attribute("bookings"));

            List<String> shiftTypesList = business.getShiftTypes();
            shiftHoursValues = new FastVector();
            for (int i = 0; i < shiftTypesList.size(); i++) {
                shiftHoursValues.addElement(shiftTypesList.get(i));
            }
            attributes.addElement(new Attribute("shiftTimes", shiftHoursValues));

            data = new Instances("Predict the shift each user will do", attributes, 0);

            for (int j = 0; j < dayList.size(); j++) {
                for (int i = 0; i < dayList.get(j).getShiftList().size(); i++) {

                    values = new double[data.numAttributes()];

                    values[0] = userIDValues.indexOf(String.valueOf(dayList.get(j).getShiftList().get(i).getUser().getId()));

                    values[1] = dateOfMonthValues.indexOf(String.valueOf(dayList.get(j).getDate().getDate()));

                    if (dayList.get(j).getDate().getDay() == 1) {
                        values[2] = dayOfWeekValues.indexOf("mon");

                    } else if (dayList.get(j).getDate().getDay() == 2) {
                        values[2] = dayOfWeekValues.indexOf("tue");

                    } else if (dayList.get(j).getDate().getDay() == 3) {
                        values[2] = dayOfWeekValues.indexOf("wed");

                    } else if (dayList.get(j).getDate().getDay() == 4) {
                        values[2] = dayOfWeekValues.indexOf("thu");

                    } else if (dayList.get(j).getDate().getDay() == 5) {
                        values[2] = dayOfWeekValues.indexOf("fri");

                    } else if (dayList.get(j).getDate().getDay() == 6) {
                        values[2] = dayOfWeekValues.indexOf("sat");

                    } else {
                        values[2] = dayOfWeekValues.indexOf("sun");

                    }
                    if (dayList.get(j).isApiCalled()) {
                        values[3] = dayList.get(j).getTemp();
                        values[4] = dayList.get(j).getPop();
                    }

                    values[5] = bankHolidayValues.indexOf(String.valueOf(dayList.get(j).isBankHoliday()));

                    values[6] = dayList.get(j).getNoBookings();

                    values[7] = shiftHoursValues.indexOf(dayList.get(j).getShiftList().get(i).getShiftStart() + "-" + dayList.get(j).getShiftList().get(i).getShiftEnd());

                    //If the shift is not on holiday then add to dataset
                    if (!"HOL-HOL".equals(dayList.get(j).getShiftList().get(i).getShiftStart() + "-" + dayList.get(j).getShiftList().get(i).getShiftEnd())) {
                        Instance instance = new DenseInstance(1.0, values);
                        data.setClassIndex(data.numAttributes() - 1);
                        instance.setDataset(data);

                        if (!dayList.get(j).isApiCalled()) {
                            instance.setMissing(3);
                            instance.setMissing(4);
                        }
                        data.add(instance);

                    }
                }
            }
            // Add the old data set to the new one
            if (f.exists()) {
                for (int i = 0; i < oldDataset.numInstances(); i++) {
                    data.add(oldDataset.get(i));

                }

            }

            // Save in the dataset file
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("./data/trainingSet.arff"));
            saver.writeBatch();

            status.setComplete();

            return "viewRota";
        }

    }


    @RequestMapping(value = "/", params = "edit", method = RequestMethod.GET)
    public String editRota(@ModelAttribute("rota") Rota rota, Model model) throws IOException {
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        List<Rota> rotaList = (List<Rota>) rotaRepo.findAll();
        List<UserInfo> list = (List<UserInfo>) userRepo.findAll();

        List<String> shiftTimes = new ArrayList<>();
        shiftTimes.add("OFF");
        shiftTimes.add("SICK");
        controllerHelper.getTimeOptions(shiftTimes);


        List<Day> dayList = new ArrayList<>();
        for (int i = 0; i < rotaList.size(); i++) {
            if (rotaList.get(i).getWeekNum() == rota.getWeekNum() && rotaList.get(i).getYear() == rota.getYear()) {
                dayList = rotaList.get(i).getDaysList();

                model.addAttribute("shiftTimesList", shiftTimes);
                controllerHelper.modelAddDays(dayList, model);


                model.addAttribute("rota", rotaList.get(i));
                model.addAttribute("validRota", true);
                model.addAttribute("userList", list);
                controllerHelper.modelCheckAdmin(model, loggedInUser);

                return "editRota";
            }

        }


        dayList = controllerHelper.setDatesInDayList(rota.getWeekNum(), rota.getYear(), loggedInUser.getBusiness().getCountry());

        Rota newRota = new Rota();
        newRota.setWeekNum(rota.getWeekNum());
        newRota.setYear(rota.getYear());

        model.addAttribute("error", true);
        model.addAttribute("message", "There is no rota for ");

        controllerHelper.modelAddDays(dayList, model);

        controllerHelper.modelReturnRota(model, false, newRota, loggedInUser);


        return "viewRota";


    }

    @RequestMapping(value = "edited", method = RequestMethod.POST)
    public String editedRota(@ModelAttribute("rota") Rota rota, Model model, BindingResult result) {
        UserInfo loggedInUser = controllerHelper.getLoggedInUser(userRepo);

        RotaValidation rotaValidation = new RotaValidation();
        rotaValidation.validate(rota, result);

        List<UserInfo> list = (List<UserInfo>) userRepo.findAll();


        if (result.hasErrors()) {

            List<String> shiftTimes = new ArrayList<>();
            shiftTimes.add("OFF");
            shiftTimes.add("SICK");
            controllerHelper.getTimeOptions(shiftTimes);

            model.addAttribute("shiftTimesList", shiftTimes);
            controllerHelper.modelAddDays(rota.getDaysList(), model);
            model.addAttribute("rota", rota);
            model.addAttribute("validRota", true);
            model.addAttribute("userList", list);

            return "editRota";

        } else {

            //Update the modified shifts
            for (int i = 0; i < rota.getDaysList().size(); i++) {
                for (int j = 0; j < rota.getDaysList().get(i).getShiftList().size(); j++) {
                    Shift shift;
                    UserInfo userInfo = userRepo.findById(rota.getDaysList().get(i).getShiftList().get(j).getUser().getId());
                    try {
                        shift = shiftRepo.findById(rota.getDaysList().get(i).getShiftList().get(j).getId());
                        shift.setShiftStart(rota.getDaysList().get(i).getShiftList().get(j).getShiftStart());
                        shift.setShiftEnd(rota.getDaysList().get(i).getShiftList().get(j).getShiftEnd());
                        shiftRepo.save(shift);
                    } catch (NullPointerException e) {
                        //This user is not on the initial rota (new registered user)
                        shift = new Shift();
                        shift.setShiftStart(rota.getDaysList().get(i).getShiftList().get(j).getShiftStart());
                        shift.setShiftEnd(rota.getDaysList().get(i).getShiftList().get(j).getShiftEnd());
                        shift.setUser(userInfo);
                        Day day = dayRepo.findById(rota.getDaysList().get(i).getId());

                        shift.setDate(day);
                        shiftRepo.save(shift);


                    }


                }


            }

            controllerHelper.modelAddDays(rota.getDaysList(), model);
            controllerHelper.modelReturnRota(model, true, rota, loggedInUser);

            return "viewRota";
        }
    }
}


