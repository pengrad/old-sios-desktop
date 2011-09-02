package manager;

import gui.EmplDialog;
import manager.GUIManager;
import model.model.Executor;
import model.tree.ManagerInitTree;
import model.tree.controller.EventTree;
import model.tree.gui.ConnectLine;
import model.tree.gui.MyTreeNode;
import model.tree.model.*;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 01.02.2011
 * Time: 13:57:13
 * To change this template use File | Settings | File Templates.
 */
public class TreeEventManager implements EventTree {
    private EmplDialog dialog;

    public TreeEventManager(GUIManager gui) {
        dialog = new EmplDialog(null, true, gui);
    }

    public void addNode(Node node) {
        Executor executor = dialog.showDialog(null, ((MyTreeNode) node).getType() == MyTreeNode.MANAGER);
        if (executor != null) {
            ((MyTreeNode) node).setInforation(executor);
              ManagerInitTree.getInstance().getModelTree().addNode(node);
              ManagerInitTree.getInstance().getTreeModelManager().addExecutor(executor);
        }
    }

    public void editNode(Node node) {
        Executor executor = dialog.showDialog((Executor) ((MyTreeNode) node).getInforation());
        if (executor != null) {
          ManagerInitTree.getInstance().getTreeModelManager().changeExecutor(((Executor) ((MyTreeNode) node).getInforation()).getIdExecutor(), executor);
        }
    }

    public void connectNode(Node parent, Node child) {
        if (((MyTreeNode) parent).getType() == MyTreeNode.INGENER && ((((MyTreeNode) child).getType() == MyTreeNode.MANAGER) || (((MyTreeNode) child).getType() == MyTreeNode.COORDINATOR))) {
            Node tmp = child;
            child = parent;
            parent = tmp;
        }
        try {
            ManagerInitTree.getInstance().getTreeModelManager().addManagerToExecutor((Executor) ((MyTreeNode) child).getInforation(), (Executor) ((MyTreeNode) parent).getInforation());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ManagerInitTree.getInstance().getTreePanel(), e.getMessage());
        }
    }

    public void disconnect(ConnectLine connectLine) {
        ManagerInitTree.getInstance().getTreeModelManager().removeManagerFromExecutor((Executor) ((MyTreeNode) connectLine.getChildNode()).getInforation(), (Executor) ((MyTreeNode) connectLine.getParentNode()).getInforation());
    }

    public void removeNode(boolean delCild, Node node) {
          ManagerInitTree.getInstance().getTreeModelManager().removeExecutor((Executor) ((MyTreeNode) node).getInforation());
    }
}
