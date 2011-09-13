package start.pleer; /****************************************************************************************/
/*											*/
/*				"start.pleer.MPEG_Play.java"					*/
/*											*/
/* This file contains the main applet class and the class "start.pleer.Element" which stores	*/
/* pixel information of a single frame. Furthermore a class "start.pleer.dispatch" is implmeneted   */
/* The object of this class controls the to threads:					*/
/*			- ScanThread : Scanning of the MPEG Stream			*/
/*			- AnimatorThread: which shows in scan phase how many		*/
/*					  frames are already scanned.			*/
/*											*/
/* In case of an application a method "main()" is implemented.				*/
/*--------------------------------------------------------------------------------------*/
/*											*/
/*		Joerg Anders, TU Chemnitz, Fakultaet fuer Informatik, GERMANY		*/
/*		ja@informatik.tu-chemnitz.de						*/
/*											*/
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




import javax.swing.*;
import java.awt.*;				// Image, Frame, BorderLayout
import java.io.*;				// DataInputStream, IOException
import java.net.*;				// URL, MalformedURLException
import java.applet.Applet;
import java.awt.image.*;			// ColorModel, DirectColorModel, MemoryImageSource

/* The class "start.pleer.Element" can store the information of a single frame. By means of the	*/
/* property "next" it can build a list whose anchor is "Frame_List" in class 		*/
/* "start.pleer.MPEG_Play".										*/
/* The pixels are given as YUV values and they are then translated into the Java-AWT	*/
/* RGB model.										*/

/* An object of class "start.pleer.dispatch" is used to control the "ScanThread" and the	*/
/* "AnimatorThread" during scan phase. The "AnimatorThread" displays information*/
/* of the state of the scanning process. It waites as long as no new frame	*/
/* is available. The "ScanThread" does never wait. The (pardonable) effect is:	*/
/* The user isn't necessarily informed of all scanned frames.			*/


/* The class "start.pleer.MPEG_Play" is the main applet. Its work is divides into 2 phases:	*/
/*	- scanning: A "ScanThread" and an "AnimatorThread" work concurrently	*/
/*		    The "ScanThread" produces a list of frames (images) and	*/
/*		    informes the "AnimatorThread" if a frame is decoded.	*/
/*	- display:  By means of the method "start.pleer.Element.close_chain()"  the list	*/
/*		    of frames is closed to a ring of frames. After that		*/
/*		    the frames are displayed. The "ScanThread" dies and the 	*/
/*		    "AnimatorThread" begins to display the frames.		*/

public class MPEG_Play extends Applet implements Runnable {
	private int h, w;				// width and height
	private int o_h, o_w;				// width and height of the original pictures
	public boolean inApplet = true;			// Is it an applet or an application ?
	private JPanel the_frame;			// THE frame (in application)
	private InputStream in_stream = null;		// THE MPEG resource
	private dispatch Dispatcher = new dispatch();	// THE dispatcher
	private Element akElement = null;		// the actual element
	private final int R = 55;			// a border

   	private Thread AnimatorThread = null;  // the animation runs as thread
   	private Thread ScanThread = null;      // the scanner runs as thread
	private boolean Scanning = true;       // in scan phase ???
	private boolean first = true;	       // for the first time in display phase ?
	private Element Frame_List = null;	// The anchor
	private Element last_P_or_I = null;	// last P or I frame;
	private DirectColorModel cm = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
	private URL old_url = null;	// to what URL belongs the decoded list
	private URL source_url = null;	// to build the URL to MPEG resource

	/* This constructor is only called in application */

	public MPEG_Play(JPanel f) {
		the_frame = f;		// notice
		inApplet = false;	// notice
	}


	public MPEG_Play() {} 	// This constructor is only called in applet

	/* The method "init()" opens up the MPEG resource and produces and */
	/* starts the "ScanThread"					   */

	public void start (InputStream is) {
        this.in_stream=is;
		MPEG_scan M_Scan;	// The scanner object
		boolean io = true;	// everything OK ????

		if (io && Frame_List == null) { // create the scanner object and start the scanner thread:
			M_Scan = new MPEG_scan(this, in_stream);
			ScanThread = new Thread(M_Scan);
			ScanThread.start();
		}

		if (AnimatorThread == null) {
	           	AnimatorThread = new Thread(this); // create the "AnimatorThread"
	           	AnimatorThread.start();      	   // pass the thread to the scheduler;
							   // At first the "AnimatorThread" works
							   // concurrently with the
		}					   // "ScanThread"
	}

	public void stop() {
		AnimatorThread = null; // stop animation loop
		if (ScanThread != null) ScanThread.stop();     // stop Scanning
		Frame_List=null;
        if (Scanning) {        // reset all in scanning process
			try {
				if (in_stream != null) in_stream.close(); // the stream must be opened again
			}
			catch (Exception e) {e.printStackTrace();}
		}
	}

	/* The method "close_chain()" is called after the scanner is ready.	*/
	/* It closed the list of Elements and establishes a ring of Images      */
	/* this way.								*/

	public void close_chain() {
		Scanning = false;		// inform the "AnimatorThread"
		Dispatcher.put(Frame_List);	// unlock the "AnimatorThread"
		try {
			in_stream.close();
		}
		catch (Exception e) {};
		if (inApplet) {
			try {
				old_url = new URL(source_url.toString()); // notice;
			}
			catch (Exception e) {
				Err.Msg = e.toString();
			}
		}
	}


