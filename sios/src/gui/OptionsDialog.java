/*
 * OptionsDialog.java
 *
 * Created on 19.12.2010, 22:34:18
 */

package gui;

import model.viewmodel.SpinnerEditor;
import model.viewmodel.SpinnerMinutesModel;
import model.manager.OptionsManager;

import javax.swing.*;
import java.util.Collection;

/**
 * @author Стас
 */
public class OptionsDialog extends javax.swing.JDialog {

    public static final int OK = 1;
    public static final int CANCEL = 0;

    private int result = CANCEL;

    private SpinnerMinutesModel modelMaxTime;
    private SpinnerMinutesModel modelManageTime;

    public OptionsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pack();
        modelManageTime = new SpinnerMinutesModel(120, 15, 120, 15);
        modelMaxTime = new SpinnerMinutesModel(480, 15, 480, 60);
        spinnerMaxTime.setModel(modelMaxTime);
        spinnerMaxTime.setEditor(new SpinnerEditor(spinnerMaxTime));
        spinnerManageTime.setModel(modelManageTime);
        spinnerManageTime.setEditor(new SpinnerEditor(spinnerManageTime));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel15 = new javax.swing.JPanel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spinnerMaxTime = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        spinnerManageTime = new javax.swing.JSpinner();
        listManagerTemplates = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Настройки");

        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        bSave.setText("Сохранить");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });
        jPanel15.add(bSave);

        bCancel.setText("Отмена");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });
        jPanel15.add(bCancel);

        getContentPane().add(jPanel15, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Предельная нагрузка элемента:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(spinnerMaxTime, gridBagConstraints);

        jLabel2.setText("Нагрузка по управленческой задаче:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel1.add(spinnerManageTime, gridBagConstraints);

        listManagerTemplates.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Управляющий обладает максимальными навыками", "Управляющий обладает минимальными навыками", "Управляющий не связан с исполнительными специализациями" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel1.add(listManagerTemplates, gridBagConstraints);

        jLabel3.setText("Исполнительные квалификации управляющего элемента:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-376)/2, (screenSize.height-204)/2, 376, 204);
    }// </editor-fold>//GEN-END:initComponents

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        if (getManageTime() * 2 > getMaxTime()) {
            JOptionPane.showMessageDialog(this, "Время на задачу по управлению не позволит управлять более чем одним исполнителем!" +
                    "\nУменьшите время управления или увеличьте максимальную нагрузку", "Большое время управления", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        result = OK;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = CANCEL;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    public int showDialog(Collection<String> specTemplates,OptionsManager options) {
        setLocationRelativeTo(getParent());
        modelManageTime.setMaxTime(options.getMaxTime());
        modelMaxTime.setMaxTime(options.getMaxTime() + 100*60);
        spinnerManageTime.setValue(options.getManageTaskTime());
        spinnerManageTime.getEditor().updateUI();
        spinnerMaxTime.setValue(options.getMaxTime());
        spinnerMaxTime.getEditor().updateUI();
        listManagerTemplates.removeAllItems();
        for(String template : specTemplates) {
            listManagerTemplates.addItem(template);
        }
        listManagerTemplates.setSelectedItem(options.getTemplateManager());
        result = CANCEL;
        setVisible(true);
        return result;
    }

    public int getMaxTime() {
        return modelMaxTime.getMinutes();
    }

    public int getManageTime() {
        return modelManageTime.getMinutes();
    }

    public String getSpecTemplate() {
        Object o = listManagerTemplates.getSelectedItem();
        return o == null ? null : o.toString();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OptionsDialog dialog = new OptionsDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JComboBox listManagerTemplates;
    private javax.swing.JSpinner spinnerManageTime;
    private javax.swing.JSpinner spinnerMaxTime;
    // End of variables declaration//GEN-END:variables

}
