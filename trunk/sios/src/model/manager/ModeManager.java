package model.manager;

public class ModeManager extends AbstractManager {

    public enum Mode {
        BUILDER(0, "Конструктор"), PROGRESS(1, "Развитие"), SYNTHES(2, "Синтез"), ANALYS(3, "Анализ");
        private int id;
        private String name;

        private Mode(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    private Mode mode;

    public ModeManager() {
        mode = Mode.BUILDER;
    }

    @Override
    void resetData() {
        mode = Mode.BUILDER;
        fireDataChanged(mode);
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        fireDataChanged(mode);
    }

    boolean isWrongModelAllow() {
        return mode.equals(Mode.BUILDER) || mode.equals(Mode.ANALYS);
    }

    boolean isAnalys() {
        return mode.equals(Mode.ANALYS);
    }
}