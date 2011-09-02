package start;

import model.manager.Controller;
import model.model.Executor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

import model.model.Task;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 16.02.2011
 * Time: 17:13:28
 * To change this template use File | Settings | File Templates.
 */
public class TreeRender extends DefaultTreeCellRenderer {
    Icon managerIcon = new ImageIcon(getClass().getResource("/res/userManager.png"));
    Icon executorIcon = new ImageIcon(getClass().getResource("/res/userExecutor.png"));
    Icon taskIcon = new ImageIcon(getClass().getResource("/res/task.png"));
    Icon rootIcon = new ImageIcon(getClass().getResource("/res/root.png"));

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, sel,
                expanded, leaf, row, hasFocus);

        this.hasFocus = hasFocus;
        setText(stringValue);

        Color fg = null;

        JTree.DropLocation dropLocation = tree.getDropLocation();
        if (dropLocation != null
                && dropLocation.getChildIndex() == -1
                && tree.getRowForPath(dropLocation.getPath()) == row) {

            Color col = UIManager.getColor("Tree.dropCellForeground");
            if (col != null) {
                fg = col;
            } else {
                fg = getTextSelectionColor();
            }

        } else if (sel) {
            fg = getTextSelectionColor();
        } else {
            fg = getTextNonSelectionColor();
        }

        setForeground(fg);

        Object object = ((DefaultMutableTreeNode) value).getUserObject();
        if (object instanceof Executor) {
            Executor executor = (Executor) object;
            if (Controller.get().getEmplManager().isManager(executor)) {
                setIcon(managerIcon);
            } else {
                setIcon(executorIcon);
            }
        } else if (object instanceof Task) {
            setIcon(taskIcon);
        } else {
            setIcon(rootIcon);

        }

        setComponentOrientation(tree.getComponentOrientation());

        selected = sel;

        return this;
    }

}
