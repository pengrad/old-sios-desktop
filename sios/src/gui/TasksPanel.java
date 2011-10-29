package gui;

import manager.GUIManager;
import model.manager.AbstractTableModelProvider.*;
import model.manager.AbstractTableModelProvider;
import model.util.Helper;
import model.viewmodel.TaskTableModel;
import model.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TasksPanel extends JPanel {

    private GUIManager manager;
    private TaskDialog taskDialog;
    private TaskTableModel model;
    private TaskTableModel modelNew;
    private JPopupMenu popupMenu;
    private boolean isSynthesOnlyNew;

    private JTable currentTable;

    public TasksPanel(GUIManager manager, boolean isNewPanel, boolean isFixButton, boolean isSynthesButton) {
        initComponents();
        init(manager, isNewPanel, isFixButton, isSynthesButton);
    }

    public void init(GUIManager manager, boolean isNewPanel, boolean isFixButton, boolean isSynthesButton) {
        //инициализация
        this.manager = manager;
        if (taskDialog == null)
            taskDialog = new TaskDialog(manager.getRootFrame(), true, manager.getSpecManager(), manager.getOptionsManager(), false);
        if (model == null) {
            model = new TaskTableModel(new TaskProvider(AbstractTableModelProvider.DATA_OLD));
            tableTasks.setModel(model);
        }
        if (modelNew == null) {
            modelNew = new TaskTableModel(new TaskProvider(AbstractTableModelProvider.DATA_NEW));
            tableNewTasks.setModel(modelNew);
        }
        if (popupMenu == null) popupMenu = new TaskPopupMenu();
        pTasks.remove(pButtonsControl);
        //pNewTasks.remove(pButtonsControl);
        this.remove(jSplitPane1);
        this.remove(pTasks);
        if (isNewPanel) {
            if (!this.isAncestorOf(jSplitPane1)) this.add(jSplitPane1, BorderLayout.CENTER);
            //pNewTasks.add(pButtonsControl, BorderLayout.NORTH);
            JPanel root = new JPanel(new BorderLayout());
            JPanel pText = new JPanel();
            pText.add(new JLabel("Новые задачи"));
            root.add(pButtonsControl, BorderLayout.WEST);
            root.add(pText, BorderLayout.CENTER);
            pButtonsControl.setCursor(Cursor.getDefaultCursor());
            Helper.setLabelToSplitPane(jSplitPane1, "Новые задачи");
            Helper.setComponentToSplitPane(jSplitPane1, root);
            jSplitPane1.setDividerLocation(300);
            currentTable = tableNewTasks;
        } else {
            if (!this.isAncestorOf(pTasks)) this.add(pTasks, BorderLayout.CENTER);
            pTasks.add(pButtonsControl, BorderLayout.NORTH);
            currentTable = tableTasks;
        }
        bFixTasks.setVisible(isFixButton);
        bSynthes.setVisible(isSynthesButton);
        isSynthesOnlyNew = (isFixButton && isSynthesButton);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pButtonsControlAdd = new javax.swing.JPanel();
        bSynthes = new javax.swing.JButton();
        bFixTasks = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        pTasks = new javax.swing.JPanel();
        pButtonsControl = new javax.swing.JPanel();
        bAddTask = new javax.swing.JButton();
        bDelTask = new javax.swing.JButton();
        bEditTask = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTasks = new javax.swing.JTable();
        pNewTasks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableNewTasks = new javax.swing.JTable();

        FormListener formListener = new FormListener();

        pButtonsControlAdd.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 0));

        bSynthes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshSmall.png"))); // NOI18N
        bSynthes.setToolTipText("Обновить исполнителей");
        bSynthes.setBorder(null);
        bSynthes.setBorderPainted(false);
        bSynthes.setContentAreaFilled(false);
        bSynthes.setDefaultCapable(false);
        bSynthes.setFocusable(false);
        bSynthes.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshBigPress.png"))); // NOI18N
        bSynthes.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshBig.png"))); // NOI18N
        bSynthes.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshBigPress.png"))); // NOI18N
        bSynthes.addActionListener(formListener);
        pButtonsControlAdd.add(bSynthes);

        bFixTasks.setText("Закрепить");
        bFixTasks.addActionListener(formListener);
        pButtonsControlAdd.add(bFixTasks);

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.setDividerSize(20);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("zxcvzxc"); // NOI18N

        pTasks.setLayout(new java.awt.BorderLayout());

        pButtonsControl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        bAddTask.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/addSmall.png"))); // NOI18N
        bAddTask.setToolTipText("Добавить задачу");
        bAddTask.setBorderPainted(false);
        bAddTask.setContentAreaFilled(false);
        bAddTask.setDefaultCapable(false);
        bAddTask.setFocusPainted(false);
        bAddTask.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bAddTask.setIconTextGap(0);
        bAddTask.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bAddTask.setPreferredSize(new java.awt.Dimension(45, 45));
        bAddTask.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/addBigPressed.png"))); // NOI18N
        bAddTask.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/addBig.png"))); // NOI18N
        bAddTask.addActionListener(formListener);
        pButtonsControl.add(bAddTask);

        bDelTask.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeSmall.png"))); // NOI18N
        bDelTask.setToolTipText("Удалить задачу");
        bDelTask.setBorderPainted(false);
        bDelTask.setContentAreaFilled(false);
        bDelTask.setFocusPainted(false);
        bDelTask.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bDelTask.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeBigPressed.png"))); // NOI18N
        bDelTask.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeBig.png"))); // NOI18N
        bDelTask.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/removeBigPressed.png"))); // NOI18N
        bDelTask.addActionListener(formListener);
        pButtonsControl.add(bDelTask);

        bEditTask.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editSmall.png"))); // NOI18N
        bEditTask.setToolTipText("Редактировать задачу");
        bEditTask.setBorderPainted(false);
        bEditTask.setContentAreaFilled(false);
        bEditTask.setFocusPainted(false);
        bEditTask.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bEditTask.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editBigPressed.png"))); // NOI18N
        bEditTask.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editBig.png"))); // NOI18N
        bEditTask.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/editBigPressed.png"))); // NOI18N
        bEditTask.addActionListener(formListener);
        pButtonsControl.add(bEditTask);

        pTasks.add(pButtonsControl, java.awt.BorderLayout.NORTH);

        tableTasks.setAutoCreateRowSorter(true);
        tableTasks.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Задача", "Сложность (разряд)", "Нагрузка (чел.-час)", "Специализация"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableTasks.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tableTasks);

        pTasks.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(pTasks);

        pNewTasks.setLayout(new java.awt.BorderLayout());

        tableNewTasks.setAutoCreateRowSorter(true);
        tableNewTasks.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Задача", "Сложность (разряд)", "Нагрузка (чел.-час)", "Специализация"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableNewTasks.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableNewTasks);

        pNewTasks.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(pNewTasks);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {
        }

        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == bAddTask) {
                TasksPanel.this.bAddTaskActionPerformed(evt);
            } else if (evt.getSource() == bDelTask) {
                TasksPanel.this.bDelTaskActionPerformed(evt);
            } else if (evt.getSource() == bEditTask) {
                TasksPanel.this.bEditTaskActionPerformed(evt);
            } else if (evt.getSource() == bFixTasks) {
                TasksPanel.this.bFixTasksActionPerformed(evt);
            } else if (evt.getSource() == bSynthes) {
                TasksPanel.this.bSynthesActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void initTableActions(final JTable table) {
        final JPopupMenu popMenu = new JPopupMenu();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    //Event a;
                    Action aaa = null;
                    aaa.actionPerformed(null);
                    ActionEvent ae;
                    editSelectedTask();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int i = table.rowAtPoint(new Point(e.getX(), e.getY()));
                if (i >= 0 && i < table.getRowCount())
                    table.setRowSelectionInterval(i, i);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (table.contains(new Point(e.getX(), e.getY())))
                        popMenu.show(table, e.getX(), e.getY());
                }
            }
        });
    }

    private void bAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddTaskActionPerformed
        addTask();
    }//GEN-LAST:event_bAddTaskActionPerformed

    private void bDelTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelTaskActionPerformed
        deleteSelectedTask();
    }//GEN-LAST:event_bDelTaskActionPerformed

    private void bEditTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditTaskActionPerformed
        editSelectedTask();
    }//GEN-LAST:event_bEditTaskActionPerformed

    private void bFixTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFixTasksActionPerformed
        //manager.fixNewTasks();
    }//GEN-LAST:event_bFixTasksActionPerformed

    private void bSynthesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSynthesActionPerformed
