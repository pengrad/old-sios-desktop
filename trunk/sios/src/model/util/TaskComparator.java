package model.util;

import model.manager.SpecialityManager;
import model.model.Task;

import java.util.Comparator;

/**
 * User: Стас
 * Date: 23.12.2010
 * Time: 23:07:38
 */

public class TaskComparator implements Comparator<Task> {
    private SpecialityManager specialityManager;

    public TaskComparator(SpecialityManager specialityManager) {
        this.specialityManager = specialityManager;
    }

    public int compare(Task t1, Task t2) {
        if (t1.getMinSpecQualify().equals(t2.getMinSpecQualify())) {
            if (t1.getTimeInMinutes() == t2.getTimeInMinutes()) {
                return 0;
            } else {
                if (t1.getTimeInMinutes() < t2.getTimeInMinutes()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        } else if (specialityManager.getBestSpecQualify(t1.getMinSpecQualify(), t2.getMinSpecQualify()).equals(t1.getMinSpecQualify())) {
            return -1;
        } else {
            return 1;
        }
    }
}
