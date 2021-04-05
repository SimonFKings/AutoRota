package autorota;

import autorota.domain.Availability;
import autorota.domain.Business;
import autorota.domain.Role;
import autorota.domain.UserInfo;
import autorota.repository.BusinessRepository;
import autorota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

;


@SpringBootApplication
public class AutorotaApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {


    @Autowired
    UserRepository  userRepo;

    @Autowired
    BusinessRepository bussRepo;


    public static void main(String[] args) {

        SpringApplication.run(AutorotaApplication.class, args);
    }


    @Override
    public void run(String... strings) {

        Business b = new Business();
        b.setName("Simon's Restaurant");
        b.setCountry("GB");
        b.setCity("Woolwich");


        BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
        List<UserInfo> userInfoList = new ArrayList<>();


        UserInfo user = new UserInfo();
        user.setEmail("simon@test.co.uk");
        user.setForename("Simon");
        user.setLastname("Fisoye-Kings");
        user.setPassword(pe.encode("password"));
        user.setConfirmPassword(pe.encode("password"));
        user.setJobTitle("CEO");
        Role role = new Role();
        role.setId(1);
        role.setRole("ADMIN");
        user.setRole(role);
        user.setDob( LocalDate.of(1998,06,16));
        user.setPhoneNum("07572846381");
        user.setSalary("11.50");
        user.setBusiness(b);

        Availability ava1 = new Availability(user,1,1,1,1,1,0,-1);
        user.setAvailability(ava1);
        userInfoList.add(user);

        UserInfo user2 ;

        user2 = new UserInfo();
        user2.setEmail("martial@test.co.uk");
        user2.setForename("Anthony");
        user2.setLastname("Martial");
        user2.setJobTitle("Waiter");
        user2.setPassword(pe.encode("password"));
        user2.setConfirmPassword(pe.encode("password"));
        role = new Role();
        role.setId(2);
        role.setRole("EMPLOYEE");
        user2.setRole(role);
        user2.setDob(LocalDate.of(1996,10,12));
        user2.setPhoneNum("07547246381");
        user2.setSalary("7.50");
        user2.setBusiness(b);

        Availability ava2 = new Availability(user2,-1,-1,-1,-1,0,1,1);
        user2.setAvailability(ava2);
        userInfoList.add(user2);

        userRepo.save(userInfoList);




        File f = new File("./data/trainingSet.arff");
        if(f.exists()){
            f.delete();
        }
        f = new File("./data/unknownSet.arff");
        if(f.exists()){
            f.delete();
        }
    }


}