//        if (isSynthesOnlyNew) manager.synthesExecutorsByNewTasks();
//        else manager.synthesExecutorsByTasks();
    }//GEN-LAST:event_bSynthesActionPerformed

    public void addTask() {
        Task task = taskDialog.showDialog(null);
        if (task != null) {
            if (task.getMinSpecQualify() == null || task.getNameOfTask() == null) return;
            task.setNew(isNewTask());
            manager.addTask(task);
        }
    }

    public void editSelectedTask() {
        Task task = getSelectedTask();
        if (task == null) return;
        Task newTask = taskDialog.showDialog(task);
        if (newTask == null) return;
        manager.changeTask(task.getIdTask(), newTask);
    }

    public void deleteSelectedTask() {
        Task task = getSelectedTask();
        if (task == null) return;
        manager.removeTask(task);
    }

    public Task getSelectedTask() {
        int index = currentTable.getSelectedRow();
        if (index >= 0) {
            index = currentTable.convertRowIndexToModel(index);
            return ((TaskTableModel) currentTable.getModel()).getTaskAt(index);
        } else return null;
    }

    public boolean isNewTask() {
        return currentTable == tableNewTasks;
    }

    public class TaskPopupMenu extends JPopupMenu {
        public TaskPopupMenu() {
            super();
            this.add(new AbstractAction("Изменить задачу") {
                public void actionPerformed(ActionEvent e) {
                    editSelectedTask();
                }
            });
            this.add(new AbstractAction("Удалить задачу") {
                public void actionPerformed(ActionEvent e) {
                    deleteSelectedTask();
                }
            });
            this.add(new JSeparator());
            this.add(new AbstractAction("Новая задача") {
                public void actionPerformed(ActionEvent e) {
                    addTask();
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddTask;
    private javax.swing.JButton bDelTask;
    private javax.swing.JButton bEditTask;
    private javax.swing.JButton bFixTasks;
    private javax.swing.JButton bSynthes;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel pButtonsControl;
    private javax.swing.JPanel pButtonsControlAdd;
    private javax.swing.JPanel pNewTasks;
    private javax.swing.JPanel pTasks;
    private javax.swing.JTable tableNewTasks;
    private javax.swing.JTable tableTasks;
    // End of variables declaration//GEN-END:variables
}
