package start.pleer; /****************************************************************************************/
/*											*/
/*				"start.pleer.MPEG_video.java"					*/
/*											*/
/* This file contains the class "start.pleer.MPEG_video". The object of this class scans the MPEG-	*/
/* video stream, extracts the information (especially the DCT values), activates the	*/
/* start.pleer.IDCT, applies the motion vectors and passes the pixel values to the applet.		*/
/* Furthermore it resizes the applet and (possibly) the frame once it has recognized	*/
/* the dimensions of the frame.								*/
/* If the scanner is ready it calls the method "close_chain" which prepares the frames	*/
/* for playing.										*/
/* To understand how the video scanner works knowledge of the MPEG file format is	*/
/* needed (See ISO 11172-2).								*/
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

public class MPEG_video implements Runnable {
	private io_tool mpeg_stream;	// input filter
	private Huffmann Huf = null;	// the VLC (Hufmann) decoder
	private int values[];		// return values of the Hufmann decoder
	private int nullmatrix[] = new int[64]; // a matrix of zeros

	private int  intramatrix[] = {		// the default intramatrix
		 8, 16, 19, 22, 26, 27, 29, 34,
                16, 16, 22, 24, 27, 29, 34, 37,
                19, 22, 26, 27, 29, 34, 34, 38,
                22, 22, 26, 27, 29, 34, 37, 40,
                22, 26, 27, 29, 32, 35, 40, 48,
                26, 27, 29, 32, 35, 40, 48, 58, 
                26, 27, 29, 34, 38, 46, 56, 69,
                27, 29, 35, 38, 46, 56, 69, 83};
	
	private int zigzag[] = {		// the reverse zigzag scan order
		 0,  1,  8, 16,  9,  2,  3, 10,
		17, 24, 32, 25, 18, 11,  4,  5,
		12, 19, 26, 33, 40, 48, 41, 34,
		27, 20, 13,  6,  7, 14, 21, 28,
		35, 42, 49, 56, 57, 50, 43, 36,
		29, 22, 15, 23, 30, 37, 44, 51,
		58, 59, 52, 45, 38, 31, 39, 46,
		53, 60, 61, 54, 47, 55, 62, 63};
	
	private IDCT idct = new IDCT();		// an start.pleer.IDCT object to tranform the DCT coefficients
	private int dct_recon[] = new int[64];	// the values before start.pleer.IDCT transformation
	private int non_intramatrix[] = new int[64]; 
	private boolean lum_block;		// is it the first luminance block ???
	private int dct_dc_cr_past, dct_dc_cb_past, dct_dc_y_past; // value of past DC values

	/* The "Pel_buffer" is a main feature of the video scanner.			*/
	/* 3 frames are stored at:							*/
	/*				Pel_buffer[0]					*/
	/*				Pel_buffer[1]					*/
	/*				Pel_buffer[2]					*/
	/* The frame at index "ak_idx" is the frame coming into being. The frame at	*/
	/* index "pred_idx" is the frame for forward prediction. The frame at index	*/
	/* "back_idx" is the frame for backward prediction. The method "Parse_Picture"	*/
	/* administers the values of these 3 variables.					*/
	/* The index in second dimension determins whether the information is:		*/
	/*										*/
	/*		luminance   information		-	Pel_buffer[?][0]	*/
	/*		chrominance information (cr)	-	Pel_buffer[?][1]	*/
	/*		chrominance information	(cb)	-	Pel_buffer[?][2]	*/

	private int Pel_buffer[][][];		
						
	private int ak_idx = 0, pred_idx = -1, back_idx = -1; //"who is who ???"
	private MPEG_Play Player;		// To reference to the applet

	// some MPEG VIDEO layer constants:

	private static final int SEQ_END_CODE = 0x000001b7;
	private static final int SEQ_START_CODE = 0x000001b3;
	private static final int GOP_START_CODE = 0x000001b8;
	private static final int PICTURE_START_CODE = 0x00000100;
	private static final int SLICE_MIN_START_CODE = 0x00000101;
	private static final int SLICE_MAX_START_CODE = 0x000001af;
	private static final int EXT_START_CODE = 0x000001b5;
	private static final int USER_START_CODE = 0x000001b2;

	public static final int I_TYPE = 0x1;
	public static final int P_TYPE = 0x2;
	public static final int B_TYPE = 0x3;

	// Some MPEG parameter:

	private int Width, Height, Asp_ratio, Pic_rate;
	private int mb_width, mb_height;
	private int Bit_rate, VBV_buffer; 
	private boolean const_param, quant_matrix;
	private int Hour, Minute, Second, Pict_Count;
	private boolean Drop_Flag, Closed_Group, Broken_Link;
	private int Temp_ref, Pic_Type, Frame_nr_offset = -1, Frame_nr = 0;
	private int VBV_Delay;

