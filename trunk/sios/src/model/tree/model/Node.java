package model.tree.model;

import model.tree.ManagerInitTree;
import model.tree.gui.ConnectLine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 15.08.2010
 * Time: 16:49:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class Node extends Figure {
    private int key;
    protected Node parent = null;
    protected ArrayList<Node> childs;
    protected boolean selected = false;
    protected boolean entered = false;

//    private final static ActionNode actionNode = new ActionNode();

    public Node() {
        super();
        key = KeyGenerator.getKey();
        setBounds(0, 0, 5000, 5000);
        childs = new ArrayList<Node>(0);
        ManagerInitTree.getInstance().getControllerTree().createListnerForNode(this);
        setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
//        addMouseListener(actionNode);

    }

    public int getKey() {
        return key;
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isRoot() {
        return parent == null;
    }


    void setParentNode(Node parent) {
        this.parent = parent;
    }

    public Node getParentNode() {
        return parent;
    }

    public abstract void paintNode(Graphics2D g);


    void addChildNode(Node child) {
        childs.add(child);
        child.setParentNode(this);
    }

    void removeChildNode(Node child) {
        if (child != null)
            childs.remove(child);
    }

    void removeAllChildNode() {
        childs.clear();
    }

    public ArrayList<Node> getChildsNode() {
        if (childs.size() == 0) return null;
        return childs;
    }

    public Node getLeftChild() {
        if (childs.size() == 0) return null;
        return childs.get(0);
    }

    public Node getRightChild() {
        if (childs.size() == 0) return null;
        return childs.get(childs.size() - 1);
    }


    public boolean isChildNode(Node child) {
        if (childs == null || childs.size() == 0) return false;
        for (Node node : childs) {
            if (child.equals(node)) return true;
        }
        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintNode((Graphics2D) g);
    }

    public int getLevel() {
        return getLevel(this, 0);
    }

    private int getLevel(Node node, int level) {
        if (node.isRoot()) {
            return level;
        } else {
            return getLevel(node.getParentNode(), ++level);
        }
    }

    public boolean contains(int x, int y) {
        if (shape != null) {
            return shape.contains(x, y);
        } else return false;
    }

    public void transform(AffineTransform af) {
        shape = af.createTransformedShape(shape);
        revalidate();
        repaint();
    }


    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Node)
            return this.key == ((Node) o).getKey();
        else if (o instanceof ConnectLine)
            return this.key == ((ConnectLine) o).getChildNode().getKey();
        else return false;
    }

    public String toString() {
        return "Node - " + key;
    }

    //todo Два метода для плавного передвижения, временно запоминают свою дистлокацию
    int tmpX, tmpY;

    public void setTmpLocation(int x, int y) {
        tmpX = x;
        tmpY = y;
    }

    public int[] getTmpLocation() {
        return new int[]{tmpX, tmpY};
    }


    //todo Для построения правильного дерева
    boolean existTree = true;

    public void setExistRealTree(boolean existTree) {
        this.existTree = existTree;
    }

    public boolean isExistRealTree() {
        return existTree;
    }
}
