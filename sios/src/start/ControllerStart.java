package start;


import gui.SpecDialog;
import gui.TaskDialog;
import manager.ResourceManager;
import manager.SynthesManager;
import manager.TreeEventManager;
import manager.GUIManager;
import model.manager.AbstractTableModelProvider;
import model.manager.Controller;
import model.manager.SpecTemplateManager;
import model.model.Executor;
import model.model.Speciality;
import model.model.Task;
import model.tree.ManagerInitTree;
import model.tree.controller.EventTree;
import model.tree.gui.ConnectLine;
import model.tree.gui.MyTreeNode;
import model.tree.model.Node;
import model.util.SIOSFileFilter;
import model.util.SIOSFileView;
import model.viewmodel.EmplTableModel;
import model.viewmodel.SpecTableModel;
import model.viewmodel.TaskTableModel;
import start.gui.FStart;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;

import start.gui.*;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 13.02.2011
 * Time: 16:24:56
 * To change this template use File | Settings | File Templates.
 */
public class ControllerStart implements ActionListener, EventTree {

    private int mode;
    public static final int SYNTHES = 1;
    public static final int ANALYS = 2;


    private FStart fStart;
    //private DStart dStart;
    private pCreate pCreate;
    private pStart pStart;
    private JPanel selectedPanel;
    private JPanel selectedLabelPanel;
    private Font fontSelectedSeep;
    private Font fontUnSelectedSteep;
    private Color colorSelectedSteep;
    private Color colorUnSelectedSteep;
    private SpecDialog dialogSpec;
    private DataManager manager;
    private SpecTableModel specTableModel;
    private TaskTableModel taskTableModel;
    private EmplTableModel emplTableModel;
    private TaskDialog taskDialog;
    private EmplDialog emplDialog;
    private JFileChooser dialogFile;
    private DChuseMode chuseMode;
    private DInfoSynthes infoSynthes;

    private DChooseStartMode dChooseStartMode;


    public ControllerStart(DataManager manager) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        dialogFile = new JFileChooser();
        dialogFile.setFileFilter(new SIOSFileFilter());
        dialogFile.setFileView(new SIOSFileView());

        fStart = new FStart();
//        dStart = new DStart(null, false);
        pCreate = new pCreate();
        pStart = new pStart();
        this.manager = manager;
        this.chuseMode = new DChuseMode(fStart, true);
        this.infoSynthes = new DInfoSynthes(fStart, true);
        specTableModel = new SpecTableModel(manager.getSpecManager());
        taskTableModel = new TaskTableModel(new AbstractTableModelProvider.TaskProvider());
        emplTableModel = new EmplTableModel(new AbstractTableModelProvider.ExecutorProvider());
        taskDialog = new TaskDialog(fStart, true, manager.getSpecManager(), manager.getOptionsManager(), true);
        pCreate.gettSpeciality().setModel(specTableModel);
        pCreate.gettTask().setModel(taskTableModel);
        pCreate.gettTask().getColumnModel().getColumn(5).setMinWidth(0);
        pCreate.gettTask().getColumnModel().getColumn(5).setMaxWidth(0);
        pCreate.gettTask().getColumnModel().getColumn(6).setMinWidth(0);
        pCreate.gettTask().getColumnModel().getColumn(6).setMaxWidth(0);
        pCreate.gettExecutor().setModel(emplTableModel);
        emplDialog = new EmplDialog(fStart, true, manager);
        dialogSpec = new SpecDialog(fStart, true, manager.getTaskManager(), manager.getSpecManager(), true);
        fontSelectedSeep = new Font("Tahoma", Font.BOLD, 13);
        colorSelectedSteep = Color.WHITE;
        fontUnSelectedSteep = new Font("Tahoma", Font.PLAIN, 13);
        colorUnSelectedSteep = Color.black;
        pCreate.getpCreateDependens().add(ManagerInitTree.getInstance().getTreePanel(), BorderLayout.CENTER);
        initListener();
        fStart.getpContainre().add(pStart);
//        dStart.getpContainre().add(pStart);

        ((DefaultTreeModel) pCreate.getTreeExecutors().getModel()).setRoot(null);

