package com.mycompany.payroll_system.model;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class settings {
    private JSONObject o;
    private JSONParser p;
    private final String setFile = "settings.json";
    String dir = System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents" + System.getProperty("file.separator") + "EmployeeData" + System.getProperty("file.separator");
    
   public settings() {
       new File(dir).mkdirs();
       if (new File(dir + setFile).exists()) {
           
       } else {
           defaultSet();
       }
   }
   
   private void defaultSet() {
       o = new JSONObject();
       o.put("Probationary", 6);
       o.put("Default Salary", 420.00);
       o.put("Pay Increase", 480.00);
       o.put("Manager Salary", 560.00);
       o.put("Payroll Period", 14);
       o.put("Time-In", "6:15");
       o.put("Late", 14);
       o.put("Payroll End", null);
       
       try (FileWriter f = new FileWriter(dir + setFile)) {
           f.write(o.toJSONString());
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
   public String loadSet(String setting) {
       p = new JSONParser();
       o = new JSONObject();
       try (FileReader f = new FileReader(dir + setFile)) {
           Object e = p.parse(f);
           o = (JSONObject) e;
       } catch (FileNotFoundException e) {
           defaultSet();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ParseException e) {
           e.printStackTrace();
       } 
       
       String result = String.valueOf(o.get(setting));
       return result;
   }
   
   public void changeSet(String setting, Object val) {
       o = new JSONObject();
       o.put(setting, val);
   }
   
   public String getDir() {
       return dir;
   }
}
