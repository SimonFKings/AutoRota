package autorota.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue
    int id;
    String startDateS;
    String endDateS;

    Date startDate;
    Date endDate;


    public void setEndDateS(String endDateS) {
        this.endDateS = endDateS;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    boolean accepted;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "holidays", referencedColumnName = "id")
    UserInfo user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }


    public String getStartDateS() {
        return startDateS;
    }

    public void setStartDateS(String startDateS) {
        this.startDateS = startDateS;
    }

    public String getEndDateS() {
        return endDateS;
    }

    public void setEndDate(String endDate) {
        this.endDateS = endDateS;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
