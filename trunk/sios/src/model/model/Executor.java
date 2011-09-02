package model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: parshin
 * Date: 19.11.2010
 * Time: 14:13:14
 */

public class Executor implements Serializable {

    private static final long serialVersionUID = 41L;    

    private int idExecutor;
    private String nameOfExecutor;
    private ArrayList<SpecQualify> specQualifies;
    private ArrayList<Task> tasks;
//    private int numberOfExecutor;
    private boolean isNew; //для блока прогресс

    public Executor(String nameOfExecutor, ArrayList<SpecQualify> specQualifies, ArrayList<Task> tasks) {
//        idExecutor = Helper.getId();
        idExecutor = -1;
        this.nameOfExecutor = nameOfExecutor;
        if (nameOfExecutor == null) this.nameOfExecutor = "Безымянный";
        if (specQualifies != null) this.specQualifies = specQualifies;
        else this.specQualifies = new ArrayList<SpecQualify>();
        if (tasks != null) this.tasks = tasks;
        else this.tasks = new ArrayList<Task>();
    }

    public Executor(String nameOfExecutor, ArrayList<SpecQualify> specQualifies, ArrayList<Task> tasks, boolean aNew) {
        this(nameOfExecutor, specQualifies, tasks);
        isNew = aNew;
    }

    public int getIdExecutor() {
        return idExecutor;
    }

    void setId(int idExecutor) {
        this.idExecutor = idExecutor;
    }

    public String getNameOfExecutor() {
        return nameOfExecutor;
    }

    public ArrayList<SpecQualify> getSpecQualifies() {
        return specQualifies;
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<Task> getExecTasks() {
        ArrayList<Task> execTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task instanceof ManageTask) {
                continue;
            } else {
                execTasks.add(task);
            }
        }
        return execTasks;
    }

    public ArrayList<ManageTask> getManageTasks() {
        ArrayList<ManageTask> manageTasks = new ArrayList<ManageTask>();
        for (Task task : tasks) {
            if (task instanceof ManageTask) {
                manageTasks.add((ManageTask) task);
            }
        }
        return manageTasks;
    }

    public int getSumTime() {
        int time = 0;
        for (Task task : tasks) {
            time += task.getTimeInMinutes();
        }
        return time;
    }

    void setNameOfExecutor(String nameOfExecutor) {
        this.nameOfExecutor = nameOfExecutor;
    }

    void setSpecQualifies(ArrayList<SpecQualify> specQualifies) {
        if (specQualifies != null) this.specQualifies = specQualifies;
        else this.specQualifies = new ArrayList<SpecQualify>();
    }

    void setTasks(ArrayList<Task> tasks) {
        if (tasks != null) this.tasks = tasks;
        else this.tasks = new ArrayList<Task>();
    }

    boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    void removeSpecQualifies(Collection<SpecQualify> specQualifies) {
        this.specQualifies.removeAll(specQualifies);
    }

    boolean removeSpecQualify(SpecQualify specQualify) {
        return this.specQualifies.remove(specQualify);
    }

    void addTask(Task task) {
        tasks.add(task);
    }

    public int getNumberOfExecutor() {
        return getIdExecutor();
    }

    public ArrayList<Executor> getManagedExecutors() {
        ArrayList<Executor> executors = new ArrayList<Executor>();
        for (ManageTask task : getManageTasks()) {
            executors.add(task.getManagedExecutor());
        }
        return executors;
    }

    public ArrayList<Speciality> getSpecialities() {
        ArrayList<Speciality> spec = new ArrayList<Speciality>(specQualifies.size());
        for (SpecQualify sq : specQualifies) {
            spec.add(sq.getSpeciality());
        }
        return spec;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return nameOfExecutor;
    }
}