	// Some values for forward prediction:

	private boolean Full_pel_forw_vector;
	private int forw_f_code, forward_f, forward_r_size; 
	private int motion_horiz_forw_code, motion_horiz_forw_r;
	private int motion_verti_forw_code, motion_verti_forw_r;

	// Some values for backward prediction:

	private boolean Full_pel_back_vector;
	private int backward_f_code, backward_f, backward_r_size;
	private int motion_horiz_back_code, motion_horiz_back_r;
	private int motion_verti_back_code, motion_verti_back_r;

	private int Quant_scale; // quantization factor
	private int macro_block_address, past_intra_address; // actual and past MB address
	private int mb_row, mb_column;	// actual position of the macro block

	// what other information are available in current macroblock:

	private boolean macro_block_motion_forward, macro_block_motion_backward, macro_block_pattern;
	private boolean macro_block_quant = false, macro_block_intra = false;

	// 2 variables to grab the referred area from predicted frame:

	private int pel1[] = new int[16*16 + 2 * 8 * 8], pel2[] = new int[16*16 + 2 * 8 * 8];

	// 2 objects of class "start.pleer.motion_data" to notice and compute the motion values:

	private motion_data Forward = new motion_data();
	private motion_data Backward = new motion_data();

	// working variables:

	private int pixel_per_lum_line, pixel_per_col_line, lum_y_incr, col_y_incr;

	/* The constructor only notices the parameter and creates an	*/
	/*  VLC (Hufmann) decoder object. 				*/

	public MPEG_video (MPEG_Play play, io_tool tool) {
		Player = play; 		// notice
		for (int i = 0; i < 64; i++) {
			non_intramatrix[i] = 16;	// initiali-
			nullmatrix[i] = 0;		// zation
		}

		/* The necessity of the following to calls is only understandable after	*/
		/* reading "http://rnvs.informatik.tu-chemnitz.de/~ja/MPEG/HTML/start.pleer.IDCT.html"*/

		idct.norm(intramatrix);
		idct.norm(non_intramatrix);

		mpeg_stream = tool;
		Huf = new Huffmann(mpeg_stream);// create VLC (Hufmann) decoder
	}

	/* The method "parse_video_stream" parsese the MPEG video stream	*/
	/* according to ISO 11172-2 and performs some initial steps.		*/

	public void run() {
		mpeg_stream.next_start_code();
		do {
			Parse_sequence_header();

			/* After reading the header the dimensions are known.
			 * Therefore let's resize the applet and (probably)
			 * the frame and initialize the 2 prediction objects.
			 * Then create the pel buffer:
			 */

			if (Pel_buffer == null) { // The sequence header can appear many times
			   Player.set_dim(mb_width * 16, mb_height * 16, Width, Height);
			   Pel_buffer = new int[3][3][mb_width * 16 * mb_height * 16];

			   // some derived values used in "set_lum_pixel", "set_col_pixel",
			   // "correct_lum_pixel" and "correct_col_pixel":

			   pixel_per_lum_line = mb_width << 4;
			   pixel_per_col_line = mb_width << 3;
			   lum_y_incr = pixel_per_lum_line - 8;
			   col_y_incr = pixel_per_col_line - 8;
			   Forward.init(pixel_per_lum_line, pixel_per_col_line, pixel_per_lum_line - 16, col_y_incr);
			   Backward.init(pixel_per_lum_line, pixel_per_col_line, pixel_per_lum_line - 16, col_y_incr);
			}

			do {
				Parse_group_of_pictures();
			}
			while (!mpeg_stream.is_eof() && mpeg_stream.next_bits(GOP_START_CODE, 32));
		}
		while(!mpeg_stream.is_eof() && mpeg_stream.next_bits(SEQ_START_CODE, 32));
		Player.close_chain(); /* End of stream! --> close the list to */
				      /* a ring of pictures		      */
			       /* Note: This is the last action of the	      */
			       /* "ScannerThread" before it dies. Therefore   */
			       /* the method "close_chain()" informs the      */
			       /* "AnimatorThread" about the demise of this   */
			       /*  thread.    				     */

			       
	}

	/* The method "Parse_sequence_header" parses the sequence header accor- */
	/* ding to ISO 11172-2.							*/

