package model.tree.model;

import model.tree.gui.ConnectLine;
import model.tree.gui.MyTreeNode;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 15.08.2010
 * Time: 21:55:50
 * To change this template use File | Settings | File Templates.
 */
public class DefaulModelMyTree {
    private ArrayList<TreeListener> treeListeners;
    private ArrayList<ConnectLine> connectLines;
    private ArrayList<Node> nodesList;

    public DefaulModelMyTree() {
        treeListeners = new ArrayList<TreeListener>(0);
        connectLines = new ArrayList<ConnectLine>(0);
        nodesList = new ArrayList<Node>(0);
    }

    public void addTreeListeners(TreeListener treeListener) {
        treeListeners.add(treeListener);
    }

    public void removeTreeListeners(TreeListener treeListener) {
        treeListeners.remove(treeListener);
    }

    public void clear() {
        int n = nodesList.size();
        for (int i = 0; i < n; i++) {
            removeNode(false, nodesList.get(0));
        }
        nodesList.clear();
        connectLines.clear();
    }

    public ArrayList<Node> getSingleNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        if (nodesList != null && nodesList.size() > 0)
            for (Node node : nodesList) {
                if (node.getParentNode() == null && node.getChildsNode() == null) {
                    nodes.add(node);
                }
            }
        return nodes;
    }

    public ArrayList<Node> getRoots() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        if (nodesList != null && nodesList.size() > 0)
            for (Node node : nodesList) {
                if (node.getParentNode() == null && node.getChildsNode() != null) {
                    nodes.add(node);
                }
            }
        if (nodes.size() == 0)
            return null;
        else
            return nodes;
    }

