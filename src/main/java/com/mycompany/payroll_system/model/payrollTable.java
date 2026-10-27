package com.mycompany.payroll_system.model;
import com.mycompany.payroll_system.util.*;
import com.opencsv.*;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class payrollTable {
    private String payHeader[] = {"ID", "Name", "Date", "Time-In", "Basic Salary", "Short", "CA", "Transpo", "Tardiness", "Uniform", "Others", "Net"};
    private settings set = new settings();
    private employee e = new employee();
    private timeTracker t = new timeTracker();
    
    @CsvBindByName (column = "ID", required = true)
    private String id;
    
    @CsvBindByName (column = "Name")
    private String name;
    
    @CsvBindByName (column = "Date")
    private String date;
    
    @CsvBindByName (column = "Date")
    private String timeIn;
    
    @CsvBindByName (column = "Basic Salary", required = true)
    private double salary;
    
    @CsvBindByName (column = "Short")
    private double Short;
    
    @CsvBindByName (column = "CA")
    private double CA;
    
    @CsvBindByName (column = "Transpo")
    private double trans;
    
    @CsvBindByName (column = "Tardiness")
    private double tardy;
    
    @CsvBindByName (column = "Uniform")
    private double uni;
    
    @CsvBindByName (column = "Others")
    private double other;
    
    @CsvBindByName (column = "Net")
    private double net;

    public payrollTable() {
    }
    
    public void addEmp(String id, String name) {
        boolean replace = true;
        if (new File (set.getDir() + id + ".csv").isFile()) {
            try (CSVReader r = new CSVReaderBuilder(new FileReader(set.getDir() + id + ".csv"))
                .withCSVParser(new com.opencsv.CSVParserBuilder().build())
                .withSkipLines(1)
                .build()) {
            String[] dataLine = r.readNext();
            if (dataLine[1].equals(name)) {
                replace = false;
            }
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        }
        
        if (replace) {
                try (CSVWriter w = new CSVWriter(new FileWriter(set.getDir() + id + ".csv"))) {
                    w.writeNext(payHeader);
                    String s[] = {id, name};
                    w.writeNext(s);
                    w.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        
    }
    
    public void startPayroll(List<String[]> data, long val) {
        List<String[]> print = new ArrayList<>();
        for (String[] dataLine : data) {
            try (CSVWriter w = new CSVWriter(new FileWriter(set.getDir() + dataLine[0] + ".csv"))) {
                    w.writeNext(payHeader);
                    String s[] = {id, name};
                    w.writeNext(s);
                    w.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    
    public void timeIn(String id) {
        LocalTime now = LocalTime.now();
        int check = t.timeIn(now);
    }
    
    public LocalDate getEnd(String id) {
        try (CSVReader r = new CSVReaderBuilder(new FileReader(set.getDir() + id + ".csv"))
                .withCSVParser(new com.opencsv.CSVParserBuilder().build())
                .withSkipLines(1)
                .build()) {
            String[] dataLine = r.readNext();
            if (dataLine[0].equals(id)) {
                
            }
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        return null;
    }
}
