package model.manager;

import model.model.Executor;
import model.model.Task;

import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 03.02.11
 * Time: 18:04
 */

abstract public class AbstractTableModelProvider<T> extends Observable implements Observer {

    public static final int DATA_ALL = 0;
    public static final int DATA_OLD = 1;
    public static final int DATA_NEW = 2;

    public int typeOfData;

    abstract public Integer[] getObjectsIds();

    abstract public T getObjectById(int id);

    protected abstract void initObserver();

    public AbstractTableModelProvider() {
        this(DATA_ALL);
    }

    public AbstractTableModelProvider(int typeOfData) {
        if (typeOfData == DATA_OLD || typeOfData == DATA_NEW) {
            this.typeOfData = typeOfData;
        } else {
            this.typeOfData = DATA_ALL;
        }
        initObserver();
    }

    private static Controller getController() {
        return Controller.get();
    }

    public Executor getExecutorByTask(Task task) {
        return getController().getEmplManager().getExecutorByTask(task);
    }

    public boolean isManager(Executor executor) {
        return getController().getEmplManager().isManager(executor);
    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public static class TaskProvider extends AbstractTableModelProvider<Task> {
        public TaskProvider() {
            super();
        }

        public TaskProvider(int typeOfData) {
            super(typeOfData);
        }

        @Override
        protected void initObserver() {
            getController().getTaskManager().addObserver(this);
        }

        @Override
        public Integer[] getObjectsIds() {
            TaskManager tm = getController().getTaskManager();
            if(typeOfData == DATA_ALL) return tm.getExecTaskIds();
            if(typeOfData == DATA_OLD) return tm.getOldExecTaskIds();
            if(typeOfData == DATA_NEW) return tm.getNewExecTaskIds();
            return null;
        }

        @Override
        public Task getObjectById(int id) {
            return getController().getTaskManager().getTaskById(id);
        }
    }

    public static class ExecutorProvider extends AbstractTableModelProvider<Executor> {
        public ExecutorProvider() {
            super();
        }

        public ExecutorProvider(int typeOfData) {
            super(typeOfData);
        }

        @Override
        protected void initObserver() {
            getController().getEmplManager().addObserver(this);
        }

        @Override
        public Integer[] getObjectsIds() {
            EmployeeManager m = getController().getEmplManager();
            if(typeOfData == DATA_ALL) return m.getExecutorIds();
            if(typeOfData == DATA_OLD) return m.getOldExecutorIds();
            if(typeOfData == DATA_NEW) return m.getNewExecutorIds();
            return null;
        }

        @Override
        public Executor getObjectById(int id) {
            return getController().getEmplManager().getExecutorById(id);
        }
    }
}
