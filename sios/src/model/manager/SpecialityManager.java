package model.manager;

import model.model.Executor;
import model.model.SpecQualify;
import model.model.Speciality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Стас
 * Date: 18.11.2010
 * Time: 0:36:18
 */

public class SpecialityManager extends AbstractManager {

    private Map<Integer, Speciality> specialities;
    private Map<Integer, SpecQualify> specQualifies;
    // ID Специализации + Отсортированный набор ID Квалификаций. от легкой к сложной. чем больше индекс в листе тем сложней.
    private Map<Integer, ArrayList<Integer>> specQualifiesOrder;
    // отдельно сохраним квалификацию по управлению, чтобы нигде не отображалась
    private SpecQualify manageQualify;

    private int currentSpecId = 0;

    public SpecialityManager() {
        this.specialities = new HashMap<Integer, Speciality>();
        this.specQualifies = new HashMap<Integer, SpecQualify>();
        this.specQualifiesOrder = new HashMap<Integer, ArrayList<Integer>>();
        manageQualify = new SpecQualify(new Speciality("Менеджмент"), "Менеджер");
        currentSpecId = 0;
    }

    // для сериализации
    public HashMap<Speciality, ArrayList<SpecQualify>> getSpecialities() {
        HashMap<Speciality, ArrayList<SpecQualify>> map = new HashMap<Speciality, ArrayList<SpecQualify>>(specialities.size());
        for (Speciality speciality : specialities.values()) {
            ArrayList<SpecQualify> sqs = new ArrayList<SpecQualify>(specQualifiesOrder.get(speciality.getIdSpeciality()).size());
            for (Integer idSQ : specQualifiesOrder.get(speciality.getIdSpeciality())) {
                sqs.add(specQualifies.get(idSQ));
            }
            map.put(speciality, sqs);
        }
        return map;
    }

    // для сериализации
    void setSpecialities(Map<Speciality, ArrayList<SpecQualify>> specialities) {
        this.specialities = new HashMap<Integer, Speciality>(specialities.size());
        this.specQualifiesOrder = new HashMap<Integer, ArrayList<Integer>>(specialities.size());
        this.specQualifies = new HashMap<Integer, SpecQualify>();
        for (Speciality speciality : specialities.keySet()) {
            addSpeciality(speciality, specialities.get(speciality), false);
        }
        fireDataChanged(specialities.values());
    }

    @Override
    void resetData() {
        specialities = new HashMap<Integer, Speciality>();
        specQualifies = new HashMap<Integer, SpecQualify>();
        specQualifiesOrder = new HashMap<Integer, ArrayList<Integer>>();
        currentSpecId = 0;
        fireDataChanged(specialities.values());
    }

    public SpecQualify getManageQualify() {
        return manageQualify;
    }

    public void setManageQualify(SpecQualify manageQualify) {
        this.manageQualify = manageQualify;
    }

    void addSpeciality(Speciality speciality, ArrayList<SpecQualify> specQualifies) {
        addSpeciality(speciality, specQualifies, true);
    }

    void addSpeciality(Speciality speciality, ArrayList<SpecQualify> specQualifies, boolean fireChanges) {
        speciality.setIdSpeciality(++currentSpecId);
        specialities.put(speciality.getIdSpeciality(), speciality);
        if (specQualifies == null) {
            specQualifiesOrder.put(speciality.getIdSpeciality(), new ArrayList<Integer>());
        } else {
            ArrayList<Integer> ids = new ArrayList<Integer>(specQualifies.size());
            for (SpecQualify sq : specQualifies) {
                sq.setIdSpecQualify(++currentSpecId);
                sq.setSpeciality(speciality);
                ids.add(sq.getIdSpecQualify());
                this.specQualifies.put(sq.getIdSpecQualify(), sq);
            }
            specQualifiesOrder.put(speciality.getIdSpeciality(), ids);
        }
        if (fireChanges) fireDataChanged(speciality);
    }

    void addSpeciality(Speciality speciality) {
        addSpeciality(speciality, new ArrayList<SpecQualify>(0), true);
        fireDataChanged(speciality);
    }

    void removeSpeciality(Speciality speciality) {
        removeSpeciality(speciality.getIdSpeciality());
    }

    void removeSpeciality(int idSpeciality) {
        ArrayList<Integer> ids = specQualifiesOrder.remove(idSpeciality);
        if (ids != null) {
            for (Integer idSQ : ids) specQualifies.remove(idSQ);
        }
        specialities.remove(idSpeciality);
        fireDataChanged(specialities.values());
    }

    void changeSpeciality(Speciality oldSpec, Speciality newSpec, ArrayList<SpecQualify> newSpecQualifies) {
        if (specialities.get(oldSpec.getIdSpeciality()) == null) return;
        oldSpec.setNameOfSpeciality(newSpec.getNameOfSpeciality()); // переименовали специальность
        specQualifiesOrder.remove(oldSpec.getIdSpeciality()); // удалили старые квалификации
        for (SpecQualify sq : getSpecQualifies(oldSpec)) {
            specQualifies.remove(sq.getIdSpecQualify());
        }
        ArrayList<Integer> sqOrder = new ArrayList<Integer>(); // добавили новые квалификации
        for (SpecQualify sq : newSpecQualifies) {
            sq.setIdSpecQualify(++currentSpecId);
            specQualifies.put(sq.getIdSpecQualify(), sq);
            sqOrder.add(sq.getIdSpecQualify());
            sq.setSpeciality(oldSpec);
        }
        specQualifiesOrder.put(oldSpec.getIdSpeciality(), sqOrder);
        fireDataChanged(oldSpec);
    }

