package model.model;

/**
 * User: parshin
 * Date: 19.11.2010
 * Time: 17:14:31
 */

public class ManageTask extends Task {

    private Executor managedExecutor;

    public ManageTask(int timeInMinutes, SpecQualify minSpecQualify, Executor managedExecutor) {
        super("Управление", timeInMinutes, minSpecQualify);
        if(managedExecutor == null) throw new NullPointerException();
        this.managedExecutor = managedExecutor;
    }

    public ManageTask(int timeInMinutes, SpecQualify minSpecQualify, Executor managedExecutor, boolean aNew) {
        super("Управление", timeInMinutes, minSpecQualify, aNew);
        this.managedExecutor = managedExecutor;
    }

    public Executor getManagedExecutor() {
        return managedExecutor;
    }
}
