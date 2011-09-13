package start.pleer; /****************************************************************************************/
/*											*/
/*				"start.pleer.motion_data.java"					*/
/*											*/
/* This file contains the class "start.pleer.motion_data" which is used to store and compute the	*/
/* motion information. Two objects of this class are created: One for forward and	*/
/* one for backward prediction.								*/
/*											*/
/* To understand the methods refer also to ISO 11172-2!					*/
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

public class motion_data {
	private int recon_right_x_prev = 0, recon_down_x_prev = 0;
	private int recon_right_x = 0, recon_down_x = 0;
	private int right_x = 0, down_x = 0;
	private boolean right_half_x = false, down_half_x = false;	 
	private int right_x_col = 0, down_x_col = 0;
	private boolean right_half_x_col = false, down_half_x_col = false;	 
	private int x_ward_f = 0,  min = 0, max = 0, range = 0;
	private boolean Full_pel_x_vector = true;

	private int pixel_per_lum_line, pixel_per_col_line, lum_y_incr, col_y_incr;

	/* The method "init" is called by the "ScanThread" as soon as the MPEG	*/
	/* dimensions are known. 						*/

	public void init (int f_pixel_per_lum_line, int f_pixel_per_col_line, int f_lum_y_incr, int f_col_y_incr) {
		pixel_per_lum_line = f_pixel_per_lum_line;
		pixel_per_col_line = f_pixel_per_col_line;
		lum_y_incr = f_lum_y_incr;
		col_y_incr = f_col_y_incr;
	}

	/* The method "set_pic_data" is called from "Parse_Picture" as soon as	*/
	/* the size of the motion data is given.				*/

	public void set_pic_data(int f, boolean Full) {
		x_ward_f = f; Full_pel_x_vector = Full;
		range = f << 5;
		max = (f << 4) - 1;
		min = -(f << 4);
	}

	/* In some situations it is necessary to reset the motion data. */
	/* Therefore a method "reset_prev" is implemented.		*/

	public void reset_prev() {
		recon_right_x_prev = recon_down_x_prev = 0;
	}

	/* The internal method "motion_displacement" computes the difference of the	*/
	/* actual motion vector in respect to the last motion vector. Refer to 		*/
	/* ISO 11172-2 to understand tho coding of the motion displacement.		*/

	private int motion_displacement(int motion_code, int PMD, int motion_r) {
		int dMD, MD;
	
		if (x_ward_f == 1 || motion_code == 0) {
			dMD = motion_code;
		}
		else {
			dMD = 1 + x_ward_f * (Math.abs(motion_code) - 1);
			dMD += motion_r;
			if (motion_code < 0) dMD = -dMD;
		}
		MD = PMD + dMD;
		if (MD > max) MD -= range;
		else if (MD < min) MD += range;
		return MD;
	}

	/* The method "compute_motion_vector" computes the motion vector according to the */
	/* values supplied by the "ScanThread". It uses the method "motion_displacement". */
	/* The result are the motion vectors for the luminance and the chrominance blocks.*/ 

	public void compute_motion_vector(int motion_horiz_x_code, int motion_verti_x_code,
					  int motion_horiz_x_r, int motion_verti_x_r) {

		recon_right_x_prev = recon_right_x = 
		motion_displacement(motion_horiz_x_code, recon_right_x_prev, motion_horiz_x_r);
		if (Full_pel_x_vector) recon_right_x <<= 1;
		recon_down_x_prev = recon_down_x = 
		motion_displacement(motion_verti_x_code, recon_down_x_prev, motion_verti_x_r);
		if (Full_pel_x_vector) recon_down_x <<= 1;

		right_x = recon_right_x >> 1;
		down_x  = recon_down_x  >> 1;
		right_half_x = (recon_right_x & 0x1) != 0;
		down_half_x  = (recon_down_x  & 0x1) != 0;

		right_x_col = recon_right_x >> 2;
		down_x_col  = recon_down_x >> 2;
		right_half_x_col = (recon_right_x & 0x2) != 0;
		down_half_x_col  = (recon_down_x & 0x2) != 0;
	}

	/* The method "get_area" grabs the area determined by the motion 	*/
	/* vector from "src" and puts it to the temporary storage "dst". If	*/
	/* the motion vectors define half steps the method follows the 		*/
	/* instructions given in ISO 11172-2.					*/
	 

