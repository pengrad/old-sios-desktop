package model.viewmodel;

import model.manager.SpecialityManager;
import model.model.Speciality;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 07.12.2010
 * Time: 14:57:58
 */

public class SpecialityComboBoxModel extends AbstractListModel implements ComboBoxModel, Observer {

    private Speciality selected;
    private SpecialityManager specManager;
    private Integer[] ids;

    public SpecialityComboBoxModel(SpecialityManager specManager) {
        this.specManager = specManager;
        specManager.addObserver(this);
        update(specManager, null);
    }

    public void setSelectedItem(Object anItem) {
        selected = (Speciality) anItem;
        fireContentsChanged(this, -1, -1);
    }

    public Object getSelectedItem() {
        return getSelectedSpeciality();
    }

    public Speciality getSelectedSpeciality() {
        return selected;
    }

    public int getSize() {
        return ids == null ? -1 : ids.length;
    }

    public Object getElementAt(int index) {
        if (index < 0 || index >= ids.length) return null;        
        return specManager.getSpecialityById(ids[index]);
    }

    public void update(Observable o, Object arg) {
        ids = specManager.getSpecialityIds();
        fireContentsChanged(this, -1, -1);
    }
}
