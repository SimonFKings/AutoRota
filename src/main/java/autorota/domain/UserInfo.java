package autorota.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class UserInfo {


    public UserInfo() {

        phoneNum = "+44";
    }

    @Id
    @GeneratedValue
    private int id;
    private String salary;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private String password;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private String confirmPassword;

    private String forename;
    private String lastname;
    private String jobTitle;
    private String phoneNum;
    private String email;
    @Transient
    String userType;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "user", referencedColumnName = "id")
    private Role role;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(columnDefinition = "user", referencedColumnName = "id")
    Availability availability;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "users", referencedColumnName = "id")
    Business business;

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    @OneToMany(mappedBy = "user")
    List<Shift> shiftList;

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    @OneToMany(mappedBy = "user")
    List<Holiday> holidays;

    boolean passwordChange;


    public boolean isPasswordChange() {
        return passwordChange;
    }

    public void setPasswordChange(boolean passwordChange) {
        this.passwordChange = passwordChange;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", salary=" + salary +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                ", forename='" + forename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", role=" + role +
                '}';
    }


    public UserInfo(int id, String phoneNum, String email,
                    String jobTitle, String password, Role role, String forename,
                    String lastname, String salary, LocalDate dob) {

        super();
        this.id = id;
        this.password = password;
        this.role = role;
        this.forename = forename;
        this.lastname = lastname;
        this.salary = salary;
        this.dob = dob;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phoneNum = phoneNum;

    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}