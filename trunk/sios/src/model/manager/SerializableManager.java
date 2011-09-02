package model.manager;

import model.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * User: parshin
 * Date: 17.12.10
 * Time: 15:57
 */

public class SerializableManager {

    public void saveModel(File file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        SavedModel s = save();
        oos.writeObject(s);
        fos.close();
    }

    public void loadModel(File f) throws Exception {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SavedModel m = (SavedModel) ois.readObject();
        fis.close();
        load(m);
    }

    public void loadModelTasks(File f) throws Exception {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SavedModel m = (SavedModel) ois.readObject();
        fis.close();
        loadWithoutExecutors(m);
    }


    public SavedModel save() {
        Collection<Executor> executors = Controller.get().getEmplManager().getAllExecutors();
        Collection<Task> tasks = Controller.get().getTaskManager().getTasks();
        Map<Speciality, ArrayList<SpecQualify>> specialities = Controller.get().getSpecManager().getSpecialities();
        OptionsManager options = Controller.get().getOptionsManager();
        SpecQualify manageSQ = Controller.get().getSpecManager().getManageQualify();
        return new SavedModel(executors, tasks, specialities, options, manageSQ);
    }

    public void load(SavedModel model) {
        Controller.get().getSpecManager().setSpecialities(model.specialities);
        Controller.get().getSpecManager().setManageQualify(model.manageSQ);
        Controller.get().getTaskManager().setTasks(model.tasks);
        Controller.get().getEmplManager().setExecutors(model.executors);
        Controller.get().getOptionsManager().setOpions(model.options);
    }

    public void loadWithoutExecutors(SavedModel model) {
        Controller.get().getSpecManager().setSpecialities(model.specialities);
        Controller.get().getSpecManager().setManageQualify(model.manageSQ);
        ArrayList<Task> execTasks = new ArrayList<Task>();
        for (Task task : model.tasks) {
            if (!(task instanceof ManageTask)) execTasks.add(task);
        }
        Controller.get().getTaskManager().setTasks(execTasks);
        Controller.get().getEmplManager().setExecutors(new ArrayList<Executor>(0));
        Controller.get().getOptionsManager().setOpions(model.options);
    }

}
