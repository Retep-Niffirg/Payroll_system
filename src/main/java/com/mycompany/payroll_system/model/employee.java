package com.mycompany.payroll_system.model;
import com.opencsv.bean.*;
import com.opencsv.*;
import com.opencsv.exceptions.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class employee {
    private String empHeader[] = {"ID", "Name", "Salary", "Manager" ,"Date Hired"};
    private String empFile = "employee.csv";
    
    @CsvBindByName (column = "ID", required = true)
    private String id;
    
    @CsvBindByName (column = "Name")
    private String name;
    
    @CsvBindByName (column = "Salary", required = true)
    private double salary;
    
    @CsvBindByName (column = "Manager", required = true)
    private boolean isManager;
    
    @CsvBindByName (column = "Date Hired", required = true)
    private LocalDate hireDate;
    
    public employee (String id, String name, double salary, boolean isManager, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.isManager = isManager;
        this.hireDate = hireDate;
    }
    
    public employee () {
    }
  
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public String getFile() {
        return empFile;
    }
    
    public boolean getManager() {
        return isManager;
    }
    
    public String[] getEmpHeader() {
        return empHeader;
    }
    
    public void empSave(List<String[]> data) {
        try (CSVWriter w = new CSVWriter(new FileWriter(empFile))) {
            w.writeAll(data);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<String[]> empLoad() {
        List<String[]>  data = new ArrayList<>();
        try (CSVReader r = new CSVReaderBuilder(new FileReader(empFile))
                .withCSVParser(new com.opencsv.CSVParserBuilder().build())
                .withSkipLines(1)
                .build()) {
            String[] dataLine;
            
            while ((dataLine = r.readNext()) != null) {
                data.add(dataLine);
            } 
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        
        return data;
    }
}