	private void Parse_sequence_header() {
		if (mpeg_stream.get_bits(32) != SEQ_START_CODE) {
			Err.Msg = "SEQ_START_CODE expected";
			Player.repaint();
			return;
		}
		Width = mpeg_stream.get_bits(12);
		Height  = mpeg_stream.get_bits(12);
		mb_width = (Width + 15) / 16;
		mb_height = (Height + 15) / 16;
		Asp_ratio = mpeg_stream.get_bits(4);
		Pic_rate = mpeg_stream.get_bits(4);
		Bit_rate = mpeg_stream.get_bits(18);
		mpeg_stream.get_bits(1);
		VBV_buffer = mpeg_stream.get_bits(10);
		const_param = mpeg_stream.get_bits(1) == 1;
		quant_matrix = mpeg_stream.get_bits(1) == 1;
		if (quant_matrix) {
			for (int i = 0; i < 64; intramatrix[i++] = (0xff & mpeg_stream.get_bits(8)));
			idct.norm(intramatrix);
		}
		quant_matrix = mpeg_stream.get_bits(1) == 1;
		if (quant_matrix) {
			for (int i = 0; i < 64; non_intramatrix[i++] = (0xff & mpeg_stream.get_bits(8)));
			idct.norm(non_intramatrix);
		}
		mpeg_stream.next_start_code();
		if (mpeg_stream.next_bits(USER_START_CODE, 32)) {
			mpeg_stream.get_bits(32);
			while (!mpeg_stream.next_bits(0x1, 24)) {
				mpeg_stream.get_bits(8);
			}
		}
	}

	/* The method "Parse_group_of_pictures" parses the group of pictures */
	/* according to ISO 11172-2. The information is simply ignored.	     */

	private void Parse_group_of_pictures() {
		if (mpeg_stream.get_bits(32) != GOP_START_CODE) {
			Err.Msg = "GOP_START_CODE expected";
			Player.repaint();
			return;
		}
		Drop_Flag = mpeg_stream.get_bits(1) == 1;
		Hour = mpeg_stream.get_bits(5);
		Minute = mpeg_stream.get_bits(6);
		mpeg_stream.get_bits(1);
		Second = mpeg_stream.get_bits(6);
		Pict_Count = mpeg_stream.get_bits(6);
		Closed_Group = mpeg_stream.get_bits(1) == 1;
		Broken_Link = mpeg_stream.get_bits(1) == 1;
		mpeg_stream.next_start_code();
		if (mpeg_stream.next_bits(EXT_START_CODE, 32)) {
			while (!mpeg_stream.next_bits(0x1,24)) {
				mpeg_stream.get_bits(8);
			}
			System.out.println("Cannot deal with MPEG-2 data stream!");
			Err.Msg = "Cannot deal with MPEG-2 data stream!";
			return;
		}
		if (mpeg_stream.next_bits(USER_START_CODE, 32)) {
			while (!mpeg_stream.next_bits(0x1,24)) {
				mpeg_stream.get_bits(8);
			}
		}

		// notice the frame number because it is reset to zero
		// at group of pictures

		Frame_nr_offset = Frame_nr + 2; // don't know why ???
		do {
			Parse_picture();
		}
		while (!mpeg_stream.is_eof() && mpeg_stream.next_bits(PICTURE_START_CODE, 32));
	}

	/* The method "Parse_picture" parses a picture according	*/
	/* to ISO 11172-2. It determines the frame number in display	*/
	/* order and the picture type. Depending on the picture type	*/
	/* some special actions are performed. Especially the index of	*/
	/* the referred frame for forward and backward prediction is to	*/
	/* be defined.							*/

	private void Parse_picture () {
		int start_c;

		if (mpeg_stream.get_bits(32) != PICTURE_START_CODE) {
			Err.Msg = "PICTURE_START_CODE expected";
			Player.repaint();
			return;
		}
		Temp_ref = mpeg_stream.get_bits(10); // picture number in display order
		Frame_nr = Frame_nr_offset + Temp_ref;
		Pic_Type = mpeg_stream.get_bits(3);
		VBV_Delay =  mpeg_stream.get_bits(16); // ignored
		if (Pic_Type == P_TYPE || Pic_Type == I_TYPE) pred_idx = back_idx;
		if (Pic_Type == P_TYPE || Pic_Type == B_TYPE) {
			if (pred_idx == -1) {
				System.out.println("Warning: No predictive Frame in P_FRAME");
				pred_idx = (ak_idx + 2) % 3;
			}

			Full_pel_forw_vector = mpeg_stream.get_bits(1) == 1;  // meaning of motion data
			forw_f_code = mpeg_stream.get_bits(3);		      // THE
			forward_r_size = forw_f_code - 1;		      // MOTION
			forward_f = 1 << forward_r_size;	   	      // DATA
			Forward.set_pic_data(forward_f, Full_pel_forw_vector);// (forward)
		}
		if (Pic_Type == B_TYPE) {
			if (back_idx == -1) {
				System.out.println("Warning: No Backward Predictive Frame in B_TYPE");
				back_idx = (ak_idx + 1) % 3;
			}
			Full_pel_back_vector =  mpeg_stream.get_bits(1) == 1;  // meaning of motion data
			backward_f_code = mpeg_stream.get_bits(3);	       // THE
			backward_r_size = backward_f_code - 1;		       // MOTION
			backward_f = 1 << backward_r_size;	               // DATA
			Backward.set_pic_data(backward_f, Full_pel_back_vector);// (backward)
		}
		while(mpeg_stream.next_bits(0x1,1)) {
			mpeg_stream.get_bits(8);
		}
		mpeg_stream.get_bits(1);
		mpeg_stream.next_start_code();

		if (mpeg_stream.next_bits(EXT_START_CODE, 32)) {
			mpeg_stream.get_bits(32);
			while (!mpeg_stream.next_bits(0x1, 24)) {
				mpeg_stream.get_bits(8);
			}
		}

		if (mpeg_stream.next_bits(USER_START_CODE,32)) {
			mpeg_stream.get_bits(32);
			while (!mpeg_stream.next_bits(0x1, 24)) {
				mpeg_stream.get_bits(8);
			}
		}
		if (Pic_Type == 4) { // implement ???
			Err.Msg = "can't decode D-Type Frames";
			return;
		}
		
		do {
			Parse_slice();
			start_c = mpeg_stream.get_bits(32);
                        mpeg_stream.unget_bits(32);
		}
		while(start_c >= SLICE_MIN_START_CODE && start_c <= SLICE_MAX_START_CODE);
		// A frame (picture) is ready. Pass the YUV values to the applet:
		if (Pic_Type != 4) { // superfluous  ???
			Player.set_Pixels(Pel_buffer[ak_idx], Frame_nr, Pic_Type);
		}
		if (Pic_Type == P_TYPE || Pic_Type == I_TYPE) { // reorder the indexes
			back_idx = ak_idx; ak_idx = (ak_idx + 1) % 3;
		}

	}

