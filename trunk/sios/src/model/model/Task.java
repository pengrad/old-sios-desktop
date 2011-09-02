package model.model;

import java.io.Serializable;

/**
 * User: parshin
 * Date: 19.11.2010
 * Time: 14:03:37
 */

public class Task implements Serializable {

    private static final long serialVersionUID = 42L;

    private int idTask;
    private String nameOfTask;
    private int timeInMinutes;
    private SpecQualify minSpecQualify;
//    private int numberOfTask;
    private boolean isNew; // для блока прогресс

    public Task(String nameOfTask, int timeInMinutes, SpecQualify minSpecQualify) {
        idTask = -1;
        this.nameOfTask = nameOfTask;
        this.timeInMinutes = timeInMinutes;
        this.minSpecQualify = minSpecQualify;
    }

    public Task(String nameOfTask, int timeInMinutes, SpecQualify minSpecQualify, boolean aNew) {
        this(nameOfTask, timeInMinutes, minSpecQualify);
        isNew = aNew;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public SpecQualify getMinSpecQualify() {
        return minSpecQualify;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public void setMinSpecQualify(SpecQualify minSpecQualify) {
        this.minSpecQualify = minSpecQualify;
    }

    public int getNumberOfTask() {
        return getIdTask();
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return nameOfTask;
    }
}
