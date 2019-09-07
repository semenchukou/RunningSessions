package Model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "RUNNING_SESSION")
public class RunningSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_ID")
    private int id;

    @Column(name = "DISTANCE", nullable = false)
    private double distance;

    @Column(name = "LENGTH", nullable = false)
    private LocalTime length;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalTime getLength() {
        return length;
    }

    public void setLength(LocalTime length) {
        this.length = length;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
