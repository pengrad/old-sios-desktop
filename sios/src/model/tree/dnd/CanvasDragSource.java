package model.tree.dnd;

import javax.swing.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class CanvasDragSource implements DragGestureListener, DragSourceListener {

    public CanvasDragSource(JComponent coordinator, JComponent manager, JComponent ingener) {
        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(coordinator, DnDConstants.ACTION_MOVE, this);
        dragSource.createDefaultDragGestureRecognizer(manager, DnDConstants.ACTION_MOVE, this);
        dragSource.createDefaultDragGestureRecognizer(ingener, DnDConstants.ACTION_MOVE, this);

    }

    public void dragGestureRecognized(DragGestureEvent dge) {
        Transferable transferable = new ViewTransferable((JComponent) dge.getComponent());
        dge.startDrag(null, transferable, this);
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
    }
}


