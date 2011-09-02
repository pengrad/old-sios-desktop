package model.tree.gui;

import model.tree.model.DefaulModelMyTree;
import model.tree.model.Node;
import model.tree.model.TreeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 15.08.2010
 * Time: 16:44:27
 * To change this template use File | Settings | File Templates.
 */
public class MyTree extends JComponent implements TreeListener {
    private JComponent tree;
    private DefaulModelMyTree model = null;
    public static int widthNode = 50;
    public static int heightNode = 50;
    private int betweenNodeVertical = 30;
    private int betweenNodeHorizontal = 50;
    private ArrayList<Dimension> sizeTree;

    public MyTree(DefaulModelMyTree defaulModelMyTree) {
        super();
        sizeTree = new ArrayList<Dimension>();
        setBounds(0, 0, 5000, 5000);
        tree = this;
        model = defaulModelMyTree;
        model.addTreeListeners(this);
        setPreferredSize(new Dimension(500, 500));
    }


    public DefaulModelMyTree getModel() {
        return model;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setBackground(Color.WHITE);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setColor(Color.WHITE);
        ((Graphics2D) g).fill(new Rectangle2D.Double(0, 0, this.getSize().getWidth(), this.getSize().getHeight()));

    }


    public void drawTree(boolean makeRendering) {
        DrawTree drawTree = new DrawTree(makeRendering);
//        drawTree.execute();
        drawTree.doInBackground();
        drawTree.draw();
    }

    public class DrawTree extends SwingWorker {
        private boolean makeRendering;

        public DrawTree(boolean makeRendering) {
            this.makeRendering = makeRendering;
        }

        public Object doInBackground() {
            //Получаем все корни деревьев
            ArrayList<Node> roots = model.getRoots();
            //Получаем одиночные элементы
            ArrayList<Node> singleNodes = model.getSingleNodes();
            //Получаем одиночные менеджеры
            ArrayList<Node> singleManager = new ArrayList<Node>();
            //Получаем одиночные рабочие
            ArrayList<Node> singleWorker = new ArrayList<Node>();
            for (Node node : singleNodes) {
                if (((MyTreeNode) node).getType() == MyTreeNode.MANAGER) {
                    singleManager.add(node);
                }
                if (((MyTreeNode) node).getType() == MyTreeNode.INGENER) {
                    singleWorker.add(node);
                }

            }
            int widthPanel = 0;
            int heightPanel = 0;

            int step = 0;
            int[] heightStep = null;
            //Достраиваем до полного
            if (roots != null) {
                heightStep = new int[roots.size()];
                for (Node root : roots) {
                    int countLevelsInTree = model.getCountLevelsInTree(root);
                    if (countLevelsInTree <= 0) break;
                    ArrayList<Node> nodesThisLevel;
                    for (int level = 1; level < countLevelsInTree - 1; level++) {
                        nodesThisLevel = model.getNodesLevel(level, root);
                        boolean existChild = false;
                        for (Node node : nodesThisLevel) {
                            if (node.getChildsNode() != null && node.getChildsNode().size() > 0) {
                                existChild = true;
                            }
                        }
                        if (existChild) {
                            for (Node node : nodesThisLevel) {
                                int count = (node.getChildsNode() == null ? 0 : node.getChildsNode().size());
                                if (count == 0) {
                                    Node n = new MyTreeNode("", MyTreeNode.INGENER);
                                    n.setExistRealTree(false);
                                    model.addNode(node, n);
                                }
                            }
                        }
                    }
                }
            }


            //Считаем размеры
            step = 0;
            if (singleManager.size() > 0) {
                heightPanel = heightPanel + heightNode + betweenNodeVertical;
                widthPanel = singleManager.size() * heightNode + (2 + singleManager.size()) * betweenNodeVertical;
            }
            if (singleWorker.size() > 0) {
                heightPanel = heightPanel + heightNode + betweenNodeVertical;
                widthPanel = singleWorker.size() * heightNode + (2 + singleWorker.size()) * betweenNodeVertical;
            }
            if (roots != null) {
                for (Node root : roots) {
                    int countLevel = model.getCountLevelsInTree(root);
                    int countWidth = model.getMaxCountWidth(root);

                    int tmpWidth = countWidth * widthNode + (2 + countWidth) * betweenNodeHorizontal;
                    if (tmpWidth > widthPanel) {
                        widthPanel = tmpWidth;
                    }
                    heightPanel = heightPanel + countLevel * heightNode + (2 + countLevel) * betweenNodeVertical;
                    heightStep[step] = heightPanel;
                    step++;
                }
            }

            setMinimumSize(new Dimension(widthPanel, heightPanel));
            setPreferredSize(new Dimension(widthPanel, heightPanel));


            //Высчитываем положение каждого на экране
            step = 0;
            if (singleManager.size() > 0) {
                int x = betweenNodeHorizontal;
                int y = betweenNodeVertical;
                for (Node node : singleManager) {
                    node.setTmpLocation(x, y);
                    x = x + widthNode + betweenNodeHorizontal;
                }
            }
            if (singleWorker.size() > 0) {
                int x = betweenNodeHorizontal;
                int y = betweenNodeVertical;
                if (singleManager.size() > 0) {
                    y = y + betweenNodeVertical + widthNode;
                }
                for (Node node : singleWorker) {
                    node.setTmpLocation(x, y);
                    x = x + widthNode + betweenNodeHorizontal;
                }
            }


            //Данная переменная нужна для того, что бы рабочие располагались на нижнем ряду!!!
            int tmpLication;

            if (roots != null) {
                for (Node root : roots) {
                    int countLevelsInTree = model.getCountLevelsInTree(root);
                    if (countLevelsInTree <= 0) break;
                    int x = betweenNodeHorizontal;
                    int y = heightStep[step] - betweenNodeVertical - heightNode;
                    tmpLication = y;
                    ArrayList<Node> nodes;
                    for (int level = countLevelsInTree; level > 0; level--) {
                        nodes = model.getNodesLevel(level - 1, root);
                        Integer[] count = new Integer[1];
                        count[0] = 0;
                        if (level == countLevelsInTree) {
                            for (Node node : nodes) {
                                node.setTmpLocation(x, y);
                                x = x + widthNode + betweenNodeHorizontal;
                            }
                        } else {
                            for (Node node : nodes) {
                                ArrayList<Node> childs = node.getChildsNode();
                                int left = (int) childs.get(0).getTmpLocation()[0];
                                int right = (int) childs.get(childs.size() - 1).getTmpLocation()[0];
                                //Данное условие нужно для того, что бы рабочие располагались на нижнем ряду!!!
                                if (((MyTreeNode) node).getType() == MyTreeNode.INGENER) {
                                    node.setTmpLocation((int) ((left + right) / 2), tmpLication);
                                } else {
                                    node.setTmpLocation((int) ((left + right) / 2), y);
                                }
                            }
                        }
                        y = y - betweenNodeVertical - heightNode;
                    }
                    step++;
                }
            }


            //Удаляем виртуальные элементы дерева
            ArrayList<Node> nodeForDel = new ArrayList<Node>();
            for (Node node : model.getAllNodesTree()) {
                if (!node.isExistRealTree())
                    nodeForDel.add(node);
            }
            for (Node node : nodeForDel) {
                model.removeNode(false, node);
            }


            //Рисуем дерево
            ArrayList<Node> nods = model.getAllNodesTree();
            for (Node nod : nods) {
                if (makeRendering) {
                    new Drawer(this, nod, nod.getTmpLocation()[0], nod.getTmpLocation()[1], (int) nod.getShape().getBounds().getX(), (int) nod.getShape().getBounds().getY()).execute();
                } else {
                    AffineTransform af = new AffineTransform();
                    af.translate(nod.getTmpLocation()[0] - nod.getShape().getBounds().getX(), nod.getTmpLocation()[1] - nod.getShape().getBounds().getY());
                    nod.transform(af);
                }
            }
            return null;
        }