	/* The method "Parse_slice" parses a slice according	 */
	/* to ISO 11172-2. It determines quantization scale and  */
	/* the macroblock address of the first macro block of    */
	/* the slice.						 */

	private void Parse_slice() {
		int k = mpeg_stream.get_bits(32); // this field contains the 
						  // macro block address
		int b_nr = 0;			  // macroblock nr in slice

		past_intra_address = -2;	// initialization (see ISO 11172-2)
		dct_dc_y_past = dct_dc_cb_past = dct_dc_cr_past = 1024; // dito
		Forward.reset_prev(); Backward.reset_prev(); 		// reset motion data
		macro_block_address = ((k & 0xff) - 1) * mb_width - 1; //extract MB address
		if (k < SLICE_MIN_START_CODE || k > SLICE_MAX_START_CODE) {
			System.out.println("SLICE START CODE expected");
			Err.Msg = "SLICE START CODE expected";
			Player.repaint();
			return;
		}
		Quant_scale = mpeg_stream.get_bits(5);
		
		while (mpeg_stream.next_bits(0x1, 1)) {
			mpeg_stream.get_bits(1);
			mpeg_stream.get_bits(8);
		}
		mpeg_stream.get_bits(1);
		do {
			Parse_macroblock(b_nr++);
		}
		while (!mpeg_stream.next_bits(0x0, 23));
		mpeg_stream.next_start_code();
	}

	/* The method "Parse_macroblock" parses a macroblock according 	     */
	/* to ISO 11172-2. It is one of the complexest methods because of    */
	/* the great variety of the constitution of a macroblock. The 	     */
	/* constitution and existence of the the most information fields     */
	/* depends on the constitution and existence of information fields   */
	/* before. 							     */
	/* Furthermore the decoding process is controlled by this method.    */
	/* In some situations some variables must be reset to some default   */
	/* values or in case of skipped macroblocks implizit values must be  */
	/* applied.							     */
	/* Bear in mind that some variables used in this method are member   */
	/* (class) variables for later reference!			     */

