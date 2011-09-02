package model.manager;

import model.model.Executor;
import model.tree.ManagerInitTree;
import model.tree.TreeUpdateManager;

import javax.swing.*;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

/**
 * User: Стас
 * Date: 19.12.2010
 * Time: 19:44:10
 */

public class TreeModelManager implements Observer {

    TreeUpdateManager treeManager;

    public  TreeModelManager() {
    }

    public TreeModelManager init() {
        Controller.get().getEmplManager().addObserver(this);
        Controller.get().getSpecManager().addObserver(this);
        Controller.get().getTaskManager().addObserver(this);
        Controller.get().getOptionsManager().addObserver(this);
        treeManager = new TreeUpdateManager(this);
        return this;
    }

    public JPanel getTreePanel() {
        return ManagerInitTree.getInstance().getTreePanel();
    }

    public void update(Observable o, Object arg) {
        treeManager.update(o, arg);
    }

    public Collection<Executor> getFreeExecutors() {
        Collection<Executor> freeExecutors = Controller.get().getEmplManager().getFreeExecutors();
        return freeExecutors;
    }

    public boolean isManager(Executor executor) {
        return Controller.get().getEmplManager().isManager(executor);
    }

    public boolean isCoordinator(Executor executor) {
        return Controller.get().getEmplManager().isCoordinator(executor);
    }

    public void addManagerToExecutor(Executor executor, Executor manager) throws Exception {
        Controller.get().addManagerToExecutor(executor, manager);
    }

    public void removeManagerFromExecutor(Executor executor, Executor manager) {
        Controller.get().removeManagerFromExecutor(executor, manager);
    }

    public void addExecutor(Executor executor) {
        Controller.get().addExecutor(executor);
    }

    public void removeExecutor(Executor executor) {
        Controller.get().removeExecutor(executor);
    }

    public void changeExecutor(int idExecutor, Executor executor) {
        Controller.get().changeExecutor(idExecutor, executor);
    }

    public int getMaxTime() {
        return Controller.get().getOptionsManager().getMaxTime();
    }

}
