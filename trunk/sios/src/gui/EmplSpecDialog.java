/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmplSpecDialog.java
 *
 * Created on 10.12.2010, 18:49:59
 */

package gui;

import model.manager.SpecialityManager;
import model.model.SpecQualify;
import model.model.Speciality;
import model.viewmodel.SpecQualifyComboBoxModel;
import model.viewmodel.SpecialityComboBoxModel;

import java.awt.event.ItemEvent;

/**
 * @author parshin
 */
public class EmplSpecDialog extends javax.swing.JDialog {

    private SpecialityComboBoxModel modelSpec;
    private SpecQualifyComboBoxModel modelSpecQual;

    private boolean result;

    public EmplSpecDialog(java.awt.Frame parent, boolean modal, SpecialityManager specialityManager) {
        super(parent, modal);
        initComponents();
        modelSpec = new SpecialityComboBoxModel(specialityManager);
        modelSpecQual = new SpecQualifyComboBoxModel(specialityManager);
        spec.setModel(modelSpec);
        specQualify.setModel(modelSpecQual);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        spec = new javax.swing.JComboBox();
        specQualify = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setTitle("Выбор специализации");

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Специализация:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        spec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                specItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(spec, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(specQualify, gridBagConstraints);

        jLabel2.setText("Квалификация:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-344)/2, (screenSize.height-131)/2, 344, 131);
    }// </editor-fold>//GEN-END:initComponents

    private void specItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_specItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Speciality spec = modelSpec.getSelectedSpeciality();
            if (spec != null) loadSpecQualifies(spec);
        }
    }//GEN-LAST:event_specItemStateChanged

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        result = true;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = false;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    public SpecQualify showDialog() {
        this.setLocationRelativeTo(getParent());
        result = false;
        if(spec.getItemCount() > 0) spec.setSelectedIndex(0);
        else spec.setSelectedItem(null);
        this.setVisible(true);
        if (result) {
            return modelSpecQual.getSelectedSpecQualify();
        } else {
            return null;
        }
    }

    public void loadSpecQualifies(Speciality speciality) {
        modelSpecQual.setSpeciality(speciality);
        if (specQualify.getItemCount() > 0) specQualify.setSelectedIndex(0);
        else specQualify.setSelectedItem(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmplSpecDialog dialog = new EmplSpecDialog(new javax.swing.JFrame(), true, new SpecialityManager());
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox spec;
    private javax.swing.JComboBox specQualify;
    // End of variables declaration//GEN-END:variables

}
