package model.tree.controller;

import model.tree.gui.MyTree;
import model.tree.gui.TreePanel;
import model.tree.model.DefaulModelMyTree;
import model.tree.model.Node;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 25.08.2010
 * Time: 16:33:27
 * To change this template use File | Settings | File Templates.
 */
public class MoverNodes implements MouseListener, MouseMotionListener {
    private int x1, y1;
    private boolean enabled = false;
    private TreePanel treePanel;

    private MyTree tree;
    private DefaulModelMyTree model;
    private ControllerTree controllerTree;


    public MoverNodes(TreePanel treePanel, MyTree tree, ControllerTree controllerTree) {
        this.treePanel = treePanel;
        this.tree = tree;
        this.model = tree.getModel();
        this.controllerTree = controllerTree;
    }


    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1 && e.getClickCount() == 1 && e.getSource() instanceof Node) {
            x1 = e.getX();
            y1 = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (e.getSource() instanceof Node) {
            if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK) {
                Node node = (Node) e.getComponent();
                int x2 = e.getX();
                int y2 = e.getY();
                AffineTransform af = new AffineTransform();
                af.translate(x2 - x1, y2 - y1);
                node.transform(af);
                x1 = x2;
                y1 = y2;
            }
        }

    }

    public void mouseMoved(MouseEvent e) {

    }


}
