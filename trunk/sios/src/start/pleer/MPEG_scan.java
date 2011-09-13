package start.pleer; /****************************************************************************************/
/*											*/
/*				"start.pleer.MPEG_scan.java"					*/
/*											*/
/* This file contains the class "start.pleer.MPEG_scan". The object of this class scans the MPEG-	*/
/* packet-layer (layer I). It creates a video-( - decoder -) object.			*/
/* It determines the kind of information in packet and passes evenually the information	*/
/* to the video ( - decoder - ) object.							*/
/* The scanner works as thread concurrently to the "AnimatorThread" which informs the	*/
/* user about the state of the scanning process.					*/
/* Currently only video tracks are analysed. If the "start.pleer.MPEG_scan" determins a video track	*/
/* it informs the input filter about the length of this video track and passes the	*/
/* control to the video decoder thread. By means of a semaphore the "start.pleer.MPEG_scan" (which	*/
/* runs as thread) will stop until the input filter has read the appropriate amount	*/
/* of bytes. After that the input filter suspends the video decoder and wakes up the 	*/
/* "start.pleer.MPEG_scan".	The whole process is difficult to understand because there is an	*/
/* contradiction: Generally the MPEG decoding process is syntax controlled. And there	*/
/* is a concret syntactical element to switch from MPEG layer I to MPEG layer II. BUT:	*/
/* There is no syntactical element to switch back to layer I. The amount of layer II	*/
/* bytes is given instead. So the input filter has to count the bytes during MPEG 	*/
/* layer II. And the input filter has to initiate the switching process from layer II	*/
/* to layer I. That is difficult because the input filter has an internal shift		*/
/* register. That is: It is possible that the shift register contains data already	*/
/* belonging to the new layer.								*/
/*											*/
/* To understand how the scanner works knowledge of the MPEG file format is needed	*/
/* (See ISO 11172-1).									*/
/*											*/
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

import start.pleer.Err;
import start.pleer.io_tool;

import java.io.InputStream;

public class MPEG_scan implements Runnable {
	private io_tool mpeg_stream;		// input filter
	private MPEG_Play Player;		// To reference to the applet
	private MPEG_video video_decoder;	// THE video decoder
	private Thread video_thread;		// The video decoder runs as thread
	private semaphor video_semaphor = new semaphor(); // toggle between layer I and II
	private boolean video_started = false;  // video already starded ?

	private int stream_id;		// the kind of data

	// some MPEG Packet layer constants:

	private static final int PACK_START_CODE = 0x000001ba;
	private static final int SYSTEM_HEADER_START_CODE = 0x000001bb;
	private static final int PACKET_START_CODE_PREFIX = 0x000001;
	private static final int ISO_11172_END_CODE = 0x000001b9;

	// Stream ID values:

	private static final int RESERVED_STREAM = 0xbc;
	private static final int PRIVATE_STREAM1 = 0xbd;
	private static final int PADDING_STREAM  = 0xbe;
	private static final int PRIVATE_STREAM2 = 0xbf;

	/* The constructor only notices the parameters and prepears the MPEG 	*/
	/* stream for reading. Furthermore it creates an video- ( - decoder - )	*/
	/* object.								*/

	MPEG_scan (MPEG_Play play, InputStream s) {
		Player = play; 				// notice
		mpeg_stream = new io_tool(s, video_semaphor);
		video_decoder = new MPEG_video(play, mpeg_stream);
		video_thread = new Thread(video_decoder);
	}

	/* The method "reset" is called after the end of the complete file */
	/* (JIT version only) 						   */

	public void reset(InputStream s) {
		video_semaphor = new semaphor();
		mpeg_stream = new io_tool(s, video_semaphor);	// reopen the stream
		video_decoder = new MPEG_video(Player, mpeg_stream);
		video_thread = new Thread(video_decoder);
		video_started = false;
	}

	/* Beause the scanner works as thread a method "run()" must be		  */
	/* implemented.	It scans the MPEG stream according to ISO 11172-1 and	  */
	/* performs some initialization steps.					  */

