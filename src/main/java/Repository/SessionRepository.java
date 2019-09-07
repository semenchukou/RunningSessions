package Repository;

import Model.RunningSession;
import Model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface SessionRepository extends CrudRepository<RunningSession, Integer> {
    RunningSession findByUser(User user);
    RunningSession findByUserLoginAndDate(String login, LocalDate date);
}