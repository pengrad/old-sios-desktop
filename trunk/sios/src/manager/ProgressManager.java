package manager;

import model.manager.Controller;
import model.model.*;
import model.util.TaskComparator;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 22.12.2010
 * Time: 14:35:06
 * To change this template use File | Settings | File Templates.
 */
public class ProgressManager {

    private void insertManager(ArrayList<Executor> oldExec, ArrayList<Executor> newExec, ArrayList<Executor> executors) {
        if ((getManageTaskTime() * 2) > getTimeMaxP() || newExec == null || newExec.size() == 0) return;
        ArrayList<Executor> managers = new ArrayList<Executor>();
        for (Executor executor : oldExec) {
            Executor manager = Controller.get().getEmplManager().getManagerOfExecutor(executor);
            if (manager != null && !managers.contains(manager)) {
                managers.add(manager);
            }
        }
        ManageTask mt = null;
        ArrayList<Executor> newManagers = new ArrayList<Executor>();
        //Если мы дошли до вершины
        if (managers.size() == 0) {
            for (Executor executor : oldExec)
                newExec.add(executor);
        }
        //Добавляем существующим менеджерам
        for (Executor executor : newExec) {
            boolean add = false;
            for (Executor manager : managers) {
                if ((manager.getSumTime() + getManageTaskTime()) <= getTimeMaxP()) {
                    mt = new ManageTask(getManageTaskTime(), Controller.get().getSpecManager().getManageQualify(), executor, true);
                    addTaskToExecutor(manager, mt);
                    executors.add(manager);
                    add = true;
                    break;
                }
            }
            if (!add) {
                //Добавляем ранее созданым менеджерам
                for (Executor manager : newManagers) {
                    if ((manager.getSumTime() + getManageTaskTime()) <= getTimeMaxP()) {
                        mt = new ManageTask(getManageTaskTime(), Controller.get().getSpecManager().getManageQualify(), executor, true);
                        addTaskToExecutor(manager, mt);
                        executors.add(manager);
                        add = true;
                        break;
                    }
                }
                if (!add) {
                    //Создаем менеджкра
                    mt = new ManageTask(getManageTaskTime(), Controller.get().getSpecManager().getManageQualify(), executor, true);
                    Executor m = new Executor(null, asArrayList(Controller.get().getSpecManager().getManageQualify()), null, true);
                    addTaskToExecutor(m, mt);
                    newManagers.add(m);
                    executors.add(m);
                }
            }
        }
        //услови  выхода
        if (managers.size() == 0 && newManagers.size() == 1) {
            return;
        }

        insertManager(managers, newManagers, executors);

    }


