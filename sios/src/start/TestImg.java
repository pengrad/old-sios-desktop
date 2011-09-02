/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 02.03.2011
 * Time: 22:45:07
 * To change this template use File | Settings | File Templates.
 */
package start;

import com.sun.java.util.jar.pack.*;
import start.gui.pStart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class TestImg {
    private static TestImg ourInstance = new TestImg();
    private BufferedImage bi = null;
    ControllerStart controllerStart;


    public static TestImg getInstance() {
        return ourInstance;
    }

    private TestImg() {
//        try {
//            bi = ImageIO.read(new File("c:\\temp\\123.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(pStart.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void setControllerStart(ControllerStart controllerStart) {
        this.controllerStart = controllerStart;
    }

    public void draw(JPanel p, Graphics2D g) {

//        g.drawImage(bi.getSubimage(p.getX(), p.getY(), p.getWidth(), p.getHeight()), null, 0, 0);
    }

    public void drawLabel(Graphics g, Component c) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint paint;
        if (c == controllerStart.getSelectedLabelPanel()) {
            paint = new GradientPaint(0, 0, new Color(0, 127, 255),
                    c.getWidth(), 0, Color.white);
        } else {
            paint = new GradientPaint(0, 0, new Color(240, 240, 240),
                    c.getWidth(), 0, Color.white);
        }
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
        c.repaint();
    }

    public void drawLeftPanel(Graphics g, Component c) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint paint = new GradientPaint(0, 0, Color.BLACK,
                0, c.getHeight(), Color.WHITE);
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
        //            g2d.dispose();
    }

    public void drawContenerPanel(Graphics g, Component c) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint paint = new GradientPaint(0, 0, Color.BLACK,
                0, c.getHeight(), Color.WHITE);
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
        //            g2d.dispose();
    }

    public void drawDownPanel(Graphics g, Component c) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint paint = new GradientPaint(0, 0, Color.WHITE,
                0, c.getHeight(), new Color(220, 220, 220));
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
        //            g2d.dispose();
    }

    public Color getColorInfPanel() {
        return new java.awt.Color(255, 255, 240);
    }


}
