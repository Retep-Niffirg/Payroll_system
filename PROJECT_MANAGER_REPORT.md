# Payroll System UI Migration and Launch Report

**Prepared:** 13 October 2026  
**Project:** Payroll System (`Payroll_system`)  
**Audience:** Project Manager

## Executive Summary

The Payroll System presentation layer was migrated from Swing to JavaFX with FXML layouts and a shared JavaFX stylesheet. The new screens follow the supplied employee management, add employee, and payroll table references. The employee workflow continues to use the existing employee model and CSV writer. Maven configuration now provides a JavaFX-aware launch path, and the IDE project Run action is mapped to that path.

The latest `mvn clean compile` completed successfully. `mvn javafx:run` started the JavaFX run goal, loaded the Windows JavaFX graphics native library, and remained active without reporting the missing-runtime error. The desktop inspection interface exposed no application windows, so the rendered UI could not be independently verified.

## Work Completed

### JavaFX UI migration

- Added FXML views for the main application shell, Employee Management, Add Employee, and Payroll Table.
- Added JavaFX controllers for navigation, employee display/search, employee creation and duplicate-ID replacement, and the payroll table placeholder.
- Added `app.css` for the shared sidebar, page header, buttons, cards, table, form, and empty-state styling.
- Replaced the old Swing `GUI` implementation with a compatibility launcher and changed `Payroll_system` to the JavaFX `Application` entry point.
- Removed the Payroll Table `CONCEPT` label and “Layout concept only” heading, plus the sidebar “DESKTOP / ADMINISTRATION” subtitle and “UI redesign concept - v1.0” footer, as requested.

### Existing behavior and data

- Employee records are held in the current session and use the existing `employee` model fields: ID, name, salary, and hire date.
- Duplicate IDs prompt before replacing the matching in-memory record.
- The explicit Save control calls the existing model CSV-writing method. Startup retains the existing header-save timing.
- The application does not load CSV records at startup. The empty employee state is therefore shown until a record is added during that session.
- The payroll model has no implemented calculations or populated employee relationship. The payroll screen presents the requested columns without fabricated employee rows or payroll results.

### Maven launch and compile fixes

- Kept Java release 24 and JavaFX 21.0.6. Maven reports JDK 27; the shell's `java -version` reports JDK 21.0.8. No release upgrade was made.
- Confirmed the JavaFX dependency metadata selects the Windows `win` artifacts for this Windows x64 environment.
- Configured `javafx-maven-plugin` 0.0.8 to use `com.mycompany.payroll_system.Payroll_system` through the `javafx.mainClass` property.
- Added `nbactions.xml` to map NetBeans' project Run action to `javafx:run`. The reported `exec-maven-plugin:3.1.0:exec` invocation was not declared in the project POM; it came from the IDE's Run action.
- Fixed three Java generic errors: the startup CSV header list now uses `Collections.singletonList`, employee records and the filtered table now share one `ObservableList<employee>`, and the loaded payroll view is typed as `Node` before `setAll`.

## Verification Results

| Command | Result |
|---|---|
| `mvn clean compile` | **BUILD SUCCESS** (latest run) |
| `mvn javafx:run` | Entered `javafx:0.0.8:run`; JavaFX native graphics library loaded; process remained active without the missing-runtime error. The desktop inspection interface returned no app windows, so visual rendering was not independently confirmed. |
| `mvn test` | **BUILD SUCCESS** in an earlier run; Maven reported no test sources/tests to run. |
| `mvn package` | **BUILD SUCCESS** in an earlier run. The later `mvn clean compile` removed generated `target` artifacts, including the JAR, and package was not rerun afterward. |

Earlier Maven attempts in the restricted environment could not create the default `C:\.m2\repository` or access cached dependency JARs. The successful compile and launch were run with Maven access to its available dependency repository.

## Current Limitations and Follow-up

- Visually inspect the running JavaFX window on the user desktop. The available desktop snapshot API returned no app windows, so visual acceptance remains unconfirmed.
- There are no automated tests in the project.
- CSV loading is not implemented. Startup preserves the pre-existing header-save behavior, which writes a header-only list before any current-session records have been loaded; this may replace previously saved CSV rows on a subsequent launch. It was retained to avoid changing backend behavior during the UI migration.
- The latest clean build removed generated files under `target`, including the packaged JAR. Run `mvn package` to regenerate it when a JAR is needed.

## Files and Working-Tree Notes

New UI files:

- `src/main/java/com/mycompany/payroll_system/ui/controllers/AddEmployeeController.java`
- `src/main/java/com/mycompany/payroll_system/ui/controllers/EmployeeManagementController.java`
- `src/main/java/com/mycompany/payroll_system/ui/controllers/MainController.java`
- `src/main/java/com/mycompany/payroll_system/ui/controllers/PayrollTableController.java`
- `src/main/resources/com/mycompany/payroll_system/ui/views/add-employee.fxml`
- `src/main/resources/com/mycompany/payroll_system/ui/views/employee-management.fxml`
- `src/main/resources/com/mycompany/payroll_system/ui/views/main-layout.fxml`
- `src/main/resources/com/mycompany/payroll_system/ui/views/payroll-table.fxml`
- `src/main/resources/com/mycompany/payroll_system/ui/styles/app.css`
- `nbactions.xml`

Updated application/build files:

- `src/main/java/com/mycompany/payroll_system/GUI.java`
- `src/main/java/com/mycompany/payroll_system/Payroll_system.java`
- `pom.xml`

The working tree also contains changes to `employee.csv` and tracked build output under `target`. Employee details are intentionally omitted from this report. Review those data/build changes separately before committing or distributing the project.
