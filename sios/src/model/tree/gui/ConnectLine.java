package model.tree.gui;

import model.tree.ManagerInitTree;
import model.tree.Options;

import model.tree.model.Figure;
import model.tree.model.Node;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 16.08.2010
 * Time: 15:07:35
 * To change this template use File | Settings | File Templates.
 */
public class ConnectLine extends Figure {
    private Node parentN;
    private Node childN;

    public ConnectLine(Node parent, Node child) {
        super();
        setBounds(0, 0, 5000, 5000);
        this.parentN = parent;
        this.childN = child;

     ManagerInitTree.getInstance().getControllerTree().createListnerForNodeLine(this);
//        this.pointer = new Pointer(10, 10);

    }

    public Node getParentNode() {
        return parentN;
    }

    public Node getChildNode() {
        return childN;
    }

    public void paintComponent(Graphics g) {
        if (parentN != null) {
            int yP = (int) parentN.getShape().getBounds().getY();
            int yT = (int) childN.getShape().getBounds().getY();
            if (yP < (10 + yT + childN.getHeight())) {
                int x1 = (int) (childN.getShape().getBounds().getX() + childN.getShape().getBounds().getWidth() / 2);
                int y1 = yT;
                int x2 = (int) (parentN.getShape().getBounds().getX() + parentN.getShape().getBounds().getWidth() / 2);
                int y2 = yP + (int) parentN.getShape().getBounds().getHeight();
                shape = new Line2D.Double(x1, y1, x2, y2);
                Graphics2D g2 = (Graphics2D) g;
                if (entered) {
                    g.setColor(Options.get().getColorOfConnection().darker());
                } else {
                    g2.setColor(Options.get().getColorOfConnection());
                }
                if (selected) {
                    g2.setColor(Color.GRAY);
                }
                g2.draw(shape);
                g.setColor(Color.ORANGE);
                g2.setColor(Color.LIGHT_GRAY);
                g2.fill(new Ellipse2D.Double(x1 - 5, y1 - 5, 10, 10));
            } else if (yP > (10 + yT + childN.getHeight())) {
                int x1 = (int) (childN.getShape().getBounds().getX() + childN.getShape().getBounds().getWidth() / 2);
                int y1 = yT;
                int x2 = (int) (parentN.getShape().getBounds().getX() + parentN.getShape().getBounds().getWidth() / 2);
                int y2 = yP + (int) parentN.getShape().getBounds().getHeight();
                shape = new Line2D.Double(x1, y1, x2, y2);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.draw(shape);
                g2.setColor(Color.LIGHT_GRAY);
                g2.fill(new Ellipse2D.Double(x1 - 5, y1 - 5, 10, 10));
            }

        }
    }

    public boolean contains(int x, int y) {
        if (shape != null) {
            return shape.intersects(x - 3, y - 3, 6, 6);
        } else return false;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
//        return child.equals(o);
        if (o instanceof Node)
            return childN.equals(o);
        else if (o instanceof ConnectLine) {
            return this == o;
        } else return false;
    }
}
