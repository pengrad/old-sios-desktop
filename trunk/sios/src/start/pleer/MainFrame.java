package start.pleer;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 24.02.2011
 * Time: 21:45:46
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setSize(500, 500);
        JPanel p = new JPanel();
        InputStream fis = null;
        try {
            fis = new DataInputStream(new FileInputStream("c:\\temp\\СИОС-2.mpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        MPEG_Play pleer = new MPEG_Play(fis,p,true);
//        pleer.init();
//        pleer.start();

        p.setBackground(Color.BLUE);
        getContentPane().add(p);
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
