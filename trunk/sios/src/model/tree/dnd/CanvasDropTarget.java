package model.tree.dnd;

import model.tree.controller.ControllerTree;
import model.tree.gui.MyTree;
import model.tree.gui.MyTreeNode;
import model.tree.gui.TreePanel;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class CanvasDropTarget implements DropTargetListener {

    private boolean acceptableType;
    private TreePanel treePanel;
    private ControllerTree controllerTree;

    public CanvasDropTarget(MyTree tree, TreePanel treePanel, ControllerTree controllerTree) {
        this.treePanel = treePanel;
        this.controllerTree = controllerTree;
        new DropTarget(tree, DnDConstants.ACTION_MOVE, this, true, null);
    }

    public void dragOver(DropTargetDragEvent dtde) {

    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent dtde) {
        int x = (int) dtde.getLocation().getX();
        int y = (int) dtde.getLocation().getY();
        //todo Информация об объекте
        MyTreeNode node = null;
        try {
            JButton b = (JButton) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            if (b.equals(treePanel.bCoordinator))
                node = new MyTreeNode( null, MyTreeNode.COORDINATOR);
            if (b.equals(treePanel.bManager)) node = new MyTreeNode( null, MyTreeNode.MANAGER);
            if (b.equals(treePanel.bIngener)) node = new MyTreeNode( null, MyTreeNode.INGENER);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (node != null) {
            AffineTransform af = new AffineTransform();
            af.translate(x - node.getShape().getBounds().getX(), y - node.getShape().getBounds().getY());
            node.transform(af);
            controllerTree.addNode(node);
        }
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        checkTransferType(dtde);
    }

    void checkTransferType(DropTargetDragEvent dtde) {
        acceptableType = dtde.isDataFlavorSupported(DataFlavor.stringFlavor);
    }
}

