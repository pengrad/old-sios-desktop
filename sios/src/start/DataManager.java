package start;

import model.manager.*;
import model.model.Executor;
import model.model.SpecQualify;
import model.model.Speciality;
import model.model.Task;

import java.util.ArrayList;

/**
 * User: parshin
 * Date: 24.11.2010
 * Time: 11:19:42
 */

public class DataManager {

    public SpecialityManager getSpecManager() {
        return Controller.get().getSpecManager();
    }

    public TaskManager getTaskManager() {
        return Controller.get().getTaskManager();
    }

    public EmployeeManager getEmplManager() {
        return Controller.get().getEmplManager();
    }

    public SerializableManager getSerializableManager() {
        return Controller.get().getSerializableManager();
    }

    public void addTask(Task task) {
        Controller.get().addTask(task);
    }

    public void removeTask(Task task) {
        Controller.get().removeTask(task);
    }

    public void changeTask(int idTask, Task newTask) {
        Controller.get().changeTask(idTask, newTask);
    }

    public void addExecutor(Executor executor) {
        Controller.get().addExecutor(executor);
    }

    public void removeExecutor(Executor executor) {
        Controller.get().removeExecutor(executor);
    }

    public void changeExecutor(int idExecutor, Executor executor) {
        Controller.get().changeExecutor(idExecutor, executor);
    }

    public void addSpeciality(Speciality speciality, ArrayList<SpecQualify> specQualifies) {
        Controller.get().addSpeciality(speciality, specQualifies);
    }

    public void removeSpeciality(Speciality speciality) {
        Controller.get().removeSpeciality(speciality);
    }

    public void changeSpeciality(Speciality oldSpeciality, Speciality newSpeciality, ArrayList<SpecQualify> newSQ) {
        Controller.get().changeSpeciality(oldSpeciality, newSpeciality, newSQ);
    }

    public void newModel() {
        Controller.get().resetModels();
    }

    public OptionsManager getOptionsManager() {
        return Controller.get().getOptionsManager();
    }

    public void changeTimeOptions(int maxTime, int manageTime) {
        Controller.get().changeTimeOptions(maxTime, manageTime);
    }
}
