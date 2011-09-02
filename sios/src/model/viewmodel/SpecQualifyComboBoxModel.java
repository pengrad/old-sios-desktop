package model.viewmodel;

import model.manager.SpecialityManager;
import model.model.SpecQualify;
import model.model.Speciality;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 07.12.2010
 * Time: 15:51:01
 */

public class SpecQualifyComboBoxModel extends AbstractListModel implements ComboBoxModel, Observer {

    private SpecQualify selected;
    private Speciality spec;
    private SpecialityManager specManager;
    private Integer[] ids;

    public SpecQualifyComboBoxModel(SpecialityManager specManager) {
        this.specManager = specManager;
        ids = new Integer[0];
        specManager.addObserver(this);
    }

    public void setSpeciality(Speciality spec) {
        this.spec = spec;
        update(specManager, null);
    }

    public void setSelectedItem(Object anItem) {
        selected = (SpecQualify) anItem;
        fireContentsChanged(this, -1, -1);
    }

    public Object getSelectedItem() {
        return getSelectedSpecQualify();
    }

    public SpecQualify getSelectedSpecQualify() {
        return selected;
    }

    public int getSize() {
        return ids == null ? 0 : ids.length;
    }

    public Object getElementAt(int index) {
        if (index < 0 || index >= ids.length) return null;
        return specManager.getSpecQualifyById(ids[index]);
    }

    public void update(Observable o, Object arg) {
        if(spec == null) return;
        if(specManager.getSpecialityById(spec.getIdSpeciality()) == null) {
            spec = null;
            return;
        }
        ids = specManager.getSpecQualifyIds(spec);
        fireContentsChanged(this, -1, -1);
    }
}