        pCreate.getTreeExecutors().setCellRenderer(new TreeRender());

        fStart.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/res/icon2.gif")).getImage());
        dChooseStartMode = new DChooseStartMode(fStart, true, SYNTHES, ANALYS);
        dChooseStartMode.setTextHelp(ResourceManager.getParam("choose_mode_dialog"));
        pCreate.initTextHelpPanes(ResourceManager.getParam("panel_spec"),
                ResourceManager.getParam("panel_tasks"), ResourceManager.getParam("panel_execs"),
                ResourceManager.getParam("panel_tasksToExecs"), ResourceManager.getParam("panel_tree"));

    }

    private void initListener() {
        pStart.getbNewProject().addActionListener(this);
        pStart.getbLoadProject().addActionListener(this);
        pStart.getbExit().addActionListener(this);
        pCreate.getbMain().addActionListener(this);
        pCreate.getbPrevious().addActionListener(this);
        pCreate.getbNext().addActionListener(this);
        pCreate.getbAddSpeciality().addActionListener(this);
        pCreate.getbEditSpeciality().addActionListener(this);
        pCreate.getbRemoveSpeciality().addActionListener(this);
        pCreate.getbAddTask().addActionListener(this);
        pCreate.getbEditTask().addActionListener(this);
        pCreate.getbRemoveTask().addActionListener(this);
        pCreate.getbAddExecutor().addActionListener(this);
        pCreate.getbEditExecutor().addActionListener(this);
        pCreate.getbRemoveExecutor().addActionListener(this);
        pCreate.getbLeft().addActionListener(this);
        pCreate.getbRight().addActionListener(this);
        pCreate.getbMake().addActionListener(this);
        pCreate.getbGenerateTask().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pStart.getbNewProject()) {
            startNewProject();
        } else if (e.getSource() == pStart.getbLoadProject()) {
            loadProject();
        } else if (e.getSource() == pStart.getbExit()) {
            exit();
        } else if (e.getSource() == pCreate.getbMain()) {
            goMain();
        } else if (e.getSource() == pCreate.getbPrevious()) {
            //   if (mode == DChuseMode.BUILDER)
            goPreviousForBuilder();
            //   if (mode == DChuseMode.SYNTHES)
            //        goPreviousForSynthes();
        } else if (e.getSource() == pCreate.getbNext()) {
            //   if (selectedPanel != pCreate.getpCreateTaskForExecutor()) {
            //         if (mode == DChuseMode.BUILDER)
            goNextForBuilder();
            //        if (mode == DChuseMode.SYNTHES)
            //            goNextForSynthes();
            //      } else {
            //        goMake();
            //       }
        } else if (e.getSource() == pCreate.getbMake()) {
            goMake();
        } else if (e.getSource() == pCreate.getbGenerateTask()) {
            generateTask();
        } else if (e.getSource() == pCreate.getbAddSpeciality()) {
            addSpeciality();
        } else if (e.getSource() == pCreate.getbEditSpeciality()) {
            editSpeciality();
        } else if (e.getSource() == pCreate.getbRemoveSpeciality()) {
            removeSpeciality();
        } else if (e.getSource() == pCreate.getbAddTask()) {
            addTask();
        } else if (e.getSource() == pCreate.getbEditTask()) {
            editTask();
        } else if (e.getSource() == pCreate.getbRemoveTask()) {
            removeTask();
        } else if (e.getSource() == pCreate.getbAddExecutor()) {
            addExecutor();
        } else if (e.getSource() == pCreate.getbEditExecutor()) {
            editExecutor();
        } else if (e.getSource() == pCreate.getbRemoveExecutor()) {
            removeExecutor();
        } else if (e.getSource() == pCreate.getbLeft()) {
            removeTaskExecutor();
        } else if (e.getSource() == pCreate.getbRight()) {
            addTaskExecutor();
        }

    }

