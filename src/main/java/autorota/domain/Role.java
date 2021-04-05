package autorota.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    public Role() {
        super();
    }

    public Role(int id, String role) {
        super();
        this.id = id;
        this.role = role;
    }

    @Id
//	@GeneratedValue
    private int id;

    private String role;

    @OneToMany(mappedBy = "role")
    private Set<UserInfo> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<UserInfo> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserInfo> userRoles) {
        this.userRoles = userRoles;
    }

}
