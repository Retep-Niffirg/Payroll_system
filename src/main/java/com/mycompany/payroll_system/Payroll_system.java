package com.mycompany.payroll_system;
import com.mycompany.payroll_system.model.*;
import java.time.*;
import java.time.format.*;
import java.util.Locale;
 
public class Payroll_system {

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        
        GUI g = new GUI();
        g.show();
        g.popUpMenu();
        System.out.println(LocalTime.of(9, 12).format(dtf));
    }
}
