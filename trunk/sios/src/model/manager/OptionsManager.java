package model.manager;

import java.io.Serializable;

/**
 * User: Стас
 * Date: 19.12.2010
 * Time: 23:08:10
 */

public class OptionsManager extends AbstractManager implements Serializable {

    private int timeManageTask;
    private int timeMaxP;
    private String templateManager;

    public OptionsManager() {
        timeManageTask = 120;
        timeMaxP = 8*60;
    }

    @Override
    void resetData() {
        timeManageTask = 2 * 60;
        timeMaxP = 8 * 60;
        templateManager = "";
    }

    public void setOpions(OptionsManager options) {
        this.timeManageTask = options.timeManageTask;
        this.timeMaxP = options.timeMaxP;
        this.templateManager = options.templateManager;
        fireDataChanged(null);
    }

    // максимальная нагрузка на исполнителя в минутах

    public int getMaxTime() {
        return timeMaxP;
    }

    // время на управленческую задачу в минутах

    public int getManageTaskTime() {
        return timeManageTask;
    }

    void setMaxTime(int maxTime) {
        timeMaxP = maxTime;
        fireDataChanged(null);
    }

    void setManageTaskTime(int manageTaskTime) {
        timeManageTask = manageTaskTime;
        fireDataChanged(null);
    }

    public String getTemplateManager() {
        return templateManager;
    }

    void setTemplateManager(String templateManager) {
        this.templateManager = templateManager;
        fireDataChanged(null);
    }
}
