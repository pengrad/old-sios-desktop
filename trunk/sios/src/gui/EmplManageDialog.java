/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmplManageDialog.java
 *
 * Created on 16.12.2010, 17:19:47
 */

package gui;

import model.manager.EmployeeManager;
import model.manager.SpecialityManager;
import model.model.Executor;
import model.model.SpecQualify;
import model.model.Speciality;
import model.viewmodel.SpecialityComboBoxModel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * @author parshin
 */
public class EmplManageDialog extends javax.swing.JDialog {

    private boolean result;
    private EmployeeManager emplManager;
    private SpecialityComboBoxModel modelSpec;

    // для неотображения текущего екзекутора (самого себяч )в списке подчиненных
    private Executor currentExecutor;

    public EmplManageDialog(java.awt.Frame parent, boolean modal, SpecialityManager specManager, EmployeeManager employeeManager) {
        super(parent, modal);
        initComponents();
        emplManager = employeeManager;
        modelSpec = new SpecialityComboBoxModel(specManager);
        listSpec.setModel(modelSpec);
        listEmpls.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    panelSpec.removeAll();
                    Executor executor = (Executor) listEmpls.getSelectedItem();
                    if (executor != null) {
                        if (executor.getSpecQualifies().size() > 0) {
                            for (SpecQualify sq : executor.getSpecQualifies()) {
                                panelSpec.add(new JLabel(sq.getSpeciality().getNameOfSpeciality()));
                            }
                        } else {
                            panelSpec.add(new JLabel("<Специализации не заданы>"));
                        }
                    }
                }
            }
        });        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listEmpls = new javax.swing.JComboBox();
        checkSpec = new javax.swing.JCheckBox();
        listSpec = new javax.swing.JComboBox();
        panelSpec = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Выбор подчиненного");
        setResizable(false);

        bSave.setText("ОК");
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

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel4.setMinimumSize(new java.awt.Dimension(163, 63));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Исполнитель:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel4.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanel4.add(listEmpls, gridBagConstraints);

        checkSpec.setText("Специализация:");
        checkSpec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkSpecItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 5);
        jPanel4.add(checkSpec, gridBagConstraints);

        listSpec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listSpecItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 10);
        jPanel4.add(listSpec, gridBagConstraints);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        panelSpec.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Специализации", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 51, 255)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)))); // NOI18N
        panelSpec.setLayout(new javax.swing.BoxLayout(panelSpec, javax.swing.BoxLayout.PAGE_AXIS));
        getContentPane().add(panelSpec, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-376)/2, (screenSize.height-169)/2, 376, 169);
    }// </editor-fold>//GEN-END:initComponents

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        result = true;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = false;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    private void listSpecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listSpecItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED && listSpec.isEnabled()) {
            loadExecutors(modelSpec.getSelectedSpeciality());
        }
    }//GEN-LAST:event_listSpecItemStateChanged

    private void checkSpecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkSpecItemStateChanged
        listSpec.setEnabled(checkSpec.isSelected());
        if (checkSpec.isSelected()) {
            loadExecutors(modelSpec.getSelectedSpeciality());
        } else {
            loadExecutors(null);
        }
    }//GEN-LAST:event_checkSpecItemStateChanged

    public Executor showDialog(Executor executor) {
        this.setLocationRelativeTo(getParent());
        currentExecutor = executor;
        panelSpec.removeAll();
        checkSpec.setSelected(false);
        listSpec.setEnabled(false);
        loadExecutors(null);
        pack();
        setSize(400, getHeight());
        result = false;
        this.setVisible(true);
        if (result) {
            return (Executor) listEmpls.getSelectedItem();
        } else {
            return null;
        }
    }

    public void loadExecutors(Speciality speciality) {
        ArrayList<Executor> executors;
        listEmpls.removeAllItems();
        if (speciality == null) executors = emplManager.getFreeExecutors();
        else executors = emplManager.getFreeExecutorsBySpeciality(speciality);
        // удаляем самого себя из списка свободных исполнителей
        if (currentExecutor != null) executors.remove(currentExecutor);
        for (Executor executor : executors) {
            listEmpls.addItem(executor);
        }
        if (listEmpls.getItemCount() > 0) listEmpls.setSelectedIndex(0);
        else listEmpls.setSelectedItem(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmplManageDialog dialog = new EmplManageDialog(new javax.swing.JFrame(), true, new SpecialityManager(), new EmployeeManager());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.showDialog(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSave;
    private javax.swing.JCheckBox checkSpec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JComboBox listEmpls;
    private javax.swing.JComboBox listSpec;
    private javax.swing.JPanel panelSpec;
    // End of variables declaration//GEN-END:variables

}
