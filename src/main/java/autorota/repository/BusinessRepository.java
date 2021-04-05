package autorota.repository;

import autorota.domain.Business;
import org.springframework.data.repository.CrudRepository;

public interface BusinessRepository extends CrudRepository<Business, Integer> {

    public Business findById(int id);

}
