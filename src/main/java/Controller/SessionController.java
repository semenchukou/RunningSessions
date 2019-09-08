package Controller;

import Model.RunningSession;
import Model.User;
import Repository.SessionRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SessionController {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/users/{login}/sessions/")
    public ResponseEntity<String> createSession(@PathVariable String login, @RequestBody RunningSession runningSession) {
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if(!user.isPresent()) {
                return new ResponseEntity<>("No such user", HttpStatus.BAD_REQUEST);
            }
            runningSession.setLogin(user.get());
            sessionRepository.save(runningSession);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{login}/sessions/{session_id}")
    public ResponseEntity<RunningSession> getSession(@PathVariable String login, @PathVariable int session_id) {
        Optional<RunningSession> result;
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if(!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            result = sessionRepository.findById(session_id);
            if (!result.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @PutMapping("/users/{login}/sessions/{session_id}")
    public ResponseEntity<String> updateSession(@PathVariable String login, @PathVariable int session_id,
                                                @RequestBody RunningSession runningSession) {
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if(!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<RunningSession> rs = sessionRepository.findById(session_id);
            if (rs.isPresent()) {
                sessionRepository.save(runningSession);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{login}/sessions/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable String login, @PathVariable int id) {
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if(!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (sessionRepository.findById(id).isPresent()) {
                sessionRepository.deleteById(id);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{login}/sessions/")
    public ResponseEntity<Iterable<RunningSession>> getSessions(@PathVariable String login) {
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if(!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Iterable<RunningSession> result = sessionRepository.findAllByLogin(user.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
