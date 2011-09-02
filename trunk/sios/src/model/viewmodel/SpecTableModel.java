package model.viewmodel;

import model.manager.SpecialityManager;
import model.model.Speciality;

import javax.swing.table.AbstractTableModel;
import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 14.12.10
 * Time: 18:34
 */

public class SpecTableModel extends AbstractTableModel implements Observer {

    private SpecialityManager specManager;
    private String[] headers = new String[]{"Специализация", "Количество квалификаций"};
    private Integer[] ids;

    public SpecTableModel(SpecialityManager manager) {
        specManager = manager;
        manager.addObserver(this);
        update(manager, null);
    }

    private Object[] specToRow(Speciality spec) {
        Object[] row = new Object[headers.length];
        row[0] = spec.getNameOfSpeciality();
        row[1] = specManager.getCountQualifiesBySpeciality(spec);
        return row;
    }

    private Object specToColumn(Speciality spec, int column) {
        if (column < 0 || column >= headers.length) return null;
        return specToRow(spec)[column];
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
        Speciality spec = getSpecialityAtRow(rowIndex);
        if (spec == null) return null;
        return specToColumn(spec, columnIndex);
    }

    public Speciality getSpecialityAtRow(int row) {
        if (row < 0 || row >= ids.length) return null;
        return specManager.getSpecialityById(ids[row]);
    }

    public void update(Observable o, Object arg) {
        ids = specManager.getSpecialityIds();
        fireTableDataChanged();
    }
}
