package start.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: parshin
 * Date: 30.09.11
 * Time: 19:20
 */
public class DChooseStartMode extends JDialog {

    private int result;
    private JEditorPane textPane;

    public DChooseStartMode(JFrame parent, boolean modal, int bSynthes, int bAnalys) {
        super(parent, modal);
        initComponents(bSynthes, bAnalys);
    }

    private void initComponents(final int bSynthes, final int bAnalys) {
        setTitle("Выберите режим...");
        setSize(300, 300);
        setResizable(false);
        setMaximumSize(new Dimension(300, 1000));
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        JButton b1 = new JButton(new AbstractAction("Синтез") {
            public void actionPerformed(ActionEvent e) {
                result = bSynthes;
                setVisible(false);
            }
        });
        panel.add(b1);
        b1 = new JButton(new AbstractAction("Анализ") {
            public void actionPerformed(ActionEvent e) {
                result = bAnalys;
                setVisible(false);
            }
        });
        panel.add(b1);
        this.add(panel, BorderLayout.NORTH);
        panel = new JPanel(new BorderLayout());
        textPane = new JEditorPane();
        //textPane = new JEditorPane("text/html", "");
        textPane.setContentType("text/html");
        textPane.setText("Описание");
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        panel.add(scrollPane);
        this.add(panel);
    }

    public JEditorPane getTextHelpPane() {
        return textPane;
    }

    public int showDialog(Component componentRelative) {
        setLocationRelativeTo(componentRelative);
        result = -1;
        setVisible(true);
        return result;
    }

    public static void main(String[] args) {
        new DChooseStartMode(new JFrame(), true, 1, 2).setVisible(true);
    }
}
