package autorota.services;

import autorota.domain.Day;
import autorota.domain.Rota;
import autorota.domain.Shift;
import autorota.domain.UserInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RotaValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Rota.class.equals(clazz);
    }

    public String dayofweek(int a) {
        if (a == 0) return " Sunday: ";
        if (a == 1) return " Monday: ";
        if (a == 2) return " Tuesday: ";
        if (a == 3) return " Wednesday: ";
        if (a == 4) return " Thursday: ";
        if (a == 5) return " Friday: ";
        else
            return "Saturday: ";
    }


    public boolean everyoneOFF(Rota rota) {
        boolean allOff = true;
        List<Day> dayList = rota.getDaysList();
        for (int i = 0; i < dayList.size(); i++) {
            for (int j = 0; j < dayList.get(i).getShiftList().size(); j++) {
                Shift shift = dayList.get(i).getShiftList().get(j);

                if (!(shift.getShiftStart().equals("OFF") && shift.getShiftEnd().equals("OFF"))) {
                    allOff = false;
                }

            }

        }


        return allOff;
    }

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Override
    public void validate(Object target, Errors errors) {


        Rota rota = (Rota) target;

        if (everyoneOFF(rota)) {
            errors.rejectValue("daysList[0].shiftList[0].shiftStart", "daysList[0].shiftList[0].shiftStart.invalid", "You can not make a rota where everyone is off everyday");

        } else {


            List<Day> dayList = rota.getDaysList();
            for (int i = 0; i < rota.getDaysList().size(); i++) {

                for (int j = 0; j < dayList.get(i).getShiftList().size(); j++) {
                    Shift shift = dayList.get(i).getShiftList().get(j);

                    String start = shift.getShiftStart();
                    String end = shift.getShiftEnd();
                    Calendar startCal = Calendar.getInstance();
                    Calendar endCal = Calendar.getInstance();
                    UserInfo user = rota.getDaysList().get(i).getShiftList().get(j).getUser();


                    try {
                        startCal.setTime(dateFormat.parse(start));
                        endCal.setTime(dateFormat.parse(end));

                        if (!startCal.before(endCal)) {

                            errors.rejectValue("daysList[" + i + "].shiftList[" + j + "].shiftStart", "daysList[" + i + "].shiftList[" + j + "].shiftStart.invalid",
                                    dayofweek(rota.getDaysList().get(i).getDate().getDay()) + user.getForename() + " " + user.getLastname() + ": Start time should be before end time");


                        }

                    } catch (ParseException e) {

                        if ((end.equals("OFF") && !start.equals("OFF")) || start.equals("OFF") && !end.equals("OFF")) {
                            errors.rejectValue("daysList[" + i + "].shiftList[" + j + "].shiftEnd", "daysList[" + i + "].shiftList[" + j + "].shiftEnd.invalid",
                                    dayofweek(rota.getDaysList().get(i).getDate().getDay()) + user.getForename() + " " + user.getLastname() + ": Both start and end time need to be set to OFF to set someone as off");
                        }
                        if ((end.equals("SICK") && !start.equals("SICK")) || start.equals("SICK") && !end.equals("SICK")) {
                            errors.rejectValue("daysList[" + i + "].shiftList[" + j + "].shiftEnd", "daysList[" + i + "].shiftList[" + j + "].shiftEnd.invalid",
                                    dayofweek(rota.getDaysList().get(i).getDate().getDay()) + user.getForename() + " " + user.getLastname() + ": Both start and end time need to be set to SICK to mark someone as sick");

                        }


                    }

                }


            }

        }
    }


}