	public void get_area(int mb_row, int mb_column, int src[][], int dst[]) {
		int ypos = (mb_row << 4) + down_x;
		int xpos = (mb_column << 4) + right_x;
		int pos, pos1, pos2, pos3, dst_pos = 0;
		int pos_c, pos1_c, pos2_c, pos3_c;

		// LUMINACE:

		if (!right_half_x && !down_half_x) {
			pos = pixel_per_lum_line * ypos + xpos;
			for (int j = 0; j < 16; j++) {
				System.arraycopy(src[0], pos, dst, dst_pos, 16);
				pos += pixel_per_lum_line; dst_pos += 16;
			}
		}
		else if (!right_half_x && down_half_x)  {
			pos = pixel_per_lum_line * ypos + xpos;
			pos1 = pixel_per_lum_line * (ypos + 1) + xpos;
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					dst[dst_pos++] = (src[0][pos++] + src[0][pos1++]) >> 1;
				}
				pos += lum_y_incr; pos1 += lum_y_incr;
			}
		}
		else if (right_half_x && !down_half_x) {
			pos = pixel_per_lum_line * ypos + xpos;
			pos1 = pixel_per_lum_line * ypos + xpos + 1;
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					dst[dst_pos++] = (src[0][pos++] + src[0][pos1++]) >> 1;
				}
				pos += lum_y_incr; pos1 += lum_y_incr;
			}
		}
		else {
			pos = pixel_per_lum_line * ypos + xpos;
			pos1 = pixel_per_lum_line * (ypos + 1) + xpos;
			pos2 = pixel_per_lum_line * ypos + xpos + 1;
			pos3 = pixel_per_lum_line * (ypos + 1) + xpos + 1;
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					dst[dst_pos++] = (src[0][pos++] + src[0][pos1++] +
							  src[0][pos2++] + src[0][pos3++]) >> 2;
				}
				pos += lum_y_incr; pos1 += lum_y_incr; pos2 += lum_y_incr; pos3 += lum_y_incr;
			}
		}


		// CHROMINANCE:

		ypos = (mb_row << 3) + down_x_col;
		xpos = (mb_column << 3) + right_x_col;


		if (!right_half_x_col && !down_half_x_col) {
			pos = pixel_per_col_line * ypos + xpos;
			for (int j = 0; j < 8; j++) {
				System.arraycopy(src[1], pos, dst, dst_pos, 8);
				dst_pos += 8;
				System.arraycopy(src[2], pos, dst, dst_pos, 8);
				dst_pos += 8;
				pos += pixel_per_col_line;
			}
		}
		else if (!right_half_x_col && !down_half_x_col) {
			pos = pixel_per_col_line * ypos + xpos;
			pos1 = pixel_per_col_line * (ypos + 1) + xpos;
			for (int j = 0; j < 8; j++) {
				pos_c = pos; pos1_c = pos1;
				for (int i = 0; i < 8; i++) {
					dst[dst_pos++] = (src[1][pos++] + src[1][pos1++]) >> 1;
				}
				for (int i = 0; i < 8; i++) {
					dst[dst_pos++] = (src[2][pos_c++] + src[2][pos1_c++]) >> 1;
				}
				pos += col_y_incr; pos1 += col_y_incr;
			}
		}
		else if (right_half_x_col  && !down_half_x_col) {
			pos_c = pos = pixel_per_col_line * ypos + xpos;
			pos1_c = pos1 = pixel_per_col_line * ypos + xpos + 1;
			for (int j = 0; j < 8; j++) {
				pos_c = pos; pos1_c = pos1;
				for (int i = 0; i < 8; i++) {
					dst[dst_pos++] = (src[1][pos++] + src[1][pos1++]) >> 1;
				}
				for (int i = 0; i < 8; i++) {
					dst[dst_pos++] = (src[2][pos_c++] + src[2][pos1_c++]) >> 1;
				}
				pos += col_y_incr; pos1 += col_y_incr;
			}
		}
		else {
			pos_c = pos = pixel_per_col_line * ypos + xpos;
			pos1_c = pos1 = pixel_per_col_line * (ypos + 1) + xpos;
			pos2_c = pos2 = pixel_per_col_line * ypos + xpos + 1;
			pos3_c = pos3 = pixel_per_col_line * (ypos + 1) + xpos + 1;
			for (int j = 0; j < 8; j++) {
				pos_c = pos; pos1_c = pos1; pos2_c = pos2; pos3_c = pos3;
				for (int i = 0; i < 8; i++) {
					dst[dst_pos++] = (src[1][pos++] + src[1][pos1++] + src[1][pos2++] + src[1][pos3++]) >> 2;
				}
				for (int i = 0; i < 8; i++) {
					dst[dst_pos++] = (src[2][pos_c++] + src[2][pos1_c++] + src[2][pos2_c++] + src[2][pos3_c++]) >> 2;
				}
				pos += col_y_incr; pos1 += col_y_incr; pos2 += col_y_incr; pos3 += col_y_incr;
			}
		}
	}

	/* The method "copy_area" grabs the area determined by the motion 	*/
	/* vector from "src" and puts it to the detination storage "dst". If	*/
	/* the motion vectors define half steps the method follows the 		*/
	/* instructions given in ISO 11172-2.					*/
	 

	public void copy_area(int mb_row, int mb_column, int src[][], int dst[][]) {
		int ypos = (mb_row << 4) + down_x;
		int xpos = (mb_column << 4) + right_x;
		int pos, pos1, pos2, pos3, dst_pos, dst_pos_c;
		int pos_c, pos1_c, pos2_c, pos3_c;

		// LUMINANCE:

		dst_pos = pixel_per_lum_line * (mb_row << 4) + (mb_column << 4);

		if (!right_half_x && !down_half_x) {
			pos = pixel_per_lum_line * ypos + xpos;
			for (int j = 0; j < 16; j++) {
				System.arraycopy(src[0], pos, dst[0], dst_pos, 16);
				pos += pixel_per_lum_line;
				dst_pos += pixel_per_lum_line;
			}
		}
		else if (!right_half_x && down_half_x)  {
			pos = pixel_per_lum_line * ypos + xpos;
			pos1 = pixel_per_lum_line * (ypos + 1) + xpos;
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					dst[0][dst_pos++] = (src[0][pos++] + src[0][pos1++]) >> 1;
				}
				pos += lum_y_incr; pos1 += lum_y_incr; dst_pos += lum_y_incr;
			}
		}
		else if (right_half_x && !down_half_x) {
			pos = pixel_per_lum_line * ypos + xpos;
			pos1 = pixel_per_lum_line * ypos + xpos + 1;
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					dst[0][dst_pos++] = (src[0][pos++] + src[0][pos1++]) >> 1;
				}
				pos += lum_y_incr; pos1 += lum_y_incr; dst_pos += lum_y_incr;
			}
		}
		else {
			pos = pixel_per_lum_line * ypos + xpos;
			pos1 = pixel_per_lum_line * (ypos + 1) + xpos;
			pos2 = pixel_per_lum_line * ypos + xpos + 1;
			pos3 = pixel_per_lum_line * (ypos + 1) + xpos + 1;
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					dst[0][dst_pos++] = (src[0][pos++] + src[0][pos1++] +
							  src[0][pos2++] + src[0][pos3++]) >> 2;
				}
				pos += lum_y_incr; pos1 += lum_y_incr; pos2 += lum_y_incr; pos3 += lum_y_incr;
				dst_pos += lum_y_incr;
			}
		}


		// CHROMINANCE:

		ypos = (mb_row << 3) + down_x_col;
		xpos = (mb_column << 3) + right_x_col;

		dst_pos = dst_pos_c = pixel_per_col_line * (mb_row << 3) + (mb_column << 3);
		pos = pixel_per_col_line * ypos + xpos;

		if (!right_half_x_col && !down_half_x_col) {
			pos = pixel_per_col_line * ypos + xpos;
			for (int j = 0; j < 8; j++) {
				System.arraycopy(src[1], pos, dst[1], dst_pos, 8);
				System.arraycopy(src[2], pos, dst[2], dst_pos, 8);
				pos += pixel_per_col_line;
				dst_pos += pixel_per_col_line;
			}
		}
		else if (!right_half_x_col && !down_half_x_col) {
			pos = pixel_per_col_line * ypos + xpos;
			pos1 = pixel_per_col_line * (ypos + 1) + xpos;
			for (int j = 0; j < 8; j++) {
				pos_c = pos; pos1_c = pos1;
				for (int i = 0; i < 8; i++) {
					dst[1][dst_pos++] = (src[1][pos++] + src[1][pos1++]) >> 1;
				}
				for (int i = 0; i < 8; i++) {
					dst[2][dst_pos_c++] = (src[2][pos_c++] + src[2][pos1_c++]) >> 1;
				}
				pos += col_y_incr; pos1 += col_y_incr;
				dst_pos += col_y_incr; dst_pos_c += col_y_incr;
			}
		}
		else if (right_half_x_col  && !down_half_x_col) {
			pos_c = pos = pixel_per_col_line * ypos + xpos;
			pos1_c = pos1 = pixel_per_col_line * ypos + xpos + 1;
			for (int j = 0; j < 8; j++) {
				pos_c = pos; pos1_c = pos1;
				for (int i = 0; i < 8; i++) {
					dst[1][dst_pos++] = (src[1][pos++] + src[1][pos1++]) >> 1;
				}
				for (int i = 0; i < 8; i++) {
					dst[2][dst_pos_c++] = (src[2][pos_c++] + src[2][pos1_c++]) >> 1;
				}
				pos += col_y_incr; pos1 += col_y_incr;
				dst_pos += col_y_incr; dst_pos_c += col_y_incr;
				
				
			}
		}
		else {
			pos_c = pos = pixel_per_col_line * ypos + xpos;
			pos1_c = pos1 = pixel_per_col_line * (ypos + 1) + xpos;
			pos2_c = pos2 = pixel_per_col_line * ypos + xpos + 1;
			pos3_c = pos3 = pixel_per_col_line * (ypos + 1) + xpos + 1;
			for (int j = 0; j < 8; j++) {
				pos_c = pos; pos1_c = pos1; pos2_c = pos2; pos3_c = pos3;
				for (int i = 0; i < 8; i++) {
					dst[1][dst_pos++] = (src[1][pos++] + src[1][pos1++] + src[1][pos2++] + src[1][pos3++]) >> 2;
				}
				for (int i = 0; i < 8; i++) {
					dst[2][dst_pos_c++] = (src[2][pos_c++] + src[2][pos1_c++] + src[2][pos2_c++] + src[2][pos3_c++]) >> 2;
				}
				pos += col_y_incr; pos1 += col_y_incr; pos2 += col_y_incr; pos3 += col_y_incr;
				dst_pos += col_y_incr; dst_pos_c += col_y_incr;
			}
		}
	}
	/* The method "copy_unchanged" grabs the "src" and puts it to the same  */
	/* position in "dst".							*/
	 

	public void copy_unchanged(int mb_row, int mb_column, int src[][], int dst[][]) {
		// LUMINANCE:

		int pos = pixel_per_lum_line * (mb_row << 4) + (mb_column << 4);

		for (int j = 0; j < 16; j++) {
			System.arraycopy(src[0], pos, dst[0], pos, 16);
			pos += pixel_per_lum_line;
		}
		// CHROMINANCE:

		pos = pixel_per_col_line * (mb_row << 3) + (mb_column << 3);

		for (int j = 0; j < 8; j++) {
			System.arraycopy(src[1], pos, dst[1], pos, 8);
			System.arraycopy(src[2], pos, dst[2], pos, 8);
			pos += pixel_per_col_line;
		}
	}

	/* The method "put_area" gets the pixels from temporary storage "src"	 */
	/* and adds them to the macroblock addressed by "mb_row" and "mb_column" */
	
	public void put_area(int mb_row, int mb_column, int src[], int dst[][]) {
		int pos = pixel_per_lum_line * (mb_row << 4) + (mb_column << 4);
		int src_pos = 0;
		for (int j = 0; j < 16; j++) {
			System.arraycopy(src, src_pos, dst[0], pos, 16);
			src_pos += 16; pos += pixel_per_lum_line;
		}

		pos = pixel_per_col_line * (mb_row << 3) + (mb_column << 3);

		for (int j = 0; j < 8; j++) {
			System.arraycopy(src, src_pos, dst[1], pos,  8);
			src_pos += 8;
			System.arraycopy(src, src_pos, dst[2], pos,  8);
			src_pos += 8; pos += pixel_per_col_line;
		}
	}


	/* The method "put_area(2)" is called if both, a forward and a backward	motion	*/
	/* vector is supplied. In this case the method computes the average color of the*/
	/* 2 given areas "src1" and "src2" and adds the result(s) to the macro block	*/
	/* addressed by "mb_row" and "mb_column".  					*/

	public void put_area(int mb_row, int mb_column, int src1[], int src2[], int dst[][]) {
		int pos = pixel_per_lum_line * (mb_row << 4) + (mb_column << 4);
		int pos_c;
		int src_pos = 0;

		for (int j = 0; j < 16; j++) {
			for (int i = 0; i < 16; i++) {
					dst[0][pos++] = (src1[src_pos] + src2[src_pos]) >> 1;
					src_pos++;
			}
			pos += lum_y_incr;
		}
		pos = pixel_per_col_line * (mb_row << 3) + (mb_column << 3);

		for (int j = 0; j < 8; j++) {
			pos_c = pos;
			for (int i = 0; i < 8; i++) {
					dst[1][pos++] = (src1[src_pos] + src2[src_pos]) >> 1;
					src_pos++;
			}
			for (int i = 0; i < 8; i++) {
					dst[2][pos_c++] = (src1[src_pos] + src2[src_pos]) >> 1;
					src_pos++;
			}
			pos += col_y_incr;
		}
	}
}
