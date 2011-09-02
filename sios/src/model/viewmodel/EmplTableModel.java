package model.viewmodel;

import model.manager.AbstractTableModelProvider;
import model.model.Executor;
import model.model.Speciality;
import model.util.Helper;

import javax.swing.table.AbstractTableModel;
import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 09.12.2010
 * Time: 17:14:21
 */

public class EmplTableModel extends AbstractTableModel implements Observer {

    private AbstractTableModelProvider<Executor> dataManager;
    private String[] headers = new String[]{"№ Исполнителя", "Имя исполнителя", "Тип", "Специализации", "Нагрузка", "Задачи"};
    private Class[] columnClass = new Class[]{Integer.class, Object.class, Object.class, Object.class, Object.class, Object.class};
    private Integer[] ids;

    public EmplTableModel(AbstractTableModelProvider<Executor> manager) {
        dataManager = manager;
        manager.addObserver(this);
        update(manager, null);
    }

    private Object[] emplToRow(Executor executor) {
        Object[] row = new Object[headers.length];
        row[0] = executor.getNumberOfExecutor();
        row[1] = executor.getNameOfExecutor();
        row[2] = getStrTypeOfExecutor(executor);
        row[3] = getStrSpecialitiesOfExecutor(executor);
        row[4] = Helper.getMinutesToString(executor.getSumTime());
        row[5] = executor.getAllTasks().size();
        return row;
    }

    private Object emplToColumn(Executor executor, int column) {
        if (column < 0 || column >= headers.length) return null;
        return emplToRow(executor)[column];
    }

    public int getRowCount() {
        return ids == null ? 0 : ids.length;
    }

    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Executor executor = getExecutorAtRow(rowIndex);
        if(executor == null) return null;        
        return emplToColumn(executor, columnIndex);
    }

    public Executor getExecutorAtRow(int row) {
        if (row < 0 || row >= ids.length) return null;
        return dataManager.getObjectById(ids[row]);
    }

    public void update(Observable o, Object arg) {        
        ids = dataManager.getObjectsIds();
        fireTableDataChanged();
    }

    public String getStrSpecialitiesOfExecutor(Executor executor) {
        StringBuilder sb = new StringBuilder();
        for (Speciality spec : executor.getSpecialities()) {
            sb.append(spec.getNameOfSpeciality());
            sb.append(", ");
        }
        if(sb.length() == 0) sb.append("<Специальности не заданы>");
        else sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public String getStrTypeOfExecutor(Executor executor) {
        return dataManager.isManager(executor) ? "Менеджер" : "Исполнитель";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }
}
