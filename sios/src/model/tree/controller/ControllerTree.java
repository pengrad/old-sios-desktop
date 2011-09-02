package model.tree.controller;


import model.model.Executor;
import model.model.Task;
import model.tree.ManagerInitTree;
import model.tree.dnd.CanvasDropTarget;
import model.tree.gui.*;
import model.tree.model.DefaulModelMyTree;
import model.tree.model.Figure;
import model.tree.model.Node;
import model.util.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 16.08.2010
 * Time: 16:53:26
 * To change this template use File | Settings | File Templates.
 */
public class ControllerTree implements MouseListener, MouseMotionListener, ActionListener, AWTEventListener {
    private ArrayList<EventTree> events = new ArrayList<EventTree>();
    private MyTree tree = null;
    private DefaulModelMyTree modelTree = null;
    private TreePanel treePanel = null;
    private Figure selectedFigure;

    private ConnectorNodes cNodes;
    private MoverNodes mNodes;
    //Попап Меню
    private JPopupMenu popupFigureMenu;
    private JMenuItem mDeleteFigure;
    private JMenuItem mEditFigure;

    // курсоры
    private Cursor cursorCurrent;

    //Информация о задаче
    private JPopupMenu popupInfTask;
    private pInfTask pInfTask;


    private int initParam;

    public ControllerTree(TreePanel treePanel, MyTree tree, int initParam) {
        this.treePanel = treePanel;
        this.tree = tree;
        this.modelTree = tree.getModel();
        cNodes = new ConnectorNodes(treePanel, tree, this);
        mNodes = new MoverNodes(treePanel, tree, this);
        //Инициализация курсоров
        cursorCurrent = new Cursor(Cursor.HAND_CURSOR);
        //Инициализация меню
        popupFigureMenu = new JPopupMenu();
        mDeleteFigure = new JMenuItem("Удалить");
        mEditFigure = new JMenuItem("Редактировать");
        popupFigureMenu.add(mDeleteFigure);
        popupFigureMenu.add(mEditFigure);
        //Информация о задаче
        popupInfTask = new JPopupMenu();
        pInfTask = new pInfTask();
        popupInfTask.add(pInfTask);
        treePanel.jTable1.addMouseListener(this);
        init(initParam);
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
        new CanvasDropTarget(tree, treePanel, this);

    }

    public void init(int initParam) {
        this.initParam = initParam;
        tree.removeMouseListener(this);
        tree.removeMouseMotionListener(this);
        tree.removeMouseListener(mNodes);
        tree.removeMouseMotionListener(mNodes);
        treePanel.bMakeTree.removeActionListener(this);
        treePanel.bMakeTasksByTree.removeActionListener(this);
        mDeleteFigure.removeActionListener(this);
        mEditFigure.removeActionListener(this);

        treePanel.jSplitPane1.setRightComponent(null);
        treePanel.remove(treePanel.panelLeft);

        for (Node n : modelTree.getAllNodesTree()) {
            n.removeMouseListener(mNodes);
            n.removeMouseMotionListener(mNodes);
            n.removeMouseListener(cNodes);
            n.removeMouseMotionListener(cNodes);
        }


        if (initParam == ManagerInitTree.BUILDER_INIT) {
            tree.addMouseListener(this);
            tree.addMouseMotionListener(this);
            mDeleteFigure.addActionListener(this);
            mEditFigure.addActionListener(this);
            treePanel.bMakeTree.addActionListener(this);
            treePanel.bMakeTasksByTree.addActionListener(this);
            tree.addMouseListener(mNodes);
            tree.addMouseMotionListener(mNodes);
            tree.addMouseListener(cNodes);
            tree.addMouseMotionListener(cNodes);

            for (Node n : modelTree.getAllNodesTree()) {
                n.addMouseListener(mNodes);
                n.addMouseMotionListener(mNodes);
                n.addMouseListener(cNodes);
                n.addMouseMotionListener(cNodes);
            }

            treePanel.add(treePanel.panelLeft, BorderLayout.WEST);
            //Правая панель
            treePanel.jSplitPane1.setRightComponent(treePanel.pRight);
            treePanel.revalidate();
            treePanel.repaint();

        } else if (initParam == ManagerInitTree.SINTHES_INIT) {
            tree.addMouseListener(this);
            tree.addMouseMotionListener(this);
            treePanel.bOpenLPanel.setVisible(false);
            //Правая панель
            treePanel.jSplitPane1.setRightComponent(treePanel.pRight);
            treePanel.revalidate();
            treePanel.repaint();
        } else if (initParam == ManagerInitTree.PROGRESS_INIT) {
            tree.addMouseListener(this);
            tree.addMouseMotionListener(this);
            //Правая панель
            treePanel.jSplitPane1.setRightComponent(treePanel.pRight);
            treePanel.revalidate();
            treePanel.repaint();
        } else if (initParam == ManagerInitTree.CREATE_INIT) {
            tree.addMouseListener(this);
            tree.addMouseMotionListener(this);
            tree.addMouseListener(mNodes);
            tree.addMouseMotionListener(mNodes);
            tree.addMouseListener(cNodes);
            tree.addMouseMotionListener(cNodes);
            mDeleteFigure.addActionListener(this);
                       
            for (Node n : modelTree.getAllNodesTree()) {
                n.addMouseListener(mNodes);
                n.addMouseMotionListener(mNodes);
                n.addMouseListener(cNodes);
                n.addMouseMotionListener(cNodes);
            }
            treePanel.revalidate();
            treePanel.repaint();

        } else if (initParam == ManagerInitTree.ANALYS_INIT) {
            tree.addMouseListener(this);
            tree.addMouseMotionListener(this);
            mDeleteFigure.addActionListener(this);
            mEditFigure.addActionListener(this);
            treePanel.bMakeTree.addActionListener(this);
            treePanel.bMakeTasksByTree.addActionListener(this);
            tree.addMouseListener(mNodes);
            tree.addMouseMotionListener(mNodes);
            tree.addMouseListener(cNodes);
            tree.addMouseMotionListener(cNodes);

            for (Node n : modelTree.getAllNodesTree()) {
                n.addMouseListener(mNodes);
                n.addMouseMotionListener(mNodes);
                n.addMouseListener(cNodes);
                n.addMouseMotionListener(cNodes);
            }

            treePanel.add(treePanel.panelLeft, BorderLayout.WEST);
            //Правая панель
            treePanel.jSplitPane1.setRightComponent(treePanel.pRight);
            treePanel.revalidate();
            treePanel.repaint();
        }
        tree.drawTree(false);
    }

