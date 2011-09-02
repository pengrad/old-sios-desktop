package model.manager;

import java.util.Observable;

/**
 * User: parshin
 * Date: 01.12.2010
 * Time: 18:23:40
 */

public abstract class AbstractManager extends Observable {

    private boolean fire = true;

    public void disableFire() {
        fire = false;
    }

    public void fireDataChanged(Object changes) {
        if(fire) {
            setChanged();
            notifyObservers(changes);
        }
        fire = true;
    }

    abstract void resetData();

}
