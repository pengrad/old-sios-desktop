package model.model;

import model.manager.OptionsManager;

import java.io.Serializable;
import java.util.*;

/**
 * User: parshin
 * Date: 24.12.10
 * Time: 12:09
 */

public class SavedModel implements Serializable {

    public Collection<Executor> executors;
    public Collection<Task> tasks;
    public Map<Speciality, ArrayList<SpecQualify>> specialities;
    public OptionsManager options;
    public SpecQualify manageSQ;

    public SavedModel(Collection<Executor> executors, Collection<Task> tasks,
                      Map<Speciality, ArrayList<SpecQualify>> specialities, OptionsManager options, SpecQualify manageSQ) {
        this.executors = executors;
        this.tasks = tasks;
        this.specialities = specialities;
        this.options = options;
        this.manageSQ = manageSQ;
    }
}
