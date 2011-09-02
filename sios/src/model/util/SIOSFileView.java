package model.util;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * User: parshin
 * Date: 20.12.10
 * Time: 18:07
 */

public class SIOSFileView extends FileView {

    public SIOSFileView() {

    }

    public boolean ourFile(File f) {
        return f.getName().toUpperCase().endsWith(SIOSFileFilter.FULL_FILE_END.toUpperCase());
    }

    @Override
    public String getName(File f) {
        if(ourFile(f)) {
            int i = f.getName().toUpperCase().lastIndexOf(SIOSFileFilter.FULL_FILE_END.toUpperCase());
            return f.getName().substring(0, i);
        } else return super.getName(f);
    }

    @Override
    public Icon getIcon(File f) {
        if(ourFile(f)) {
            return new ImageIcon(this.getClass().getResource("/res/iconFile.gif"));
        } else return super.getIcon(f);
    }
}
