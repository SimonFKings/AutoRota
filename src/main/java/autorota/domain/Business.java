package autorota.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue
    int id;
    String name;

    String country;
    String city;

    public Business() {
        shiftTypes = new ArrayList<>();
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public Business(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(List<Rota> rotas) {
        this.rotas = rotas;
    }

    @OneToMany(mappedBy = "business")
    List<Rota> rotas;

    @OneToMany(mappedBy = "business")
    List<UserInfo> users;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @OneToMany(mappedBy = "business")
    List<Booking> bookings;

    @ElementCollection
    @CollectionTable(name = "shiftTypes")
    private List<String> shiftTypes = new ArrayList<String>();

    public List<String> getShiftTypes() {
        return shiftTypes;
    }

    public void setShiftTypes(List<String> shiftTypes) {
        this.shiftTypes = shiftTypes;
    }
}
