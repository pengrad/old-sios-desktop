package model.viewmodel;

import model.manager.OptionsManager;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * User: parshin
 * Date: 30.11.2010
 * Time: 18:10:09
 */

public class SpinnerMinutesModel extends AbstractSpinnerModel implements Observer {

    private int minutes;
    private int step;
    private int maxTime;
    private int minTime;

    public SpinnerMinutesModel(OptionsManager options) {
        options.addObserver(this);
        minutes = 60;
        step = 15;
        maxTime = options.getMaxTime();
        minTime = 15;
    }

    public SpinnerMinutesModel(int minutes, int step, int maxTime, int minTime) {
        this.minutes = minutes;
        this.step = step;
        this.maxTime = maxTime;
        this.minTime = minTime;
    }

    public Object getValue() {
//        return Helper.getMinutesToString(minutes);
        return minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int min) {
        if (min > maxTime) min = maxTime;
        if (min < minTime) min = minTime;
        minutes = min;
        this.fireStateChanged();
    }


    public void setValue(Object value) {
        int min = Integer.parseInt(value.toString());
        setMinutes(min);
    }

    public Object getNextValue() {
        if (minutes >= maxTime) {
            return minutes;
        } else {
            return minutes + step;
        }
    }

    public Object getPreviousValue() {
        if (minutes <= minTime) {
            return minutes;
        } else {
            return minutes - step;
        }
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        if (minutes > maxTime) {
            minutes = maxTime;
        }
        fireStateChanged();
    }

    public void update(Observable o, Object arg) {
        maxTime = ((OptionsManager)o).getMaxTime();
    }
}
