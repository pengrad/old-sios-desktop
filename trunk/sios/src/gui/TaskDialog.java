/*
 * TaskDialog.java
 *
 * Created on 20.06.2010, 21:03:18
 */
package gui;

import model.manager.OptionsManager;
import model.manager.SpecialityManager;
import model.model.Speciality;
import model.model.Task;
import start.TestImg;
import model.viewmodel.SpecQualifyComboBoxModel;
import model.viewmodel.*;
import model.viewmodel.SpinnerMinutesModel;
import model.viewmodel.SpinnerEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * @author Стас
 */
public class TaskDialog extends javax.swing.JDialog {

    public static final String NEW = "Создание задачи";
    public static final String EDIT = "Изменение задачи";

    private boolean result;
    private SpinnerMinutesModel modelTime;
    private SpecialityComboBoxModel modelSpec;
    private SpecQualifyComboBoxModel modelSpecQual;

    public TaskDialog(Frame parent, boolean modal, SpecialityManager specManager, OptionsManager options, boolean helpText) {
        super(parent, modal);
        initComponents();
        initModels(specManager, options);
        if (!helpText) pRoot.remove(pHelpText);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pRoot = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        taskSpec = new javax.swing.JComboBox();
        taskTime = new javax.swing.JSpinner();
        taskName = new javax.swing.JTextField();
        taskSpecType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pHelpText = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textHelpTasks = new javax.swing.JEditorPane();

        setTitle("Изменение задачи");
        setIconImage(null);
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        pRoot.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pRoot.setLayout(new java.awt.BorderLayout(2, 2));

        bSave.setText("Сохранить");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });
        jPanel1.add(bSave);

        bCancel.setText("Отмена");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });
        jPanel1.add(bCancel);

        pRoot.add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Специализация:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        taskSpec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                taskSpecItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(taskSpec, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(taskTime, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(taskName, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(taskSpecType, gridBagConstraints);

        jLabel3.setText("Нагрузка:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel2.setText("Сложность:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel1.setText("Название задачи:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        pRoot.add(jPanel2, java.awt.BorderLayout.CENTER);

        pHelpText.setBackground(TestImg.getInstance().getColorInfPanel());
        pHelpText.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.orange));
        pHelpText.setPreferredSize(new java.awt.Dimension(10, 70));
        pHelpText.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(textHelpTasks);

        pHelpText.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pRoot.add(pHelpText, java.awt.BorderLayout.NORTH);

        getContentPane().add(pRoot, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-419)/2, (screenSize.height-321)/2, 419, 321);
    }// </editor-fold>//GEN-END:initComponents

    private void initModels(SpecialityManager specManager, OptionsManager options) {
        modelSpec = new SpecialityComboBoxModel(specManager);
        modelSpecQual = new SpecQualifyComboBoxModel(specManager);
        modelTime = new SpinnerMinutesModel(options);
        taskTime.setModel(modelTime);
        taskTime.setEditor(new SpinnerEditor(taskTime));
        taskSpec.setModel(modelSpec);
        taskSpecType.setModel(modelSpecQual);
    }

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        if (taskName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Введите имя задачи!", "Пустое имя", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (modelSpecQual.getSelectedSpecQualify() == null) {
            JOptionPane.showMessageDialog(this, "Задайте квалификацию задачи!", "Нет квалификации", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        result = true;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = false;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    private void taskSpecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_taskSpecItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Speciality spec = modelSpec.getSelectedSpeciality();
            if (spec != null) loadSpecQualifies(spec);
        }
    }//GEN-LAST:event_taskSpecItemStateChanged

    public void loadSpecQualifies(Speciality spec) {
        modelSpecQual.setSpeciality(spec);
        if (taskSpecType.getItemCount() > 0) taskSpecType.setSelectedIndex(0);
        else taskSpecType.setSelectedItem(null);
    }

    public Task showDialog(final Task task) {
        this.setLocationRelativeTo(getParent());
        result = false;
        if (task == null) { //новая задача
            this.setTitle(NEW);
        } else { // редактируем
            this.setTitle(EDIT);
        }
        setOptionsFromTask(task);
        this.setVisible(true);
        if (result) {
            return new Task(taskName.getText(), modelTime.getMinutes(), modelSpecQual.getSelectedSpecQualify());
        } else {
            return null;
        }
    }

    private void setDefaultOptions() {
        taskName.setText("");
        taskTime.setValue(60);
        if (taskSpec.getItemCount() > 0) taskSpec.setSelectedIndex(0);
        else taskSpec.setSelectedItem(null);
        modelSpecQual.setSpeciality(modelSpec.getSelectedSpeciality());
        if (taskSpecType.getItemCount() > 0) taskSpecType.setSelectedIndex(0);
        else taskSpecType.setSelectedItem(null);
    }

    private void setOptionsFromTask(Task task) {
        if (task == null) {
            setDefaultOptions();
            return;
        }
        taskName.setText(task.getNameOfTask());
        modelTime.setMinutes(task.getTimeInMinutes());
        modelSpec.setSelectedItem(task.getMinSpecQualify().getSpeciality());
        modelSpecQual.setSpeciality(task.getMinSpecQualify().getSpeciality());
        modelSpecQual.setSelectedItem(task.getMinSpecQualify());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                TaskDialog dialog = new TaskDialog(new javax.swing.JFrame(), true, new SpecialityManager(), new OptionsManager(), true);
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
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pHelpText;
    private javax.swing.JPanel pRoot;
    private javax.swing.JTextField taskName;
    private javax.swing.JComboBox taskSpec;
    private javax.swing.JComboBox taskSpecType;
    private javax.swing.JSpinner taskTime;
    private javax.swing.JEditorPane textHelpTasks;
    // End of variables declaration//GEN-END:variables
}
