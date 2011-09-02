package model.manager;

import model.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: parshin
 * Date: 19.11.2010
 * Time: 17:24:15
 */

public class EmployeeManager extends AbstractManager {

    private Map<Integer, Executor> executors;
    private EmployeeEditor emplEditor;
    private int currentExecutorNumber;

    public EmployeeManager() {
        executors = new HashMap<Integer, Executor>();
        emplEditor = new EmployeeEditor();
        currentExecutorNumber = 0;
    }

    public ArrayList<Executor> getAllExecutors() {
        ArrayList<Executor> executors = new ArrayList<Executor>(this.executors.size());
        executors.addAll(this.executors.values());
        return executors;
    }

    void setExecutors(Collection<Executor> executors) {
        this.executors = new HashMap<Integer, Executor>(executors.size());
        currentExecutorNumber = 0;
        for (Executor executor : executors) {
            emplEditor.setIdOfExecutor(executor, -1);
            addExecutor(executor, false);
        }
        fireDataChanged(this.executors.values());
    }

    @Override
    void resetData() {
        executors = new HashMap<Integer, Executor>();
        currentExecutorNumber = 0;
        fireDataChanged(executors.values());
    }

    // Главный и единственный метод, совершающий пут в мэп исполнителей

    private void addExecutor(Executor executor, boolean withFire) {
        Executor oldExecutor = executors.get(executor.getIdExecutor());
        if (oldExecutor == null) {
            emplEditor.setIdOfExecutor(executor, ++currentExecutorNumber);                        
        }
        executors.put(executor.getIdExecutor(), executor);
        if (withFire) fireDataChanged(executor);
    }

    void addExecutor(Executor executor) {
        addExecutor(executor, true);
    }

    void removeExecutor(Executor executor) {
        executors.remove(executor.getIdExecutor());
        fireDataChanged(executors.values());
    }

    void removeTaskFromExecutors(Task task) {
        for (Executor executor : executors.values()) {
            if (emplEditor.removeTask(executor, task)) {
                fireDataChanged(executor);
                return;
            }
        }
    }

    void removeSpecialityFromExecutors(Speciality speciality) {
        for (Executor executor : executors.values()) {
            emplEditor.removeSpecQualifies(executor, Controller.get().getSpecManager().getSpecQualifies(speciality));
        }
        fireDataChanged(executors.values());
    }

    void removeSpecialityFromExecutor(Executor executor, Collection<Speciality> specialities) {
        for (Speciality speciality : specialities) {
            emplEditor.removeSpecQualifies(executor, Controller.get().getSpecManager().getSpecQualifies(speciality));
        }
        fireDataChanged(executor);
    }

    void removeSpecQualityFromExecutors(SpecQualify specQualify) {
        for (Executor executor : executors.values()) {
            emplEditor.removeSpecQualify(executor, specQualify);
        }
        fireDataChanged(executors.values());
    }

    void addSpecQualifiesToExecutor(Executor executor, Collection<SpecQualify> specQualifies) {
        emplEditor.addSpecQualifies(executor, specQualifies);
    }

    void changeExecutor(int idExec, Executor newExecutor) {
        Executor exec = executors.get(idExec);
        if (exec == null) return;
        changeExecutor(exec, newExecutor);
    }

    void changeExecutor(Executor prevExec, Executor futureExec) {
        emplEditor.changeExecutor(prevExec, futureExec);
        fireDataChanged(prevExec);
    }

    public Integer[] getExecutorIds() {
        return executors.keySet().toArray(new Integer[0]);
    }

    public Integer[] getOldExecutorIds() {
        ArrayList<Integer> execs = new ArrayList<Integer>();
        for (Executor executor : executors.values()) {
            if (!executor.isNew()) execs.add(executor.getIdExecutor());
        }
        return execs.toArray(new Integer[0]);
    }

    public Integer[] getNewExecutorIds() {
        ArrayList<Integer> execs = new ArrayList<Integer>();
        for (Executor executor : executors.values()) {
            if (executor.isNew()) execs.add(executor.getIdExecutor());
        }
        return execs.toArray(new Integer[0]);
    }

    public Collection<Executor> getAllNewExecutors() {
        ArrayList<Executor> execs = new ArrayList<Executor>();
        for (Executor executor : executors.values()) {
            if (executor.isNew()) execs.add(executor);
        }
        return execs;
    }

    // все элементы исполнительного уровня (не менеджеры)

    public Collection<Executor> getWorkExecutors() {
        ArrayList<Executor> executors = new ArrayList<Executor>();
        for (Executor executor : this.executors.values()) {
            if (!isManager(executor)) executors.add(executor);
        }
        return executors;
    }

    public Executor getExecutorById(int id) {
        return executors.get(id);
    }

    public boolean isManager(Executor executor) {
        return Controller.get().getSpecManager().isManager(executor);
    }

    // Логика определения координатора - если среди непосредственных подчиненных есть менеджеры.
    public boolean isCoordinator(Executor executor) {
        for(ManageTask mTask: executor.getManageTasks()) {
            if(isManager(mTask.getManagedExecutor())) return true;
        }
        return false;
    }

    public Executor getExecutorByTask(Task task) {
        for (Executor executor : executors.values()) {
            for (Task t : executor.getAllTasks()) {
                if (t.equals(task)) return executor;
            }
        }
        return null;
    }

    public Executor getManagerOfExecutor(Executor executor) {
        ManageTask manageTask = getManagingTaskOfExecutor(executor);
        return manageTask == null ? null : getExecutorByTask(manageTask);
    }

    public ManageTask getManagingTaskOfExecutor(Executor executor) {
        for (Executor manager : executors.values()) {
            for (ManageTask task : manager.getManageTasks()) {
                if (task.getManagedExecutor().equals(executor)) return task;
            }
        }
        return null;
    }

    public boolean isFreeExecutor(Executor executor) {
        return getManagerOfExecutor(executor) == null;
    }

    public ArrayList<Executor> getFreeExecutors() {
        ArrayList<Executor> freeExecutors = new ArrayList<Executor>();
        for (Executor executor : executors.values()) {
            if (isFreeExecutor(executor)) {
                freeExecutors.add(executor);
            }
        }
        return freeExecutors;
    }

    public ArrayList<Executor> getFreeExecutorsBySpeciality(Speciality speciality) {
        ArrayList<Executor> freeExecutors = getFreeExecutors();
        ArrayList<Executor> freeExecutorBySpec = new ArrayList<Executor>(freeExecutors.size());
        for (Executor executor : freeExecutors) {
            if (executor.getSpecialities().contains(speciality)) {
                freeExecutorBySpec.add(executor);
            }
        }
        return freeExecutorBySpec;
    }

    void removeTasksFromExecutorsOverMaxTime(int maxTime) {
        for (Executor executor : executors.values()) {
            while (executor.getAllTasks().size() > 0 && executor.getSumTime() > maxTime) {
                if (executor.getExecTasks().size() > 0) {
                    emplEditor.removeTask(executor, executor.getExecTasks().get(0));
                } else {
                    ManageTask task = executor.getManageTasks().get(0);
                    emplEditor.removeTask(executor, task);
                    Controller.get().getTaskManager().removeTask(task);
                }
            }
        }
        fireDataChanged(executors.values());
    }

    void addTaskToExecutor(Executor executor, Task task) {
        emplEditor.addTaskToExecutor(executor, task);
        fireDataChanged(executor);
    }
}