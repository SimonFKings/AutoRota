package autorota.repository;

import autorota.domain.Shift;
import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    public Shift findById(int id);
}
