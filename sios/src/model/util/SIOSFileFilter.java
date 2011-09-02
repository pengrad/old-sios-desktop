package model.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * User: parshin
 * Date: 17.12.10
 * Time: 17:19
 */

public class SIOSFileFilter extends FileFilter {

    public static final String FILE_END = "sios";
    public static final String FULL_FILE_END = "." + SIOSFileFilter.FILE_END;

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) return true;
        String name = file.getName().toUpperCase();
        return name.endsWith(("." + FILE_END).toUpperCase());
    }

    @Override
    public String getDescription() {
        return "Модель структуры управления";
    }
}
