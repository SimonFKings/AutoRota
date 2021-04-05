package autorota.repository;

import autorota.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Integer> {

    public UserInfo findById(int id);

    public UserInfo findByEmail(String email);

}
