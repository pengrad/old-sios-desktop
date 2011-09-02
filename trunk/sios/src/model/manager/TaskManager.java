package model.manager;

import model.model.ManageTask;
import model.model.SpecQualify;
import model.model.Speciality;
import model.model.Task;

import java.util.*;

/**
 * User: parshin
 * Date: 19.11.2010
 * Time: 17:23:52
 */

public class TaskManager extends AbstractManager {

    private Map<Integer, Task> tasks;
    private int currentTaskNumber;
    private int currentManageTaskNumber;

    public TaskManager() {
        tasks = new TreeMap<Integer, Task>();
        currentTaskNumber = 0;
        currentManageTaskNumber = 100000;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>(this.tasks.size());
        tasks.addAll(this.tasks.values());
        return tasks;
    }

    void setTasks(Collection<Task> tasks) {
        this.tasks = new TreeMap<Integer, Task>();
        currentTaskNumber = 0;
        currentManageTaskNumber = 100000;
        for (Task task : tasks) {
            addTask(task, false);
        }
        fireDataChanged(tasks);
    }

    @Override
    void resetData() {
        tasks = new TreeMap<Integer, Task>();
        currentTaskNumber = 0;
        currentManageTaskNumber = 100000;
        fireDataChanged(tasks.values());
    }

    void addTask(Task task) {
        addTask(task, true);
    }

    // главный метод добавления тасков. Надо следить, чтобы в мэп tasks добавлял только он

    private void addTask(Task task, boolean withFire) {
        Task oldTask = tasks.get(task.getIdTask());        
        if (oldTask == null) {
            task.setIdTask((task instanceof ManageTask) ? ++currentManageTaskNumber : ++currentTaskNumber);
        }
        tasks.put(task.getIdTask(), task);
        if (withFire) fireDataChanged(task);
    }

    void removeTask(Task task) {
        removeTask(task.getIdTask());
    }

    void removeTask(int idTask) {
        tasks.remove(idTask);
        fireDataChanged(tasks.values());
    }

    void addManageTasks(Collection<ManageTask> tasks) {
        for (Task task : tasks) {
            addTask(task, false);
        }
        fireDataChanged(tasks);
    }

    void removeManageTasks(Collection<ManageTask> tasks) {
        for (Task task : tasks) {
            this.tasks.remove(task.getIdTask());
        }
        fireDataChanged(this.tasks.values());
    }

    void changeTask(int idEditableTask, Task newTask) {
        Task task = tasks.get(idEditableTask);
        if (task == null) return;
        changeTask(task, newTask);
    }

    void changeTask(Task task, Task newTask) {
        task.setMinSpecQualify(newTask.getMinSpecQualify());
        task.setNameOfTask(newTask.getNameOfTask());
        task.setTimeInMinutes(newTask.getTimeInMinutes());
        fireDataChanged(task);
    }

    public ArrayList<Task> getExecTasks() {
        ArrayList<Task> execTasks = new ArrayList<Task>();
        for (Task task : tasks.values()) {
            if (!(task instanceof ManageTask)) {
                execTasks.add(task);
            }
        }
        return execTasks;
    }

    public Integer[] getExecTaskIds() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Task task : getExecTasks()) {
            ids.add(task.getIdTask());
        }
        return ids.toArray(new Integer[0]);
    }

    public Integer[] getOldExecTaskIds() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Task task : getExecTasks()) {
            if (!task.isNew()) ids.add(task.getIdTask());
        }
        return ids.toArray(new Integer[0]);
    }

    public Integer[] getNewExecTaskIds() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Task task : getNewExecTasks()) {
            ids.add(task.getIdTask());
        }
        return ids.toArray(new Integer[0]);
    }

    public Collection<Task> getNewExecTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (Task task : getExecTasks()) {
            if (task.isNew()) tasks.add(task);
        }
        return tasks;
    }

    public Collection<Task> getAllNewTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (Task task : this.tasks.values()) {
            if (task.isNew()) tasks.add(task);
        }
        return tasks;
    }

    public ArrayList<ManageTask> getManageTasks() {
        ArrayList<ManageTask> manageTasks = new ArrayList<ManageTask>();
        for (Task task : tasks.values()) {
            if ((task instanceof ManageTask)) {
                manageTasks.add((ManageTask) task);
            }
        }
        return manageTasks;
    }

    public Task getTaskById(int idTask) {
        return tasks.get(idTask);
    }

    public ArrayList<Task> getFreeTasksForSpecQualify(SpecQualify specQualify) {
        return getFreeTasksForSpecQualify(Arrays.asList(specQualify));
    }

    public ArrayList<Task> getFreeTasksForSpecQualify(Collection<SpecQualify> specQualifies) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (Task task : this.tasks.values()) {
            if (!isFreeTask(task)) continue;
            SpecQualify taskSQ = task.getMinSpecQualify();
            for (SpecQualify specQualify : specQualifies) {
                if (taskSQ.equals(specQualify)) {
                    tasks.add(task);
                } else {
                    SpecQualify best = Controller.get().getSpecManager().getBestSpecQualify(taskSQ, specQualify);
                    if (best != null && best.equals(specQualify)) tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public ArrayList<Task> getAllTasksBySpeciality(Speciality speciality) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (Task task : this.tasks.values()) {
            if (task.getMinSpecQualify().getSpeciality().equals(speciality)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public ArrayList<Task> getAllTasksBySpecQualify(SpecQualify specQualify) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (Task task : this.tasks.values()) {
            if (task.getMinSpecQualify().equals(specQualify)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public ArrayList<SpecQualify> getSpecQualifiesHavingFreeTasks(Collection<SpecQualify> specQualifies, Collection<Task> excludedTasks) {
        ArrayList<SpecQualify> sq = new ArrayList<SpecQualify>();
        ArrayList<Task> freeTasks = getFreeTasksForSpecQualify(specQualifies);
        freeTasks.removeAll(excludedTasks);
        for (SpecQualify specQualify : specQualifies) {
            for (Task task : freeTasks) {
                if (!task.getMinSpecQualify().getSpeciality().equals(specQualify.getSpeciality())) continue;
                if (Controller.get().getSpecManager().getBetterSpecQualifies(task.getMinSpecQualify()).contains(specQualify)) {
                    sq.add(specQualify);
                    break;
                }
            }
        }
        return sq;
    }

    public boolean isFreeTask(Task task) {
        return Controller.get().getEmplManager().getExecutorByTask(task) == null;
    }

    public boolean hasTasksBySpecQualify(SpecQualify specQualify) {
        for (Task task : tasks.values()) {
            if (task.getMinSpecQualify().equals(specQualify)) return true;
        }
        return false;
    }

    public void setTimeToManageTasks(int newManageTaskTime) {
        for (ManageTask task : getManageTasks()) {
            task.setTimeInMinutes(newManageTaskTime);
        }
        fireDataChanged(tasks.values());
    }

    void cutTaskTimeByMaxTime(int maxTime) {
        for (Task task : tasks.values()) {
            if (task.getTimeInMinutes() > maxTime) {
                task.setTimeInMinutes(maxTime);
            }
        }
        fireDataChanged(tasks.values());
    }
}