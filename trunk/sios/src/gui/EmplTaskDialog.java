package gui;

import model.manager.TaskManager;
import model.model.SpecQualify;
import model.model.Task;

import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * @author parshin
 */
public class EmplTaskDialog extends javax.swing.JDialog {


    private boolean result;
    private TaskManager taskManager;

    // уже задействованные таски, но еще не сохраненные как занятые
    private ArrayList<Task> usedTasks;

    public EmplTaskDialog(java.awt.Frame parent, boolean modal, TaskManager taskManager) {
        super(parent, modal);
        this.taskManager = taskManager;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        listSpecQ = new javax.swing.JComboBox();
        listTask = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Выбор задачи");

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

        listSpecQ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listSpecQItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(listSpecQ, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(listTask, gridBagConstraints);

        jLabel2.setText("Задача:");
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
        setBounds((screenSize.width-356)/2, (screenSize.height-126)/2, 356, 126);
    }// </editor-fold>//GEN-END:initComponents

    private void listSpecQItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listSpecQItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Object o = listSpecQ.getSelectedItem();
            if (o != null) loadTasksForSQ(((SpecialitySQ) o).specQualify);
        }
    }//GEN-LAST:event_listSpecQItemStateChanged

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        result = true;
        this.setVisible(false);
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        result = false;
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    // список специализаций по которым предлагать свободные задачи, и задачи которые исключить (уже выбраны но не сохранены)
    public Task showDialog(ArrayList<SpecQualify> sq, ArrayList<Task> usedTasks) {
        this.setLocationRelativeTo(getParent());
        result = false;
        this.usedTasks = usedTasks;
        setOptions(sq);
        this.setVisible(true);
        if (result) {
            return (Task) listTask.getSelectedItem();
        } else {
            return null;
        }
    }

    public void setOptions(ArrayList<SpecQualify> sq) {
        listSpecQ.removeAllItems();
        for (SpecQualify s : sq) {
            listSpecQ.addItem(new SpecialitySQ(s));
        }
        if (listSpecQ.getItemCount() == 0) {
            listSpecQ.setSelectedItem(null);
            listTask.removeAllItems();
            listTask.setSelectedItem(null);
        } else {
            listSpecQ.setSelectedIndex(0);
            loadTasksForSQ(((SpecialitySQ) listSpecQ.getSelectedItem()).specQualify);
        }
    }

    public void loadTasksForSQ(SpecQualify sq) {
        listTask.removeAllItems();
        for (Task task : taskManager.getFreeTasksForSpecQualify(sq)) {
            if (!usedTasks.contains(task)) listTask.addItem(task);
        }
        if (listTask.getItemCount() == 0) listTask.setSelectedItem(null);
        else listTask.setSelectedIndex(0);
    }

    private class SpecialitySQ {
        SpecQualify specQualify;

        private SpecialitySQ(SpecQualify specQualify) {
            this.specQualify = specQualify;
        }

        @Override
        public String toString() {
            return specQualify.getSpeciality() + " - " + specQualify;
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmplTaskDialog dialog = new EmplTaskDialog(new javax.swing.JFrame(), true, new TaskManager());
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
    private javax.swing.JComboBox listSpecQ;
    private javax.swing.JComboBox listTask;
    // End of variables declaration//GEN-END:variables

}
