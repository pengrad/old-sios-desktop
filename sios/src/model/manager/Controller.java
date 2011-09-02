package model.manager;

import model.model.*;
import model.util.Helper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: parshin
 * Date: 24.11.2010
 * Time: 11:25:11
 */

public class Controller {
    public final static int MODE_BUILDER = 0;
    public final static int MODE_PROGRESS = 1;
    public final static int MODE_SYNTHES = 2;
    public final static int MODE_ANALYS = 3;

    private static Controller instance;

    private Controller() {
    }

    public static synchronized Controller get() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private EmployeeManager emplManager;
    private SpecialityManager specManager;
    private TaskManager taskManager;
    private SerializableManager serializableManager;
    private OptionsManager optionsManager;
    private SpecTemplateManager specTemplateManager;
    private int mode;

    public void init() {
        optionsManager = new OptionsManager();
        taskManager = new TaskManager();
        emplManager = new EmployeeManager();
        specManager = new SpecialityManager();
        serializableManager = new SerializableManager();
        specTemplateManager = new SpecTemplateManager().init();
//        testSynthes();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private void test() {
        Speciality s = new Speciality("Спец 1");
        SpecQualify sq = new SpecQualify(s, "СпецКат 1");
        SpecQualify sq2 = new SpecQualify(s, "СпецКат 1+");
        specManager.addSpeciality(s);
        specManager.addSpecQualify(sq);
        specManager.addSpecQualify(sq2);
        Task task1 = new Task("Задача 1", 340, sq);
        Task task2 = new Task("Задача 2", 60, sq);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(new Task("Задача 3", 460, sq));
        taskManager.addTask(new Task("Задача 4", 120, sq));
        taskManager.addTask(new Task("Задача 5", 210, sq));
        s = new Speciality("Спец 2");
        sq = new SpecQualify(s, "СпецКат 2");
        specManager.addSpeciality(s);
        specManager.addSpecQualify(sq);
        emplManager.addExecutor(new Executor("Исполнитель 1", null, null));
        ArrayList<SpecQualify> sqs = new ArrayList<SpecQualify>(1);
        sqs.add(sq);
        ArrayList<Task> tasks = new ArrayList<Task>(2);
        tasks.add(task1);
        tasks.add(task2);
        emplManager.addExecutor(new Executor("Исполнитель 2", sqs, tasks));
    }

