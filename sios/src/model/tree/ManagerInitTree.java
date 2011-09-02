/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 21.12.2010
 * Time: 15:15:40
 * To change this template use File | Settings | File Templates.
 */
package model.tree;


import model.manager.TreeModelManager;
import model.tree.controller.ControllerTree;
import model.tree.controller.EventTree;
import model.tree.gui.MyTree;
import model.tree.gui.TreePanel;
import model.tree.model.DefaulModelMyTree;


public class ManagerInitTree {
    public final static int BUILDER_INIT = 0;
    public final static int SINTHES_INIT = 1;
    public final static int PROGRESS_INIT = 2;
    public final static int CREATE_INIT = 3;
    public final static int ANALYS_INIT = 4;
    private static ManagerInitTree ourInstance = new ManagerInitTree();
    private int initParam;

    public static ManagerInitTree getInstance() {
        return ourInstance;
    }

    private MyTree myTree;
    private TreePanel treePanel;
    private ControllerTree controllerTree;
    private DefaulModelMyTree modelTree;
    private TreeUpdateManager treeManager;
    private TreeModelManager treeModelManager;


    private ManagerInitTree() {
        modelTree = new DefaulModelMyTree();
        myTree = new MyTree(modelTree);
        treePanel = new TreePanel(myTree);
        controllerTree = new ControllerTree(treePanel, myTree, BUILDER_INIT);
//           controllerTree.addEventListener(controllerEvent);
    }

    public void addControllerEvent(EventTree controllerEvent) {
        controllerTree.addEventListener(controllerEvent);
    }

    public void setTreeInit(int paramInit) {
        this.initParam = paramInit;
        controllerTree.init(paramInit);
    }


//    //see BUILDER_INIT and SINTHES_INIT
//    public void init(int paramInit, EventTree controllerEvent) {
//        this.initParam = paramInit;
//        modelTree = new DefaulModelMyTree();
//        myTree = new MyTree(modelTree);
//        treePanel = new TreePanel(myTree);
//        controllerTree = new ControllerTree(treePanel, myTree, paramInit);
//        controllerTree.addEventListener(controllerEvent);
//    }

    public MyTree getMyTree() {
        return myTree;
    }

    public TreePanel getTreePanel() {
        return treePanel;
    }

    public ControllerTree getControllerTree() {
        return controllerTree;
    }

    public DefaulModelMyTree getModelTree() {
        return modelTree;
    }

    public TreeUpdateManager getTreeManager() {
        return treeManager;
    }

    public void setTreeManager(TreeUpdateManager treeManager) {
        this.treeManager = treeManager;
    }

    public TreeModelManager getTreeModelManager() {
        return treeModelManager;
    }

    public void setTreeModelManager(TreeModelManager treeModelManager) {
        this.treeModelManager = treeModelManager;
    }

    public int getInitParam() {
        return initParam;
    }
}
