package model.model;

import model.util.Helper;

import java.io.Serializable;

/**
 * User: Стас
 * Date: 17.11.2010
 * Time: 23:24:58
 */

public class SpecQualify implements Serializable {

    private static final long serialVersionUID = 44L;

    private int idSpecQualify;
    private Speciality speciality;
    private String nameOfQualify;

    public SpecQualify(Speciality speciality, String nameOfQualify) {
        idSpecQualify = Helper.getId();
        this.speciality = speciality;
        this.nameOfQualify = nameOfQualify;
    }

    public int getIdSpecQualify() {
        return idSpecQualify;
    }

    public void setIdSpecQualify(int idSpecQualify) {
        this.idSpecQualify = idSpecQualify;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public String getNameOfQualify() {
        return nameOfQualify;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public void setNameOfQualify(String nameOfQualify) {
        this.nameOfQualify = nameOfQualify;
    }

    @Override
    public String toString() {
        return nameOfQualify;
    }
}
