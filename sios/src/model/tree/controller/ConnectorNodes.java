package model.tree.controller;

import model.tree.gui.Connector;
import model.tree.gui.MyTree;
import model.tree.gui.MyTreeNode;
import model.tree.gui.TreePanel;
import model.tree.model.DefaulModelMyTree;
import model.tree.model.Node;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 25.08.2010
 * Time: 16:33:46
 * To change this template use File | Settings | File Templates.
 */


public class ConnectorNodes implements MouseListener, MouseMotionListener {
    private MyTree tree = null;
    private Node nodeConnect = null;
    private Connector con;
    private DefaulModelMyTree modelTree;
    private TreePanel treePanel;
    private ControllerTree controllerTree;

    public ConnectorNodes(TreePanel treePanel, MyTree tree, ControllerTree controllerTree) {
        this.treePanel = treePanel;
        this.tree = tree;
        this.modelTree = tree.getModel();
        this.controllerTree = controllerTree;
        con = new Connector();
        tree.add(con);
    }


    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1 && e.getSource() instanceof Node && e.getClickCount() == 2) {
            //Начало конекта
            ((Node) e.getSource()).setSelected(true);
            nodeConnect = (Node) e.getSource();
            con.setParent(nodeConnect);
            con.setEnabled(true);
            tree.repaint();
        } else if (e.getSource() instanceof MyTree && e.getButton() == MouseEvent.BUTTON1) {
            //отмена коннекта
            modelTree.setSelectedNodes(modelTree.getSelectedNodes(), false);
            con.setEnabled(false);
            nodeConnect = null;
            tree.repaint();
        } else if (e.getButton() == 1 && e.getClickCount() == 1 && e.getSource() instanceof Node) {
            //Коннект
            if (nodeConnect != null && nodeConnect != e.getSource()) {
                MyTreeNode parent = (MyTreeNode) nodeConnect;
                MyTreeNode child = (MyTreeNode) e.getSource();
                con.setEnabled(false);
                nodeConnect = null;
//                if (parent.getType() == MyTreeNode.COORDINATOR && child.getType() == MyTreeNode.COORDINATOR ||
//                        parent.getType() == MyTreeNode.COORDINATOR && child.getType() == MyTreeNode.MANAGER ||
//                        parent.getType() == MyTreeNode.MANAGER && child.getType() == MyTreeNode.INGENER) {
                controllerTree.connectNodes(parent, child);
//                } else {
//                    JOptionPane.showMessageDialog(treePanel, "Выбранные узлы нельзя соединить");
//                }
                tree.repaint();
            }
        }


    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        con.update(e.getX(), e.getY());
    }
}
