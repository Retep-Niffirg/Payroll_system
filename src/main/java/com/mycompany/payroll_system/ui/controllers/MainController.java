package com.mycompany.payroll_system.ui.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML private StackPane pageHost;
    @FXML private Label breadcrumb, pageTitle, pageDescription;
    @FXML private Button employeeNav, payrollNav;
    private EmployeeManagementController employeeController;
    private Node employeeView;

    @FXML private void initialize() { showEmployees(); }

    @FXML private void showEmployees() {
        try {
            if (employeeView == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/payroll_system/ui/views/employee-management.fxml"));
                employeeView = loader.load();
                employeeController = loader.getController();
            }
            pageHost.getChildren().setAll(employeeView);
            breadcrumb.setText("Payroll System   ›   Employee Management");
            pageTitle.setText("Employee Management");
            pageDescription.setText("Manage the employee records supported by the current application.");
            employeeNav.getStyleClass().setAll("nav-button", "nav-active");
            payrollNav.getStyleClass().setAll("nav-button");
        } catch (IOException ex) { throw new IllegalStateException("Unable to load employee screen", ex); }
    }

    @FXML private void showPayroll() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/payroll_system/ui/views/payroll-table.fxml"));
            Node payrollPage = loader.load();
            pageHost.getChildren().setAll(payrollPage);
            breadcrumb.setText("Payroll System   ›   Payroll Table");
            pageTitle.setText("Payroll Table");
            pageDescription.setText("Visual layout concept based on the existing payroll table structure.");
            employeeNav.getStyleClass().setAll("nav-button");
            payrollNav.getStyleClass().setAll("nav-button", "nav-active");
        } catch (IOException ex) { throw new IllegalStateException("Unable to load payroll screen", ex); }
    }
}
