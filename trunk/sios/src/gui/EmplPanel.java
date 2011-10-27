package gui;

import static model.manager.AbstractTableModelProvider.*;

import gui.listener.TableMouseListener;
import manager.AnalysManager;
import manager.GUIManager;
import model.manager.Controller;
import model.util.Helper;
import model.viewmodel.EmplTableModel;
import model.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

/**
 * @author parshin
 */
public class EmplPanel extends javax.swing.JPanel {

    private GUIManager manager;
    private EmplTableModel model;
    private EmplTableModel modelNew;
    private EmplDialog emplDialog;
    private JPopupMenu popupMenu;
    private MouseAdapter mouseTableListener;
    private MouseAdapter mouseTableNewListener;

    public EmplPanel(GUIManager manager, boolean isEditable, boolean isShowNewPanel) {
        initComponents();
        init(manager, isEditable, isShowNewPanel);
    }

    public void init(GUIManager manager, boolean isEditable, boolean isShowNewPanel) {
        // инициализация
        this.manager = manager;
        if (emplDialog == null) emplDialog = new EmplDialog(manager.getRootFrame(), true, manager);
        if (model == null) {
            model = new EmplTableModel(new ExecutorProvider(DATA_OLD));
            tableEmpls.setModel(model);
        }
        if (modelNew == null) {
            modelNew = new EmplTableModel(new ExecutorProvider(DATA_NEW));
            tableNewEmpls.setModel(modelNew);
        }
        if (popupMenu == null) popupMenu = new EmplPopupMenu();
        if (mouseTableListener == null)
            mouseTableListener = new TableMouseListener(tableEmpls, popupMenu, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    editSelectedExecutor();
                }
            });
        if (mouseTableNewListener == null)
            mouseTableNewListener = new TableMouseListener(tableNewEmpls, popupMenu, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    editSelectedExecutor();
                }
            });
        Helper.setLabelToSplitPane(jSplitPane1, "Новые исполнители");
        tableEmpls.setDefaultRenderer(Object.class, new EmplRenderer());
        this.remove(jSplitPane1);
        this.remove(jScrollPane1);
        // Обновление
        if (isEditable) {
            tableEmpls.addMouseListener(mouseTableListener);
            //tableNewEmpls.addMouseListener(mouseTableNewListener);
            if (!this.isAncestorOf(pButtonsControl)) this.add(pButtonsControl, BorderLayout.NORTH);
        } else {
            tableEmpls.removeMouseListener(mouseTableListener);
            //tableNewEmpls.removeMouseListener(mouseTableNewListener);
            initRenameAction();
            this.remove(pButtonsControl);
        }
        if (isShowNewPanel) {
            if (!this.isAncestorOf(jSplitPane1)) this.add(jSplitPane1, BorderLayout.CENTER);
            jSplitPane1.setDividerLocation(300);
        } else {
            if (!this.isAncestorOf(jScrollPane1)) this.add(jScrollPane1, BorderLayout.CENTER);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pButtonsControl = new javax.swing.JPanel();
        bAddEmpl = new javax.swing.JButton();
        bDelEmpl = new javax.swing.JButton();
        bEditEmpl = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmpls = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableNewEmpls = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pButtonsControl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        bAddEmpl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/addSmall.png"))); // NOI18N
        bAddEmpl.setToolTipText("Добавить исполнителя");
        bAddEmpl.setBorderPainted(false);
        bAddEmpl.setContentAreaFilled(false);
        bAddEmpl.setDefaultCapable(false);
        bAddEmpl.setFocusPainted(false);
        bAddEmpl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bAddEmpl.setIconTextGap(0);
        bAddEmpl.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bAddEmpl.setPreferredSize(new java.awt.Dimension(45, 45));
        bAddEmpl.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/addBigPressed.png"))); // NOI18N
        bAddEmpl.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/addBig.png"))); // NOI18N
        bAddEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddEmplActionPerformed(evt);
            }
        });
        pButtonsControl.add(bAddEmpl);

        bDelEmpl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeSmall.png"))); // NOI18N
        bDelEmpl.setToolTipText("Удалить исполнителя");
        bDelEmpl.setBorderPainted(false);
        bDelEmpl.setContentAreaFilled(false);
        bDelEmpl.setFocusPainted(false);
        bDelEmpl.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bDelEmpl.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeBigPressed.png"))); // NOI18N
        bDelEmpl.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeBig.png"))); // NOI18N
        bDelEmpl.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeBigPressed.png"))); // NOI18N
        bDelEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelEmplActionPerformed(evt);
            }
        });
        pButtonsControl.add(bDelEmpl);

        bEditEmpl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editSmall.png"))); // NOI18N
        bEditEmpl.setToolTipText("Редактировать исполнителя");
        bEditEmpl.setBorderPainted(false);
        bEditEmpl.setContentAreaFilled(false);
        bEditEmpl.setFocusPainted(false);
        bEditEmpl.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bEditEmpl.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editBigPressed.png"))); // NOI18N
        bEditEmpl.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editBig.png"))); // NOI18N
        bEditEmpl.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editBigPressed.png"))); // NOI18N
        bEditEmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditEmplActionPerformed(evt);
            }
        });
        pButtonsControl.add(bEditEmpl);

        add(pButtonsControl, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.setDividerSize(20);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        tableEmpls.setAutoCreateRowSorter(true);
        tableEmpls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableEmpls);

        jSplitPane1.setLeftComponent(jScrollPane1);

        tableNewEmpls.setAutoCreateRowSorter(true);
        tableNewEmpls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableNewEmpls);

        jSplitPane1.setRightComponent(jScrollPane2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void initRenameAction() {

    }

    private void bAddEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddEmplActionPerformed
        addExecutor();
    }//GEN-LAST:event_bAddEmplActionPerformed

    private void bDelEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelEmplActionPerformed
        deleteSelectedExecutor();
    }//GEN-LAST:event_bDelEmplActionPerformed

    private void bEditEmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditEmplActionPerformed
        editSelectedExecutor();
    }//GEN-LAST:event_bEditEmplActionPerformed

    public void addExecutor() {
        Executor executor = emplDialog.showDialog(null);
        if (executor != null) {
            manager.addExecutor(executor);
        }
    }

    public void deleteSelectedExecutor() {
        Executor exec = getSelectedExecutor();
        if (exec == null) return;
        manager.removeExecutor(exec);
    }

    public void editSelectedExecutor() {
        Executor executor = getSelectedExecutor();
        if (executor == null) return;
        Executor newExecutor = emplDialog.showDialog(executor);
        if (newExecutor != null) {
            manager.changeExecutor(executor.getIdExecutor(), newExecutor);
        }
    }

    public Executor getSelectedExecutor() {
        int index = tableEmpls.getSelectedRow();
        if (index >= 0) {
            index = tableEmpls.convertRowIndexToModel(index);
            return model.getExecutorAtRow(index);
        } else return null;
    }

    class EmplRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value.toString());
            if (Controller.get().isAnalys()) {
                if (column == 5 && !AnalysManager.isMakeTaskExecutor(model.getExecutorAtRow(row))) {
                    setFont(new Font("Arial", Font.BOLD, 12));
                    setForeground(Color.BLUE);
                } else if (column == 4 && AnalysManager.isOutOfTimeExecutor(model.getExecutorAtRow(row))) {
                    setFont(new Font("Arial", Font.BOLD, 12));
                    setForeground(Color.RED);
                } else {
                    setFont(new Font("Arial", Font.PLAIN, 12));
                    setForeground(table.getForeground());
                }
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                } else {
                    setBackground(table.getBackground());
                }
            } else {
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
            }
            return this;
        }
    }

    public class EmplPopupMenu extends JPopupMenu {
        public EmplPopupMenu() {
            super();
            this.add(new AbstractAction("Изменить исполнителя") {
                public void actionPerformed(ActionEvent e) {
                    editSelectedExecutor();
                }
            });
            this.add(new AbstractAction("Удалить исполнителя") {
                public void actionPerformed(ActionEvent e) {
                    deleteSelectedExecutor();
                }
            });
            this.add(new JSeparator());
            this.add(new AbstractAction("Новый исполнитель") {
                public void actionPerformed(ActionEvent e) {
                    addExecutor();
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddEmpl;
    private javax.swing.JButton bDelEmpl;
    private javax.swing.JButton bEditEmpl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel pButtonsControl;
    private javax.swing.JTable tableEmpls;
    private javax.swing.JTable tableNewEmpls;
    // End of variables declaration//GEN-END:variables
}
