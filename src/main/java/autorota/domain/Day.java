package autorota.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "days")
public class Day {

    @Id
    @GeneratedValue
    int id;


    public boolean isApiCalled() {
        return apiCalled;
    }

    public void setApiCalled(boolean apiCalled) {
        this.apiCalled = apiCalled;
    }

    public Day(Date date) {
        this.date = date;
        apiCalled = false;

    }

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date date;
    @OneToMany(mappedBy = "day")
    List<Shift> shiftList;


    int pop;
    double temp;
    String weatherDes;
    boolean apiCalled;
    boolean bankHoliday;
    int noBookings;
    String bankHolidayTitle;

    public String getBankHolidayTitle() {
        return bankHolidayTitle;
    }

    public void setBankHolidayTitle(String bankHolidayTitle) {
        this.bankHolidayTitle = bankHolidayTitle;
    }

    public int getNoBookings() {
        return noBookings;
    }

    public void setNoBookings(int noBookings) {
        this.noBookings = noBookings;
    }

    public boolean isBankHoliday() {
        return bankHoliday;
    }

    public void setBankHoliday(boolean bankHoliday) {
        this.bankHoliday = bankHoliday;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getWeatherDes() {
        return weatherDes;
    }

    public void setWeatherDes(String weatherDes) {
        this.weatherDes = weatherDes;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "daysList", referencedColumnName = "id")
    Rota rota;

    public Day(int id, Date date, List<Shift> shiftList, Rota rota) {
        this.id = id;
        this.date = date;
        this.shiftList = shiftList;
        this.rota = rota;
    }

    public Day() {

        shiftList = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }
}
