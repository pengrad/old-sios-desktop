package model.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Стас
 * Date: 19.12.2010
 * Time: 20:03:55
 */

public class EmployeeEditor {

    public EmployeeEditor() {

    }

    public void changeExecutor(Executor prevExec, Executor newExecutor) {
        prevExec.setNameOfExecutor(newExecutor.getNameOfExecutor());
        prevExec.setSpecQualifies(newExecutor.getSpecQualifies());
        prevExec.setTasks(newExecutor.getAllTasks());
    }

    public boolean removeTask(Executor executor, Task task) {
        return executor.removeTask(task);
    }

    public void removeSpecQualifies(Executor executor, Collection<SpecQualify> specQualifies) {
        executor.removeSpecQualifies(specQualifies);
    }

    public boolean removeSpecQualify(Executor executor, SpecQualify specQualify) {
        return executor.removeSpecQualify(specQualify);
    }

    public void addTaskToExecutor(Executor executor, Task task) {
        executor.addTask(task);
    }

    public void setIdOfExecutor(Executor executor, int numberOfExecutor) {
        executor.setId(numberOfExecutor);
    }

    public void addSpecQualifies(Executor executor, Collection<SpecQualify> specQualifies) {
        ArrayList<SpecQualify> sq = executor.getSpecQualifies();
        sq.addAll(specQualifies);
        executor.setSpecQualifies(sq);
    }

    public void setNew(Executor executor, boolean aNew) {
        executor.setNew(aNew);
    }
}
