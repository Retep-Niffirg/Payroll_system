package com.mycompany.payroll_system;
import com.mycompany.payroll_system.model.*;
import com.mycompany.payroll_system.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.time.*;
import java.util.*;
import javax.swing.text.*;

public class GUI {
    private employee e = new employee(); 
    private timeTracker time = new timeTracker(); 
    
    DefaultTableModel empModel = new DefaultTableModel(null, e.getEmpHeader()) {
        @Override
            public boolean isCellEditable(int row, int column) { 
                return false;
            }
            public boolean setCellSelectionEnabled () { 
                return true;
            }
    };
    
    private JFrame f;
    private JMenuBar M;
    private JMenu m;
    private JMenuItem i;
    private JTable t = new JTable(empModel);
    private JScrollPane s;
    private payrollTable pt;
    private JTabbedPane tp = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
    
    public GUI() {
        f = new JFrame("Employee Attendance");
        f.setSize(1280, 720);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(tp);
    }
    public void show() {
        menuBar();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public void menuBar() {
        f.setVisible(true);
        
        M = new JMenuBar();
        
        m = new JMenu("File");
        i = new JMenuItem("Add pane");
        i.addActionListener((ActionEvent e) -> {
            addTab();
        });
        m.add(i);
        
        i = new JMenuItem("Add Employee");
        i.addActionListener((ActionEvent e) -> {
            addEmp();
        });
        m.add(i);
        m.addSeparator();
        
        i = new JMenuItem("Save");
        i.addActionListener((ActionEvent e) -> {
            empSave();
        });
        m.add(i);
        
        M.add(m);
        f.setJMenuBar(M);
    }
    
    public void addTab() {
        s = new JScrollPane(t);
        t.setAutoCreateRowSorter(true);
        
        tp.addTab("filename", s);
    }
    
    public void addRow(String id, String name, double salary, LocalDate hireDate){
        e = new employee(id, name, salary, hireDate);
        Object arr[] = {e.getId(), e.getName(), e.getSalary(), e.getHireDate()};
        empModel.addRow(arr);
    }
    
    public void addEmp() {
        JFrame f1 = new JFrame("Add Employee");
        JPanel p = new JPanel(); 
        LocalDate date = LocalDate.now();
        
        JLabel l1 = new JLabel("ID");
        JLabel l2 = new JLabel("Name");
        JLabel l3 = new JLabel("Salary");
        JLabel l4 = new JLabel("Hire Date");
                
        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField salary = new JTextField();
        JTextField day = new JTextField("dd");
        JTextField month = new JTextField("mm");
        JTextField year = new JTextField("yyyy");
        
        JButton add = new JButton("ADD EMPLOYEE");
        add.setEnabled(false);
        
        DocumentListener d = new DocumentListener(){ 
            
            @Override
            public void changedUpdate(DocumentEvent e) {
              Empty();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
              Empty();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
              Empty();
            }

            public void Empty() {
                if (id.getText().trim().isBlank() || name.getText().trim().isBlank() || salary.getText().trim().isBlank() || day.getText().trim().isBlank() || month.getText().trim().isBlank() || year.getText().trim().isBlank()) {
                    add.setEnabled(false);
                } else { 
                    add.setEnabled(true);
                }
            }
        };
        
        id.getDocument().addDocumentListener(d); 
        name.getDocument().addDocumentListener(d);
        salary.getDocument().addDocumentListener(d);
        day.getDocument().addDocumentListener(d);
        month.getDocument().addDocumentListener(d);
        year.getDocument().addDocumentListener(d);
        
        
         GroupLayout Group = new GroupLayout(p); 
            Group.setAutoCreateGaps(true);
            Group.setAutoCreateContainerGaps(true);
            p.setLayout(Group);
            Group.setHorizontalGroup(
                Group.createSequentialGroup()
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(l1)
                        .addComponent(l2)
                        .addComponent(l3)
                        .addComponent(l4))
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(id)   
                        .addComponent(name)
                        .addComponent(salary)
                        .addGroup(Group.createSequentialGroup()
                            .addGroup(Group.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(day))
                            .addGroup(Group.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(month))
                            .addGroup(Group.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(year))
                        )
                        .addComponent(add))
                    
                        
            );
            Group.setVerticalGroup(
                Group.createSequentialGroup()
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(l1)
                        .addComponent(id))
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.BASELINE)    
                        .addComponent(l2)
                        .addComponent(name))
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.BASELINE) 
                        .addComponent(l3)
                        .addComponent(salary))
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.BASELINE) 
                        .addComponent(l4)
                        .addComponent(day)
                        .addComponent(month)
                        .addComponent(year))
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.CENTER) 
                        .addComponent(add))
            );
            
            
            add.addActionListener((ActionEvent e) -> {
                int check = idExists(id.getText());
                int confirm = -1;
                LocalDate dateAdd;
                
                if (check != -1) {
                    confirm = JOptionPane.showConfirmDialog(f, "This ID is already assigned to " + empModel.getValueAt(check, 1) + ".\n Replace the entry?", "Lost and Found", JOptionPane.YES_NO_OPTION);
                } else {
                    try {
                        dateAdd = time.dateConv(day.getText(), month.getText(), year.getText());
                        addRow(id.getText(), name.getText(), Double.parseDouble(salary.getText()), dateAdd);
                        f1.setVisible(false);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(f, "Please enter a number for salary.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (DateTimeException ex) {
                            JOptionPane.showMessageDialog(f, "Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                switch (confirm) {
                    case -1: 
                        break;
                    case 0:
                        try {
                            dateAdd = time.dateConv(day.getText(), month.getText(), year.getText());
                            empModel.setValueAt(dateAdd, check, 3); 
                            empModel.setValueAt(Double.parseDouble(salary.getText()), check, 2); 
                            empModel.setValueAt(name.getText(), check, 1); 
                            f1.setVisible(false);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(f, "Please enter a number for salary.", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (DateTimeException ex) {
                            JOptionPane.showMessageDialog(f, "Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
                
                
            });
            p.setLayout(Group);
            f1.add(p);
            f1.setSize(300, 200);
            f1.setLocationRelativeTo(f);
            f1.setVisible(true);
    }
    
    public int idExists(String id) {
        for (int j = 0; j < empModel.getRowCount(); j++) {
                if (empModel.getValueAt(j, 0).equals(id.trim())) {
                    return j;
                }
        }
        return -1;
    }
    
    public void empSave() {
        List<String[]>  data = new ArrayList<>();
        data.add(e.getEmpHeader());
        for (int j = 0; j < empModel.getRowCount(); j++) {
            data.add(new String[]{String.valueOf(empModel.getValueAt(j, 0)), String.valueOf(empModel.getValueAt(j, 1)), String.valueOf(empModel.getValueAt(j, 2)), String.valueOf(empModel.getValueAt(j, 3))});
        }
        e.empSave(data);
    }
}
