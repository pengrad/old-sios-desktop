package gui.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableMouseListener extends MouseAdapter {
    private JPopupMenu popMenu;
    private JTable table;
    private ActionListener actionOnClick;


    public TableMouseListener(JTable table, JPopupMenu popupMenu, ActionListener actionOnClick) {
        this.table = table;
        this.popMenu = popupMenu;
        this.actionOnClick = actionOnClick;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            actionOnClick.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = table.rowAtPoint(new Point(e.getX(), e.getY()));
        if (i >= 0 && i < table.getRowCount())
            table.setRowSelectionInterval(i, i);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (table.contains(new Point(e.getX(), e.getY())))
                popMenu.show(table, e.getX(), e.getY());
        }
    }
}