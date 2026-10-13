package com.mycompany.payroll_system.ui.controllers;

import com.mycompany.payroll_system.model.employee;
import java.time.DateTimeException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class AddEmployeeController {
    @FXML private TextField idField, nameField, salaryField;
    @FXML private DatePicker hireDatePicker;
    @FXML private Label feedbackLabel;

    boolean submit(ObservableList<employee> records, TableView<employee> table) {
        String id = idField.getText().trim(), name = nameField.getText().trim();
        if (id.isBlank() || name.isBlank() || salaryField.getText().trim().isBlank() || hireDatePicker.getValue() == null) {
            feedbackLabel.setText("Complete all four fields before saving."); return false;
        }
        final double salary;
        final LocalDate date;
        try { salary = Double.parseDouble(salaryField.getText().trim()); }
        catch (NumberFormatException ex) { feedbackLabel.setText("Please enter a number for salary."); return false; }
        try { date = hireDatePicker.getValue(); }
        catch (DateTimeException ex) { feedbackLabel.setText("Please enter a valid date."); return false; }

        int found = -1;
        for (int i = 0; i < records.size(); i++) if (records.get(i).getId().equals(id)) { found = i; break; }
        if (found >= 0) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "This ID is already assigned to " + records.get(found).getName() + ".\nReplace the entry?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Duplicate employee ID"); confirm.setHeaderText("Employee ID already exists");
            Window window = table.getScene().getWindow(); confirm.initOwner(window);
            if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) return false;
            records.set(found, new employee(id, name, salary, date));
        } else records.add(new employee(id, name, salary, date));
        return true;
    }
}
