package start;

import model.manager.Controller;
import model.manager.TreeModelManager;
import model.tree.ManagerInitTree;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 13.02.2011
 * Time: 23:29:32
 * To change this template use File | Settings | File Templates.
 */
public class Run {
    public static void main(String[] args) {
           try {
            UIManager.setLookAndFeel(new com.jtattoo.plaf.mcwin.McWinLookAndFeel());
        } catch (Exception e) {
//            e.printStackTrace();
        }
        Controller.get().init();
//        TreeModelManager treeManager = new TreeModelManager().init();
        DataManager dataManager = new DataManager();
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.CREATE_INIT);
//        ManagerInitTree.getInstance().init(ManagerInitTree.CREATE_INIT, null);
        TreeModelManager treeManager = new TreeModelManager().init();
        ControllerStart c = new ControllerStart(dataManager);
        TestImg.getInstance().setControllerStart(c);
        ManagerInitTree.getInstance().getControllerTree().addEventListener(c);
        int w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        c.getfStart().setLocation(w / 2 - c.getfStart().getWidth() / 2, h / 2 - c.getfStart().getHeight() / 2);
        c.getfStart().setVisible(true);
    }
}
