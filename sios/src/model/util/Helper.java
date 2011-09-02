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

        BasicSplitPaneDivider d = (BasicSplitPaneDivider)jSplitPane1.getComponent(0);
//        Component l = d.getComponent(0);
//        Component r = d.getComponent(1);
//        System.out.println(d.getLayout());

        d.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel toolbar = new JPanel();
        toolbar.add(new JLabel(text));
//        toolbar.add(new JButton(new AbstractAction("D") {
//            public void actionPerformed(ActionEvent e) {
//                jSplitPane1.setDividerLocation(0);
//            }
//        }));
//        toolbar.add(new JButton(new AbstractAction("U") {
//            public void actionPerformed(ActionEvent e) {
//                jSplitPane1.setDividerLocation(1.0d);
//            }
//        }));
        d.add(toolbar);
        int w = 2*toolbar.getPreferredSize().height - toolbar.getComponent(0).getPreferredSize().height;
        jSplitPane1.setDividerSize(w);
    }
}