	private void Parse_macroblock(int b_nr) {
		int inc = 0;	// if the macro block increment is 
				// greater than 1 some blocks are
				// skipped; this requires special treatment.
		int cbp = 0x3f; // coded block pattern: it determins which of
				// the 6 blocks are really coded
		int inc_tmp, mb_a_tmp, mb_r_tmp, mb_c_tmp; // working variables

		while (mpeg_stream.next_bits(0xf, 11)) {
			mpeg_stream.get_bits(11); // skip macro block escape
		}
		while (mpeg_stream.next_bits(0x8, 11)) {
			mpeg_stream.get_bits(11); // macro block skipping
			inc += 33; 		  // every skip means +33
		}
		values = Huf.decode(11, Huf.macro_block_inc); 
		inc += values[2];		  // decode macro block increment
		if (inc > 1) {	// special treatment for skipped macroblocks
		   dct_dc_y_past = dct_dc_cr_past = dct_dc_cb_past = 1024; // default values
		   if (Pic_Type == B_TYPE && b_nr > 0) { // in this case the motion vectors rest valid
			for (inc_tmp = inc - 1, mb_a_tmp = macro_block_address + 1; inc_tmp-- > 0; mb_a_tmp++) {
				mb_r_tmp = mb_a_tmp / mb_width;	// compute the macroblock row and
				mb_c_tmp = mb_a_tmp % mb_width; // column for the next skipped block
				if (macro_block_motion_forward) { // apply forward prediction
					if (!macro_block_motion_backward) { 
						Forward.copy_area(mb_r_tmp, mb_c_tmp, Pel_buffer[pred_idx], Pel_buffer[ak_idx]);
					}
					else {
						Forward.get_area(mb_r_tmp, mb_c_tmp, Pel_buffer[pred_idx], pel1);
					}
				}
				if (macro_block_motion_backward) { // apply backward prediction
					if (!macro_block_motion_forward) { 
						Backward.copy_area(mb_r_tmp, mb_c_tmp, Pel_buffer[back_idx],Pel_buffer[ak_idx]);
					}
					else {
						Backward.get_area(mb_r_tmp, mb_c_tmp, Pel_buffer[back_idx], pel2);
						Backward.put_area(mb_r_tmp, mb_c_tmp, pel1, pel2, Pel_buffer[ak_idx]);
					}
				}
			}
		   }
		   else if (Pic_Type != I_TYPE) { // in P_TYPE the motion vector is to be reset
			Forward.reset_prev();
			if (b_nr > 0 && !macro_block_motion_backward && !macro_block_motion_backward) {
				for (inc_tmp = inc - 1, mb_a_tmp = macro_block_address + 1; inc_tmp-- > 0; mb_a_tmp++) {
					 mb_r_tmp = mb_a_tmp / mb_width; // compute the macroblock row and
					 mb_c_tmp = mb_a_tmp % mb_width; // column for the next skipped block
					 Forward.copy_unchanged(mb_r_tmp, mb_c_tmp, Pel_buffer[pred_idx], Pel_buffer[ak_idx]);
				}
			}
		   }
					
		}
		macro_block_address += inc; // (but) now: compute the new macro block address
		mb_row = macro_block_address / mb_width;    
		mb_column = macro_block_address % mb_width;
		switch (Pic_Type) { // depending on the frame type the existence of some information
				    // fields must be determined
			case I_TYPE: macro_block_motion_forward =   // these information is certainly not
				     macro_block_motion_backward =  // suppplied in intra coded frames
				     macro_block_pattern = false; 
				     macro_block_intra = true;	    // Of course!
				     if (mpeg_stream.get_bits(1) == 1) { // possibly a
					     macro_block_quant = false;  // new quantization
				     }					 // factor is
				     else {				 // supplied
					     macro_block_quant = true;
					     mpeg_stream.get_bits(1);
				     }
				     break;
			case P_TYPE: values = Huf.decode(6, Huf.p_type_mb_type);   // decode
				     macro_block_quant = values[2] != 0;	   // extract
				     macro_block_motion_forward = (values[3] == 1);
				     macro_block_motion_backward = false;
				     macro_block_pattern = (values[4] == 1);
				     if (!(macro_block_intra = values[5] != 0)) { // default values
						dct_dc_y_past = dct_dc_cr_past = dct_dc_cb_past = 1024;
						cbp = 0;
				     }
				     break;
			case B_TYPE: values = Huf.decode(6, Huf.b_type_mb_type); // decode
				     macro_block_quant = values[2] != 0;	 // extract
				     macro_block_motion_forward = (values[3] == 1);
				     macro_block_motion_backward = (values[4] == 1);
				     macro_block_pattern = (values[5] == 1);
				     if (!(macro_block_intra = values[6] != 0)) { // default values
						dct_dc_y_past = dct_dc_cr_past = dct_dc_cb_past = 1024;
						cbp = 0;
				     }
				     break;
			default: Err.Msg = "unknown Frame-Typee : " + Pic_Type;
				 Player.repaint();
				 return;
			}
		if (macro_block_quant) { // extract new quantization factor
			Quant_scale = mpeg_stream.get_bits(5);
		}
		if (macro_block_motion_forward) { // motion vector for forward prediction exists
			values = Huf.decode(11, Huf.motion_code); // decode horizontal motion information
			motion_horiz_forw_code = values[2];	  // extract horizontal motion information
			if (forward_f != 1 && motion_horiz_forw_code != 0) {
				motion_horiz_forw_r = mpeg_stream.get_bits(forward_r_size);
			}
			values = Huf.decode(11, Huf.motion_code); // decode vertical motion information
			motion_verti_forw_code = values[2];	  // extract vertical motion information
			if (forward_f != 1 && motion_verti_forw_code != 0) {
				motion_verti_forw_r = mpeg_stream.get_bits(forward_r_size);
			}
			// according to this information the motion vector must be decoded
			Forward.compute_motion_vector(motion_horiz_forw_code, motion_verti_forw_code,
						     motion_horiz_forw_r, motion_verti_forw_r);
				
			// grab the referred area into "pel1"
			if (Pic_Type != B_TYPE || !macro_block_motion_backward) { // no backward prediction
				// put the grabbed area into the actual frame:
				Forward.copy_area(mb_row, mb_column, Pel_buffer[pred_idx], Pel_buffer[ak_idx]);
			}
			else {
				Forward.get_area(mb_row, mb_column, Pel_buffer[pred_idx], pel1);
			}
		}
		else if (Pic_Type != B_TYPE){ // (only) in P_TYPE the motion vector is to be reset. 
			Forward.reset_prev(); // (in B_TYPE it rests valid)
		}
		if (macro_block_motion_backward) { // motion vector for backward prediction exists
			values = Huf.decode(11, Huf.motion_code); // decode horizontal motion information
			motion_horiz_back_code = values[2];	  // extract horizontal motion information
			if (backward_f != 1 && motion_horiz_back_code != 0) { 
				motion_horiz_back_r = mpeg_stream.get_bits(backward_r_size);
			}
			values = Huf.decode(11, Huf.motion_code);  // decode vertical motion information
			motion_verti_back_code = values[2];	   // extract vertical motion information
			if (backward_f != 1 && motion_verti_back_code != 0) {
				motion_verti_back_r = mpeg_stream.get_bits(backward_r_size);
			}
			// according to this information the motion vector must be decoded
			Backward.compute_motion_vector(motion_horiz_back_code, motion_verti_back_code,
						     motion_horiz_back_r, motion_verti_back_r);
				
			if (!macro_block_motion_forward) { // no forward prediction
				// put the grabbed area into the actual frame:
				Backward.copy_area(mb_row, mb_column, Pel_buffer[back_idx], Pel_buffer[ak_idx]);
			}
			else { // forward and backward prediction:
				// grab the refered area into "pel2"
				Backward.get_area(mb_row, mb_column, Pel_buffer[back_idx], pel2);
			       // put the average of the 2 areas into the actual frame:
				Backward.put_area(mb_row, mb_column, pel1, pel2,Pel_buffer[ak_idx]);
			}
		}
		
		if (macro_block_pattern) { // coded block pattern supplied
			values = Huf.decode(9, Huf.block_pattern); // decode coded block pattern
			cbp = values[2];			   // extract
		}

		if (Pic_Type == P_TYPE && !macro_block_motion_backward && !macro_block_motion_forward) {
			Forward.copy_unchanged(mb_row, mb_column, Pel_buffer[pred_idx], Pel_buffer[ak_idx]);
		}
		lum_block = false; // there wasn't any luminace block in this macroblock
		for (int i = 0; i < 6; i++) { // all 6 blocks
			if ((cbp & (1 << (5 - i))) != 0) { // block information supplied ?
				Parse_Block(i);		   // yes -->  get block information
				if (macro_block_intra) {   // in intra macro blocks the values are absolute
				 	if (i < 4) set_lum_pixel(i);
					else	   set_col_pixel(i);
				}
				else { // in inter coded macroblocks the values are correctings
					if (i < 4) correct_lum_pixel(i);
					else       correct_col_pixel(i);
				}
			}
		}
		if (Pic_Type == B_TYPE && macro_block_intra) {		// otherwise the motion
			Forward.reset_prev(); Backward.reset_prev();    // vectors rest valid
		}
	}

