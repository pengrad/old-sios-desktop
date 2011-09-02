package model.tree.dnd;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ViewTransferable implements Transferable {
    private JComponent node;
    DataFlavor[] flavors = new DataFlavor[]{DataFlavor.javaFileListFlavor};

    public ViewTransferable(JComponent node) {
        this.node = node;
    }


    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(flavors[0])) {
            return true;
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        return node;
    }
}