    public Collection<Executor> insertExecutor(Collection<Task> tasks) {
        if (tasks == null || tasks.size() == 0) return new ArrayList<Executor>(0);
        Collection<Task> tt = new ArrayList<Task>();
        for (Task it : tasks) {
        tt.add(it);
        }
        for (Task it : tasks) {
            for (Executor ex : Controller.get().getEmplManager().getAllExecutors()) {
                if (ex.getExecTasks().contains(it)) {
                tt.remove(it);
                }
            }
        }
        tasks=tt;
        if (tasks.size() == 0) return new ArrayList<Executor>(0);

        Collection<Executor> allExecutors = new ArrayList<Executor>();
        for (Executor e : Controller.get().getEmplManager().getWorkExecutors()) {
            allExecutors.add(e);
        }
        ArrayList<Executor> newExecutors = new ArrayList<Executor>();
        //Групперуем по специализации
        HashMap<Speciality, ArrayList<Task>> groupTasks = getGroupTasksBySpeciality(tasks);
        if (groupTasks != null && groupTasks.size() != 0) {
            Set<Speciality> keys = groupTasks.keySet();
            Iterator<Speciality> itSpeciality = keys.iterator();
            while (itSpeciality.hasNext()) {
                Speciality speciality = itSpeciality.next();
                //Получаем задачи группы и сортируем по убыванию специализации и времени
                ArrayList<Task> sortTasksBySpec = getSortTasksByQualifyAndTime(groupTasks.get(speciality));
//                while (!sortTasksBySpec.isEmpty()) {
                for (Executor executor : allExecutors) {
                    ArrayList<ArrayList<Task>> tmpTasks = new ArrayList<ArrayList<Task>>();
                    for (int k = 0; k < sortTasksBySpec.size(); k++) {
                        ArrayList<Task> t = new ArrayList<Task>();
                        for (int j = k; j < sortTasksBySpec.size(); j++) {
                            Task task = sortTasksBySpec.get(j);
                            if (executor.getSpecialities().contains(task.getMinSpecQualify().getSpeciality()) && (executor.getSumTime() + sumTime(t) + task.getTimeInMinutes()) <= getTimeMaxP()) {
                                t.add(task);
                            }
                        }
                        if (t.size() > 0)
                            tmpTasks.add(t);
                    }
                    if (tmpTasks.size() > 0) {
                        int max = 0;
                        int pos = 0;
                        for (int p = 0; p < tmpTasks.size(); p++) {
                            ArrayList<Task> lTasks = tmpTasks.get(p);
                            if (sumTime(lTasks) > max) {
                                max = sumTime(lTasks);
                                pos = p;
                            }
                        }
                        ArrayList<Task> bestTasks = tmpTasks.get(pos);
                        for (Task task : bestTasks) {
                            addTaskToExecutor(executor, task);
                            sortTasksBySpec.remove(task);
                        }
                    }
                }
                //Если существующим исполнителям не удалось раздать задачи создаем новых
                for (Task task : sortTasksBySpec) {
                    boolean flg = true;
                    for (Executor executor : allExecutors) {
                        if (executor.getSpecialities().contains(task.getMinSpecQualify().getSpeciality()) && (executor.getSumTime() + task.getTimeInMinutes()) <= getTimeMaxP()) {
                            addTaskToExecutor(executor, task);
                            flg = false;
                            break;
                        }
                    }
                    if (flg) {
                        Executor newExecutor = new Executor(null, asArrayList(task.getMinSpecQualify()), asArrayList(task), true);
                        allExecutors.add(newExecutor);
                        newExecutors.add(newExecutor);
                    }
                }
            }
        }


        ArrayList<Executor> oldExecutors = new ArrayList<Executor>();
        for (Executor executor : Controller.get().getEmplManager().getWorkExecutors()) {
            oldExecutors.add(executor);
        }

        ArrayList<Executor> copyNewExec = new ArrayList<Executor>();
        for (Executor e : newExecutors)
            copyNewExec.add(e);

        insertManager(oldExecutors, newExecutors, copyNewExec);

        return copyNewExec;
    }

    private HashMap<Speciality, ArrayList<Task>> getGroupTasksBySpeciality(Collection<Task> tasks) {
        if (tasks == null || tasks.size() == 0) return null;
        HashMap<Speciality, ArrayList<Task>> groupTask = new HashMap<Speciality, ArrayList<Task>>();
        for (Task task : tasks) {
            ArrayList<Task> ts = groupTask.get(task.getMinSpecQualify().getSpeciality());
            if (ts != null) {
                ts.add(task);
            } else {
                groupTask.put(task.getMinSpecQualify().getSpeciality(), asArrayList(task));
            }
        }
        return groupTask;
    }

    private ArrayList<Task> getSortTasksByQualifyAndTime(ArrayList<Task> tasks) {
        Collections.sort(tasks, new TaskComparator(Controller.get().getSpecManager()));
        return tasks;
    }

    private int sumTime(Collection<Task> tasks) {
        if (tasks == null) return 0;
        int sTime = 0;
        for (Task task : tasks) {
            sTime = sTime + task.getTimeInMinutes();
        }
        return sTime;
    }


    private void addTaskToExecutor(Executor executor, Task task) {
//       executor.getAllTasks().add(task);
//       for(Executor ex:Controller.get().getEmplManager().getAllExecutors()){
//        if(ex.getExecTasks().contains(task)) return;
//       }
        new EmployeeEditor().addTaskToExecutor(executor, task);
    }

    private int getTimeMaxP() {
        return Controller.get().getOptionsManager().getMaxTime();
    }

    private int getManageTaskTime() {
        return Controller.get().getOptionsManager().getManageTaskTime();
    }

    private static <T> ArrayList<T> asArrayList(T... mas) {
        if (mas == null || mas.length == 0) return null;
        ArrayList<T> list = new ArrayList<T>();
        for (T t : Arrays.asList(mas)) {
            list.add(t);
        }
        return list;
    }

    public static void main(String[] args) {
        ArrayList l = asArrayList(1, 2, 3, 4);
        while (!l.isEmpty()) {
            System.out.println("ewf");
            l.remove(0);
        }
    }

}
