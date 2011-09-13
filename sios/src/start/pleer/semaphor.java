package start.pleer; /****************************************************************************************/
/*											*/
/*			    "start.pleer.semaphor.java"						*/
/*											*/
/* This file contains the class "start.pleer.semaphor". Objects of this class permit to suspend 2	*/
/* threads reciprocally. This is necessary to stop the MPEG layer I decoder during work	*/
/* of MPEG layer II decoder and vice versa.						*/
/*											*/
/*--------------------------------------------------------------------------------------*/
/*											*/
/* This program is free software; you can redistribute it and/or modify it under the	*/
/* terms of the GNU General Public License as published by the Free Software		*/
/* Foundation; either version 2 of the License, or (at your option) any later version.	*/
/*											*/
/* This program is distributed in the hope that it will be useful, but WITHOUT ANY	*/
/* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A	*/
/* PARTICULAR PURPOSE. See the GNU General Public License for more details.		*/
/*											*/
/* You should have received a copy of the GNU General Public License along with this	*/
/* program; (See "LICENSE.GPL"). If not, write to the Free Software Foundation, Inc.,	*/
/* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.				*/
/*											*/
/*--------------------------------------------------------------------------------------*/
/*											*/
/* If the program runs as Java applet it isn't "interactive" in the sense of the GNU 	*/
/* General Public License. So paragraph 2c doesn't apply.				*/
/*											*/
/****************************************************************************************/

public class semaphor {
	private boolean ready;

	public synchronized void toggle1() {
		ready = false; 				// de-block the other thread
		notify(); 				// inform the other thread
		while (!ready) {			// block until the other
			try {				// thread sets "ready" to
				wait();			// "true".
			} catch (InterruptedException e) {}
		}
	}

	public synchronized void toggle0() {
		ready = true;				// de-block the other thread
		notify();				// inform the other thread
		while (ready) {				// block until the other
			try {				// thread sets "ready" to
				wait();			// "false".
			} catch (InterruptedException e) {}
		}
	}
}
