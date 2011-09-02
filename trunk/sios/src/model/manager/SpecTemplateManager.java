package model.manager;

import model.model.Executor;
import model.model.SpecQualify;
import model.model.Speciality;
import model.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * User: parshin
 * Date: 01.02.11
 * Time: 14:16
 */

public class SpecTemplateManager {

    public void updateExecutor(Executor executor) {
    }

    private HashMap<String, SpecTemplateManager> templates;

    public SpecTemplateManager() {
        templates = new HashMap<String, SpecTemplateManager>(3);
    }

    public SpecTemplateManager init() {
        templates.put("Управляющий обладает максимальными навыками", new MaxSpecTemplate());
        templates.put("Управляющий обладает минимальными навыками", new MinSpecTemplate());
        templates.put("Управляющий не связан с исполнительными специализациями", new EmptySpecTemplate());
        return this;
    }

    public Collection<String> getTemplatesNames() {
        return templates.keySet();
    }

    public SpecTemplateManager getTemplateByName(String templateName) {
        SpecTemplateManager template = templates.get(templateName);
        return template == null ? this : template;
    }

    private class MaxSpecTemplate extends SpecTemplateManager {
        @Override
        public void updateExecutor(Executor executor) {
            // выбираем всех подчиненных
            if (executor.getManagedExecutors().size() > 0) {
                // собираем все задачи со всех подчиненных
                ArrayList<Task> execTasks = new ArrayList<Task>();
                for (Executor managedExecutor : executor.getManagedExecutors()) {
                    execTasks.addAll(managedExecutor.getExecTasks());
                }
                // набор специализаций, применяемых подчиненными  (без задвоений)
                ArrayList<Speciality> specialities = new ArrayList<Speciality>();
                for (Task task : execTasks) {
                    Speciality spec = task.getMinSpecQualify().getSpeciality();
                    if (!specialities.contains(spec)) specialities.add(spec);
                }
                // квалификации, которыми должен обладать менеджер
                ArrayList<SpecQualify> bestSQ = new ArrayList<SpecQualify>();
                SpecialityManager sm = Controller.get().getSpecManager();
                for (Speciality spec : specialities) {
                    // поиск лучшей квалификации по специализации
                    bestSQ.add(sm.getBestSpecQualify(sm.getSpecQualifies(spec).toArray(new SpecQualify[0])));
                }
                // удаляем текущие квалификации менеджера по этим специализациям
                Controller.get().getEmplManager().removeSpecialityFromExecutor(executor, specialities);
                // добавляем новые квалификации
                Controller.get().getEmplManager().addSpecQualifiesToExecutor(executor, bestSQ);
            }
        }
    }

    private class MinSpecTemplate extends SpecTemplateManager {
        @Override
        public void updateExecutor(Executor executor) {
            // выбираем всех подчиненных
            if (executor.getManagedExecutors().size() > 0) {
                // собираем все задачи со всех подчиненных
                ArrayList<Task> execTasks = new ArrayList<Task>();
                for (Executor managedExecutor : executor.getManagedExecutors()) {
                    execTasks.addAll(managedExecutor.getExecTasks());
                }
                // набор специализаций, применяемых подчиненными (без задвоений)
                ArrayList<Speciality> specialities = new ArrayList<Speciality>();
                for (Task task : execTasks) {
                    Speciality spec = task.getMinSpecQualify().getSpeciality();
                    if (!specialities.contains(spec)) specialities.add(spec);
                }
                // квалификации, которыми должен обладать менеджер
                ArrayList<SpecQualify> bestSQ = new ArrayList<SpecQualify>();
                SpecialityManager sm = Controller.get().getSpecManager();
                for (Speciality spec : specialities) {
                    // поиск минимальной (худшей) квалификации по специализации
                    bestSQ.add(sm.getWorstSpecQualify(sm.getSpecQualifies(spec).toArray(new SpecQualify[0])));
                }
                // удаляем текущие квалификации менеджера по этим специализациям
                Controller.get().getEmplManager().removeSpecialityFromExecutor(executor, specialities);
                // добавляем новые квалификации
                Controller.get().getEmplManager().addSpecQualifiesToExecutor(executor, bestSQ);
            }
        }
    }

    private class EmptySpecTemplate extends SpecTemplateManager {
        @Override
        public void updateExecutor(Executor executor) {
        }
    }

}
