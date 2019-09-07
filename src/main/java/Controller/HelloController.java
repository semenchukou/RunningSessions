package Controller;

import Model.User;
import Repository.SessionRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    private final UserRepository userRepository;
   private final SessionRepository sessionRepository;

    @Autowired
    public HelloController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/register")
    public User index(@RequestBody Model.User user) {
        return userRepository.save(user);
    }

    @RequestMapping("/users/")
    public String index() {
        return "yo";
    }

}