	/* The method "Parse_Block" parses a block according to ISO 11172-2. */
	/* Thereby the DC and AC coefficients are reconstructed and placed   */
	/* into the "dct_recon" field in de-"zigzag"-ed order. After that    */
	/* the start.pleer.IDCT routine is called. The method counts the coefficients    */
	/* and calls a sparse start.pleer.IDCT method if the coefficient count is 1.     */

	private void Parse_Block(int nr) {
		int idx = 0, size, sign, idx_run  = 0, level; // working variables
		int coeffCount = 0;	// coefficient count
		int pos = 0;		// the actual (de-"zigzag"-ed) position of the coefficient
		System.arraycopy(nullmatrix, 0, dct_recon, 0, 64); // initialization
		if (macro_block_intra) {
			if (nr < 4) { // luminance block
				values = Huf.decode(7, Huf.dct_size_luminance);
				size = values[2]; // size of the DC coefficient
				if (size != 0) {
					set_dct_diff(mpeg_stream.get_bits(size), size);
				}
				if (lum_block) { // not first luminance block
					dct_dc_y_past = dct_recon[0] = dct_dc_y_past + (dct_recon[0] << 3);
				}
				else {		// first luminance block
					lum_block = true;
					dct_recon[0] <<= 3;
					if (macro_block_address - past_intra_address > 1) {
						dct_dc_y_past = dct_recon[0] += 1024;
					}
					else { // relative if no skipping
						dct_dc_y_past = dct_recon[0] += dct_dc_y_past;
					}
				}
				past_intra_address = macro_block_address; // notice
			}
			else { // chrominance block
				values = Huf.decode(8, Huf.dct_size_crominance);
				size = values[2];
				if (size != 0) {
					set_dct_diff(mpeg_stream.get_bits(size), size);
				}
				switch (nr) {
					case 4: dct_recon[0] <<= 3;
						if (macro_block_address - past_intra_address > 1) {
							dct_dc_cb_past = dct_recon[0] += 1024;
						}
						else { // relative if no skipping
							dct_dc_cb_past = dct_recon[0] += dct_dc_cb_past;
						}
						break;
					case 5: dct_recon[0] <<= 3;
						if (macro_block_address - past_intra_address > 1) {
							dct_dc_cr_past = dct_recon[0] += 1024;
						}
						else { // relative if no skipping
							dct_dc_cr_past = dct_recon[0] += dct_dc_cr_past;
						}
						break;
				}
			}
			past_intra_address = macro_block_address; // notice
			if (dct_recon[0] != 0) coeffCount = 1; // count coefficients
			dct_recon[0] <<= idct.VAL_BITS - 3;    // because of the start.pleer.IDCT technique:
							       // the DC values are not quantized;
							       // therefore the fix point translation
							       // must be performed
		}
		else { //  no intra coded block --> first AC value
			if (mpeg_stream.next_bits(0x1, 1)) { // special treatment of the VLC "1"
				idx = 0; 
				mpeg_stream.get_bits(1);
				sign = level = mpeg_stream.get_bits(1) == 0 ? 1 : -1; // the sign follows
			}
			else {
				values = Huf.decode(28, Huf.dct_coeff); // decode AC value
				idx  = values[2];			// extract AC value
				if (idx == Huf.DCT_ESCAPE) {		// special treatment
					idx  = mpeg_stream.get_bits(6); // once again
					if ((((level = mpeg_stream.get_bits(8)) & 0x7f) == 0)) { // 16 bit
						level <<= 8;
						level |= mpeg_stream.get_bits(8);
						if ((level & 0x8000) != 0) level |= 0xffffff00; // sign ??
					}
					else if ((0x80 & level) != 0) { // sign ??
						level |= 0xffffff00;
					}
				}
				else { // "normal" treatment (no escape); extract AC coefficient
					level = mpeg_stream.get_bits(1) == 0 ? values[3] : -values[3];
				}
				sign = (level == 0) ? 0 : ((level < 0) ? -1 : 1);  // determine sign
			}
			pos = zigzag[idx]; // de-"zigzag" 

			// Quantization:

			dct_recon[pos] = ((level + sign) * Quant_scale *  non_intramatrix[pos]) >> 3;

 	/*++++++ ATTENTION: The "oddification" absences here. Feel free to insert the code. ++++*/
	/*++++++            I've regarded this as a waste of time because I can't recognize ++++*/
	/*++++++	    any difference.						    ++++*/
	
			if (level != 0) coeffCount++; // count the coefficients
		}
		values = Huf.decode(28, Huf.dct_coeff); // decode AC value
		while((idx_run  = values[2]) != Huf.EOB) { // no end of block; read the other AC values 
			if (idx_run  == Huf.DCT_ESCAPE) {           // special treatment
				idx_run  = mpeg_stream.get_bits(6); // once again
				if ((((level = mpeg_stream.get_bits(8)) & 0x7f) == 0)) { // 16 bit
					level <<= 8;
					level |= mpeg_stream.get_bits(8);
					if ((level & 0x8000) != 0) level |= 0xffffff00;  // sign ??
				}
				else if ((0x80 & level) != 0) { // sign ??
					level |= 0xffffff00;
				}
				idx += idx_run  + 1; // the position is now given as a difference
			}
			else { // "normal" treatment (no escape); extract AC coefficient
				idx += idx_run  + 1; // the position is now given as a difference
				level = mpeg_stream.get_bits(1) == 0 ? values[3] : -values[3]; 
			}
				if (idx > 63) idx = 63;
				pos = zigzag[idx]; // de-"zigzag"
			if (macro_block_intra) { // different treatment of quantization depending on type
				dct_recon[pos] = (level * Quant_scale *  intramatrix[pos]) >> 3;
			}
			else {
				sign = (level == 0) ? 0 : ((level < 0) ? -1 : 1); 
				dct_recon[pos] = ((level + sign) * Quant_scale *  non_intramatrix[pos]) >> 3;
			}

 	/*++++++ ATTENTION: The "oddification" absences here. Feel free to insert the code. ++++*/
	/*++++++            I've regarded this as a waste of time because I can't recognize ++++*/
	/*++++++	    any difference.						    ++++*/
	
			if (level != 0) coeffCount++; // count the coefficients
			values = Huf.decode(28, Huf.dct_coeff); // decode next value
		}
		if (coeffCount == 1) { // only one coefficient ??
			idct.invers_dct_special (dct_recon, pos); // call a sparse method
		}
		else {
			idct.invers_dct(dct_recon); // full decoding
		}
	}

