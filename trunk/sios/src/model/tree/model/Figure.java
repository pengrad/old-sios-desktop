package model.tree.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 16.12.2010
 * Time: 17:35:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class Figure extends JComponent {
    protected Shape shape;
    protected boolean selected = false;
    protected boolean entered = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public abstract boolean contains(int x, int y);

}