    private boolean isShowPopup(Object source) {
        if (initParam == ManagerInitTree.BUILDER_INIT || initParam == ManagerInitTree.ANALYS_INIT) return true;
        if (source instanceof ConnectLine && initParam == ManagerInitTree.CREATE_INIT)
            return true;
        else return false;
    }

    public void createListnerForNode(Node n) {
        n.addMouseListener(this);
        n.addMouseMotionListener(this);

        if (initParam == ManagerInitTree.BUILDER_INIT || initParam == ManagerInitTree.CREATE_INIT || initParam == ManagerInitTree.ANALYS_INIT) {
            n.addMouseListener(mNodes);
            n.addMouseMotionListener(mNodes);
            n.addMouseListener(cNodes);
            n.addMouseMotionListener(cNodes);
        }
    }

    public void createListnerForNodeLine(ConnectLine n) {
        n.addMouseListener(this);
        n.addMouseMotionListener(this);
        if (initParam == ManagerInitTree.BUILDER_INIT || initParam == ManagerInitTree.CREATE_INIT || initParam == ManagerInitTree.ANALYS_INIT) {
            n.addMouseListener(mNodes);
            n.addMouseMotionListener(mNodes);
            n.addMouseListener(cNodes);
            n.addMouseMotionListener(cNodes);
        }
    }


    public void mouseClicked(MouseEvent e) {

    }

