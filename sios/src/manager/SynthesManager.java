package manager;

import model.manager.Controller;
import model.util.TaskComparator;
import model.model.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 22.12.2010
 * Time: 14:35:06
 * To change this template use File | Settings | File Templates.
 */
public class SynthesManager {

    public Collection<Executor> createExecutorOld(Collection<Task> tasks) {
        if (tasks == null || tasks.size() == 0) return null;
        Collection<Executor> executors = new ArrayList<Executor>();
        //Групперуем по специализации
        HashMap<Speciality, ArrayList<Task>> groupTasks = getGroupTasksBySpeciality(tasks);
        if (groupTasks != null && groupTasks.size() != 0) {
            Set<Speciality> keys = groupTasks.keySet();
            Iterator<Speciality> itSpeciality = keys.iterator();
            while (itSpeciality.hasNext()) {
                Speciality speciality = itSpeciality.next();
                //Получаем задачи группы и сортируем по убыванию специализации
                Collection<Task> sortTasksBySpec = getSortTasksByQualifyAndTime(groupTasks.get(speciality));

                for (Task task : sortTasksBySpec) {
                    boolean createNewExecutor = true;
                    for (Executor executor : executors) {
                        //Учитывая что у пользователя одна специализация
                        if (executor.getSpecQualifies().get(0).getSpeciality().equals(task.getMinSpecQualify().getSpeciality()) && ((executor.getSumTime() + task.getTimeInMinutes()) <= getTimeMaxP())) {
                            addTaskToExecutor(executor, task);
                            createNewExecutor = false;
                            break;
                        }
                    }
                    if (createNewExecutor) {
                        Executor executor = new Executor(null, asArrayList(task.getMinSpecQualify()), asArrayList(task));
                        executors.add(executor);
                    }
                }
            }
        }
        ArrayList<Executor> e = new ArrayList<Executor>();
        for (Executor executor : executors) {
            e.add(executor);
        }
        createManager(e, executors, 0);
        return executors;
    }


    public Collection<Executor> createExecutor(Collection<Task> tasks) {
        if (tasks == null || tasks.size() == 0) return null;
        Collection<Executor> executors = new ArrayList<Executor>();
        //Групперуем по специализации
        HashMap<Speciality, ArrayList<Task>> groupTasks = getGroupTasksBySpeciality(tasks);
        if (groupTasks != null && groupTasks.size() != 0) {
            Set<Speciality> keys = groupTasks.keySet();
            Iterator<Speciality> itSpeciality = keys.iterator();
            while (itSpeciality.hasNext()) {
                Speciality speciality = itSpeciality.next();
                //Получаем задачи группы и сортируем по убыванию специализации и времени
                ArrayList<Task> sortTasksBySpec = getSortTasksByQualifyAndTime(groupTasks.get(speciality));
                int time = sumTime(sortTasksBySpec);
                int count = (time % getTimeMaxP() == 0) ? time / getTimeMaxP() : (((int) ((double) time / (double) getTimeMaxP())) + 1);
                for (int i = 0; i < count; i++) {
                    Executor executor = new Executor(null, asArrayList(sortTasksBySpec.get(0).getMinSpecQualify()), asArrayList(sortTasksBySpec.get(0)));
                    ArrayList<ArrayList<Task>> tmpTasks = new ArrayList<ArrayList<Task>>();
                    for (int k = 1; k < sortTasksBySpec.size(); k++) {
                        ArrayList<Task> t = new ArrayList<Task>();
                        for (int j = k; j < sortTasksBySpec.size(); j++) {
                            Task task = sortTasksBySpec.get(j);
                            if ((executor.getSumTime() + sumTime(t) + task.getTimeInMinutes()) <= getTimeMaxP()) {
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
                    sortTasksBySpec.remove(sortTasksBySpec.get(0));
                    executors.add(executor);
                }
            }
        }

        ArrayList<Executor> e = new ArrayList<Executor>();
        for (Executor executor : executors) {
            e.add(executor);
        }

        createManager(e, executors, 0);
        executors = balans(executors);


        return executors;
    }

    private Collection<Executor> balans(Collection<Executor> executors) {
        return executors;
    }

    //TODO  нодо переписать эту херь

    private int[] toSr(int countExecutors) {
        int countManageTask = (int) (getTimeMaxP() / getManageTaskTime());
        double tmp = (double) countExecutors / (double) countManageTask;
        int countManagerInLevel = (((int) tmp) == tmp ? (int) tmp : (((int) tmp) + 1));
        int[] m = new int[countManagerInLevel];
        int sum = 0;
        for (int i = 0; i < countManageTask; i++) {
            for (int j = 0; j < countManagerInLevel; j++) {
                if (sum > countExecutors - 1) break;
                sum++;
                m[j]++;
            }
        }
        int p = 0;
        int[] mm = new int[countExecutors];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i]; j++) {
                mm[p] = m[i];
                p++;
            }
        }
        return mm;
    }


    private void createManager(Collection<Executor> levelExecutors, Collection<Executor> executors, int level) {
        if ((getManageTaskTime() * 2) > getTimeMaxP() || levelExecutors == null || levelExecutors.size() == 0) return;
        Collection<Executor> e = new ArrayList<Executor>();
        Executor manager = new Executor(null, asArrayList(Controller.get().getSpecManager().getManageQualify()), null);
        ManageTask mt;
        int p = 0;
        int countSr[] = toSr(levelExecutors.size());
        for (Executor executor : levelExecutors) {
            if ((manager.getSumTime() + getManageTaskTime()) <= getTimeMaxP() && countSr[p] > manager.getManageTasks().size()) {
                mt = new ManageTask(getManageTaskTime(), Controller.get().getSpecManager().getManageQualify(), executor);
                addTaskToExecutor(manager, mt);
            } else {
                if (manager.getManageTasks().size() > 1 || level == 0) {
                    e.add(manager);
                    executors.add(manager);
                } else {
                    e.add(manager.getManageTasks().get(0).getManagedExecutor());
                }
                manager = new Executor(null, asArrayList(Controller.get().getSpecManager().getManageQualify()), null);
                mt = new ManageTask(getManageTaskTime(), Controller.get().getSpecManager().getManageQualify(), executor);
                addTaskToExecutor(manager, mt);
            }
            p++;
        }
        if (manager.getManageTasks().size() > 1 || level == 0) {
            e.add(manager);
            executors.add(manager);
        } else {
            e.add(manager.getManageTasks().get(0).getManagedExecutor());
        }
        if (e.size() > 1) createManager(e, executors, ++level);
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
        ArrayList l = asArrayList(1, 2, 3, 4, 5);
        for (Object o : l) {
//            if(l.size()<=0) break;
            System.out.println(o);
            if (new Integer(o.toString()) == 2) {
                l.add(6);
            }


        }
    }

}
