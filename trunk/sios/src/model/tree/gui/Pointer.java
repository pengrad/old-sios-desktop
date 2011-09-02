package model.tree.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 25.08.2010
 * Time: 14:44:27
 * To change this template use File | Settings | File Templates.
 */
public class Pointer {
    private Shape shape;

    public Pointer(int x, int y) {
        Path2D area = new Path2D.Double(new Line2D.Double(0, 0, x, 0));
        area.append((Shape) new Line2D.Double(x, 0, 0, y), true);
        area.append((Shape) new Line2D.Double(0, y, 0, 0), true);
        area.closePath();
        shape = area;

    }

    public Shape draw(int x1, int y1, int x2, int y2) {
        double d = Math.toDegrees(Math.atan2(x1 * Math.abs(x2 - x1), y1 * Math.abs(y2 - y1)));
        if (x2 >= x1 && y2 >= y1) {
            d = 90 - d;
        } else if (x2 < x1 && y2 >= y1) {
            d = d + 90;
        } else {
            if (x2 < x1 && y2 < y1) {
                d = 270 - d;
            } else if (x2 >= x1 && y2 < y1) {
                d = 270 + d;
            }
        }
//        AffineTransform af = new AffineTransform();
        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(d+135), shape.getBounds().getX(), shape.getBounds().getY());
        AffineTransform translate = new AffineTransform();
        translate.translate(x2 - shape.getBounds().getX(), y2 - shape.getBounds().getY());
        Shape s = rotate.createTransformedShape(shape);
        return translate.createTransformedShape(s);
    }

}