    private void testSynthes() {
        Speciality pr = new Speciality("Программист");
        Speciality th = new Speciality("Технолог");
        Speciality in = new Speciality("Инженер");
        specManager.addSpeciality(pr);
        specManager.addSpeciality(th);
        specManager.addSpeciality(in);
        SpecQualify sq4 = new SpecQualify(pr, "4");
        SpecQualify sq3 = new SpecQualify(pr, "3");
        SpecQualify sq2 = new SpecQualify(pr, "2");
        specManager.addSpecQualify(sq2);
        specManager.addSpecQualify(sq3);
        specManager.addSpecQualify(sq4);
        SpecQualify sq44 = new SpecQualify(th, "4");
        SpecQualify sq33 = new SpecQualify(th, "3");
        SpecQualify sq22 = new SpecQualify(th, "2");
        specManager.addSpecQualify(sq22);
        specManager.addSpecQualify(sq33);
        specManager.addSpecQualify(sq44);
        SpecQualify sq444 = new SpecQualify(in, "4");
        SpecQualify sq333 = new SpecQualify(in, "3");
        SpecQualify sq222 = new SpecQualify(in, "2");
        specManager.addSpecQualify(sq222);
        specManager.addSpecQualify(sq333);
        specManager.addSpecQualify(sq444);
//        taskManager.addTask(new Task("TASK_1", 300, sq4));
//        taskManager.addTask(new Task("TASK_2", 120, sq3));
//        taskManager.addTask(new Task("TASK_3", 180, sq2));
//        taskManager.addTask(new Task("TASK_4", 360, sq2));
        taskManager.addTask(new Task("TASK_1", 480, sq4));
        taskManager.addTask(new Task("TASK_2", 240, sq4));
        taskManager.addTask(new Task("TASK_3", 240, sq3));
        taskManager.addTask(new Task("TASK_4", 120, sq3));
        taskManager.addTask(new Task("TASK_5", 50, sq2));
        taskManager.addTask(new Task("TASK_6", 70, sq2));
        taskManager.addTask(new Task("TASK_7", 50, sq44));
        taskManager.addTask(new Task("TASK_8", 450, sq44));
        taskManager.addTask(new Task("TASK_9", 250, sq33));
        taskManager.addTask(new Task("TASK_10", 180, sq33));
        taskManager.addTask(new Task("TASK_11", 240, sq22));
        taskManager.addTask(new Task("TASK_12", 150, sq444));
        taskManager.addTask(new Task("TASK_13", 200, sq444));
        taskManager.addTask(new Task("TASK_14", 300, sq333));
        taskManager.addTask(new Task("TASK_15", 450, sq333));
        taskManager.addTask(new Task("TASK_16", 30, sq222));
        taskManager.addTask(new Task("TASK_17", 180, sq222));
        taskManager.addTask(new Task("TASK_17", 240, sq222));
        taskManager.addTask(new Task("TASK_19", 60, sq444));
        taskManager.addTask(new Task("TASK_20", 400, sq4));
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public EmployeeManager getEmplManager() {
        return emplManager;
    }

    public SpecialityManager getSpecManager() {
        return specManager;
    }

    public SerializableManager getSerializableManager() {
        return serializableManager;
    }

    public OptionsManager getOptionsManager() {
        return optionsManager;
    }

    public SpecTemplateManager getSpecTemplateManager() {
        return specTemplateManager;
    }

    public void addTask(Task task) {
        taskManager.addTask(task);
    }

    public void removeTask(Task task) {
        emplManager.removeTaskFromExecutors(task);
        taskManager.removeTask(task);
    }

    public void changeTask(int idEditableTask, Task newTask) {
        taskManager.changeTask(idEditableTask, newTask);
    }

    public void addExecutor(Executor executor) {
        taskManager.addManageTasks(executor.getManageTasks());
        emplManager.addExecutor(executor);
    }

    public void removeExecutor(Executor executor) {
        // удаление задачи, которая управляет исполнителем
        removeManagerFromExecutor(executor, emplManager.getManagerOfExecutor(executor));
        // удаление самого исполнителя и его управленческих задач
        taskManager.removeManageTasks(executor.getManageTasks());
        emplManager.removeExecutor(executor);
    }

    public void changeExecutor(int idExecutor, Executor newExecutor) {
        Executor oldExecutor = emplManager.getExecutorById(idExecutor);
        taskManager.removeManageTasks(oldExecutor.getManageTasks());
        taskManager.addManageTasks(newExecutor.getManageTasks());
        emplManager.changeExecutor(idExecutor, newExecutor);
    }

    public void addSpeciality(Speciality speciality, ArrayList<SpecQualify> specQualifies) {
        specManager.addSpeciality(speciality, specQualifies);
    }

    public void removeSpeciality(Speciality speciality) {
        for (Task task : taskManager.getAllTasksBySpeciality(speciality)) {
            removeTask(task);
        }
        emplManager.removeSpecialityFromExecutors(speciality);
        specManager.removeSpeciality(speciality);
    }

    public void changeSpeciality(Speciality oldSpeciality, Speciality newSpeciality, ArrayList<SpecQualify> newSQ) {
        for (SpecQualify sq : specManager.getSpecQualifies(oldSpeciality)) {
            if (!newSQ.contains(sq)) { // квалификация будет удалена, необходимо удалить таски для нее и исполнителей
                for (Task task : taskManager.getAllTasksBySpecQualify(sq)) {
                    removeTask(task);
                }
                emplManager.removeSpecQualityFromExecutors(sq);
            }
        }
        specManager.changeSpeciality(oldSpeciality, newSpeciality, newSQ);
    }

    public void resetModels() {
        taskManager.resetData();
        specManager.resetData();
        emplManager.resetData();
        optionsManager.resetData();
    }

    public void changeTimeOptions(int maxTime, int manageTime) {
        taskManager.setTimeToManageTasks(manageTime);
        taskManager.cutTaskTimeByMaxTime(maxTime);
        emplManager.removeTasksFromExecutorsOverMaxTime(maxTime);
        optionsManager.setMaxTime(maxTime);
        optionsManager.setManageTaskTime(manageTime);
    }

    public void changeSpecTemplate(String specTemplate) {
        optionsManager.setTemplateManager(specTemplate);
    }

    public void addManagerToExecutor(Executor executor, Executor manager) throws Exception {
        if (!emplManager.isManager(manager))
            throw new Exception("Исполнитель \"" + manager + "\" не является менеджером!");
        if (!emplManager.isFreeExecutor(executor)) throw new Exception("Исполнитель \"" + executor + "\" занят!");
        ManageTask task = new ManageTask(optionsManager.getManageTaskTime(), specManager.getManageQualify(), executor);
        int futureTime = manager.getSumTime() + task.getTimeInMinutes();
        if ((mode!=MODE_BUILDER&&mode!=MODE_ANALYS)&&futureTime > optionsManager.getMaxTime())
            throw new Exception("Нагрузка исполнителя \"" + manager + "\" станет равной - "
                    + Helper.getMinutesToString(futureTime) + ", что превысит максимальную норму в "
                    + Helper.getMinutesToString(optionsManager.getMaxTime()));
        taskManager.addTask(task);
        emplManager.addTaskToExecutor(manager, task);
    }

    public void removeManagerFromExecutor(Executor executor, Executor manager) {
        // если этот исполнитель действительно управляется этим менеджером - удаляем задачу
        ManageTask task = emplManager.getManagingTaskOfExecutor(executor);
        if (task != null && emplManager.getExecutorByTask(task).equals(manager)) {
            removeTask(task);
        }
//        if (emplManager.getManagerOfExecutor(executor).equals(manager)) {
//            for (ManageTask task : manager.getManageTasks()) {
//                if (task.getManagedExecutor().equals(executor)) {
//                    removeTask(task);
//                    return;
//                }
//            }
//        }
    }

    public void setExecutors(Collection<Executor> executors) {
        // отменяем рассылку на одно обращение к модели.
        taskManager.disableFire();
        emplManager.disableFire();
        taskManager.removeManageTasks(taskManager.getManageTasks());
        emplManager.resetData();
        // здесь рассылку надо отменять опять.
        for (Executor executor : executors) {
            taskManager.disableFire();
            emplManager.disableFire();
            if (taskManager.getTasks().containsAll(executor.getExecTasks())) {
                taskManager.addManageTasks(executor.getManageTasks());
                emplManager.addExecutor(executor);
            }
        }
        taskManager.fireDataChanged(null);
        emplManager.fireDataChanged(null);
    }

    public void fireAllData() {
        specManager.fireDataChanged(null);
        taskManager.fireDataChanged(null);
        emplManager.fireDataChanged(null);
        optionsManager.fireDataChanged(null);
    }

    public void addTaskToExecutor(Executor executor, Task task) {
        executor.getAllTasks().add(task);
    }

    public void removeTaskFromExecutor(Executor executor, Task task) {
        executor.getAllTasks().remove(task);
    }
}