	/* The method "set_dct_diff" computes the DCT difference according to	*/
	/* ISO 11172-2. Is sets "dct_recon[0]".					*/

	private void set_dct_diff(int dct_diff, int dct_size) {
		if ((dct_diff & (1 << (dct_size - 1))) != 0) dct_recon[0] = dct_diff;
		else dct_recon[0] = ((-1) << (dct_size)) | (dct_diff+1);
	}

	/* The method "set_lum_pixel" takes the re-transformed luminance values and	*/
	/* places them at the appropriate position. Note that the variables:		*/
	/* 										*/
	/* 			pixel_per_lum_line					*/
	/*			mb_row							*/
	/*			mb_column						*/
	/*										*/
	/* are computed in "run()" as soon it was possible.				*/

	private void set_lum_pixel(int nr) {
		int i, j;
		int pos = pixel_per_lum_line * ((mb_row << 4) + ((nr & 0x2) << 2)) +
			  (mb_column << 4) + ((nr & 0x1) << 3);

		for (j = 0; j < 64; j += 8) {
			System.arraycopy(dct_recon, j,  Pel_buffer[ak_idx][0], pos, 8);
			pos += pixel_per_lum_line;
		}
	}

	/* The method "set_col_pixel" takes the re-transformed chrominance values and	*/
	/* places them at the appropriate position. Note that the variables:		*/
	/* 										*/
	/* 			pixel_per_col_line					*/
	/*			mb_row							*/
	/*			mb_column						*/
	/*										*/
	/* are computed in "run()" as soon it was possible.				*/

