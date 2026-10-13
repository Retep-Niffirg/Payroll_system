package com.mycompany.payroll_system.ui.controllers;

import com.mycompany.payroll_system.model.employee;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Window;

public class EmployeeManagementController {
    @FXML private TableView<employee> employeeTable;
    @FXML private TableColumn<employee, String> idColumn, nameColumn;
    @FXML private TableColumn<employee, Number> salaryColumn;
    @FXML private TableColumn<employee, String> dateColumn;
    @FXML private TextField searchField;
    @FXML private Label recordDescription, countLabel;
    private final ObservableList<employee> records = FXCollections.observableArrayList();
    private FilteredList<employee> filtered;

    @FXML private void initialize() {
        idColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));
        nameColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        salaryColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getSalary()));
        salaryColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-PH")).format(value.doubleValue()));
            }
        });
        dateColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getHireDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))));
        filtered = new FilteredList<>(records, p -> true);
        employeeTable.setItems(filtered);
        searchField.textProperty().addListener((o, a, value) -> {
            String query = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
            filtered.setPredicate(e -> query.isEmpty() || e.getId().toLowerCase(Locale.ROOT).contains(query) || e.getName().toLowerCase(Locale.ROOT).contains(query));
            updateCount();
        });
        updateCount();
    }

    private void updateCount() {
        int shown = filtered == null ? 0 : filtered.size();
        recordDescription.setText(shown == 0 ? "No employee records" : shown + (shown == 1 ? " employee record" : " employee records"));
        countLabel.setText("Showing " + shown + (records.size() == shown ? "" : " of " + records.size()));
    }

    @FXML private void saveEmployees() {
        List<String[]> data = new ArrayList<>();
        data.add(new employee().getEmpHeader());
        for (employee e : records) data.add(new String[]{e.getId(), e.getName(), String.valueOf(e.getSalary()), String.valueOf(e.getHireDate())});
        new employee().empSave(data);
    }

    @FXML private void openAddEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/payroll_system/ui/views/add-employee.fxml"));
            DialogPane pane = loader.load();
            AddEmployeeController controller = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add employee");
            dialog.setDialogPane(pane);
            dialog.setResultConverter(button -> button);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, new ButtonType("Save employee", ButtonBar.ButtonData.OK_DONE));
            dialog.getDialogPane().getStyleClass().add("employee-dialog");
            Window owner = employeeTable.getScene().getWindow();
            dialog.initOwner(owner);
            Button save = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
            save.getStyleClass().add("primary-button");
            Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.getStyleClass().add("secondary-button");
            save.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.submit(records, employeeTable)) event.consume();
            });
            dialog.showAndWait();
            updateCount();
        } catch (IOException ex) { throw new IllegalStateException("Unable to load add employee dialog", ex); }
    }
}
