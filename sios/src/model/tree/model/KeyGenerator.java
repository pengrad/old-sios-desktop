package model.tree.model;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 20.08.2010
 * Time: 17:21:54
 * To change this template use File | Settings | File Templates.
 */
public class KeyGenerator {
    private static int i = 0;

    public static int getKey() {
        return i++;
    }
}
