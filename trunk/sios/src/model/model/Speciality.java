package model.model;

import model.util.Helper;

import java.io.Serializable;

/**
 * User: Стас
 * Date: 17.11.2010
 * Time: 23:17:15
 */

public class Speciality implements Serializable {

    private static final long serialVersionUID = 43L;

    private int idSpeciality;
    private String nameOfSpeciality;

    public Speciality(String nameOfSpeciality) {
        idSpeciality = Helper.getId();
        this.nameOfSpeciality = nameOfSpeciality;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(int idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getNameOfSpeciality() {
        return nameOfSpeciality;
    }

    public void setNameOfSpeciality(String nameOfSpeciality) {
        this.nameOfSpeciality = nameOfSpeciality;
    }

    @Override
    public String toString() {
        return nameOfSpeciality;
    }
}
