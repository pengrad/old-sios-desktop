package model.tree.gui;

import manager.AnalysManager;
import model.model.Executor;
import model.model.Task;
import model.tree.ManagerInitTree;
import model.tree.Options;
import model.tree.gui.gradient.RoundGradientPaint;
import model.tree.model.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ReplicateScaleFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 15.08.2010
 * Time: 18:11:06
 * To change this template use File | Settings | File Templates.
 */
public class MyTreeNode extends Node {
    public final static int COORDINATOR = 0;
    public final static int MANAGER = 1;
    public final static int INGENER = 2;
    private int type;
    private Object information = null;
    private JLabel label = new JLabel();
    private Shape s;
    BufferedImage coordinatorBi;
    BufferedImage managerBi;
    BufferedImage ingenerBi;


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public MyTreeNode(Object information, int type) {
        super();

        this.type = type;
        shape = new Ellipse2D.Double(0, 0, MyTree.widthNode, MyTree.heightNode);
        this.information = information;
        setToolTip();
        setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        //Нужно для отображения номера по центру, нужно переделать как нибудь поэлегантнее
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        add(label);
        s = new Rectangle2D.Double(0, 0, MyTree.widthNode, MyTree.heightNode);
        BufferedImage bi = null;
        try {
            coordinatorBi = setResize(ImageIO.read(this.getClass().getResource("/model/tree/image/c.jpg")), (int) shape.getBounds().getWidth(), (int) shape.getBounds().getHeight());
            managerBi = setResize(ImageIO.read(this.getClass().getResource("/model/tree/image/m.jpg")), (int) shape.getBounds().getWidth(), (int) shape.getBounds().getHeight());
            ingenerBi = setResize(ImageIO.read(this.getClass().getResource("/model/tree/image/e.jpg")), (int) shape.getBounds().getWidth(), (int) shape.getBounds().getHeight());

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void setToolTip() {
        Options opt = Options.get();
        if (information != null) {
            if (type == COORDINATOR) {
                setToolTipText("<html>" +
                        "Элемент: <b>Координатор</b><br>" +
//                        "Загруженность: <b>" + (int) (((Executor) information).getSumTime() / ManagerInitTree.getInstance().getTreeManager().getMaxTime()) + "%</b><br>" +
                        "</html>");
            }
            if (type == MANAGER) {
                setToolTipText("<html>" +
                        "Элемент: <b>Менеджер</b><br>" +
//                        "Загруженность: <b>" + (int) (((Executor) information).getSumTime() / ManagerInitTree.getInstance().getTreeManager().getMaxTime()) + "%</b><br>" +
                        "</html>");
            }
            if (type == INGENER) {
                setToolTipText("<html>" +
                        "Элемент: <b>Инженер</b><br>" +
//                        "Специализация: <b>" + opt.getSpecName(((Ingener) information).getSpec()) + "</b><br>" +
//                        "Квалификация: <b>" + opt.getDifficultyName(((Ingener) information).getDifficulty()) + "</b><br>" +
//                        "Загруженность: <b>" + (int) (((Executor) information).getSumTime() / ManagerInitTree.getInstance().getTreeManager().getMaxTime()) + "%</b><br>" +
                        "</html>");
            }
        }
    }

    public Object getInforation() {
        return information;
    }

    public void setInforation(Object information) {
        this.information = information;
        setToolTip();
    }

    @Override
    public void paintNode(Graphics2D g) {
        Options opt = Options.get();
        Color color = Color.BLUE;
        if (information != null) {
            Executor executor = (Executor) information;
            //Сектор забрузки
//            color = Color.GRAY; //ColorGenerator.makeColor(ingener.getSpec());
            g.setColor(color);
//            int t = (int) (((double) executor.getSumTime() / ManagerInitTree.getInstance().getTreeModelManager().getMaxTime()) * 360);
//            Shape s = new Arc2D.Double(new Rectangle2D.Double(shape.getBounds2D().getX() - 3, shape.getBounds2D().getY() - 3, shape.getBounds2D().getWidth() + 6, shape.getBounds2D().getHeight() + 6), 90, -t, Arc2D.PIE);
//            g.draw(s);
//            int x1=(int)shape.getBounds().getX()-3;
//            int y1=(int)(shape.getBounds().getY()+shape.getBounds().getHeight());
//            int x2=(int)shape.getBounds().getX()-3;;
//            int y2=(int)(shape.getBounds().getY()+shape.getBounds().getHeight())-(int)(((double) executor.getSumTime() / ManagerInitTree.getInstance().getTreeModelManager().getMaxTime())*shape.getBounds().getHeight());
//
//            Shape s= new Line2D.Double(x1,y1,x2,y2);
//            g.draw(s);
        }
//        if (type == COORDINATOR) color = opt.getColorOfCoordinator();
//        if (type == MANAGER) color = opt.getColorOfManager();
//        if (type == INGENER) color = opt.getColorOfIngener();

        if (isEntered()) {
            g.setColor(Color.orange);
            Shape s = new Rectangle2D.Double(shape.getBounds2D().getX() - 3, shape.getBounds2D().getY() - 3, shape.getBounds2D().getWidth() + 6, shape.getBounds2D().getHeight() + 6);
            g.fill(s);
        }
        g.setColor(color);
        if (isSelected()) color = opt.getColorOfSelection();
//        RoundGradientPaint rgp = new RoundGradientPaint(shape.getBounds().getX(), shape.getBounds().getY(), Color.WHITE,
//                new Point2D.Double(shape.getBounds().getBounds().getWidth() / 2 + 10, shape.getBounds().getBounds().getHeight() / 2), color);

        if (type == COORDINATOR)
            g.drawImage(coordinatorBi, null, (int) shape.getBounds().getX(), (int) shape.getBounds().getY());
        if (type == MANAGER)
            g.drawImage(managerBi, null, (int) shape.getBounds().getX(), (int) shape.getBounds().getY());
        if (type == INGENER)
            g.drawImage(ingenerBi, null, (int) shape.getBounds().getX(), (int) shape.getBounds().getY());

//        g.setPaint(rgp);
//        g.fill(shape);
        if (ManagerInitTree.getInstance().getInitParam() == ManagerInitTree.PROGRESS_INIT && information != null) {
            if (((Executor) information).isNew()) {
                g.setColor(Color.BLUE);
                g.draw(new Rectangle2D.Double(shape.getBounds2D().getX() - 5, shape.getBounds2D().getY() - 5, shape.getBounds2D().getWidth() + 8, shape.getBounds2D().getHeight() + 8));
            } else {
                ArrayList<Task> tasks = ((Executor) information).getAllTasks();
                boolean isNewTask = false;
                for (Task task : tasks) {
                    if (task.isNew()) {
                        isNewTask = true;
                        break;
                    }
                }
                if (isNewTask) {
                    g.setColor(Color.GREEN);
                    g.draw(new Rectangle2D.Double(shape.getBounds2D().getX() - 1, shape.getBounds2D().getY() - 1, shape.getBounds2D().getWidth() + 2, shape.getBounds2D().getHeight() + 2));
                }
            }
        }
        if (ManagerInitTree.getInstance().getInitParam() == ManagerInitTree.ANALYS_INIT && information != null) {
            if (AnalysManager.isOutOfTimeExecutor((Executor) information)) {
                g.setColor(Color.RED);
                g.draw(new Rectangle2D.Double(shape.getBounds2D().getX() - 5, shape.getBounds2D().getY() - 5, shape.getBounds2D().getWidth() + 8, shape.getBounds2D().getHeight() + 8));
            }
            if (!AnalysManager.isMakeTaskExecutor((Executor) information)) {
                g.setColor(Color.BLUE);
                g.draw(new Rectangle2D.Double(shape.getBounds2D().getX() - 8, shape.getBounds2D().getY() - 8, shape.getBounds2D().getWidth() + 15, shape.getBounds2D().getHeight() + 15));
            }
        }
        if (information != null)
            drawNumber(g, Integer.toString(((Executor) information).getNumberOfExecutor()));
    }

    private void drawNumber(Graphics2D g, String number) {
        Graphics gStr = label.getGraphics();
        FontMetrics fm = gStr.getFontMetrics();
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        g.drawString(number, (int) (shape.getBounds().getX() + shape.getBounds().getWidth() / 2 - fm.stringWidth(number) / 2), (int) (shape.getBounds().getY() + shape.getBounds().getHeight() / 2 + fm.getHeight() / 2 - 4));
    }

    public BufferedImage setResize(BufferedImage img, int w, int h) {
        BufferedImage bim;
        ReplicateScaleFilter rsf = new ReplicateScaleFilter(w, h);
        Image im = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), rsf));
        bim = new BufferedImage(im.getWidth(null), im.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        bim.getGraphics().drawImage(im, 0, 0, null);
        return bim;
    }

}
