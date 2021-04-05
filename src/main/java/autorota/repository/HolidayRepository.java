package autorota.repository;

import autorota.domain.Holiday;
import org.springframework.data.repository.CrudRepository;

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {

    public Holiday findById(int id);


}