//    private void createNewProject() {
//        //Удаляем старотовую панель и добавляем панель костуктор
//        fStart.getpContainre().removeAll();
//        fStart.getpContainre().add(pCreate);
//        fStart.getpContainre().revalidate();
//        fStart.getpContainre().repaint();
//        pStart.stopVideo();
//        pCreate.getbPrevious().setEnabled(false);
//        pCreate.getbNext().setEnabled(true);
//
//        pCreate.getpCont().removeAll();
//        pCreate.getpCont().add(pCreate.getpQuery());
//        pCreate.getpCont().revalidate();
//        pCreate.getpCont().repaint();
//        selectedPanel = pCreate.getpQuery();
//
//    }

    private void selectMode_Old() {
        chuseMode.setLocationRelativeTo(fStart);
        int mode = chuseMode.open();
        this.mode = mode;
        if (mode == DChuseMode.BUILDER) {
            createProjectForBuilder();
        }
        if (mode == DChuseMode.SYNTHES) {
            createProjectForSynthes();
        }
    }

    private void startNewProject() {
        int res = dChooseStartMode.showDialog(fStart);
        switch (res) {
            case SYNTHES:
                mode = SYNTHES;
                createProjectForSynthes();
                break;
            case ANALYS:
                mode = ANALYS;
                createProjectForBuilder();
                break;
        }
    }

    private void createProjectForBuilder() {
        fStart.setTitle("Создание специализаций");
        fStart.getpContainre().removeAll();
        fStart.getpContainre().add(pCreate);
        fStart.getpContainre().revalidate();
        fStart.getpContainre().repaint();
        pStart.stopVideo();
        pCreate.getbPrevious().setEnabled(false);
        pCreate.getbNext().setEnabled(true);
        pCreate.getbNext().setText("Далее");
        pCreate.getpCont().removeAll();
        pCreate.getpCont().add(pCreate.getpModificateProject());
        pCreate.getpCont().revalidate();
        pCreate.getpCont().repaint();

        pCreate.pStep3.setVisible(true);
        pCreate.pStep4.setVisible(true);
        pCreate.pStep5.setVisible(true);
        pCreate.pStep6.setVisible(false);

        pCreate.getLSteep1().setFont(fontSelectedSeep);
        pCreate.getLSteep1().setForeground(colorSelectedSteep);
        pCreate.getLSteep2().setFont(fontUnSelectedSteep);
        pCreate.getLSteep2().setForeground(colorUnSelectedSteep);
        pCreate.getLSteep3().setFont(fontUnSelectedSteep);
        pCreate.getLSteep3().setForeground(colorUnSelectedSteep);
        pCreate.getLSteep4().setFont(fontUnSelectedSteep);
        pCreate.getLSteep4().setForeground(colorUnSelectedSteep);
        pCreate.getLSteep5().setFont(fontUnSelectedSteep);
        pCreate.getLSteep5().setForeground(colorUnSelectedSteep);
        selectedPanel = pCreate.getpCreateSpeciality();
        selectedLabelPanel = pCreate.getpStep1();
        ((CardLayout) pCreate.getpContener().getLayout()).first(pCreate.getpContener());
    }

    private void createProjectForSynthes() {
        fStart.setTitle("Создание специализаций");
        fStart.getpContainre().removeAll();
        fStart.getpContainre().add(pCreate);
        fStart.getpContainre().revalidate();
        fStart.getpContainre().repaint();
        pStart.stopVideo();
        pCreate.getbPrevious().setEnabled(false);
        pCreate.getbNext().setEnabled(true);
        pCreate.getbNext().setText("Далее");
        pCreate.getpCont().removeAll();
        pCreate.getpCont().add(pCreate.getpModificateProject());
        pCreate.getpCont().revalidate();
        pCreate.getpCont().repaint();

        pCreate.pStep3.setVisible(false);
        pCreate.pStep4.setVisible(false);
        pCreate.pStep5.setVisible(false);
        pCreate.pStep6.setVisible(false);

        pCreate.getLSteep1().setFont(fontSelectedSeep);
        pCreate.getLSteep1().setForeground(colorSelectedSteep);
        pCreate.getLSteep2().setFont(fontUnSelectedSteep);
        pCreate.getLSteep2().setForeground(colorUnSelectedSteep);
        pCreate.getLSteep3().setFont(fontUnSelectedSteep);
        pCreate.getLSteep3().setForeground(colorUnSelectedSteep);
        pCreate.getLSteep4().setFont(fontUnSelectedSteep);
        pCreate.getLSteep4().setForeground(colorUnSelectedSteep);
        pCreate.getLSteep6().setFont(fontUnSelectedSteep);
        pCreate.getLSteep6().setForeground(colorUnSelectedSteep);
        selectedPanel = pCreate.getpCreateSpeciality();
        selectedLabelPanel = pCreate.getpStep1();
        ((CardLayout) pCreate.getpContener().getLayout()).first(pCreate.getpContener());
    }

    private void exit() {
        int exit = JOptionPane.showConfirmDialog(getpStart(), "Вы действительно хотите покинуть прогамму?", "Внимание...", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) System.exit(0);
    }

    private void goMain() {
        fStart.setTitle("СИОС");
        pStart.startVideo();
        fStart.getpContainre().removeAll();
        fStart.getpContainre().add(pStart);
        fStart.getpContainre().revalidate();
        fStart.getpContainre().repaint();
    }

    private void goPreviousForBuilder() {
        if (selectedPanel == pCreate.getpCreateTask()) {
            selectedLabelPanel = pCreate.getpStep1();
            fStart.setTitle("Создание специализаций");
            pCreate.getbPrevious().setEnabled(false);
            selectedPanel = pCreate.getpCreateSpeciality();
            pCreate.getLSteep1().setFont(fontSelectedSeep);
            pCreate.getLSteep1().setForeground(colorSelectedSteep);
            pCreate.getLSteep2().setFont(fontUnSelectedSteep);
            pCreate.getLSteep2().setForeground(colorUnSelectedSteep);
        } else if (selectedPanel == pCreate.getpCreateExecutor()) {
            selectedLabelPanel = pCreate.getpStep2();
            fStart.setTitle("Создание задач");
            selectedPanel = pCreate.getpCreateTask();
            pCreate.getLSteep2().setFont(fontSelectedSeep);
            pCreate.getLSteep2().setForeground(colorSelectedSteep);
            pCreate.getLSteep3().setFont(fontUnSelectedSteep);
            pCreate.getLSteep3().setForeground(colorUnSelectedSteep);
        } else if (selectedPanel == pCreate.getpCreateDependens()) {
            selectedLabelPanel = pCreate.getpStep3();
            fStart.setTitle("Создание исполнителей");
            selectedPanel = pCreate.getpCreateExecutor();
            pCreate.getLSteep3().setFont(fontSelectedSeep);
            pCreate.getLSteep3().setForeground(colorSelectedSteep);
            pCreate.getLSteep4().setFont(fontUnSelectedSteep);
            pCreate.getLSteep4().setForeground(colorUnSelectedSteep);
        } else if (selectedPanel == pCreate.getpCreateTaskForExecutor()) {
            selectedLabelPanel = pCreate.getpStep4();
            fStart.setTitle("Построение структуры");
            selectedPanel = pCreate.getpCreateDependens();
//            pCreate.getbNext().setText("Далее");
            pCreate.getLSteep4().setFont(fontSelectedSeep);
            pCreate.getLSteep4().setForeground(colorSelectedSteep);
            pCreate.getLSteep5().setFont(fontUnSelectedSteep);
            pCreate.getLSteep5().setForeground(colorUnSelectedSteep);
        }
        pCreate.getbNext().setEnabled(true);
        ((CardLayout) pCreate.getpContener().getLayout()).previous(pCreate.getpContener());
    }

    private void goNextForBuilder() {
        if (selectedPanel == pCreate.getpCreateSpeciality()) {
            selectedLabelPanel = pCreate.getpStep2();
            fStart.setTitle("Создание задач");
            pCreate.getbPrevious().setEnabled(true);
            selectedPanel = pCreate.getpCreateTask();
            pCreate.getLSteep2().setFont(fontSelectedSeep);
            pCreate.getLSteep2().setForeground(colorSelectedSteep);
            pCreate.getLSteep1().setFont(fontUnSelectedSteep);
            pCreate.getLSteep1().setForeground(colorUnSelectedSteep);
            pCreate.getbNext().setEnabled(mode == ANALYS);
        } else if (selectedPanel == pCreate.getpCreateTask()) {
            selectedLabelPanel = pCreate.getpStep3();
            fStart.setTitle("Создание исполнителей");
            selectedPanel = pCreate.getpCreateExecutor();
            pCreate.getLSteep3().setFont(fontSelectedSeep);
            pCreate.getLSteep3().setForeground(colorSelectedSteep);
            pCreate.getLSteep2().setFont(fontUnSelectedSteep);
            pCreate.getLSteep2().setForeground(colorUnSelectedSteep);
        } else if (selectedPanel == pCreate.getpCreateExecutor()) {
            selectedLabelPanel = pCreate.getpStep4();
            fStart.setTitle("Построение структуры");
            selectedPanel = pCreate.getpCreateDependens();
            pCreate.getLSteep4().setFont(fontSelectedSeep);
            pCreate.getLSteep4().setForeground(colorSelectedSteep);
            pCreate.getLSteep3().setFont(fontUnSelectedSteep);
            pCreate.getLSteep3().setForeground(colorUnSelectedSteep);
        } else if (selectedPanel == pCreate.getpCreateDependens()) {
            selectedLabelPanel = pCreate.getpStep5();
            fStart.setTitle("Назначение задач");
            selectedPanel = pCreate.getpCreateTaskForExecutor();
//            pCreate.getbNext().setText("Готово");
            pCreate.getbNext().setEnabled(false);
            pCreate.getLSteep5().setFont(fontSelectedSeep);
            pCreate.getLSteep5().setForeground(colorSelectedSteep);
            pCreate.getLSteep4().setFont(fontUnSelectedSteep);
            pCreate.getLSteep4().setForeground(colorUnSelectedSteep);
            updateTreeExecutor();
            updateListTask();
        }

        ((CardLayout) pCreate.getpContener().getLayout()).next(pCreate.getpContener());
    }

    private void goMake() {
        fStart.removeAll();
        fStart.setVisible(false);
        GUIManager gui = new GUIManager();
        TreeEventManager c = new TreeEventManager(gui);
        ManagerInitTree.getInstance().getControllerTree().removeAllEventListener();
        ManagerInitTree.getInstance().getControllerTree().addEventListener(c);
        gui.init().showFrame();
        Controller.get().fireAllData();

    }

    private void generateTask() {
        final Controller c = Controller.get();
        final Collection<Task> tasks = c.getTaskManager().getExecTasks();
        if (tasks.size() > 0) {
            new SwingWorker() {
                protected Object doInBackground() throws Exception {
                    Collection<Executor> executors = new SynthesManager().createExecutor(tasks);
                    SpecTemplateManager templateManager = c.getSpecTemplateManager().getTemplateByName(c.getOptionsManager().getTemplateManager());
                    for (Executor executor : executors) {
                        templateManager.updateExecutor(executor);
                    }
                    c.setExecutors(executors);
                    Thread.sleep(1500);
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                protected void done() {
                    infoSynthes.enabledButton();
                }

            }.execute();
            infoSynthes.setLocationRelativeTo(fStart);
            int mode = infoSynthes.open();
            if (mode == DInfoSynthes.GO_MAIN) {
                goMake();
            }
        } else {
            JOptionPane.showMessageDialog(fStart, "Нет исходных данных(задач) для синтеза структуры", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadProject() {
        if (dialogFile.showOpenDialog(fStart) == JFileChooser.APPROVE_OPTION) {
            // todo предложить сохранить текущую модель, если она не пустая
            try {
                manager.getSerializableManager().loadModel(dialogFile.getSelectedFile());
                goMake();
            } catch (Exception e) {
                // todo обработать ошибку
                e.printStackTrace();
            }
        }
    }

    private void addSpeciality() {
        if (dialogSpec.showDialog(null) == SpecDialog.OK) {
            manager.addSpeciality(dialogSpec.getSpeciality(), dialogSpec.getSpecQualifies());
        }
    }

    private void editSpeciality() {
        Speciality speciality = getSelectedSpeciality();
        if (speciality == null) return;
        if (dialogSpec.showDialog(speciality) == SpecDialog.OK) {
            manager.changeSpeciality(speciality, dialogSpec.getSpeciality(), dialogSpec.getSpecQualifies());
        }
    }

    private void removeSpeciality() {
        Speciality speciality = getSelectedSpeciality();
        if (speciality == null) return;
        manager.removeSpeciality(speciality);
    }

    private void addTask() {
        Task task = taskDialog.showDialog(null);
        if (task != null) {
            if (task.getMinSpecQualify() == null || task.getNameOfTask() == null) return;
            manager.addTask(task);
        }
    }

    private void editTask() {
        Task task = getSelectedTask();
        if (task == null) return;
        Task newTask = taskDialog.showDialog(task);
        if (newTask == null) return;
        manager.changeTask(task.getIdTask(), newTask);
    }

    private void removeTask() {
        Task task = getSelectedTask();
        if (task == null) return;
        manager.removeTask(task);
    }

    private void addExecutor() {
        Executor executor = emplDialog.showDialog(null);
        if (executor != null) {
            manager.addExecutor(executor);
        }
    }

    private void editExecutor() {
        Executor executor = getSelectedExecutor();
        if (executor == null) return;
        Executor newExecutor = emplDialog.showDialog(executor);
        if (newExecutor != null) {
            manager.changeExecutor(executor.getIdExecutor(), newExecutor);
        }
    }

    private void removeExecutor() {
        Executor exec = getSelectedExecutor();
        if (exec == null) return;
        manager.removeExecutor(exec);
    }

    public FStart getfStart() {
        return fStart;
    }

//    public DStart getdStart() {
//        return dStart;
//    }

    public pCreate getpCreate() {
        return pCreate;
    }

    public pStart getpStart() {
        return pStart;
    }

    public Component getSelectedPanel() {
        return selectedPanel;
    }

    public JPanel getSelectedLabelPanel() {
        return selectedLabelPanel;
    }

    private Speciality getSelectedSpeciality() {
        int index = pCreate.gettSpeciality().getSelectedRow();
        if (index >= 0) {
            index = pCreate.gettSpeciality().convertRowIndexToModel(index);
            return specTableModel.getSpecialityAtRow(index);
        } else return null;
    }

    private Task getSelectedTask() {
        int index = pCreate.gettTask().getSelectedRow();
        if (index >= 0) {
            index = pCreate.gettTask().convertRowIndexToModel(index);
            return taskTableModel.getTaskAt(index);
        } else return null;
    }

    private Executor getSelectedExecutor() {
        int index = pCreate.gettExecutor().getSelectedRow();
        if (index >= 0) {
            index = pCreate.gettExecutor().convertRowIndexToModel(index);
            return emplTableModel.getExecutorAtRow(index);
        } else return null;
    }

    private void updateTreeExecutor() {
        ArrayList<Executor> freeExecutors = Controller.get().getEmplManager().getFreeExecutors();
        if (freeExecutors.size() > 0) {
            JTree tree = pCreate.getTreeExecutors();
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            HashMap<Executor, DefaultMutableTreeNode> treeExecutors = new HashMap<Executor, DefaultMutableTreeNode>();
            if (root != null) {
                getAllNodes(root, treeExecutors);
            }
//            else {
            root = new DefaultMutableTreeNode("");
            model.setRoot(root);
//                model.reload(root);
//            }
            for (Executor executor : freeExecutors) {
                makeTree(null, executor, root, treeExecutors, model);
            }
            model.reload();
        }
    }

    private void getAllNodes(DefaultMutableTreeNode node, HashMap<Executor, DefaultMutableTreeNode> list) {
        Enumeration nodes = node.children();
        while (nodes.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) nodes.nextElement();
            if (n.getUserObject() != null && n.getUserObject() instanceof Executor)
                list.put((Executor) n.getUserObject(), node);
            getAllNodes(n, list);
        }
    }


    public void makeTree(Executor parent, Executor executor, DefaultMutableTreeNode node, HashMap<Executor, DefaultMutableTreeNode> existExecutors, DefaultTreeModel model) {
        DefaultMutableTreeNode mNode;
//        if (!existExecutors.containsKey(executor)) {
        mNode = new DefaultMutableTreeNode(executor);
        node.add(mNode);
        for (Task task : executor.getExecTasks()) {
            mNode.add(new DefaultMutableTreeNode(task));
        }
//            model.reload(node);
//        } else {
//        mNode = existExecutors.get(executor);
//            DefaultMutableTreeNode p = ((DefaultMutableTreeNode) mNode.getParent());
//            if (p != null && !p.getUserObject().equals(parent)) {
//                System.out.println("***********");
//                model.removeNodeFromParent(mNode);
//                model.reload(p);
//                mNode = new DefaultMutableTreeNode(executor);
//                node.add(mNode);
//                model.reload(node);
//            }
//        }
        for (Executor e : executor.getManagedExecutors()) {
            makeTree(executor, e, mNode, existExecutors, model);
        }
    }


    private void updateListTask() {
        ArrayList<Task> tasks = Controller.get().getTaskManager().getTasks();
        JList list = pCreate.getListTasks();
        ((DefaultListModel) list.getModel()).removeAllElements();
        for (Task task : tasks) {
            if (Controller.get().getTaskManager().isFreeTask(task)) {
                ((DefaultListModel) list.getModel()).addElement(task);
            }
        }
    }

    private void removeTaskExecutor() {
        JTree tree = pCreate.getTreeExecutors();
        JList list = pCreate.getListTasks();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        if (!tree.isSelectionEmpty()) {
            TreePath[] selectedPath = tree.getSelectionPaths();
            for (int k = 0; k < selectedPath.length; k++) {
                Object[] selectedValues = selectedPath[k].getPath();
                for (int i = 0; i < selectedValues.length; i++) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedValues[i];
                    if (node.getUserObject() instanceof Task) {
                        ((DefaultListModel) list.getModel()).addElement(node.getUserObject());
                        Controller.get().removeTaskFromExecutor(((Executor) ((DefaultMutableTreeNode) node.getParent()).getUserObject()), (Task) node.getUserObject());
                        model.removeNodeFromParent(node);
                        model.reload(node.getParent());
                    }
                }
            }
        }
    }

    private void addTaskExecutor() {
        JTree tree = pCreate.getTreeExecutors();
        JList list = pCreate.getListTasks();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        if (!tree.isSelectionEmpty() && !list.isSelectionEmpty()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
            if (node.getUserObject() instanceof Executor) {
                Object[] selectedValues = list.getSelectedValues();
                for (int i = 0; i < selectedValues.length; i++) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(selectedValues[i]);
                    node.add(newNode);
                    Controller.get().addTaskToExecutor(((Executor) node.getUserObject()), (Task) selectedValues[i]);
                    ((DefaultListModel) list.getModel()).removeElement(selectedValues[i]);
                    model.reload(node);
                }
            }

        }
    }


    public void addNode(Node node) {

    }

    public void editNode(Node node) {
    }


    public void removeNode(boolean delCild, Node node) {

    }

    public void connectNode(Node parent, Node child) {
        if (((MyTreeNode) parent).getType() == MyTreeNode.INGENER && ((((MyTreeNode) child).getType() == MyTreeNode.MANAGER) || (((MyTreeNode) child).getType() == MyTreeNode.COORDINATOR))) {
            Node tmp = child;
            child = parent;
            parent = tmp;
        }
        try {
            ManagerInitTree.getInstance().getTreeModelManager().addManagerToExecutor((Executor) ((MyTreeNode) child).getInforation(), (Executor) ((MyTreeNode) parent).getInforation());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ManagerInitTree.getInstance().getTreePanel(), e.getMessage());
        }
    }

    public void disconnect(ConnectLine connectLine) {
        ManagerInitTree.getInstance().getTreeModelManager().removeManagerFromExecutor((Executor) ((MyTreeNode) connectLine.getChildNode()).getInforation(), (Executor) ((MyTreeNode) connectLine.getParentNode()).getInforation());
    }
}
