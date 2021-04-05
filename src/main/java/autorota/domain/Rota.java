package autorota.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//
@Entity
@Table(name = "rota")
public class Rota {
    @Id
    @GeneratedValue
    int id;

    public Rota(int weekNum, int year) {
        this.weekNum = weekNum;
        this.year = year;
        daysList = new ArrayList<>(7);

    }

    public Rota() {

        daysList = new ArrayList<>(7);
    }

    public Rota(List<Day> daysList, int weekNum, int year) {
        this.daysList = daysList;
        this.weekNum = weekNum;
        this.year = year;
    }

    @OneToMany(mappedBy = "rota")
    private List<Day> daysList;
    int weekNum;
    int year;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "rotas", referencedColumnName = "id")
    Business business;


    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Day> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<Day> daysList) {
        this.daysList = daysList;
    }


}
