package start.pleer; /****************************************************************************************/
/*											*/
/*				  "start.pleer.io_tool.java"					*/
/*											*/
/* This file contains some special IO methods. They are necessary because the MPEG	*/
/* stream is bit oriented. The io tool is constituted by a DataInputStream on to of	*/
/* a BufferedInputStream.  Furthermore a bit shift register is supplied.		*/
/*											*/
/* The "start.pleer.io_tool" has the special task to assist the switching between MPEG layer I and	*/
/* MPEG layer II. During analysing  MPEG layer II it counts the remaining bytes for	*/
/* layer II and initiates a switch back to layer I if the counter is (near) zero.	*/
/* (See also comment in "start.pleer.MPEG_scan.java"!)						*/
/*											*/
/* See also: http://rnvs.informatik.tu-chemnitz.de/~ja/MPEG/HTML/imp_asp.html		*/
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

import java.io.*;	// BufferedInputStream, DataInputStream, IOException

public class io_tool {
	DataInputStream  dis = null;		// THE input stream
	private int bit_pos = 0; 		// bit- "pointer" 
	private long longword = 0L;		// THE bit shift register
	private long save_word = 0L;		// A copy of the bit shift register
	private int save_pos = 0;		// A copy of "bit_pos"
	private boolean eof = false;

	private long next_bits_val;		// the last value of "next_bits"
	private int next_bits_available = 0; 	// how many bits of "next_bits" are valid
	public int rest_bytes = -1;		// the counter: How many MPEG layer II bytes
						// remain. A negative value means: This is
						// MPEG layer I
	private semaphor video_semaphor;	// Semaphor to stop video decoder and
						// layer I decoder reciprocally

	io_tool(InputStream stream, semaphor sem ) {
		dis = new DataInputStream (new BufferedInputStream(stream)); // create the stream
		video_semaphor = sem;	// notice
	}

	public void close() throws IOException {
		dis.close();
	}

	public boolean is_eof() { return eof; }

	/* The method "skip(long n)" skpis over "n" bytes but takes into account that	*/
	/* some of them are already in shift reagister.					*/

	public void skip(int n) {
		int k = bit_pos / 8; // How many bytes are already in shift register ?
		n -= k;		     // substract from whole count
		bit_pos -= k * 8;    // skip over the bits in shift register
		next_bits_available = 0; // destroy correlation between "get_bits" and "net_bits"
		try {
			dis.skipBytes(n);
		}
		catch (IOException e) {
			Err.Msg = "skip: " + e.toString();
		}
	}

	/* The method "get_read_bytes()" tells how many bytes are in shift register.	*/

	public int get_read_bytes() {
		if ((bit_pos & 0x7) != 0) { // "bit_pos" sould be byte aligned
			Err.Msg = "get_read_bytes: bit_pos = " + bit_pos;
		}
		return (bit_pos / 8);
	}

	/* The method "set_rest_bytes" sets the number of MPEG layer II bytes.	*/

	public void set_rest_bytes(int bytes) {
		rest_bytes = bytes;
	}

	/* The method "get_long" grabs "bytes" from data input stream 	*/
	/* into the shift register "longword".				*/

	public void get_long () {
		try {
			/* If less than 4 bytes remain in layer II it must be checked */
			/* whether it is possible to return 32 bit .		      */
			if ((rest_bytes & 0xfffffffc) == 0 ) { // rest_bytes > 0 && rest_bytes < 4
				if (rest_bytes * 8 >= 32 - bit_pos) { // still 32 bit available ?
					while (bit_pos < 32) { // Yes --> read still some bytes
						longword = (longword << 8) | (0xff & dis.readByte());
						bit_pos += 8;
						rest_bytes--;
					}
					return;
				}

				/* There aren't still 32 bit available in layer II --> save the	*/
				/* bits on bit shift register:					*/
				save_word = longword & ((1L << bit_pos) - 1L);
				save_pos = bit_pos;

				/* read the remaining layer II bytes and put them to the saved	*/
				/* shift register.						*/
				for(;rest_bytes > 0; rest_bytes--) {
					save_word <<= 8;
					save_word |= (0xff & dis.readByte());
					save_pos += 8;
				}

				/* prepare for switch to layer I:				*/

				rest_bytes = -1; /* signal; we are now in layer I !!!		*/
				bit_pos = 0;	 /* clear shift register.			*/
				next_bits_available = 0; /* destroy correlation between		*/
							 /* "get_bits" and "next_bits"		*/
							
				video_semaphor.toggle0(); /* suspend layer II thread; wake up	*/
							  /* layer I thread.			*/
				if (save_pos + bit_pos > 63) { // ERROR ERROR ERROR
					System.out.println("Recovery not possible = " + save_pos +
					"; bit_pos = " + bit_pos);
				}
				longword &= ((1L << bit_pos) - 1L); // cut leading bits to "0"
				longword |= (save_word << bit_pos); // recover saved bits
				next_bits_available = 0; /* destroy correlation between		*/
							 /* "get_bits" and "next_bits"		*/
				bit_pos += save_pos;	// correct bit pointer
				while (bit_pos < 32) {  // make 32 bit available
					longword = (longword << 8) | (0xff & dis.readByte());
					bit_pos += 8; rest_bytes--;
				}
				return;
			}
			else { // normal processing
				longword = (longword << 32) | (0xffffffffL & dis.readInt());
				bit_pos += 32;
				rest_bytes -= 4;
			}
		}
		catch (EOFException e) {
			eof = true;
			bit_pos += 32;
			longword <<= 32;
			System.out.flush();
		}
		catch (IOException e) {
			System.out.println("get_long: " + e.toString());
			System.exit(10);
		}
	}

	/* The method "get_bits" gets the next "n" bits from shift register */
	/* interprets them as integer and returns this integer value.       */

	public int get_bits (int n) {
		long val;
		int nb;

		if (next_bits_available >= n) { // if last access was via "next_bits" 
			bit_pos -= n;		// use this result
			nb = next_bits_available;
			next_bits_available = 0;
			return (int) next_bits_val >>> (nb - n);
		}
		next_bits_available = 0;
		if (bit_pos < n) {
			get_long();
		}
		val = longword >>> (bit_pos - n);
		val &= (1L << n) - 1L; // mask leading bits
		bit_pos -= n; // correct bit "pointer"
		return (int) val;
	}

	/* The method "next_bits" checks whether the next "n" bits match the	*/
	/* "pattern". If so it returns "true"; otherwise "false".		*/
	/* Note: This method changes the bit "pointer" physically BUT NOT	*/
	/* logically !!!							*/

	public boolean next_bits (int pattern, int n) {
		next_bits_available = n;
		if (bit_pos < n) {
			get_long();
		}
		next_bits_val = longword >>> (bit_pos - n);
		next_bits_val &= (1L << n) - 1L; // mask leading bits
		return (next_bits_val == pattern);
	}

	/* The method "unget_bits" gives "n" bits back to the IO system. Because */
	/* "n" is always less than 32 this can be performed by a simple 	 */
	/* correction of the bit "pointer".				 	 */

	public void unget_bits(int n) {
		next_bits_available = 0;
		bit_pos += n;
	}

	/* The method "next_start_code" aligns the bit "pointer" to the next	*/
	/* byte and tries to find the next MPEG start code. Because (only) start*/
	/* codes are made of a 24-bit ONE the method searches such a pattern.	*/
	/* (see also: ISO 11172-2)						*/

	public void next_start_code() {
		if ((bit_pos & 0x7) != 0) {
			bit_pos &= ~0x7;
		}
		while (!next_bits(0x1, 24) && !eof) get_bits(8);
	} 
}
