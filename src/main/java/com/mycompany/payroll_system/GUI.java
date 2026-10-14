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
        @Override
            public Class getColumnClass(int Index) { 
                Class c = String.class;
                if (Index == 3) {
                    return Boolean.class;
                }
                return c;
            }
    };
    
    private JFrame f;
    private JMenuBar M;
    private JMenu m;
    private JMenu subm;
    private JMenuItem i;
    private JTable t = new JTable(empModel);
    private JScrollPane s;
    private payrollTable pt;
    private JTabbedPane tp = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
    JPopupMenu p;
    
    public GUI() {
        f = new JFrame("Employee Attendance");
        f.setSize(1280, 720);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(tp);
    }
    public void show() {
        menuBar();
        addTab(e.getFile());
        empLoad();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public void menuBar() {
        f.setVisible(true);
        
        M = new JMenuBar();
        
        m = new JMenu("File");
        subm = new JMenu("Manage Employees");
        m.add(subm);
        
        i = new JMenuItem("Add Employee");
        i.addActionListener((ActionEvent e) -> {
            addEmp();
        });
        subm.add(i);
        subm.addSeparator();
        
        i = new JMenuItem("Save Employee File");
        i.addActionListener((ActionEvent e) -> {
            empSave();
        });
        subm.add(i);
        
        i = new JMenuItem("Load Employee File");
        i.addActionListener((ActionEvent e) -> {
            empLoad();
        });
        subm.add(i);
        
        M.add(m);
        f.setJMenuBar(M);
    }
    
    public  void popUpMenu(){
        p = new JPopupMenu();       
        
        i = new JMenuItem("Delete");
        i.addActionListener ((ActionEvent e) -> {
            delRow();
        });
        p.add(i);
        
        t.addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed(MouseEvent e) {
                Show(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                Show(e);
            }
            private void Show(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    p.show(e.getComponent(),
                    e.getX(), e.getY());
                }
            }
        });
    }
    
    public void addTab(String filename) {
        s = new JScrollPane(t);
        t.setAutoCreateRowSorter(true);
        
        tp.addTab(filename, s);
    }
    
    public void delRow() {
        if (t.getSelectedRow() != -1) {
            empModel.removeRow(t.convertRowIndexToModel(t.getSelectedRow()));
        } else {
            
        }  
    } 
    
    public void addRowEmp(String id, String name, double salary, boolean isManager, LocalDate hireDate){
        e = new employee(id, name, salary, isManager , hireDate);
        Object arr[] = {e.getId(), e.getName(), e.getSalary(), e.getManager(), e.getHireDate()};
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
        JCheckBox isManager = new JCheckBox("Is this employee a Manager?");
        
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
                        .addComponent(isManager)
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
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.BASELINE) 
                        .addComponent(isManager))
                    .addGroup(Group.createParallelGroup(GroupLayout.Alignment.LEADING) 
                        .addComponent(add))
            );
            
            
            add.addActionListener((ActionEvent e) -> {
                int check = idExists(id.getText());
                int confirm = -1;
                LocalDate dateAdd;
                
                if (check != -1) {
                    confirm = JOptionPane.showConfirmDialog(f, "This ID is already assigned to " + empModel.getValueAt(check, 1) + ".\n Replace the entry?", "ID Conflict", JOptionPane.YES_NO_OPTION);
                } else {
                    try {
                        dateAdd = time.dateConv(day.getText(), month.getText(), year.getText());
                        addRowEmp(id.getText(), name.getText(), Double.parseDouble(salary.getText()), isManager.isSelected() , dateAdd);
                        f1.setVisible(false);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(f, "Please enter a number for salary.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (DateTimeException ex) {
                            JOptionPane.showMessageDialog(f, "Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                switch (confirm) {
                    case 0:
                        try {
                            dateAdd = time.dateConv(day.getText(), month.getText(), year.getText());
                            empModel.setValueAt(dateAdd, check, 4); 
                            empModel.setValueAt(isManager.isSelected(), check, 3); 
                            empModel.setValueAt(Double.parseDouble(salary.getText()), check, 2); 
                            empModel.setValueAt(name.getText(), check, 1);
                            f1.setVisible(false);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(f, "Please enter a number for salary.", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (DateTimeException ex) {
                            JOptionPane.showMessageDialog(f, "Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    default:
                        break;
                }
                
                
            });
            p.setLayout(Group);
            f1.add(p);
            f1.setSize(300, 220);
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
            data.add(new String[]{String.valueOf(empModel.getValueAt(j, 0)), 
                                  String.valueOf(empModel.getValueAt(j, 1)), 
                                  String.valueOf(empModel.getValueAt(j, 2)), 
                                  String.valueOf(empModel.getValueAt(j, 3)),
                                  String.valueOf(empModel.getValueAt(j, 4))});
        }
        e.empSave(data);
    }
    
    public void empLoad() {
        List<String[]> data = e.empLoad();
        int confirm = 0;
        
        if (empModel.getRowCount() != 0) {
            confirm = JOptionPane.showConfirmDialog(f, "This will discard ALL UNSAVED CHANGES. \nContinue loading?", "Load", JOptionPane.YES_NO_OPTION);    
        }
        
        switch (confirm) {
            case 0:
                empModel.setRowCount(0);
                for (String[] data1 : data) {
                    addRowEmp(data1[0], data1[1], Double.parseDouble(data1[2]), Boolean.parseBoolean(data1[3]), LocalDate.parse(data1[4]));
                }
                break;
            default:
                break;
        }
        
    }
}
