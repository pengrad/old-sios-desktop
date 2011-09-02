package model.tree.model;

import model.tree.gui.ConnectLine;

/*
 Интерфейс между моделью дерева и использующим дерево классом
 */
public interface TreeListener {

    public void addComponentNode(Node node);

    public void removeComponentNode(Node node);

    public void addComponentBetweenNodes(ConnectLine line);

    public void removeComponentBetweenNodes(ConnectLine line);
}