//    public boolean isCorrectTree() {
//        int i = 0;
//        if (nodesList != null && nodesList.size() > 0)
//            for (Node node : nodesList) {
//                if (node.getParentNode() == null) {
//                    i++;
//                }
//            }
//        return i == 1;
//    }


    public boolean connect(Node parent, Node child) {
        //корень дерева
        if (parent != null) {
            //Добавляем детей
            if (child.getParentNode() != null) {
                child.getParentNode().removeChildNode(child);
                removeComponentTree(connectLines.get(connectLines.indexOf(child)));
                connectLines.remove(child);
            }
            if (parent.getChildsNode() == null || parent.getChildsNode().indexOf(child) == -1) {
                parent.addChildNode(child);
                // addComponentTree(child);
                ConnectLine cl = new ConnectLine(parent, child);
                connectLines.add(cl);
                addComponentTree(cl);
            }
            return true;
        }
        return false;
    }


    public boolean disConnect(Node n1, Node n2) {
        if (n1 == null || n2 == null) return false;
        if (n1.isChildNode(n2)) {
            n1.removeChildNode(n2);
            removeComponentTree(connectLines.get(connectLines.indexOf(n2)));
            connectLines.remove(n2);
            n2.setParentNode(null);
            return true;
        } else if (n2.isChildNode(n1)) {
            n2.removeChildNode(n1);
            removeComponentTree(connectLines.get(connectLines.indexOf(n1)));
            connectLines.remove(n1);
            n1.setParentNode(null);
            return true;
        } else {
            return false;
        }

    }

    public void addNode(Node node) {
        nodesList.add(node);
        addComponentTree(node);
    }

    public void addNode(Node parent, Node node) {
        addNode(node);
        connect(parent, node);
    }

    private void addComponentTree(Node node) {
        for (TreeListener treeListener : treeListeners) {
            treeListener.addComponentNode(node);
        }
    }

    private void addComponentTree(ConnectLine line) {
        for (TreeListener treeListener : treeListeners) {
            treeListener.addComponentBetweenNodes(line);
        }
    }


    public void removeNode(boolean delChild, Node... node) {
        if (node != null) {
            for (int i = 0; i < node.length; i++) {
                if (node[i].getParentNode() != null) {
                    removeComponentTree(connectLines.get(connectLines.indexOf(node[i])));
                    connectLines.remove(node[i]);
                }
                if (!delChild) {
                    ArrayList<Node> childs = node[i].getChildsNode();
                    if (childs != null && childs.size() > 0)
                        for (Node child : childs) {
                            child.setParentNode(null);
                            removeComponentTree(connectLines.get(connectLines.indexOf(child)));
                            connectLines.remove(child);
                        }
                } else {
                    //Удаляем всех детей узла
                    ArrayList<Node> nodes = getAllChildNode(node[i]);
                    if (nodes != null && nodes.size() > 0)
                        for (Node nod : nodes) {
                            removeComponentTree(connectLines.get(connectLines.indexOf(nod)));
                            connectLines.remove(nod);
                            removeComponentTree(nod);
                            nodesList.remove(nod);
                        }
                }
                if (node[i].getParentNode() != null)
                    node[i].getParentNode().removeChildNode(node[i]);
                removeComponentTree(node[i]);
                nodesList.remove(node[i]);
            }
        }
    }


    private void removeComponentTree(Node node) {
        for (TreeListener treeListener : treeListeners) {
            treeListener.removeComponentNode(node);
        }
    }

    private void removeComponentTree(ConnectLine line) {
        for (TreeListener treeListener : treeListeners) {
            treeListener.removeComponentBetweenNodes(line);
        }
    }


    public int getCountLevelsInTree(Node root) {
        int level = 0;
        while (true) {
            int l = getCountNodeInLevel(level, root);
            if (l == -1) return 0;
            if (l == 0) return level;
            level++;
        }
    }


    public int getCountNodeInLevel(int level, Node root) {
        if (level == 0 && root == null) return -1;
        if (level == 0 && root != null) return 1;
        ArrayList<Node> nodes = getNodesLevel(level, root);
        if (nodes != null) return nodes.size();
        else
            return 0;
    }

    public ArrayList<Node> getNodesLevel(int level, Node root) {
        ArrayList<Node> nodes = new ArrayList<Node>(0);
        if (level == 0) {
            nodes.add(root);
            return nodes;
        } else {
            return getNodesLevel(root, level, nodes);
//            return getNodesLevel(level,nodes);
        }
    }

    private ArrayList<Node> getNodesLevel(Node node, int level, ArrayList<Node> nodes) {
        if (node == null) return null;
        ArrayList<Node> childs = node.getChildsNode();
        if (childs != null) {
            for (Node child : childs) {
                if (child.getLevel() == level) nodes.add(child);
                getNodesLevel(child, level, nodes);
            }
        }
        if (nodes.size() > 0) {
            return nodes;
        } else
            return null;
    }

    public int getMaxCountWidth(Node root) {
        int width = 1;
        for (int i = 0; i < getCountLevelsInTree(root); i++) {
            int k = getCountNodeInLevel(i, root);
            if (k > width) width = k;
        }
        return width;
    }

    public ArrayList<Node> getAllNodesTree() {
        return nodesList;
    }

    public ArrayList<Node> getAllChildNode(Node node) {
        ArrayList<Node> nodes = new ArrayList<Node>(0);
        return getAllChildNode(node, nodes);
    }

    private ArrayList<Node> getAllChildNode(Node node, ArrayList<Node> nodes) {
        ArrayList<Node> childs = node.getChildsNode();
        if (childs != null) {
            for (Node child : childs) {
                nodes.add(child);
                getAllChildNode(child, nodes);
            }
        }
        if (nodes.size() > 0) {
            return nodes;
        } else
            return null;
    }

    public void setSelectedNodes(ArrayList<Node> nodes, boolean select) {
        if (nodes != null)
            for (Node node : nodes) {
                node.setSelected(select);
            }
    }

    public void setSelectedConnectionLine(ArrayList<ConnectLine> lines, boolean select) {
        if (lines != null)
            for (ConnectLine line : lines) {
                line.setSelected(select);
            }
    }


    public ArrayList<Node> getSelectedNodes() {
        ArrayList<Node> nodesSelected = new ArrayList<Node>(0);
        for (Node node : nodesList) {
            nodesSelected.add(node);
        }
        if (nodesSelected.size() == 0) return null;
        return nodesSelected;
    }

    public ArrayList<ConnectLine> getSelectedConnectLine() {
        ArrayList<ConnectLine> lineSelected = new ArrayList<ConnectLine>(0);
        for (ConnectLine line : connectLines) {
            lineSelected.add(line);
        }
        if (lineSelected.size() == 0) return null;
        return lineSelected;
    }

    public Node getNodeByInfObj(Object obj) {
        if (obj == null) return null;
        ArrayList<Node> nodes = getAllNodesTree();
        if (nodes == null) return null;
        for (Node node : nodes) {
            if (((MyTreeNode) node).getInforation().equals(obj)) {
                return node;
            }
        }
        return null;
    }
}
