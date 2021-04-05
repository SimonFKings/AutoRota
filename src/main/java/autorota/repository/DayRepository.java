package autorota.repository;

import autorota.domain.Day;
import org.springframework.data.repository.CrudRepository;

public interface DayRepository extends CrudRepository<Day, Integer> {

    public Day findById(int id);
}
