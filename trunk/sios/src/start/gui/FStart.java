/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on 10.02.2011, 22:26:53
 */

package start.gui;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

import javax.swing.*;

/**
 * @author Евгений
 */
public class FStart extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public FStart() {

        try {
            com.jtattoo.plaf.mcwin.McWinLookAndFeel ll = new McWinLookAndFeel();
            McWinLookAndFeel.setTheme("Large-Font");
            UIManager.setLookAndFeel(ll);

        } catch (Exception e) {
//            e.printStackTrace();
        }

        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pContainre = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        pContainre.setLayout(new javax.swing.BoxLayout(pContainre, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pContainre, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pContainre, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FStart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pContainre;
    // End of variables declaration//GEN-END:variables

    public JPanel getpContainre() {
        return pContainre;
    }
}
