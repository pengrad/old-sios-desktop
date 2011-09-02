package model.tree;

import model.manager.EmployeeManager;
import model.manager.TreeModelManager;
import model.model.Executor;
import model.model.ManageTask;
import model.tree.model.*;
import model.tree.gui.MyTreeNode;

import java.util.*;

public class TreeUpdateManager implements Observer {
    TreeModelManager modelManager;

    public TreeUpdateManager(TreeModelManager modelManager) {
        this.modelManager = modelManager;
        ManagerInitTree.getInstance().setTreeManager(this);
        ManagerInitTree.getInstance().setTreeModelManager(modelManager);
    }

    private void removeNodes(ArrayList<Executor> allExecutors) {
        ArrayList<Node> allNodes = ManagerInitTree.getInstance().getModelTree().getAllNodesTree();
        ArrayList<Node> nodes = new ArrayList<Node>(allNodes.size());
        for (Node n : allNodes) nodes.add(n);
        for (Node node : nodes) {
            boolean b = false;
            for (Executor executor : allExecutors) {
                if (executor.equals(((MyTreeNode) node).getInforation())) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                ManagerInitTree.getInstance().getModelTree().removeNode(false, node);
            }
        }
    }

    private MyTreeNode isExistExecutor(Executor executor) {
        ArrayList<Node> allNodes = ManagerInitTree.getInstance().getModelTree().getAllNodesTree();
        for (Node node : allNodes) {
            if (((Executor) ((MyTreeNode) node).getInforation()).equals(executor)) {
                int type = getTypeNode(executor);
                if (type != ((MyTreeNode) node).getType()) {
                    ((MyTreeNode) node).setType(type);
                }
                return (MyTreeNode) node;
            }
        }
        return null;
    }

    public ArrayList<Executor> makeTree() {
        ArrayList<Node> allNodes = ManagerInitTree.getInstance().getModelTree().getAllNodesTree();
        for (Node node : allNodes) {
            Node parent = node.getParentNode();
            if (parent != null)
                ManagerInitTree.getInstance().getModelTree().disConnect(parent, node);
        }

        ArrayList<Executor> allExecutors = new ArrayList<Executor>();
        Collection<Executor> freeExecutors = modelManager.getFreeExecutors();
        for (Executor executor : freeExecutors) {
            allExecutors.add(executor);
            MyTreeNode node;
            if ((node = isExistExecutor(executor)) == null) {
                node = new MyTreeNode(executor, getTypeNode(executor));
                ManagerInitTree.getInstance().getModelTree().addNode(node);
            }
            makeTree(executor.getManageTasks(), node, allExecutors);
        }
        return allExecutors;
    }


    private void makeTree(ArrayList<ManageTask> manageTasks, Node node, ArrayList<Executor> allExecutors) {
        if (manageTasks != null) {
            for (ManageTask mt : manageTasks) {
                Executor executor = mt.getManagedExecutor();
                allExecutors.add(executor);
                MyTreeNode childNode;
                if ((childNode = isExistExecutor(executor)) == null) {
                    childNode = new MyTreeNode(executor, getTypeNode(executor));
                    ManagerInitTree.getInstance().getModelTree().addNode(node, childNode);
                } else {
                    ManagerInitTree.getInstance().getModelTree().connect(node, childNode);
                }
                makeTree(executor.getManageTasks(), childNode, allExecutors);
            }
        }
    }

    private int getTypeNode(Executor e) {
        if (modelManager.isCoordinator(e)) return MyTreeNode.COORDINATOR;
        else if (modelManager.isManager(e)) return MyTreeNode.MANAGER;
        else return MyTreeNode.INGENER;
    }

    public void update(Observable o, Object arg) {
        if (o instanceof EmployeeManager) {
            ManagerInitTree.getInstance().getControllerTree().setViewInformation(null);
            ArrayList<Executor> allExecutors = makeTree();
            removeNodes(allExecutors);
        }
        if (ManagerInitTree.getInstance().getInitParam() == ManagerInitTree.SINTHES_INIT || ManagerInitTree.getInstance().getInitParam() == ManagerInitTree.PROGRESS_INIT || ManagerInitTree.getInstance().getInitParam() == ManagerInitTree.CREATE_INIT) {
            ManagerInitTree.getInstance().getMyTree().drawTree(false);
        }
    }
}