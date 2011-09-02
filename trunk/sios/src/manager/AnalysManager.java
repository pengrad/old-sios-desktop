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
public class AnalysManager {

    //Перегружен ли Executor

    public static boolean isOutOfTimeExecutor(Executor executor) {
        return getSumTime(executor) > Controller.get().getOptionsManager().getMaxTime();
    }

    //Может ли выполнять исполнительсвои задачи, соответсвуюбт ли они квалификации и специализации

    public static boolean isMakeTaskExecutor(Executor executor) {
        if (executor == null) return false;
        int count = 0;
        ArrayList<Task> execTask = executor.getExecTasks();
        if (execTask.size() == 0) return true;
        ArrayList<SpecQualify> specQualifies = executor.getSpecQualifies();
        for (Task task : execTask) {
            SpecQualify taskSpecQualify = task.getMinSpecQualify();
            for (SpecQualify sq : specQualifies) {
                if (sq.getSpeciality().equals(taskSpecQualify.getSpeciality())) {
                    if (sq.getIdSpecQualify() >= taskSpecQualify.getIdSpecQualify()) count++;
                }
            }
        }
        return count == execTask.size();
    }

    public static boolean isMakeTaskExecutor(Executor executor, Task task) {
        if (executor == null||task==null) return false;
        ArrayList<SpecQualify> specQualifies = executor.getSpecQualifies();
            SpecQualify taskSpecQualify = task.getMinSpecQualify();
            for (SpecQualify sq : specQualifies) {
                if (sq.getSpeciality().equals(taskSpecQualify.getSpeciality())) {
                    if (sq.getIdSpecQualify() >= taskSpecQualify.getIdSpecQualify()) return true;
                }
            }

        return false;
    }

    private static int getSumTime(Executor executor) {
        int time = 0;
        for (Task task : executor.getExecTasks()) {
            time = time + task.getTimeInMinutes();
        }
        return executor.getManagedExecutors().size() * Controller.get().getOptionsManager().getManageTaskTime() + time;
    }

}
