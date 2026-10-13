package com.mycompany.payroll_system;

import com.mycompany.payroll_system.model.employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Payroll_system extends Application {
    private static final employee employeeStore = new employee();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/payroll_system/ui/views/main-layout.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 820);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/payroll_system/ui/styles/app.css").toExternalForm());
        stage.setTitle("Payroll System");
        stage.setMinWidth(980);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();
        employeeStore.empSave(java.util.Collections.singletonList(employeeStore.getEmpHeader()));
    }

    public static void launchJavaFx() { Application.launch(Payroll_system.class); }
    public static void main(String[] args) { launchJavaFx(); }
}
