package test;

import manager.TreeEventManager;
import manager.GUIManager;
import model.manager.Controller;
import model.manager.TreeModelManager;
import model.tree.ManagerInitTree;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 03.04.2011
 * Time: 16:39:31
 * To change this template use File | Settings | File Templates.
 */
public class Run {
    public static void main(String[] args) {
        Controller.get().init();
        GUIManager gui = new GUIManager();
        TreeEventManager c = new TreeEventManager(gui);
//        ManagerInitTree.getInstance().init(ManagerInitTree.BUILDER_INIT, c);
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.BUILDER_INIT);
        ManagerInitTree.getInstance().addControllerEvent(c);
        TreeModelManager treeManager = new TreeModelManager().init();
        gui.init().showFrame();
//          gui.init(new JPanel()).showFrame();
        Controller.get().fireAllData();
    }
}
