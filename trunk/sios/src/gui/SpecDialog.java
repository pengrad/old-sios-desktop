package gui;

import model.manager.SpecialityManager;
import model.manager.TaskManager;
import model.model.SpecQualify;
import model.model.Speciality;
import start.DataManager;
import start.TestImg;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author parshin
 */
public class SpecDialog extends javax.swing.JDialog {

    public static final String NEW = "Новая специализация";
    public static final String EDIT = "Изменение специализации";

    public static final int OK = 1;
    public static final int CANCEL = 0;

    private int result = CANCEL;
    private TaskManager taskManager;
    private SpecialityManager specManager;
    private DefaultListModel modelSQ;
    private SpecQualDialog dialogSpecQual;

    public SpecDialog(Frame parent, boolean modal, TaskManager taskManager, SpecialityManager specManager, boolean helpText) {
        super(parent, modal);
        this.taskManager = taskManager;
        this.specManager = specManager;
        dialogSpecQual = new SpecQualDialog(parent, true);
        initComponents();
        initList();
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
        specName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bAdd = new javax.swing.JButton();
        bDel = new javax.swing.JButton();
        bRename = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        bUp = new javax.swing.JButton();
        bDown = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSQ = new javax.swing.JList();
        pHelpText = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout(2, 2));

        pRoot.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pRoot.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        jPanel2.add(specName, gridBagConstraints);

        jLabel1.setText("Название специализации:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        bAdd.setText("Добавить");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });
        jPanel3.add(bAdd);

        bDel.setText("Удалить");
        bDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelActionPerformed(evt);
            }
        });
        jPanel3.add(bDel);

        bRename.setText("Переименовать");
        bRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRenameActionPerformed(evt);
            }
        });
        jPanel3.add(bRename);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel2.add(jPanel3, gridBagConstraints);

        jLabel2.setText("Список квалификаций:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel2.add(jLabel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.BorderLayout(5, 0));

        jPanel5.setLayout(new java.awt.GridBagLayout());

        bUp.setText("Вверх");
        bUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel5.add(bUp, gridBagConstraints);

        bDown.setText("Вниз");
        bDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDownActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
        jPanel5.add(bDown, gridBagConstraints);

        jPanel4.add(jPanel5, java.awt.BorderLayout.EAST);

        listSQ.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listSQ);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel2.add(jPanel4, gridBagConstraints);

        pRoot.add(jPanel2, java.awt.BorderLayout.CENTER);

        pHelpText.setBackground(TestImg.getInstance().getColorInfPanel());
        pHelpText.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));
        pHelpText.setPreferredSize(new java.awt.Dimension(10, 70));

        jLabel3.setText("<текст>");
        pHelpText.add(jLabel3);

        pRoot.add(pHelpText, java.awt.BorderLayout.NORTH);

        getContentPane().add(pRoot, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-414)/2, (screenSize.height-475)/2, 414, 475);
    }// </editor-fold>//GEN-END:initComponents

    public void initList() {
        modelSQ = new DefaultListModel();
        listSQ.setModel(modelSQ);
        listSQ.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                l.setText("<html><font size=4 color=#bbbbbb>" + (index + 1) + "</font>&nbsp&nbsp&nbsp " + l.getText() + "</html>");
                return l;
            }
        });
    }

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        if (specName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Введите имя специализации!", "Пустое имя", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        result = OK;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = CANCEL;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        SpecQualify newSQ = dialogSpecQual.showDialog(null);
        if (newSQ != null) {
            modelSQ.addElement(newSQ);
        }
    }//GEN-LAST:event_bAddActionPerformed

    private void bDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelActionPerformed
        SpecQualify sq = (SpecQualify) listSQ.getSelectedValue();
        if (sq != null) {
            if (taskManager.hasTasksBySpecQualify(sq)) {
                int res = JOptionPane.showConfirmDialog(this, "При сохранении вместе с квалификацией будут удалены задачи, вы уверены?", "Внимание!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    ((DefaultListModel) listSQ.getModel()).removeElement(sq);
                }
            } else {
                ((DefaultListModel) listSQ.getModel()).removeElement(sq);
            }
        }
    }//GEN-LAST:event_bDelActionPerformed

    private void bRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRenameActionPerformed
        SpecQualify sq = (SpecQualify) listSQ.getSelectedValue();
        if (sq != null) {
            SpecQualify newSQ = dialogSpecQual.showDialog(sq);
            if (newSQ != null) sq.setNameOfQualify(newSQ.getNameOfQualify());
        }
        listSQ.updateUI();
    }//GEN-LAST:event_bRenameActionPerformed

    private void bUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpActionPerformed
        int i = listSQ.getSelectedIndex();
        if (i <= 0) return;
        Object o = listSQ.getSelectedValue();
        modelSQ.removeElement(o);
        modelSQ.add(i - 1, o);
        listSQ.setSelectedValue(o, true);
    }//GEN-LAST:event_bUpActionPerformed

    private void bDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDownActionPerformed
        int i = listSQ.getSelectedIndex();
        if (i == -1 || i == modelSQ.getSize() - 1) return;
        Object o = listSQ.getSelectedValue();
        modelSQ.removeElement(o);
        modelSQ.add(i + 1, o);
        listSQ.setSelectedValue(o, true);
    }//GEN-LAST:event_bDownActionPerformed

    public int showDialog(Speciality speciality) {
        setLocationRelativeTo(getParent());
        result = CANCEL;
        setOptions(speciality);
        setVisible(true);
        return result;
    }

    public void setOptions(Speciality speciality) {
        modelSQ.removeAllElements();
        if (speciality == null) {
            setTitle(NEW);
            specName.setText("");
        } else {
            setTitle(EDIT);
            specName.setText(speciality.getNameOfSpeciality());
            for (SpecQualify sq : specManager.getSpecQualifies(speciality)) {
                modelSQ.addElement(sq);
            }
        }

    }

    public Speciality getSpeciality() {
        return new Speciality(specName.getText());
    }

    public ArrayList<SpecQualify> getSpecQualifies() {
        ArrayList<SpecQualify> list = new ArrayList<SpecQualify>(modelSQ.getSize());
        for (int i = 0; i < modelSQ.getSize(); i++) {
            list.add((SpecQualify) modelSQ.getElementAt(i));
        }
        return list;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SpecDialog dialog = new SpecDialog(new javax.swing.JFrame(), true, new DataManager().getTaskManager(), new DataManager().getSpecManager(), true);
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
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bDel;
    private javax.swing.JButton bDown;
    private javax.swing.JButton bRename;
    private javax.swing.JButton bSave;
    private javax.swing.JButton bUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listSQ;
    private javax.swing.JPanel pHelpText;
    private javax.swing.JPanel pRoot;
    private javax.swing.JTextField specName;
    // End of variables declaration//GEN-END:variables

}
