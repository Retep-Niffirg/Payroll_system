package com.mycompany.payroll_system.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class PayrollTableController {
    @FXML private TableView<?> payrollTable;
    @FXML private void initialize() { payrollTable.setPlaceholder(new javafx.scene.control.Label("No payroll records available")); }
}
