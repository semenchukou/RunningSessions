package Model;

import java.time.LocalDate;

public class UserWeeklyReport {
    private int weekNumber;
    private double totalDistance;
    private double averageSpeed;
    private LocalDate weekStart;
    private LocalDate weekEnd;

    public UserWeeklyReport(int weekNumber, double totalDistance, double averageSpeed, LocalDate weekStart, LocalDate weekEnd) {
        this.weekNumber = weekNumber;
        this.totalDistance = totalDistance;
        this.averageSpeed = averageSpeed;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
    }
}