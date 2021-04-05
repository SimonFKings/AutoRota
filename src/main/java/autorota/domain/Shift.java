package autorota.domain;

import javax.persistence.*;

@Entity
@Table(name = "shifts")
public class Shift {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Id
    @GeneratedValue
    int id;

    public Shift(UserInfo user, String shiftStart, String shiftEnd, Day day) {
        this.user = user;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.day = day;

    }

    public Shift() {
        shiftStart = "OFF";
        shiftEnd = "OFF";
        onHoliday = false;
    }

    public Shift(UserInfo user) {
        this.user = user;
        shiftStart = "OFF";
        shiftEnd = "OFF";
        onHoliday = false;
    }


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "shiftList", referencedColumnName = "id")
    UserInfo user;
    String shiftStart;
    String shiftEnd;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "shiftList", referencedColumnName = "id")
    Day day;

    boolean onHoliday;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public boolean isOnHoliday() {
        return onHoliday;
    }

    public void setOnHoliday(boolean onHoliday) {
        this.onHoliday = onHoliday;
    }

    public Day getDate() {
        return day;
    }

    public void setDate(Day date) {
        this.day = date;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }
}
