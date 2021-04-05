package autorota.repository;

import autorota.domain.Rota;
import org.springframework.data.repository.CrudRepository;

public interface RotaRepository extends CrudRepository<Rota, Integer> {

    Rota findById(int id);
}
