/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmplDialog.java
 *
 * Created on 10.12.2010, 17:00:35
 */

package gui;

import manager.GUIManager;
import model.manager.EmployeeManager;
import model.manager.SpecialityManager;
import model.manager.TaskManager;
import model.model.*;
import model.util.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author parshin
 */
public class EmplDialog extends javax.swing.JDialog {

    public static final String NEW = "Заведение нового исполнителя";
    public static final String EDIT = "Изменение исполнителя";

    public static final String EMPL_TYPE_EXECUTOR = "Исполнительный элемент";
    public static final String EMPL_TYPE_MANAGER = "Управляющий элемент";

    public static final String EMPL_NOT_MANAGER = "Исполнитель содержит задачи по управлению, но не является менеджером. Задачи по управлению будут удалены, продолжить?";

    private boolean result;
    private TableSpecModel modelSpec;
    private TableTaskModel modelTask;
    private TableEmplModel modelEmpl;
    private EmplSpecDialog dialogSpec;
    private EmplTaskDialog dialogTask;
    private EmplManageDialog dialogManage;

    // сохраняем текущего исполнителя для передачи во внутренний диалог выбора подчиненных
    private Executor currentExecutor;
    private GUIManager manager;

    public EmplDialog(java.awt.Frame parent, boolean modal, GUIManager manager) {
        super(parent, modal);
        this.manager = manager;
        initComponents();
        initModels(parent, manager.getSpecManager(), manager.getTaskManager(), manager.getEmplManager());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        panelGeneral = new javax.swing.JPanel();
        emplName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        emplTypeExecutor = new javax.swing.JComboBox();
        panelSpec = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableSpec = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        bAddSpec = new javax.swing.JButton();
        bDelSpec = new javax.swing.JButton();
        bAddManageSQ = new javax.swing.JButton();
        panelTask = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableTask = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelSumTime = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        bAddTask = new javax.swing.JButton();
        bDelTask = new javax.swing.JButton();
        panelManage = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableEmpl = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        bAddEmpl = new javax.swing.JButton();
        bDelEmpl = new javax.swing.JButton();
        labelTitle = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        bSaveEmpl = new javax.swing.JButton();
        bCancelEmpl = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Общие свойства", "Специализации", "Исполнительные задачи", "Управленческие задачи" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jPanel8.add(jScrollPane3, java.awt.BorderLayout.WEST);

        jPanel9.setLayout(new java.awt.BorderLayout());

        panelCard.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102))));
        panelCard.setLayout(new java.awt.CardLayout());

        jLabel6.setText("Имя исполнителя:");

        jLabel2.setText("Тип исполнителя:");

        emplTypeExecutor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                emplTypeExecutorItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelGeneralLayout = new javax.swing.GroupLayout(panelGeneral);
        panelGeneral.setLayout(panelGeneralLayout);
        panelGeneralLayout.setHorizontalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emplName, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emplTypeExecutor, 0, 418, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelGeneralLayout.setVerticalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emplName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(emplTypeExecutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        panelCard.add(panelGeneral, "Общие свойства");

        panelSpec.setLayout(new java.awt.BorderLayout(5, 0));

        tableSpec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Специализация", "Квалификация"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableSpec);

        panelSpec.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        bAddSpec.setText("Добавить");
        bAddSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddSpecActionPerformed(evt);
            }
        });
        jPanel12.add(bAddSpec);

        bDelSpec.setText("Удалить");
        bDelSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelSpecActionPerformed(evt);
            }
        });
        jPanel12.add(bDelSpec);

        bAddManageSQ.setText("Сделать менеджером");
        bAddManageSQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddManageSQActionPerformed(evt);
            }
        });
        jPanel12.add(bAddManageSQ);

        panelSpec.add(jPanel12, java.awt.BorderLayout.SOUTH);

        panelCard.add(panelSpec, "Специализации");

        panelTask.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        tableTask.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Задача", "Специализация", "Нагрузка"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tableTask);

        jPanel1.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel1.setText("Общая нагрузка:");
        jPanel2.add(jLabel1);

        labelSumTime.setText("0");
        jPanel2.add(labelSumTime);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        panelTask.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        bAddTask.setText("Добавить");
        bAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddTaskActionPerformed(evt);
            }
        });
        jPanel13.add(bAddTask);

        bDelTask.setText("Удалить");
        bDelTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelTaskActionPerformed(evt);
            }
        });
        jPanel13.add(bDelTask);

        panelTask.add(jPanel13, java.awt.BorderLayout.SOUTH);

        panelCard.add(panelTask, "Исполнительные задачи");

        panelManage.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        tableEmpl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Задача", "Специализация", "Нагрузка"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tableEmpl);

        jPanel3.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        panelManage.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        bAddEmpl.setText("Добавить");
        bAddEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddEmplActionPerformed(evt);
            }
        });
        jPanel14.add(bAddEmpl);

        bDelEmpl.setText("Удалить");
        bDelEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelEmplActionPerformed(evt);
            }
        });
        jPanel14.add(bDelEmpl);

        panelManage.add(jPanel14, java.awt.BorderLayout.SOUTH);

        panelCard.add(panelManage, "Управленческие задачи");

        jPanel9.add(panelCard, java.awt.BorderLayout.CENTER);

        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("Общие свойства исполнителя");
        labelTitle.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1), javax.swing.BorderFactory.createTitledBorder("")));
        jPanel9.add(labelTitle, java.awt.BorderLayout.NORTH);

        jPanel8.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        bSaveEmpl.setText("Сохранить");
        bSaveEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveEmplActionPerformed(evt);
            }
        });
        jPanel15.add(bSaveEmpl);

        bCancelEmpl.setText("Отмена");
        bCancelEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelEmplActionPerformed(evt);
            }
        });
        jPanel15.add(bCancelEmpl);

        jPanel8.add(jPanel15, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel8, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initModels(Frame parent, SpecialityManager specManager, TaskManager taskManager, EmployeeManager employeeManager) {
        modelSpec = new TableSpecModel();
        modelTask = new TableTaskModel();
        emplTypeExecutor.addItem(EMPL_TYPE_EXECUTOR);
        emplTypeExecutor.addItem(EMPL_TYPE_MANAGER);
        modelTask.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                labelSumTime.setText(Helper.getMinutesToString(modelTask.getSumTime()));
                if (modelTask.getSumTime() > manager.getOptionsManager().getMaxTime()) {
                    labelSumTime.setForeground(Color.red);
                } else {
                    labelSumTime.setForeground(Color.black);
                }
            }
        });
        modelSpec.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                boolean isManager = modelSpec.hasSpecQualify(manager.getSpecManager().getManageQualify());
                if (isManager) {
                    bAddManageSQ.setEnabled(false);
                    emplTypeExecutor.setSelectedItem(EMPL_TYPE_MANAGER);
                } else {
                    bAddManageSQ.setEnabled(true);
                    emplTypeExecutor.setSelectedItem(EMPL_TYPE_EXECUTOR);
                }

            }
        });
        modelEmpl = new TableEmplModel();
        tableSpec.setModel(modelSpec);
        tableTask.setModel(modelTask);
        tableEmpl.setModel(modelEmpl);
        dialogSpec = new EmplSpecDialog(parent, true, specManager);
        dialogTask = new EmplTaskDialog(parent, true, taskManager);
        dialogManage = new EmplManageDialog(parent, true, specManager, employeeManager);
    }

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {
        ((CardLayout) panelCard.getLayout()).show(panelCard, jList2.getSelectedValue().toString());
        labelTitle.setText(jList2.getSelectedValue().toString() + " исполнителя");
    }

    private void bSaveEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveEmplActionPerformed
        if (emplName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Введите имя исполнителя!", "Пустое имя", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
//        if (modelEmpl.getSumTime() + modelTask.getSumTime() > manager.getOptionsManager().getMaxTime()) {
//            JOptionPane.showMessageDialog(this, "Нагрузка на исполнителя превышает максимальную норму в "
//                    + Helper.getMinutesToString(manager.getOptionsManager().getMaxTime()), "Большая нагрузка", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
        if ((!modelSpec.hasSpecQualify(manager.getSpecManager().getManageQualify())) && modelEmpl.getRowCount() > 0) {
            int res = JOptionPane.showConfirmDialog(this, EMPL_NOT_MANAGER, "Внимание!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.CANCEL_OPTION) return;
        }
        result = true;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveEmplActionPerformed

    private void bCancelEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelEmplActionPerformed
        result = false;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelEmplActionPerformed

    private void bAddSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddSpecActionPerformed
        SpecQualify sq = dialogSpec.showDialog();
        if (sq != null) modelSpec.addSpecQualify(sq);
    }//GEN-LAST:event_bAddSpecActionPerformed

    private void bDelSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelSpecActionPerformed
        int index = tableSpec.getSelectedRow();
        if (index >= 0) {
            index = tableSpec.convertRowIndexToModel(index);
//            if (modelTask.hasTasksBySpeciality(modelSpec.getSpecQualifyByRow(index).getSpeciality())) {
//                int res = JOptionPane.showConfirmDialog(this, "Вместе со специализацией будут удалены задачи, вы уверены?", "Внимание!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//                if (res == JOptionPane.OK_OPTION) {
//                    modelTask.removeBySpeciality(modelSpec.getSpecQualifyByRow(index).getSpeciality());
//                    modelSpec.deleteRow(index);
//                }
//            } else {
                modelSpec.deleteRow(index);
//            }
        }
    }//GEN-LAST:event_bDelSpecActionPerformed

    private void bAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddTaskActionPerformed
        // Выбираем все специализации и квалификаци
        ArrayList<SpecQualify> specQualifies = new ArrayList<SpecQualify>();
        HashMap<Speciality, ArrayList<SpecQualify>> map = manager.getSpecManager().getSpecialities();
        Collection<Speciality> keys = map.keySet();
        for (Speciality spec : keys) {
            for (SpecQualify specQualify : map.get(spec))
                specQualifies.add(specQualify);
        }
        Task task = dialogTask.showDialog(manager.getTaskManager().getSpecQualifiesHavingFreeTasks(specQualifies, modelTask.getTasks()), modelTask.getTasks());
        if (task != null) modelTask.addTask(task);
    }//GEN-LAST:event_bAddTaskActionPerformed

    private void bDelTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelTaskActionPerformed
        int index = tableTask.getSelectedRow();
        if (index >= 0) {
            index = tableTask.convertRowIndexToModel(index);
            modelTask.deleteRow(index);
        }
    }//GEN-LAST:event_bDelTaskActionPerformed

    private void bAddEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddEmplActionPerformed
        if (!modelSpec.hasSpecQualify(manager.getSpecManager().getManageQualify())) {
            int res = JOptionPane.showConfirmDialog(this, "Исполнитель не может иметь подчиненных, не обладая специализацией менеджера!\nСделать исполнителя менеджером?", "Отсутствует специализация менеджера", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.CANCEL_OPTION) return;
            else modelSpec.addSpecQualify(manager.getSpecManager().getManageQualify());
        }
        Executor exec = dialogManage.showDialog(currentExecutor);
        if (exec != null) {
            modelEmpl.addExecutor(exec);
        }
    }//GEN-LAST:event_bAddEmplActionPerformed

    private void bDelEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelEmplActionPerformed
        int index = tableEmpl.getSelectedRow();
        if (index >= 0) {
            index = tableEmpl.convertRowIndexToModel(index);
            modelEmpl.deleteRow(index);
        }
    }//GEN-LAST:event_bDelEmplActionPerformed

    private void bAddManageSQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddManageSQActionPerformed
        SpecQualify manageSQ = manager.getSpecManager().getManageQualify();
        if (!modelSpec.hasSpecQualify(manageSQ)) {
            modelSpec.addSpecQualify(manageSQ);
        }
    }//GEN-LAST:event_bAddManageSQActionPerformed

    private void emplTypeExecutorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_emplTypeExecutorItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (emplTypeExecutor.getSelectedItem().equals(EMPL_TYPE_MANAGER)) {
                modelSpec.addSpecQualify(manager.getSpecManager().getManageQualify());
            } else {
                modelSpec.removeSpecQaulify(manager.getSpecManager().getManageQualify());
            }
        }
    }//GEN-LAST:event_emplTypeExecutorItemStateChanged

    public Executor showDialog(final Executor executor) {
        return showDialog(executor, false);
    }

    public Executor showDialog(final Executor executor, boolean isManager) {
        this.setLocationRelativeTo(getParent());
        jList2.setSelectedIndex(0);
        jList2ValueChanged(new ListSelectionEvent(jList2, 0, 0, true));
        result = false;
        setOptionsFromExecutor(executor, isManager);
        this.setVisible(true);
        if (result) {
            SpecQualify manageSQ = manager.getSpecManager().getManageQualify();
            ArrayList<Task> tasks = modelTask.getTasks();
            ArrayList<SpecQualify> sq = modelSpec.getSq();
            if (sq.contains(manageSQ)) {
                for (Executor exec : modelEmpl.getExecutors()) {
                    tasks.add(new ManageTask(manager.getOptionsManager().getManageTaskTime(), manageSQ, exec));
                }
            }
            return new Executor(emplName.getText(), sq, tasks);
        } else {
            return null;
        }
    }

    public void setOptionsFromExecutor(Executor executor, boolean isManager) {
        currentExecutor = executor;
        if (executor == null) {
            setDefaultOptions(isManager);
            return;
        }
        this.setTitle(EDIT);
        emplName.setText(executor.getNameOfExecutor());
        modelSpec.setSq(executor.getSpecQualifies());
        modelTask.setTasks(executor.getExecTasks());
        ArrayList<Executor> executors = new ArrayList<Executor>();
        for (ManageTask manageTask : executor.getManageTasks()) {
            executors.add(manageTask.getManagedExecutor());
        }
        SpecQualify manageQualify = manager.getSpecManager().getManageQualify();
        if (isManager && !modelSpec.hasSpecQualify(manageQualify)) modelSpec.addSpecQualify(manageQualify);
        modelEmpl.setExecutors(executors);
    }

    public void setDefaultOptions(boolean isManager) {
        this.setTitle(NEW);
        emplName.setText("");
        modelSpec.removeAll();
        modelTask.removeAll();
        modelEmpl.removeAll();
        if (isManager) modelSpec.addSpecQualify(manager.getSpecManager().getManageQualify());
    }

    private class TableSpecModel extends AbstractTableModel {
        private ArrayList<SpecQualify> sq;
        private String headers[] = new String[]{"Специализация", "Квалификация"};

        private TableSpecModel() {
            sq = new ArrayList<SpecQualify>(0);
        }

        public ArrayList<SpecQualify> getSq() {
            return sq;
        }

        public void setSq(ArrayList<SpecQualify> sq) {
            this.sq = sq;
            this.fireTableDataChanged();
        }

        public SpecQualify getSpecQualifyByRow(int row) {
            return sq.get(row);
        }

        public void removeAll() {
            sq = new ArrayList<SpecQualify>(0);
            this.fireTableDataChanged();
        }

        public void deleteRow(int row) {
            sq.remove(row);
            this.fireTableDataChanged();
        }

        public void addSpecQualify(SpecQualify specQualify) {
            if (sq.contains(specQualify)) return;
            for (SpecQualify s : sq) {
                if (s.getSpeciality().equals(specQualify.getSpeciality())) {
                    SpecQualify newSQ = manager.getSpecManager().getBestSpecQualify(s, specQualify);
                    if (newSQ.equals(s)) {
                        this.fireTableDataChanged();
                        return;
                    } else {
                        sq.remove(s);
                        sq.add(newSQ);
                        this.fireTableDataChanged();
                        return;
                    }
                }
            }
            sq.add(specQualify);
            this.fireTableDataChanged();
        }

        public void removeSpecQaulify(SpecQualify specQualify) {
            if (sq.remove(specQualify)) this.fireTableDataChanged();

        }

        public int getRowCount() {
            return sq.size();
        }

        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public String getColumnName(int column) {
            return headers[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            SpecQualify s = sq.get(rowIndex);
            if (s == null) return null;
            switch (columnIndex) {
                case 0:
                    return s.getSpeciality().getNameOfSpeciality();
                case 1:
                    return s.getNameOfQualify();
                default:
                    return null;
            }
        }

        public boolean hasSpecQualify(SpecQualify sq) {
            return (this.sq != null && this.sq.contains(sq));
        }
    }

    private class TableTaskModel extends AbstractTableModel {
        private ArrayList<Task> tasks;
        private String headers[] = new String[]{"Задача", "Специализация", "Нагрузка"};

        private TableTaskModel() {
            tasks = new ArrayList<Task>(0);
        }

        public ArrayList<Task> getTasks() {
            return tasks;
        }

        public void setTasks(ArrayList<Task> tasks) {
            this.tasks = tasks;
            this.fireTableDataChanged();
        }

        public int getSumTime() {
            int sumTime = 0;
            for (Task task : getTasks()) {
                sumTime += task.getTimeInMinutes();
            }
            return sumTime;
        }

        public boolean hasTasksBySpeciality(Speciality speciality) {
            for (Task task : getTasks()) {
                if (task.getMinSpecQualify().getSpeciality().equals(speciality)) return true;
            }
            return false;
        }

        public void removeBySpeciality(Speciality speciality) {
            tasks.removeAll(manager.getTaskManager().getAllTasksBySpeciality(speciality));
            this.fireTableDataChanged();
        }

        public void removeAll() {
            tasks = new ArrayList<Task>(0);
            this.fireTableDataChanged();
        }

        public void deleteRow(int row) {
            tasks.remove(row);
            this.fireTableDataChanged();
        }

        public Task getTaskByRow(int row) {
            return tasks.get(row);
        }

        public void addTask(Task task) {
            if (tasks.contains(task)) return;
            tasks.add(task);
            this.fireTableDataChanged();
        }

        public int getRowCount() {
            return tasks.size();
        }

        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public String getColumnName(int column) {
            return headers[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Task task = tasks.get(rowIndex);
            if (task == null) return null;
            switch (columnIndex) {
                case 0:
                    return task.getNameOfTask();
                case 1:
                    return task.getMinSpecQualify().getNameOfQualify();
                case 2:
                    return Helper.getMinutesToString(task.getTimeInMinutes());
                default:
                    return null;
            }
        }
    }

    private class TableEmplModel extends AbstractTableModel {
        private ArrayList<Executor> executors;
        private String headers[] = new String[]{"Исполнитель", "Специализации"};

        private TableEmplModel() {
            executors = new ArrayList<Executor>(0);
        }

        public ArrayList<Executor> getExecutors() {
            return executors;
        }

        public void setExecutors(ArrayList<Executor> executors) {
            this.executors = executors;
            fireTableDataChanged();
        }

        public void removeAll() {
            executors = new ArrayList<Executor>(0);
            this.fireTableDataChanged();
        }

        public void deleteRow(int row) {
            executors.remove(row);
            this.fireTableDataChanged();
        }

        public Executor getExecutorByRow(int row) {
            return executors.get(row);
        }

        public void addExecutor(Executor executor) {
            if (executors.contains(executor)) return;
            executors.add(executor);
            this.fireTableDataChanged();
        }

        public int getRowCount() {
            return executors.size();
        }

        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public String getColumnName(int column) {
            return headers[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Executor executor = executors.get(rowIndex);
            if (executor == null) return null;
            switch (columnIndex) {
                case 0:
                    return executor.getNameOfExecutor();
                case 1:
                    return getStrSpecialitiesOfExecutor(executor);
                default:
                    return null;
            }
        }

        public String getStrSpecialitiesOfExecutor(Executor executor) {
            StringBuilder sb = new StringBuilder();
            for (Speciality spec : executor.getSpecialities()) {
                sb.append(spec.getNameOfSpeciality());
                sb.append(", ");
            }
            if (sb.length() == 0) sb.append("<Специальности не заданы>");
            else sb.delete(sb.length() - 2, sb.length());
            return sb.toString();
        }

        public int getSumTime() {
            return executors.size() * manager.getOptionsManager().getManageTaskTime();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmplDialog dialog = new EmplDialog(new javax.swing.JFrame(), true, new GUIManager());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddEmpl;
    private javax.swing.JButton bAddManageSQ;
    private javax.swing.JButton bAddSpec;
    private javax.swing.JButton bAddTask;
    private javax.swing.JButton bCancelEmpl;
    private javax.swing.JButton bDelEmpl;
    private javax.swing.JButton bDelSpec;
    private javax.swing.JButton bDelTask;
    private javax.swing.JButton bSaveEmpl;
    private javax.swing.JTextField emplName;
    private javax.swing.JComboBox emplTypeExecutor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelSumTime;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPanel panelManage;
    private javax.swing.JPanel panelSpec;
    private javax.swing.JPanel panelTask;
    private javax.swing.JTable tableEmpl;
    private javax.swing.JTable tableSpec;
    private javax.swing.JTable tableTask;
    // End of variables declaration//GEN-END:variables

}
