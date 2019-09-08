package Repository;

import Model.RunningSession;
import Model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface SessionRepository extends CrudRepository<RunningSession, Integer> {
    Iterable<RunningSession> findAllByLogin(User login);
}