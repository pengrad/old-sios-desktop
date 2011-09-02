package model.viewmodel;

import model.manager.AbstractTableModelProvider;
import model.model.Executor;
import model.model.Task;
import model.util.Helper;

import javax.swing.table.AbstractTableModel;
import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 01.12.2010
 * Time: 14:03:49
 */

public class TaskTableModel extends AbstractTableModel implements Observer {

    private AbstractTableModelProvider<Task> dataManager;
    private String[] headers = new String[]{"№ Задачи", "Название задачи", "Специализация", "Сложность", "Нагрузка", "Исполнитель", "№ Исполнителя"};
    private Class[] columnClass = new Class[]{Integer.class, Object.class, Object.class, Object.class, Object.class, Object.class, Integer.class};
    private Integer[] ids;

    public TaskTableModel(AbstractTableModelProvider<Task> dataManager) {
        this.dataManager = dataManager;
        dataManager.addObserver(this);
        update(null, null);
    }

    private Object[] taskToRow(Task task) {
        Object[] row = new Object[headers.length];
        row[0] = task.getNumberOfTask();
        row[1] = task.getNameOfTask();
        row[2] = task.getMinSpecQualify().getSpeciality().getNameOfSpeciality();
        row[3] = task.getMinSpecQualify().getNameOfQualify();
        row[4] = Helper.getMinutesToString(task.getTimeInMinutes());
        Executor executor = dataManager.getExecutorByTask(task);
        row[5] = executor == null ? "" : executor.getNameOfExecutor();
        row[6] = executor == null ? null : executor.getNumberOfExecutor();
        return row;
    }

    private Object taskToColumn(Task task, int column) {
        if (column < 0 || column >= headers.length) return null;
        return taskToRow(task)[column];
    }

    public void update(Observable o, Object arg) {
        ids = dataManager.getObjectsIds();
        fireTableDataChanged();
    }

    public int getRowCount() {
        return ids.length;
    }

    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = getTaskAt(rowIndex);
        if (task == null) return null;
        return taskToColumn(task, columnIndex);
    }

    public Task getTaskAt(int row) {
        if (row < 0 || row >= ids.length) return null;
        return dataManager.getObjectById(ids[row]);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }
}
