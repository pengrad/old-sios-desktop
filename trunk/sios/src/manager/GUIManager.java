package manager;

import gui.EmplPanel;
import gui.SpecPanel;
import gui.TasksPanel;
import model.manager.*;
import model.model.Executor;
import model.model.SpecQualify;
import model.model.Speciality;
import model.model.Task;

import javax.swing.*;
import java.util.ArrayList;

import model.tree.ManagerInitTree;
import gui.MainFrame;

import java.util.Collection;

/**
 * User: parshin
 * Date: 24.11.2010
 * Time: 11:19:42
 */

public class GUIManager {
    private MainFrame mainFrame;
    private SynthesManager synthesManager;
    private JTabbedPane tabBuilder;
    private JTabbedPane tabProgress;
    private JTabbedPane tabSynthes;
    private JTabbedPane tabAnalys;


    public GUIManager() {
        try {
            UIManager.setLookAndFeel(new com.jtattoo.plaf.mcwin.McWinLookAndFeel());
        } catch (Exception e) {
        }
        synthesManager = new SynthesManager();
    }

    public GUIManager init() {
        mainFrame = new MainFrame(this);
        tabBuilder = new JTabbedPane();
        TasksPanel taskPanel = new TasksPanel(this, false, false, false);
        EmplPanel emplPanel = new EmplPanel(this, true, false);
        SpecPanel specPanel = new SpecPanel(this);
        tabBuilder.add("Специализации", specPanel);
        tabBuilder.add("Задачи", taskPanel);
        tabBuilder.add("Исполнители", emplPanel);
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.BUILDER_INIT);
        tabBuilder.add("Графическое представление", ManagerInitTree.getInstance().getTreePanel());
        tabProgress = new JTabbedPane();
        TasksPanel taskPanel2 = new TasksPanel(this, true, true, true);
        EmplPanel emplPanel2 = new EmplPanel(this, false, true);
        SpecPanel specPanel2 = new SpecPanel(this);
        tabProgress.add("Специализации", specPanel2);
        tabProgress.add("Задачи", taskPanel2);
        tabProgress.add("Исполнители", emplPanel2);
        //    tabProgress.add("Графическое представление", treePanel);
        tabSynthes = new JTabbedPane();
        TasksPanel taskPanel3 = new TasksPanel(this, false, false, true);
        EmplPanel emplPanel3 = new EmplPanel(this, false, false);
        SpecPanel specPanel3 = new SpecPanel(this);
        tabSynthes.add("Специализации", specPanel3);
        tabSynthes.add("Задачи", taskPanel3);
        tabSynthes.add("Исполнители", emplPanel3);
        //   tabSynthes.add("Графическое представление", treePanel);
        tabAnalys = new JTabbedPane();
        TasksPanel taskPanel4 = new TasksPanel(this, false, false, false);
        EmplPanel emplPanel4 = new EmplPanel(this, true, false);
        SpecPanel specPanel4 = new SpecPanel(this);
        tabAnalys.add("Специализации", specPanel4);
        tabAnalys.add("Задачи", taskPanel4);
        tabAnalys.add("Исполнители", emplPanel4);

        mainFrame.pCont.add(getBuilder());
        return this;
    }

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

    public Collection<String> getTemplatesNames() {
        return Controller.get().getSpecTemplateManager().getTemplatesNames();
    }

    public void changeSpecTemplate(String specTemplate) {
        Controller.get().changeSpecTemplate(specTemplate);
    }

    public void destroy() {
        if (mainFrame != null) mainFrame.dispose();
    }

    public void showFrame() {
        mainFrame.setVisible(true);
    }

    public void hideFrame() {
        mainFrame.setVisible(false);
    }

    public JFrame getRootFrame() {
        return mainFrame;
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

    public JTabbedPane getBuilder() {
        Controller.get().setMode(Controller.MODE_BUILDER);
        tabProgress.remove(ManagerInitTree.getInstance().getTreePanel());
        tabSynthes.remove(ManagerInitTree.getInstance().getTreePanel());
        tabAnalys.remove(ManagerInitTree.getInstance().getTreePanel());
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.BUILDER_INIT);
        tabBuilder.add("Графическое представление", ManagerInitTree.getInstance().getTreePanel());
        return tabBuilder;
    }

    public JTabbedPane getProgress() {
        Controller.get().setMode(Controller.MODE_PROGRESS);
        tabBuilder.remove(ManagerInitTree.getInstance().getTreePanel());
        tabSynthes.remove(ManagerInitTree.getInstance().getTreePanel());
        tabAnalys.remove(ManagerInitTree.getInstance().getTreePanel());
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.PROGRESS_INIT);
        tabProgress.add("Графическое представление", ManagerInitTree.getInstance().getTreePanel());
        return tabProgress;
    }

    public JTabbedPane getSynthes() {
        Controller.get().setMode(Controller.MODE_SYNTHES);
        tabBuilder.remove(ManagerInitTree.getInstance().getTreePanel());
        tabProgress.remove(ManagerInitTree.getInstance().getTreePanel());
        tabAnalys.remove(ManagerInitTree.getInstance().getTreePanel());
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.SINTHES_INIT);
        tabSynthes.add("Графическое представление", ManagerInitTree.getInstance().getTreePanel());
        return tabSynthes;
    }

    public JTabbedPane getAnalys() {
        Controller.get().setMode(Controller.MODE_ANALYS);
        tabBuilder.remove(ManagerInitTree.getInstance().getTreePanel());
        tabProgress.remove(ManagerInitTree.getInstance().getTreePanel());
        tabSynthes.remove(ManagerInitTree.getInstance().getTreePanel());
        ManagerInitTree.getInstance().setTreeInit(ManagerInitTree.ANALYS_INIT);
        tabAnalys.add("Графическое представление", ManagerInitTree.getInstance().getTreePanel());
        return tabAnalys;
    }


    public OptionsManager getOptionsManager() {
        return Controller.get().getOptionsManager();
    }

    public void changeTimeOptions(int maxTime, int manageTime) {
        Controller.get().changeTimeOptions(maxTime, manageTime);
    }

    public void fixNewTasks() {
        Collection<Task> newTasks = getTaskManager().getAllNewTasks();
        for (Task task : newTasks) {
            if (getTaskManager().isFreeTask(task)) {
                JOptionPane.showMessageDialog(mainFrame, "Не все новые задачи распределены по исполнителям!", "Есть свободные задачи", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        for (Task task : newTasks) {
            task.setNew(false);
        }
        for (Executor executor : getEmplManager().getAllNewExecutors()) {
            executor.setNew(false);
        }
        Controller.get().fireAllData();
    }

    public void synthesExecutorsByTasks() {
        Controller c = Controller.get();
        Collection<Task> tasks = c.getTaskManager().getExecTasks();
        Collection<Executor> executors = synthesManager.createExecutor(tasks);
        SpecTemplateManager templateManager = c.getSpecTemplateManager().getTemplateByName(c.getOptionsManager().getTemplateManager());
        for (Executor executor : executors) {
            templateManager.updateExecutor(executor);
        }
        c.setExecutors(executors);
    }

    public void updateExecutorsByNewTasks() {
        Collection<Task> newTasks = getTaskManager().getNewExecTasks();
        Collection<Executor> updatedExecutors = new ProgressManager().insertExecutor(newTasks);
        for (Executor executor : updatedExecutors) {
//            templateManager.updateExecutor(executor);
            Controller.get().addExecutor(executor);
        }
    }
}