	private void set_col_pixel(int nr) {
		int i, j;
		int pos = pixel_per_col_line * (mb_row << 3) + (mb_column << 3);
		switch (nr) {
		case 4: for (j = 0; j < 64; j += 8) {
				System.arraycopy(dct_recon, j, Pel_buffer[ak_idx][2], pos, 8);
				pos += pixel_per_col_line;
			}
			break;
		case 5: for (j = 0; j < 64; j += 8) {
				System.arraycopy(dct_recon, j, Pel_buffer[ak_idx][1], pos, 8);
				pos += pixel_per_col_line;
			}
			break;
		}
	}
		
	/* The method "correct_lum_pixel" is called in predicted macro blocks. Because	*/
	/* the values in "dct_recon" are motion compensation information the task of	*/
	/* this method is to correct the already supplied (copied) values.		*/
	/* 										*/
	/* Note that the variables:							*/
	/* 										*/
	/* 			pixel_per_lum_line					*/
	/*			lum_y_incr						*/
	/*			mb_row							*/
	/*			mb_column						*/
	/*										*/
	/* are computed in "run()" as soon it was possible.				*/

	private void correct_lum_pixel(int nr) {
		int i, j, k = 0;
		int pos = pixel_per_lum_line * ((mb_row << 4) + ((nr & 0x2) << 2)) +
			  (mb_column << 4) + ((nr & 0x1) << 3);

		for (j = 0; j < 8; j++) {
			for (i = 0; i < 8; i++) {
				Pel_buffer[ak_idx][0][pos++] += dct_recon[k++];
			}
			pos += lum_y_incr; // pixel_per_lum_line - 8
		}
	}

	/* The method "correct_col_pixel" is called in predicted macro blocks. Because	*/
	/* the values in "dct_recon" are motion compensation information the task of	*/
	/* this method is to correct the already supplied (copied) values.		*/
	/* 										*/
	/* Note that the variables:							*/
	/* 										*/
	/* 			pixel_per_col_line					*/
	/*			col_y_incr						*/
	/*			mb_row							*/
	/*			mb_column						*/
	/*										*/
	/* are computed in "run()" as soon it was possible.				*/

	private void correct_col_pixel(int nr) {
		int i, j, k = 0;
		int pos = pixel_per_col_line * (mb_row << 3) + (mb_column << 3);
		switch (nr) {
		case 4: for (j = 0; j < 8; j++) {
				for (i = 0; i < 8; i++) {
					Pel_buffer[ak_idx][2][pos++] += dct_recon[k++];
				}
				pos += col_y_incr; // pixel_per_col_line - 8
			}
			break;
		case 5: for (j = 0; j < 8; j++) {
				for (i = 0; i < 8; i++) {
					Pel_buffer[ak_idx][1][pos++] += dct_recon[k++];
				}
				pos += col_y_incr; // pixel_per_col_line - 8
			}
			break;
		}
	}
}