	public void run () {
		mpeg_stream.next_start_code();
		if (mpeg_stream.next_bits(PACK_START_CODE, 32)) {
			do {
				Parse_pack();
			}
			while (!mpeg_stream.is_eof() && mpeg_stream.next_bits(PACK_START_CODE, 32));
		}
		else {	/* Many MPEG files only contain video (layer II) data. In	*/
			/* this case the video parser is called:			*/
			video_thread.start();
			return;	// The thread dies and the video decoder thread continues!
		}
		if (mpeg_stream.next_bits(ISO_11172_END_CODE, 32)) {
			System.out.println("ISO_11172_END_CODE found");
		}
		else {
			int wert = mpeg_stream.get_bits(32);
			System.out.println("ISO_11172_END_CODE missing(0x" + Integer.toHexString(wert)
				+ ")" );
		}
	}

	private void Parse_pack() {
		mpeg_stream.get_bits(32);
		if (mpeg_stream.get_bits(4) != 0x2) {
			Err.Msg = "sychronization error in pack";
			return;
		}
		mpeg_stream.get_bits(30); // system clock reference ignored
		mpeg_stream.get_bits(30); // system clock reference ignored
		if (mpeg_stream.next_bits(SYSTEM_HEADER_START_CODE, 32)) {
			Parse_system_header();
		}

		while (!mpeg_stream.is_eof() && !mpeg_stream.next_bits(PACK_START_CODE, 32) &&
				!mpeg_stream.next_bits(ISO_11172_END_CODE, 32)) {
			Parse_packet();
		}

	}

	private void Parse_system_header() {
		mpeg_stream.get_bits(30); // ignore information
		mpeg_stream.get_bits(30); // ignore information
		mpeg_stream.get_bits(30); // ignore information
		mpeg_stream.get_bits(6);  // ignore information
		while (mpeg_stream.next_bits(1, 1)) {
			mpeg_stream.get_bits(24);
		}
	}

	private void Parse_packet() {
		int pack_length;
		int read_bytes;
		boolean skip_bits = true;
		if (mpeg_stream.get_bits(24) != 0x1) {
                        Err.Msg = "sychronization error in packet(!)";
			return;
                }
		stream_id = mpeg_stream.get_bits(8);
		pack_length = mpeg_stream.get_bits(16);

		if (stream_id != PRIVATE_STREAM2) {
			while (mpeg_stream.next_bits(0xff, 8)) {
				mpeg_stream.get_bits(8); // ignore information
				pack_length -= 1;
			}
			if (mpeg_stream.next_bits(0x1, 2)) {
				mpeg_stream.get_bits(16); // ignore information
				pack_length -= 2;
			}
			if (mpeg_stream.next_bits(0x2, 4)) {
				mpeg_stream.get_bits(20); // ignore information
				mpeg_stream.get_bits(20); // ignore information
				pack_length -= 5;
			}
			else if (mpeg_stream.next_bits(0x3, 4)) {
				mpeg_stream.get_bits(20); // ignore information
				mpeg_stream.get_bits(20); // ignore information
				mpeg_stream.get_bits(20); // ignore information
				mpeg_stream.get_bits(20); // ignore information
				pack_length -= 10;
			}
			else if (mpeg_stream.get_bits(8) != 0x0f) {
				System.out.println("synchronization error (3)");
				System.exit(10);
			}
			else {
				pack_length--;
			}
		}
		if ((0xe0 & stream_id) == 0xc0) { // MPEG AUDIO stream --> skip
		}
		else switch (0xf0 & stream_id) { // MPEG VIDEO STREAM --> analyse
			case 0xe0:	skip_bits = false;

					/* substract the bytes already in bit shift register: */

					pack_length -= mpeg_stream.get_read_bytes();

					/* inform the input filter about the length of	*/
					/* the video track:				*/
					mpeg_stream.set_rest_bytes(pack_length);

					if (!video_started) {
						video_started = true;
						video_thread.start();
					}
					video_semaphor.toggle1(); /* pass control to video     */
								  /* decoder thread and wait   */
								  /* until the video part      */
								  /* is analysed	       */
				   break;
			case 0xf0: // RESERVED STREAM ID
				   break;
			default: switch (stream_id) {
				 case RESERVED_STREAM: break;
				 case PRIVATE_STREAM1: break;
				 case PADDING_STREAM: break;
				 case PRIVATE_STREAM2: break;
				 default: Err.Msg = "unknown Stream: 0x" + 
							Integer.toHexString(stream_id); return;
				 }
		}
		/* skip over non-video tracks: */
		if (skip_bits) mpeg_stream.skip(pack_length);
	}
}
