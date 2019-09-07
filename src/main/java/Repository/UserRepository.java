package Repository;

import Model.User;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByLogin(String login);
}
