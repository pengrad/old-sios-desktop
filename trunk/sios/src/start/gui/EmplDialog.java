/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmplDialog.java
 *
 * Created on 10.12.2010, 17:00:35
 */

package start.gui;

import gui.EmplManageDialog;
import gui.EmplSpecDialog;
import gui.EmplTaskDialog;
import model.manager.EmployeeManager;

import model.manager.SpecialityManager;
import model.manager.TaskManager;
import model.model.*;
import start.DataManager;
import start.TestImg;
import model.util.Helper;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

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
    private TableEmplModel modelEmpl;
    private EmplSpecDialog dialogSpec;
    private EmplTaskDialog dialogTask;
    private EmplManageDialog dialogManage;
    private TableTaskModel modelTask;


    // сохраняем текущего исполнителя для передачи во внутренний диалог выбора подчиненных
    private Executor currentExecutor;
    private DataManager manager;

    public EmplDialog(java.awt.Frame parent, boolean modal, DataManager manager) {
        super(parent, modal);
        this.manager = manager;
        initComponents();
        initModels(parent, manager.getSpecManager(), manager.getTaskManager(), manager.getEmplManager());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        panelGeneral = new javax.swing.JPanel();
        emplName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        emplTypeExecutor = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableSpec = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        bAddSpec = new javax.swing.JButton();
        bDelSpec = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        bSaveEmpl = new javax.swing.JButton();
        bCancelEmpl = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.BorderLayout(2, 2));

        panelCard.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelCard.setLayout(new javax.swing.BoxLayout(panelCard, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel6.setText("Имя исполнителя:");

        jLabel2.setText("Тип исполнителя:");

        emplTypeExecutor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                emplTypeExecutorItemStateChanged(evt);
            }
        });

        jPanel4.setLayout(new java.awt.BorderLayout());

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
        jScrollPane7.setViewportView(tableSpec);

        jPanel4.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        bAddSpec.setText("Добавить");
        bAddSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddSpecActionPerformed(evt);
            }
        });
        jPanel16.add(bAddSpec);

        bDelSpec.setText("Удалить");
        bDelSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelSpecActionPerformed(evt);
            }
        });
        jPanel16.add(bDelSpec);

        jPanel4.add(jPanel16, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout panelGeneralLayout = new javax.swing.GroupLayout(panelGeneral);
        panelGeneral.setLayout(panelGeneralLayout);
        panelGeneralLayout.setHorizontalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(emplName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emplTypeExecutor, javax.swing.GroupLayout.Alignment.LEADING, 0, 551, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCard.add(panelGeneral);

        jPanel9.add(panelCard, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(TestImg.getInstance().getColorInfPanel());
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));
        jPanel1.setMinimumSize(new java.awt.Dimension(57, 70));
        jPanel1.setPreferredSize(new java.awt.Dimension(575, 70));

        jLabel3.setText("<текст>");
        jPanel1.add(jLabel3);

        jPanel9.add(jPanel1, java.awt.BorderLayout.NORTH);

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
        emplTypeExecutor.addItem(EMPL_TYPE_EXECUTOR);
        emplTypeExecutor.addItem(EMPL_TYPE_MANAGER);
        modelEmpl = new TableEmplModel();
        modelTask = new TableTaskModel();

        tableSpec.setModel(modelSpec);
        dialogSpec = new EmplSpecDialog(parent, true, specManager);
        dialogTask = new EmplTaskDialog(parent, true, taskManager);
        dialogManage = new EmplManageDialog(parent, true, specManager, employeeManager);
    }


    private void bSaveEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveEmplActionPerformed
        if (emplName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Введите имя исполнителя!", "Пустое имя", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
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

    private void emplTypeExecutorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_emplTypeExecutorItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (emplTypeExecutor.getSelectedItem().equals(EMPL_TYPE_MANAGER)) {
                modelSpec.addSpecQualify(manager.getSpecManager().getManageQualify());
            } else {
                modelSpec.removeSpecQaulify(manager.getSpecManager().getManageQualify());
            }
        }
    }//GEN-LAST:event_emplTypeExecutorItemStateChanged

    private void bAddSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddSpecActionPerformed
        SpecQualify sq = dialogSpec.showDialog();
        if (sq != null) modelSpec.addSpecQualify(sq);
    }//GEN-LAST:event_bAddSpecActionPerformed

    private void bDelSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelSpecActionPerformed
        int index = tableSpec.getSelectedRow();
        if (index >= 0) {
            index = tableSpec.convertRowIndexToModel(index);
            if (modelTask.hasTasksBySpeciality(modelSpec.getSpecQualifyByRow(index).getSpeciality())) {
                int res = JOptionPane.showConfirmDialog(this, "Вместе со специализацией будут удалены задачи, вы уверены?", "Внимание!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    modelTask.removeBySpeciality(modelSpec.getSpecQualifyByRow(index).getSpeciality());
                    modelSpec.deleteRow(index);
                }
            } else {
                modelSpec.deleteRow(index);
            }
        }
    }//GEN-LAST:event_bDelSpecActionPerformed

    public Executor showDialog(final Executor executor) {
        return showDialog(executor, false);
    }

    public Executor showDialog(final Executor executor, boolean isManager) {
        this.setLocationRelativeTo(getParent());
        result = false;
        setOptionsFromExecutor(executor, isManager);
        this.setVisible(true);
        if (result) {
            ArrayList<SpecQualify> sq = modelSpec.getSq();
            ArrayList<Task> tasks = new ArrayList<Task>();
            if (executor != null) {
                tasks = executor.getAllTasks();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddSpec;
    private javax.swing.JButton bCancelEmpl;
    private javax.swing.JButton bDelSpec;
    private javax.swing.JButton bSaveEmpl;
    private javax.swing.JTextField emplName;
    private javax.swing.JComboBox emplTypeExecutor;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JTable tableSpec;
    // End of variables declaration//GEN-END:variables

}