        protected void draw() {
            tree.repaint();
        }
    }

    public void addComponentNode(Node node) {
        this.add(node);
        repaint();
    }

    public void removeComponentNode(Node node) {
        Component[] mc = getComponents();
        for (int i = 0; i < mc.length; i++) {
            if (node == mc[i]) {
                remove(i);
                break;
            }
        }
        repaint();
    }

    public void addComponentBetweenNodes(ConnectLine line) {
        this.add(line);
        repaint();
    }

    public void removeComponentBetweenNodes(ConnectLine line) {
        //this.remove(line);
        Component[] mc = getComponents();
        int k = 0;
        for (int i = 0; i < mc.length; i++) {
            if (line == mc[i]) {
                remove(i);
                break;
            }
        }
        repaint();
    }

    class Drawer extends SwingWorker {
        private Node node;
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        private int tx, ty, kx, ky;
        private Integer[] count;
        private DrawTree drawTree;

        Drawer(DrawTree drawTree, Node node, int _x1, int _y1, int _x2, int _y2) {
            this.drawTree = drawTree;
            this.node = node;
            this.x1 = _x1; //Нужное положение
            this.y1 = _y1; //Нужное положение
            this.x2 = _x2; //Текущее положение
            this.y2 = _y2; //
            this.count = count;
            tx = x2;
            ty = y2;
            kx = ((_x1 - _x2) > 0 ? 1 : -1);
            ky = ((_y1 - _y2) > 0 ? 1 : -1);
            kx = (x1 == x2) ? 0 : kx;
            ky = (y1 == y2) ? 0 : ky;
        }

        @Override
        protected Object doInBackground() throws Exception {
            while (tx != x1 || ty != y1) {
                if (tx != x1) tx += kx;
                if (ty != y1) ty += ky;
                AffineTransform af = new AffineTransform();
//                af.translate(-10,10);
                af.translate((tx != x1 ? kx : 0), (ty != y1 ? ky : 0));
//                  af.translate(kx,ky);
                node.transform(af);
//                tree.repaint();
                publish(new ArrayList());
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            //   drawTree.setCount();
            //    count[0] = count[0] + 1;
            return null;
        }

//        protected void done() {
//        }

        protected void process(java.util.List chunks) {
            drawTree.draw();
        }


//        public void run() {
//            while (tx != x1 || ty != y1) {
//                if (tx != x1) tx += kx;
//                if (ty != y1) ty += ky;
//                AffineTransform af = new AffineTransform();
////                af.translate(-10,10);
//                af.translate((tx != x1 ? kx : 0), (ty != y1 ? ky : 0));
////                  af.translate(kx,ky);
//                node.transform(af);
//                tree.repaint();
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//            count[0] = count[0] + 1;
//        }
    }
}