    void addSpecQualify(SpecQualify specQualify) {
        specQualify.setIdSpecQualify(++currentSpecId);
        int idSQ = specQualify.getIdSpecQualify();
        specQualifies.put(idSQ, specQualify);
        int idSpec = specQualify.getSpeciality().getIdSpeciality();
        ArrayList<Integer> idsSQ = specQualifiesOrder.get(idSpec);
        if (idsSQ == null) {
            idsSQ = new ArrayList<Integer>(1);
            specQualifiesOrder.put(idSpec, idsSQ);
        }
        idsSQ.add(idSQ);
        fireDataChanged(specQualify);
    }

    void removeSpecQualify(SpecQualify specQualify) {
        specQualifiesOrder.get(specQualify.getSpeciality().getIdSpeciality()).remove(specQualify.getIdSpecQualify());
        specQualifies.remove(specQualify.getIdSpecQualify());
        fireDataChanged(specialities.values());
    }

    public ArrayList<SpecQualify> getSpecQualifies(Speciality spec) {
        ArrayList<Integer> ids = specQualifiesOrder.get(spec.getIdSpeciality());
        if (ids == null) {
            return new ArrayList<SpecQualify>(0);
        } else {
            ArrayList<SpecQualify> sq = new ArrayList<SpecQualify>(ids.size());
            for (Integer idSQ : ids) sq.add(specQualifies.get(idSQ));
            return sq;
        }
    }

    public ArrayList<SpecQualify> getWorseSpecQualifies(SpecQualify specQualify) {
        ArrayList<Integer> all = specQualifiesOrder.get(specQualify.getSpeciality().getIdSpeciality());
        if (all == null) return new ArrayList<SpecQualify>(0);
        int indexSQ = all.indexOf(specQualify.getIdSpecQualify());
        if (indexSQ == -1) return new ArrayList<SpecQualify>(0);
        List<Integer> ids = all.subList(0, indexSQ + 1);
        ArrayList<SpecQualify> sqs = new ArrayList<SpecQualify>(ids.size());
        for (Integer id : ids) {
            sqs.add(specQualifies.get(id));
        }
        return sqs;
    }

    public ArrayList<SpecQualify> getBetterSpecQualifies(SpecQualify specQualify) {
        ArrayList<Integer> all = specQualifiesOrder.get(specQualify.getSpeciality().getIdSpeciality());
        if (all == null) return new ArrayList<SpecQualify>(0);
        int indexSQ = all.indexOf(specQualify.getIdSpecQualify());
        if (indexSQ == -1) return new ArrayList<SpecQualify>(0);
        List<Integer> ids = all.subList(indexSQ, all.size());
        ArrayList<SpecQualify> sqs = new ArrayList<SpecQualify>(ids.size());
        for (Integer id : ids) {
            sqs.add(specQualifies.get(id));
        }
        return sqs;
    }

    public int getSpecialityCount() {
        return specialities.size();
    }

    public Integer[] getSpecialityIds() {
        return specialities.keySet().toArray(new Integer[0]);
    }

    public Speciality getSpecialityById(int id) {
        return specialities.get(id);
    }

    public Integer[] getSpecQualifyIds(Speciality spec) {
        return specQualifiesOrder.get(spec.getIdSpeciality()).toArray(new Integer[0]);
    }

    public SpecQualify getSpecQualifyById(int id) {
        return specQualifies.get(id);
    }

    public boolean isManager(Executor executor) {
        return executor.getSpecQualifies().contains(manageQualify);
    }

    // лучшая (более сложная) квалификация из набора квалификаций по одной специализации
    // если специализации разные - return null;
    public SpecQualify getBestSpecQualify(SpecQualify... sq) {
        if (sq.length == 1) return sq[0];
        int idSpec = sq[0].getSpeciality().getIdSpeciality();
        for (SpecQualify s : sq) {
            if (idSpec != s.getSpeciality().getIdSpeciality()) return null;
        }
        ArrayList<Integer> order = specQualifiesOrder.get(idSpec);
        for (int best = order.size() - 1; best >= 0; best--) {
            for (SpecQualify s : sq) {
                if (s.getIdSpecQualify() == order.get(best)) return s;
            }
        }
        return null;
    }

    public SpecQualify getWorstSpecQualify(SpecQualify... sq) {
        if (sq.length == 1) return sq[0];
        int idSpec = sq[0].getSpeciality().getIdSpeciality();
        for (SpecQualify s : sq) {
            if (idSpec != s.getSpeciality().getIdSpeciality()) return null;
        }
        ArrayList<Integer> order = specQualifiesOrder.get(idSpec);
        for (int worst = 0; worst < order.size(); worst++) {
            for (SpecQualify s : sq) {
                if (s.getIdSpecQualify() == order.get(worst)) return s;
            }
        }
        return null;
    }

    public int getCountQualifiesBySpeciality(Speciality spec) {
        ArrayList sq = specQualifiesOrder.get(spec.getIdSpeciality());
        if (sq == null) return 0;
        else return sq.size();
    }
}
