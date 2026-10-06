package com.mycompany.payroll_system.util;
import java.time.*;

public class timeTracker {
    private LocalTime timeIn;
    private LocalTime lateIn;
    
    public timeTracker(LocalTime timeIn, LocalTime lateIn) {
        this.timeIn = timeIn;
        this.lateIn = lateIn;
    }
    
    public timeTracker() {
        this(LocalTime.of(6, 0), LocalTime.of(6, 15)); 
    }
    
    public char clockIn(LocalDateTime empLog) {
        LocalTime empLogTime = LocalTime.of(empLog.getHour(), empLog.getMinute());
        
        if (empLogTime.isAfter(lateIn)) {
            return 'a';
        } else if (empLogTime.isAfter(timeIn) && empLogTime.isBefore(lateIn)) {
            return 'l';
        } else {
            return 'p';
        }
    }
    
    public LocalTime getTimeIn() {
        return timeIn;
    }
    
    public LocalTime getLateIn() {
        return lateIn;
    }
    
    public LocalDate dateConv(String day, String month, String year) {
        int dayInt = Integer.parseInt(day);
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        
        LocalDate d = LocalDate.of(yearInt, monthInt, dayInt);
        return d;
    }
}
