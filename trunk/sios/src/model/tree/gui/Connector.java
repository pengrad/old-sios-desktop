package model.tree.gui;

import model.tree.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 25.08.2010
 * Time: 10:52:01
 * To change this template use File | Settings | File Templates.
 */
public class Connector extends JComponent {
    private int x1, y1, x2, y2;
    private Shape shape;
    private Node parent;
    private Node child;
    private Pointer p = new Pointer(10, 10);
    private boolean enabled = false;

    public Connector() {
        super();
        setBounds(0, 0, 5000, 5000);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setParent(Node parent) {
        this.parent = parent;
        x1 = (int) (parent.getShape().getBounds().getX() + parent.getShape().getBounds().getWidth() / 2);
        y1 = (int) (parent.getShape().getBounds().getY() + parent.getShape().getBounds().getHeight() / 2);
        update(x1, y1);
    }

    Shape ss;

    public void update(int x, int y) {
        if (parent != null) {
            x1 = (int) (parent.getShape().getBounds().getX() + parent.getShape().getBounds().getWidth() / 2);
            y1 = (int) (parent.getShape().getBounds().getY() + parent.getShape().getBounds().getHeight() / 2);
            x2 = x;
            y2 = y;
            ss = p.draw(x1, y1, x2, y2);
            shape = new Line2D.Double(x1, y1, x, y);
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        if (enabled) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.fill(new Ellipse2D.Double(x1 - 5, y1 - 5, 10, 10));
            try {
                g2.draw(shape);
                g2.drawString("Выберите элемент для связвывния", x2 + 10, y2 + 10);
                g2.setColor(Color.ORANGE);
                g2.fill(ss);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