    private void selectFigure(Figure figure) {
        modelTree.setSelectedNodes(modelTree.getSelectedNodes(), false);
        modelTree.setSelectedConnectionLine(modelTree.getSelectedConnectLine(), false);
        if (figure != null) {
            figure.setSelected(true);
            selectedFigure = figure;
        } else {
            selectedFigure = null;
        }
        tree.repaint();
    }


    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof Figure) {
            selectedFigure = (Figure) e.getSource();
            selectFigure((Figure) e.getSource());
            if (e.getSource() instanceof Node) {
                setViewInformation((Executor) ((MyTreeNode) e.getSource()).getInforation());
            }
        }
        if (e.getButton() == 1 && e.getClickCount() == 1 && e.getSource() instanceof Node) {
        } else if (e.getButton() == 1 && e.getSource() instanceof Node && e.getClickCount() == 2) {
        } else if (e.getSource() instanceof MyTree && e.getButton() == MouseEvent.BUTTON1) {
            setViewInformation(null);
            selectFigure(null);
        } else if (e.getButton() == 3 && e.getSource() instanceof Figure && isShowPopup(e.getSource())) {
            mEditFigure.setEnabled((e.getSource() instanceof Node));
            popupFigureMenu.show(tree, e.getX(), e.getY());
        }
        if (e.getButton() == 1 &&e.getSource() == treePanel.jTable1) {
            int index=treePanel.jTable1.getSelectedRow();
            if(index>=0){
                Task task=(Task)treePanel.jTable1.getValueAt(index,0);
                if(task!=null){
                    pInfTask.jTable1.setValueAt(task.getNumberOfTask(),0,0);
                    pInfTask.jTable1.setValueAt(task.getNameOfTask(),0,1);
                    pInfTask.jTable1.setValueAt(task.getMinSpecQualify().getSpeciality(),0,2);
                    pInfTask.jTable1.setValueAt(task.getMinSpecQualify().getNameOfQualify(),0,3);
                    pInfTask.jTable1.setValueAt(Helper.getMinutesToString(task.getTimeInMinutes()),0,4);

                    pInfTask.setSize((int)tree.getVisibleRect().getWidth()-15,53);
                    pInfTask.setMinimumSize(new Dimension((int)tree.getVisibleRect().getWidth()-15,53));
                    pInfTask.setPreferredSize(new Dimension((int)tree.getVisibleRect().getWidth()-15,53));

                    popupInfTask.show(treePanel, (int)treePanel.panelLeft.getWidth(),(int)(treePanel.getHeight()-63));
                }
            }
        }
    }


    public void setViewInformation(Executor executor) {
        if (executor != null) {
            try {
                treePanel.setName(executor.getNameOfExecutor());
                treePanel.setSpecQualify(executor.getSpecQualifies());
                treePanel.setTask(executor.getExecTasks(), executor);
                treePanel.setManagerTask(executor.getManageTasks());
                treePanel.setTime(Helper.getMinutesToString(executor.getSumTime()));
            } catch (Exception e) {
            }
        } else {
            treePanel.setName("");
            treePanel.setSpecQualify(null);
            treePanel.setTask(null, null);
            treePanel.setTime("");
        }
    }


    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Figure) {
            Figure figure = (Figure) e.getSource();
            tree.setCursor(cursorCurrent);
            figure.setEntered(true);
            figure.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof Figure) {
            Figure figure = (Figure) e.getSource();
            tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            figure.setEntered(false);
            figure.repaint();
        }
        if (e.getSource() == treePanel.jTable1) {
            popupInfTask.setVisible(false);
        }
    }


    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(treePanel.bMakeTree)) {
            ManagerInitTree.getInstance().getTreeManager().makeTree();
            tree.drawTree(true);
        } else if (e.getSource().equals(treePanel.bMakeTasksByTree)) {
            ManagerInitTree.getInstance().getTreeManager().makeTree();
            tree.drawTree(false);
        } else if (e.getSource().equals(mDeleteFigure)) {
            if (selectedFigure instanceof Node) {
                int res = JOptionPane.showConfirmDialog(treePanel, "Уделить элемент?", "Удаление...", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    removeNode(false, ((Node) selectedFigure));
                    tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    selectedFigure = null;
                }
                tree.repaint();
            } else if (selectedFigure instanceof ConnectLine) {
                int res = JOptionPane.showConfirmDialog(treePanel, "Уделить элемент?", "Удаление...", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    disconnect(((ConnectLine) selectedFigure));
                    tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    selectedFigure = null;
                }
            }
        } else if (e.getSource().equals(mEditFigure)) {
            editNode((Node) selectedFigure);
        }
    }

    private boolean keyPress = false;

    public void eventDispatched(AWTEvent event) {
        KeyEvent keyEvent = (KeyEvent) event;
        if (keyEvent.getKeyCode() == 127 && keyEvent.getID() == 401 && !keyPress && selectedFigure != null) {
            keyPress = true;
            System.out.println(((KeyEvent) event).getID());
            if (selectedFigure instanceof Node) {
                int res = JOptionPane.showConfirmDialog(treePanel, "Уделить элемент?", "Удаление...", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    removeNode(false, ((Node) selectedFigure));
                    tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    selectedFigure = null;
                }
                tree.repaint();
            } else if (selectedFigure instanceof ConnectLine) {
                int res = JOptionPane.showConfirmDialog(treePanel, "Уделить элемент?", "Удаление...", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    disconnect(((ConnectLine) selectedFigure));
                    tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    selectedFigure = null;
                }
            }
            keyPress = false;
        }
    }

    public void addEventListener(EventTree eventTree) {
        if (eventTree != null)
            events.add(eventTree);
    }

    public void removeAllEventListener() {
        events.clear();
    }


    public void removeEventListener(EventTree eventTree) {
        if (eventTree != null)
            events.remove(eventTree);
    }


    //Действия

    public void addNode(Node node) {
        for (EventTree eventTree : events)
            eventTree.addNode(node);
    }

    public void editNode(Node node) {
        for (EventTree eventTree : events)
            eventTree.editNode(node);
    }

    public void removeNode(boolean delCild, Node node) {
        for (EventTree eventTree : events)
            eventTree.removeNode(delCild, node);
    }

    public void connectNodes(Node parent, Node child) {
        for (EventTree eventTree : events)
            eventTree.connectNode(parent, child);
    }

    private void disconnect(ConnectLine connectLine) {
        for (EventTree eventTree : events)
            eventTree.disconnect(connectLine);
    }

}
