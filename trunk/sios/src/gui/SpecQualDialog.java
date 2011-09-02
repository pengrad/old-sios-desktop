/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SpecQualDialog.java
 *
 * Created on 15.12.2010, 19:14:13
 */

package gui;

import model.model.SpecQualify;

import javax.swing.*;

/**
 *
 * @author parshin
 */
public class SpecQualDialog extends javax.swing.JDialog {

    public static final String NEW = "Новая квалификация";
    public static final String EDIT = "Изменение квалификации";

    private boolean result;

    public SpecQualDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        specName = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Название квалификации:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(specName, gridBagConstraints);

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
        setBounds((screenSize.width-368)/2, (screenSize.height-124)/2, 368, 124);
    }// </editor-fold>//GEN-END:initComponents

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        if (specName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Введите имя квалификации!", "Пустое имя", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        result = true;
        this.setVisible(false);
}//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = false;
        this.setVisible(false);
}//GEN-LAST:event_bCancelActionPerformed

    public SpecQualify showDialog(SpecQualify sq) {
        setLocationRelativeTo(getParent());
        setOptions(sq);
        setVisible(true);
        if(result) {
            return new SpecQualify(null, specName.getText());
        } else {
            return null;
        }
    }

    public void setOptions(SpecQualify sq) {
        if (sq == null) {
            setTitle(NEW);
            specName.setText("");
        } else {
            setTitle(EDIT);
            specName.setText(sq.getNameOfQualify());
        }

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SpecQualDialog dialog = new SpecQualDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField specName;
    // End of variables declaration//GEN-END:variables

}
