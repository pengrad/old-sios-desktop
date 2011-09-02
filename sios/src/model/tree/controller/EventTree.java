package model.tree.controller;

import model.tree.gui.ConnectLine;
import model.tree.model.Node;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 01.02.2011
 * Time: 14:11:32
 * To change this template use File | Settings | File Templates.
 */
public interface EventTree {

    public void addNode(Node node);

    public void editNode(Node node);

    public void connectNode(Node parent, Node child);

    public void disconnect(ConnectLine connectLine);

    public void removeNode(boolean delCild, Node node);

}
