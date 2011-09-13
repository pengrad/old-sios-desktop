package start.pleer;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 24.02.2011
 * Time: 22:48:52
 * To change this template use File | Settings | File Templates.
 */
class dispatch {
	private Element newframe = null;
	private boolean available = false;

	public synchronized Element get() {
		if (available == false) {
			try {
				wait();
			} catch (InterruptedException e) {
				Err.Msg = "Exception : " + e.toString();
			}
		}
		available = false;
		notifyAll();
		return newframe;
	}

	public synchronized void put(Element akEle) {
		newframe = akEle;
		available = true;
		notifyAll();
	}
}
