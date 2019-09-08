package Controller;

import Model.RunningSession;
import Model.User;
import Model.UserWeeklyReport;
import Repository.SessionRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

@RestController
public class ReportController {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public ReportController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/users/{login}/sessions/report")
    public ResponseEntity<LinkedList<UserWeeklyReport>> getReport(@PathVariable String login) {
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if (!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Iterable<RunningSession> result = sessionRepository.findAllByLogin(user.get());
            return new ResponseEntity<>(generateReport(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private static LinkedList<UserWeeklyReport> generateReport(Iterable<RunningSession> input) {
        LinkedList<UserWeeklyReport> output = new LinkedList<>();
        int weekCount = 1;
        LocalDate startWeek = LocalDate.now();
        LocalDate endWeek = LocalDate.now();
        int dayCount = 0;
        double averageSpeed;
        LinkedList<LocalTime> times = new LinkedList<>();
        LocalTime averageTime;
        double totalDistance = 0;
        Iterator itr = input.iterator();
        while (itr.hasNext()) {
            RunningSession rs = (RunningSession) itr.next();
            if (!rs.getDate().getDayOfWeek().name().equals("MONDAY")) {
                dayCount++;
                endWeek = rs.getDate();
                totalDistance += rs.getDistance();
                times.add(rs.getLength());
            }
            if (rs.getDate().getDayOfWeek().name().equals("MONDAY") ||
                    input.spliterator().getExactSizeIfKnown() - dayCount == 0) {
                averageTime = average(times);
                averageSpeed = totalDistance / countSeconds(averageTime);
                output.add(new UserWeeklyReport(weekCount, totalDistance, averageSpeed, startWeek, endWeek));
                startWeek = rs.getDate();
                weekCount++;
            }
        }
        return output;
    }

    private static int countSeconds(LocalTime time) {
        String[] sections = time.toString().split(":");
        int seconds = Integer.parseInt(sections[2]);
        return (Integer.parseInt(sections[0]) * 60 * 60) +
                (Integer.parseInt(sections[1]) * 60) +
                (Integer.parseInt(sections[2])) + seconds;
    }

    private static LocalTime average(LinkedList<LocalTime> times) {
        long nanosSum = 0;
        for (LocalTime time : times) {
            nanosSum += time.toNanoOfDay();
        }
        return LocalTime.ofNanoOfDay(nanosSum / (1 + times.size()));
    }
}
