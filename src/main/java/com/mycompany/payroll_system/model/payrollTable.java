package com.mycompany.payroll_system.model;
import com.opencsv.*;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;

public class payrollTable {
    public String payHeader[] = {"ID", "Name", "Date", "Basic Salary", "Short", "CA", "Transpo", "Tardiness", "Uniform", "Others", "Net"};
    
    @CsvBindByName (column = "ID", required = true)
    private String id;
    
    @CsvBindByName (column = "Name")
    private String name;
    
    @CsvBindByName (column = "Date")
    private String date;
    
    @CsvBindByName (column = "Basic Salary", required = true)
    private double salary;
    
    @CsvBindByName (column = "Short")
    private double Short;
    
    @CsvBindByName (column = "CA")
    private double CA;
    
    @CsvBindByName (column = "Transpo")
    private double Transpo;
    
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
    
    public void write() {
        
    }
}
