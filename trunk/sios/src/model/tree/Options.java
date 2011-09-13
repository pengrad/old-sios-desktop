package model.tree;

import java.awt.*;
import java.io.Serializable;

/**
 * @author РЎС‚Р°СЃ
 */
public class Options implements Serializable {
    public static final Options options = new Options();

    private Color colorOfIngener;
    private Color colorOfManager;
    private Color colorOfCoordinator;
    private Color colorOfConnection;
    private Color colorOfSelection;

    public static Options get() {
        return options;
    }

    private Options() {
         colorOfIngener = Color.blue;
        colorOfManager = Color.magenta;
        colorOfCoordinator = Color.red;
        colorOfConnection = Color.gray;
        colorOfSelection = Color.gray;
    }             

    public Color getColorOfIngener() {
        return colorOfIngener;
    }

    public void setColorOfIngener(Color colorOfIngener) {
        this.colorOfIngener = colorOfIngener;
    }

    public Color getColorOfManager() {
        return colorOfManager;
    }

    public void setColorOfManager(Color colorOfManager) {
        this.colorOfManager = colorOfManager;
    }

    public Color getColorOfCoordinator() {
        return colorOfCoordinator;
    }

    public void setColorOfCoordinator(Color colorOfCoordinator) {
        this.colorOfCoordinator = colorOfCoordinator;
    }

    public Color getColorOfConnection() {
        return colorOfConnection;
    }

    public void setColorOfConnection(Color colorOfConnection) {
        this.colorOfConnection = colorOfConnection;
    }

    public Color getColorOfSelection() {
        return colorOfSelection;
    }

    public void setColorOfSelection(Color colorOfSelection) {
        this.colorOfSelection = colorOfSelection;
    }
}
