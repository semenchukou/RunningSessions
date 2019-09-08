package Controller;

import Model.User;
import Repository.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegisterController {

    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody ObjectNode user) {
        try {
            User userToSave = new User(user.get("login").asText(), user.get("password").asText());
            userRepository.save(userToSave);
            return new ResponseEntity<>(userToSave, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ObjectNode objectNode) {
        try {
            User user = new User(objectNode.get("login").asText(), objectNode.get("password").asText());
            Optional<User> userFromDB = userRepository.findByLogin(user.getLogin());
            if(!userFromDB.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(userFromDB.get().getPassword().equals(user.getPassword())){
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
