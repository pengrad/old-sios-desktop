/*
 * MainFrame.java
 *
 * Created on 25.08.2010, 1:10:35
 */

package gui;

import manager.GUIManager;
import model.manager.ModeManager;
import model.util.SIOSFileFilter;
import model.util.SIOSFileView;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Стас
 */
public class MainFrame extends javax.swing.JFrame {

    GUIManager manager;
    JFileChooser dialogFile;
    private OptionsDialog dialogOptions;
    private OptionsTemplateDialog dialogOptionsTemplate;

    private HashMap<Integer, JRadioButtonMenuItem> modeButtons;
    private ItemListener cbModeListener;

    public MainFrame(GUIManager manager) {
        this.manager = manager;
        dialogFile = new JFileChooser();
        dialogFile.setFileFilter(new SIOSFileFilter());
        dialogFile.setFileView(new SIOSFileView());
        dialogOptions = new OptionsDialog(this, true);
        dialogOptionsTemplate = new OptionsTemplateDialog(this, true);
        initComponents();
        initModels(manager.getModes());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pCont = new javax.swing.JPanel();
        panelDownStatus = new javax.swing.JPanel();
        labelStatus = new javax.swing.JLabel();
        panelToolbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbModes = new javax.swing.JComboBox();
        pButtonsControlAdd = new javax.swing.JPanel();
        bAlgoritm = new javax.swing.JButton();
        bFix = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNew = new javax.swing.JMenuItem();
        menuOptionsTime = new javax.swing.JMenuItem();
        menuOptionsTemplates = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuModes = new javax.swing.JMenu();
        menuSave = new javax.swing.JMenuItem();
        menuLoad = new javax.swing.JMenuItem();
        menuLoadTasks = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("СИОС - Конструктор");
        setIconImage(new javax.swing.ImageIcon(this.getClass().getResource("/res/icon2.gif")).getImage());

        pCont.setLayout(new javax.swing.BoxLayout(pCont, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(pCont, java.awt.BorderLayout.CENTER);

        panelDownStatus.setLayout(new java.awt.BorderLayout());

        labelStatus.setText(" ");
        panelDownStatus.add(labelStatus, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelDownStatus, java.awt.BorderLayout.SOUTH);

        panelToolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Режим:");
        panelToolbar.add(jLabel1);

        cbModes.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        panelToolbar.add(cbModes);

        pButtonsControlAdd.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 0));

        bAlgoritm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshSmall.png"))); // NOI18N
        bAlgoritm.setToolTipText("Обновить исполнителей");
        bAlgoritm.setBorder(null);
        bAlgoritm.setBorderPainted(false);
        bAlgoritm.setContentAreaFilled(false);
        bAlgoritm.setDefaultCapable(false);
        bAlgoritm.setFocusable(false);
        bAlgoritm.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshBigPress.png"))); // NOI18N
        bAlgoritm.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshBig.png"))); // NOI18N
        bAlgoritm.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/refreshBigPress.png"))); // NOI18N
        bAlgoritm.addActionListener(formListener);
        pButtonsControlAdd.add(bAlgoritm);

        bFix.setText("Закрепить");
        bFix.addActionListener(formListener);
        pButtonsControlAdd.add(bFix);

        panelToolbar.add(pButtonsControlAdd);

        getContentPane().add(panelToolbar, java.awt.BorderLayout.NORTH);

        jMenu1.setText("Файл");

        menuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNew.setText("Новый");
        menuNew.setMinimumSize(new java.awt.Dimension(247, 0));
        menuNew.setPreferredSize(new java.awt.Dimension(247, 24));
        menuNew.addActionListener(formListener);
        jMenu1.add(menuNew);

        menuOptionsTime.setText("Настройки времени");
        menuOptionsTime.addActionListener(formListener);
        jMenu1.add(menuOptionsTime);

        menuOptionsTemplates.setText("Настройки шаблонов");
        menuOptionsTemplates.addActionListener(formListener);
        jMenu1.add(menuOptionsTemplates);
        jMenu1.add(jSeparator1);

        menuModes.setBorder(null);
        menuModes.setText(" Режим");
        jMenu1.add(menuModes);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSave.setText("Сохранить модель");
        menuSave.addActionListener(formListener);
        jMenu1.add(menuSave);

        menuLoad.setText("Загрузить модель");
        menuLoad.addActionListener(formListener);
        jMenu1.add(menuLoad);

        menuLoadTasks.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menuLoadTasks.setText("Загрузить набор задач");
        menuLoadTasks.addActionListener(formListener);
        jMenu1.add(menuLoadTasks);
        jMenu1.add(jSeparator2);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        menuExit.setText("Выход...");
        menuExit.addActionListener(formListener);
        jMenu1.add(menuExit);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 679) / 2, (screenSize.height - 502) / 2, 679, 502);
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {
        }

        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == menuNew) {
                MainFrame.this.menuNewActionPerformed(evt);
            } else if (evt.getSource() == menuOptionsTime) {
                MainFrame.this.menuOptionsTimeActionPerformed(evt);
            } else if (evt.getSource() == menuOptionsTemplates) {
                MainFrame.this.menuOptionsTemplatesActionPerformed(evt);
            } else if (evt.getSource() == menuSave) {
                MainFrame.this.menuSaveActionPerformed(evt);
            } else if (evt.getSource() == menuLoad) {
                MainFrame.this.menuLoadActionPerformed(evt);
            } else if (evt.getSource() == menuLoadTasks) {
                MainFrame.this.menuLoadTasksActionPerformed(evt);
            } else if (evt.getSource() == menuExit) {
                MainFrame.this.menuExitActionPerformed(evt);
            } else if (evt.getSource() == bAlgoritm) {
                MainFrame.this.bAlgoritmActionPerformed(evt);
            } else if (evt.getSource() == bFix) {
                MainFrame.this.bFixActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void menuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewActionPerformed
        // todo предложить сохранить модель, если она не пустая.
        manager.newModel();
    }//GEN-LAST:event_menuNewActionPerformed

    private void menuOptionsTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOptionsTimeActionPerformed
        if (dialogOptions.showDialog(manager.getOptionsManager().getMaxTime(), manager.getOptionsManager().getManageTaskTime()) == OptionsDialog.OK) {
            manager.changeTimeOptions(dialogOptions.getMaxTime(), dialogOptions.getManageTime());
        }
    }//GEN-LAST:event_menuOptionsTimeActionPerformed

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed
        if (dialogFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = dialogFile.getSelectedFile();
            if (!f.getName().toUpperCase().endsWith(("" + SIOSFileFilter.FILE_END).toUpperCase())) {
                f = new File(f.getAbsolutePath() + "." + SIOSFileFilter.FILE_END);
                try {
                    f.createNewFile();
                } catch (Exception e) {
                    // todo обработать ошибку
                    e.printStackTrace();
                    return;
                }
            }
            // todo проверить нет ли такого файла уже. Предложить переписать.
            try {
                manager.getSerializableManager().saveModel(f);
            } catch (Exception e) {
                // todo обработать ошибку
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_menuSaveActionPerformed

    private void menuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoadActionPerformed
        if (dialogFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // todo предложить сохранить текущую модель, если она не пустая
            try {
                manager.getSerializableManager().loadModel(dialogFile.getSelectedFile());
            } catch (Exception e) {
                // todo обработать ошибку
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_menuLoadActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        // todo предложить сохранить модель если она не пустая.
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuLoadTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoadTasksActionPerformed
        if (dialogFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // todo предложить сохранить текущую модель, если она не пустая
            try {
                manager.getSerializableManager().loadModelTasks(dialogFile.getSelectedFile());
            } catch (Exception e) {
                // todo обработать ошибку
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_menuLoadTasksActionPerformed

    private void menuOptionsTemplatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOptionsTemplatesActionPerformed
        if (dialogOptionsTemplate.showDialog(manager.getTemplatesNames(), manager.getOptionsManager().getTemplateManager()) == OptionsDialog.OK) {
            manager.changeSpecTemplate(dialogOptionsTemplate.getSpecTemplate());
        }
    }//GEN-LAST:event_menuOptionsTemplatesActionPerformed

    private void bAlgoritmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAlgoritmActionPerformed
//        if(isSynthesOnlyNew) manager.synthesExecutorsByNewTasks();
        manager.synthesExecutors();
    }//GEN-LAST:event_bAlgoritmActionPerformed

    private void bFixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFixActionPerformed
        manager.fixNewTasks();
    }//GEN-LAST:event_bFixActionPerformed

    public void initModels(ModeManager.Mode[] modes) {
        menuModes.removeAll();
        modeButtons = new HashMap<Integer, JRadioButtonMenuItem>(modes.length);
        cbModes.removeAllItems();
        for (final ModeManager.Mode mode : modes) {
            JRadioButtonMenuItem b = new JRadioButtonMenuItem(new AbstractAction(mode.getName()) {
                public void actionPerformed(ActionEvent e) {
                    manager.setMode(mode);
                }
            });
            modeButtons.put(mode.getId(), b);
            buttonGroup1.add(b);
            menuModes.add(b);
            b.setSelected(true);
            cbModes.addItem(mode);
        }
        cbModeListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    manager.setMode((ModeManager.Mode) e.getItem());
                }
            }
        };
        cbModes.addItemListener(cbModeListener);
        manager.addModeObserver(new Observer() {
            public void update(Observable o, Object arg) {
                ModeManager.Mode m = (ModeManager.Mode) arg;
                setMode(m);
            }
        });
    }

    public void setMode(ModeManager.Mode mode) {
        cbModes.removeItemListener(cbModeListener);
        cbModes.setSelectedItem(mode);
        cbModes.addItemListener(cbModeListener);
        modeButtons.get(mode.getId()).setSelected(true);
        setContentMode(manager.getPaneByMode(mode), "СИОС - " + mode.getName());
    }

    public void setContentMode(JComponent contentPane, String title) {
        pCont.removeAll();
        setTitle(title);
        pCont.add(contentPane);
        pCont.revalidate();
        pCont.repaint();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(new GUIManager()).setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bAlgoritm;
    protected javax.swing.JButton bFix;
    protected javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JComboBox cbModes;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JPopupMenu.Separator jSeparator1;
    protected javax.swing.JPopupMenu.Separator jSeparator2;
    protected javax.swing.JLabel labelStatus;
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JMenuItem menuExit;
    protected javax.swing.JMenuItem menuLoad;
    protected javax.swing.JMenuItem menuLoadTasks;
    protected javax.swing.JMenu menuModes;
    protected javax.swing.JMenuItem menuNew;
    protected javax.swing.JMenuItem menuOptionsTemplates;
    protected javax.swing.JMenuItem menuOptionsTime;
    protected javax.swing.JMenuItem menuSave;
    protected javax.swing.JPanel pButtonsControlAdd;
    public javax.swing.JPanel pCont;
    protected javax.swing.JPanel panelDownStatus;
    protected javax.swing.JPanel panelToolbar;
    // End of variables declaration//GEN-END:variables

}