	/* The method "run()" implements the "AnimatorThread". During scan phase */
	/* is waits in "Dispatcher.get()". Then it calls "repaint()" to activate */
	/* "update()". In scan phase "update()" only prints a decoder status	 */
	/* information to the user.						 */
	/* After scan phase the frames are displayed.				 */

	public void run() { 
		while (AnimatorThread != null && Scanning) { // not dead; in scan phase
			akElement = Dispatcher.get();  // wait till a new frame arrives
			repaint();		       // activate (probably) "update"
			try {
				Thread.yield();	       // give "ScanThread" a chance 
			}
			catch (Exception e) { // don't know what to do in applet
				Err.Msg = "Exception: " + e.toString();
			}
		}
	}

	/* This method is necessary because the scanner must have the possibility */
	/* to resize the applet and (possibly) the frame once he has recognized	  */
	/* the dimensions of the MPEG frames.					  */
	/* This method is called by the "ScanThread".				  */

	public void set_dim (int width, int height, int o_width, int o_height) {
		w = width; h = height; // The width in MPEG stream
		o_w = o_width; o_h = o_height; // The original width
		resize(width < 250 ? 250 : o_width, o_height + R);
		if (!inApplet) {
			 the_frame.resize(width < 250 ? 250 : o_width, o_height + R + 20);
			 the_frame.setLayout( new BorderLayout());
			 the_frame.add("Center", this);
//			 the_frame.setTitle("JAVA-MPEG-Player");
			 the_frame.show();
		}
	}


	/* The method "set_Pixels" expects the pixel data, the type and number	*/
	/* of the next frame in display order to produce an new frame-"start.pleer.Element".*/
	/* Then it places the new start.pleer.Element at the appropriate position in List.	*/
	/* It is called in "ScanThread".					*/

	public void set_Pixels(int Pixels[][], int f_idx, int f_type) {

		Element newElement = new Element(this, Pixels, cm, f_idx, f_type, w, h, o_w, o_h);

		/* put the frame into the list in display order: */

		if (Frame_List == null) { // first start.pleer.Element
			Dispatcher.put(newElement); // inform the dispatcher of the arrival of a new frame
		}
		else if (f_type == MPEG_video.B_TYPE) { // B - Frames are already in display order
			Dispatcher.put(newElement); // inform the dispatcher of the arrival of a new frame
		}
		else { // a P or I frame; they are not necessary in display order
			if (last_P_or_I != null) { // last I or P frame
				Dispatcher.put(last_P_or_I); // inform the dispatcher of the arrival of a new frame
			}
			last_P_or_I = newElement;	     // notice for later insertion;
		}

		try {				// give the "AnimatorThread"
			Thread.sleep(50);	// 50 msec to inform the user
		}				// of the arrival of the new frame
		catch (Exception e) {	// don't know what to do in applet
			Err.Msg = "Exception : " + e.toString();
		}
	}

	/* The method "action" is the event handler and is called whenever the	*/
	/* user pushed a button. It determins the button and changes the 	*/
	/* behaviour of the animation.						*/


	public void stopanim() {
		AnimatorThread = null;
	}

	/* The method "update" must decide whether the program is in	*/
	/* scan phase or not. If so, it displays the actual scan state	*/
	/* to the user. Otherwise it displays the next frame.		*/
	/* If an error occured it calls "paint()" to show the error 	*/
	/* message.							*/

   	public void update(Graphics g) {  // overwrites the "update()" - method of Applet
		if (Err.Msg != null) { // error ?
    			paint(g);
			return;
		}
		if (Scanning) {
           			if (akElement != null) {
				String Type_s = null;
				switch(akElement.Frame_type) {
					case MPEG_video.I_TYPE: Type_s = " (I_FRAME) decoded"; break;
					case MPEG_video.B_TYPE: Type_s = " (B_FRAME) decoded"; break;
					case MPEG_video.P_TYPE: Type_s = " (P_FRAME) decoded"; break;
					default: Type_s = "unknown"; break;
				}
//				g.setColor(getBackground()); // clear background
//				g.fillRect(0, o_h, 250, R + 20);
//				g.setColor(Color.red);
//				g.drawString("Decoding WAIT!!!" , 10, o_h + 15);
//				g.setColor(Color.black);
//				g.drawString("Frame Nr. " + akElement.Frame_idx + Type_s, 10, o_h + 30);
				g.drawImage(akElement.Picture, 0, 0, this);
                           akElement.Picture=null;
                           akElement=null;
             	return;
			}
		}

         	}

	/* The method "paint()" is only called in error case. It displays the	*/
	/* error message.							*/

	public void paint(Graphics g) {
		if (Err.Msg != null) {
			if (!inApplet) {
				the_frame.resize(200, 60);
			}
			resize(200, 60);
			g.setColor(getBackground());
			g.fillRect(0, 0, o_w, o_h);
			g.setColor(Color.black);
			g.drawString(Err.Msg, 1, 15);
			if (!inApplet) {
				System.out.println(Err.Msg);
				System.exit(10);
			}
		}
	}

	/* If the program runs as application the method "main()" is called at first.	*/
	/* It creates a frame to get a display surface.					*/

	
}
