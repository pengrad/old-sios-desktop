package model.util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import java.awt.*;

/**
 * User: parshin
 * Date: 01.12.2010
 * Time: 14:19:51
 */

public class Helper {

    public static String getMinutesToString(int min) {
        int h = min / 60;
        int m = min % 60;
        String mid = m < 10 ? ":0" : ":";
        return h + mid + m;
    }

    private static int id = 1;

    public static int getId() {
        return 10000000+(++id);
    }
    
    public static void setLabelToSplitPane(JSplitPane jSplitPane1, String text) {
        JPanel p = new JPanel();
        p.add(new JLabel(text));
        setComponentToSplitPane(jSplitPane1, p);
    }

    public static void setComponentToSplitPane(JSplitPane jSplitPane1, JComponent component) {
        BasicSplitPaneDivider d = (BasicSplitPaneDivider)jSplitPane1.getComponent(0);
        d.setLayout(new BorderLayout());
        d.add(component);
        int w = 2*component.getPreferredSize().height - component.getComponent(0).getPreferredSize().height;
        jSplitPane1.setDividerSize(w);
    }
}
