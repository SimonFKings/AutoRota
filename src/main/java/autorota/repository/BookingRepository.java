package autorota.repository;

import autorota.domain.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

    public Booking findById(int id);

}
