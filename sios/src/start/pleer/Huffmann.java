package start.pleer; /****************************************************************************************/
/*											*/
/*				"Hufmann.java"						*/
/*											*/
/* This file contains VLC (Hufmann) decoder. Its data base are some arrays which 	*/
/* logically constitute a binary tree.							*/
/*											*/
/* (see also: http://rnvs.informatik.tu-chemnitz.de/~ja/MPEG/HTML/imp_asp.html		*/
/*											*/
/* The first element is the node in zero case the second in 1 case. These 2 values are	*/
/* followed by one or more result values. 						*/
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

public class Huffmann {
	private io_tool mpeg_stream = null; // To avoid passing of the start.pleer.io_tool
					    // on every decoder call the start.pleer.io_tool
	Huffmann(io_tool stream) {	    // is given to the constructor.
		mpeg_stream = stream;
	}

	// THE data base (see also: ISO 11172-2)
	// The tables must be public because they are referenced in "start.pleer.MPEG_scan".

	public final int macro_block_inc[][] = {
		{45, 1, 0}, {-1, -1, 1}, {-1, -1, 2}, {-1, -1, 3}, {-1, -1, 4}, {-1, -1, 5}, 
		{-1, -1, 6}, {-1, -1, 7}, {-1, -1, 8}, {-1, -1, 9}, {-1, -1, 10}, {-1, -1, 11}, 
		{-1, -1, 12}, {-1, -1, 13}, {-1, -1, 14}, {-1, -1, 15}, {-1, -1, 16}, {-1, -1, 17}, 
		{-1, -1, 18}, {-1, -1, 19}, {-1, -1, 20}, {-1, -1, 21}, {-1, -1, 22}, {-1, -1, 23}, 
		{-1, -1, 24}, {-1, -1, 25}, {-1, -1, 26}, {-1, -1, 27}, {-1, -1, 28}, {-1, -1, 29}, 
		{-1, -1, 30}, {-1, -1, 31}, {-1, -1, 32}, {-1, -1, 33}, {-1, -1, -1}, {-1, -1, -2}, 
		{23, 22, 0}, {47, 36, 0}, {37, 75, 0}, {38, 73, 0}, {39, 71, 0}, {52, 40, 0}, 
		{41, 67, 0}, {42, 65, 0}, {43, 64, 0}, {44, 63, 0}, {45, -1, 0}, {25, 24, 0}, 
		{27, 26, 0}, {53, 48, 0}, {55, 49, 0}, {-1, 50, 0}, {60, 51, 0}, {29, 28, 0}, 
		{31, 30, 0}, {56, 54, 0}, {33, 32, 0}, {-1, 34, 0}, {-1, 57, 0}, {62, 58, 0}, 
		{-1, 59, 0}, {35, -1, 0}, {61, -1, 0}, {3, 2, 0}, {5, 4, 0}, {7, 6, 0}, 
		{9, 8, 0}, {69, 66, 0}, {11, 10, 0}, {70, 68, 0}, {13, 12, 0}, {15, 14, 0}, 
		{17, 16, 0}, {74, 72, 0}, {19, 18, 0}, {21, 20, 0}  
	};

	public final int p_type_mb_type[][] = {
		{12, 1, 0, 0, 0, 0}, {-1, -1, 0, 1, 1, 0}, {-1, -1, 0, 0, 1, 0}, {-1, -1, 0, 1, 0, 0}, 
		{-1, -1, 0, 0, 0, 1}, {-1, -1, 1, 1, 1, 0}, {-1, -1, 1, 0, 1, 0}, {-1, -1, 1, 0, 0, 1}, 
		{-1, 7, 0, 0, 0, 0}, {8, 6, 0, 0, 0, 0}, {9, 14, 0, 0, 0, 0}, {10, 3, 0, 0, 0, 0}, 
		{11, 2, 0, 0, 0, 0}, {12, -1, 0, 0, 0, 0}, {5, 4, 0, 0, 0, 0}  
	};

	public final int b_type_mb_type[][] = {
		{16, 19, 0, 0, 0, 0, 0}, {-1, -1, 0, 1, 1, 0, 0}, {-1, -1, 0, 1, 1, 1, 0}, 
		{-1, -1, 0, 0, 1, 0, 0}, {-1, -1, 0, 0, 1, 1, 0}, {-1, -1, 0, 1, 0, 0, 0}, 
		{-1, -1, 0, 1, 0, 1, 0}, {-1, -1, 0, 0, 0, 0, 1}, {-1, -1, 1, 1, 1, 1, 0}, 
		{-1, -1, 1, 1, 0, 1, 0}, {-1, -1, 1, 0, 1, 1, 0}, {-1, -1, 1, 0, 0, 0, 1}, 
		{10, 9, 0, 0, 0, 0, 0}, {18, 12, 0, 0, 0, 0, 0}, {13, 22, 0, 0, 0, 0, 0}, 
		{14, 21, 0, 0, 0, 0, 0}, {15, 20, 0, 0, 0, 0, 0}, {16, 19, 0, 0, 0, 0, 0}, 
		{-1, 11, 0, 0, 0, 0, 0}, {1, 2, 0, 0, 0, 0, 0}, {3, 4, 0, 0, 0, 0, 0}, 
		{5, 6, 0, 0, 0, 0, 0}, {8, 7, 0, 0, 0, 0, 0}  
	};
	public final int motion_code[][] = {
		{43, 17, 0}, {-1, -1, -16}, {-1, -1, -15}, {-1, -1, -14}, {-1, -1, -13}, {-1, -1, -12}, 
		{-1, -1, -11}, {-1, -1, -10}, {-1, -1, -9}, {-1, -1, -8}, {-1, -1, -7}, {-1, -1, -6}, 
		{-1, -1, -5}, {-1, -1, -4}, {-1, -1, -3}, {-1, -1, -2}, {-1, -1, -1}, {-1, -1, 0}, 
		{-1, -1, 1}, {-1, -1, 2}, {-1, -1, 3}, {-1, -1, 4}, {-1, -1, 5}, {-1, -1, 6}, 
		{-1, -1, 7}, {-1, -1, 8}, {-1, -1, 9}, {-1, -1, 10}, {-1, -1, 11}, {-1, -1, 12}, 
		{-1, -1, 13}, {-1, -1, 14}, {-1, -1, 15}, {-1, -1, 16}, {33, 1, 0}, {34, 45, 0}, 
		{35, 47, 0}, {-1, 36, 0}, {-1, 37, 0}, {38, 53, 0}, {39, 62, 0}, {40, 65, 0}, 
		{41, 66, 0}, {42, 67, 0}, {43, -1, 0}, {32, 2, 0}, {31, 3, 0}, {46, 48, 0}, 
		{30, 4, 0}, {29, 5, 0}, {49, 54, 0}, {50, 55, 0}, {51, 57, 0}, {52, 59, 0}, 
		{28, 6, 0}, {27, 7, 0}, {26, 8, 0}, {56, 58, 0}, {25, 9, 0}, {24, 10, 0}, 
		{23, 11, 0}, {60, 63, 0}, {61, 64, 0}, {22, 12, 0}, {21, 13, 0}, {20, 14, 0}, 
		{19, 15, 0}, {18, 16, 0}  
	};

	public final int block_pattern[][] = {
		{71, 77, 0}, {-1, -1, 60}, {-1, -1, 4}, {-1, -1, 8}, {-1, -1, 16}, {-1, -1, 32}, 
		{-1, -1, 12}, {-1, -1, 48}, {-1, -1, 20}, {-1, -1, 40}, {-1, -1, 28}, {-1, -1, 44}, 
		{-1, -1, 52}, {-1, -1, 56}, {-1, -1, 1}, {-1, -1, 61}, {-1, -1, 2}, {-1, -1, 62}, 
		{-1, -1, 24}, {-1, -1, 36}, {-1, -1, 3}, {-1, -1, 63}, {-1, -1, 5}, {-1, -1, 9}, 
		{-1, -1, 17}, {-1, -1, 33}, {-1, -1, 6}, {-1, -1, 10}, {-1, -1, 18}, {-1, -1, 34}, 
		{-1, -1, 7}, {-1, -1, 11}, {-1, -1, 19}, {-1, -1, 35}, {-1, -1, 13}, {-1, -1, 49}, 
		{-1, -1, 21}, {-1, -1, 41}, {-1, -1, 14}, {-1, -1, 50}, {-1, -1, 22}, {-1, -1, 42}, 
		{-1, -1, 15}, {-1, -1, 51}, {-1, -1, 23}, {-1, -1, 43}, {-1, -1, 25}, {-1, -1, 37}, 
		{-1, -1, 26}, {-1, -1, 38}, {-1, -1, 29}, {-1, -1, 45}, {-1, -1, 53}, {-1, -1, 57}, 
		{-1, -1, 30}, {-1, -1, 46}, {-1, -1, 54}, {-1, -1, 58}, {-1, -1, 31}, {-1, -1, 47}, 
		{-1, -1, 55}, {-1, -1, 59}, {-1, -1, 27}, {-1, -1, 39}, {59, 58, 0}, {73, 64, 0}, 
		{75, 65, 0}, {66, 125, 0}, {67, 119, 0}, {68, 105, 0}, {69, 93, 0}, {70, 86, 0}, 
		{71, 77, 0}, {61, 60, 0}, {63, 62, 0}, {-1, 74, 0}, {78, 1, 0}, {80, 76, 0}, 
		{3, 2, 0}, {5, 4, 0}, {82, 79, 0}, {7, 6, 0}, {83, 81, 0}, {9, 8, 0}, 
		{11, 10, 0}, {87, 84, 0}, {89, 85, 0}, {13, 12, 0}, {15, 14, 0}, {90, 88, 0}, 
		{17, 16, 0}, {19, 18, 0}, {94, 91, 0}, {97, 92, 0}, {21, 20, 0}, {23, 22, 0}, 
		{98, 95, 0}, {100, 96, 0}, {25, 24, 0}, {27, 26, 0}, {101, 99, 0}, {29, 28, 0}, 
		{31, 30, 0}, {106, 102, 0}, {108, 103, 0}, {112, 104, 0}, {33, 32, 0}, {35, 34, 0}, 
		{109, 107, 0}, {37, 36, 0}, {39, 38, 0}, {113, 110, 0}, {115, 111, 0}, {41, 40, 0}, 
		{43, 42, 0}, {116, 114, 0}, {45, 44, 0}, {47, 46, 0}, {120, 117, 0}, {122, 118, 0}, 
		{49, 48, 0}, {51, 50, 0}, {123, 121, 0}, {53, 52, 0}, {55, 54, 0}, {126, 124, 0}, 
		{57, 56, 0}  
	};

	public final int dct_size_luminance[][] = {
		{18, 15, 0}, {-1, -1, 0}, {-1, -1, 1}, {-1, -1, 2}, {-1, -1, 3}, {-1, -1, 4}, 
		{-1, -1, 5}, {-1, -1, 6}, {-1, -1, 7}, {-1, -1, 8}, {9, -1, 0}, {8, 10, 0}, 
		{7, 11, 0}, {6, 12, 0}, {5, 13, 0}, {17, 14, 0}, {18, 15, 0}, {1, 4, 0}, 
		{2, 3, 0}  
	};

	public final int dct_size_crominance[][] = {
		{18, 16, 0}, {-1, -1, 0}, {-1, -1, 1}, {-1, -1, 2}, {-1, -1, 3}, {-1, -1, 4}, 
		{-1, -1, 5}, {-1, -1, 6}, {-1, -1, 7}, {-1, -1, 8}, {9, -1, 0}, {8, 10, 0}, 
		{7, 11, 0}, {6, 12, 0}, {5, 13, 0}, {4, 14, 0}, {3, 15, 0}, {18, 16, 0}, 
		{1, 2, 0}  
	};

	// the following 2 constants must be public because they are referenced in "start.pleer.MPEG_scan"

	public final int DCT_ESCAPE = -2, EOB = -5; /* end of block */

	public final int dct_coeff[][] = {
		{128, 141, 0, 0}, {-1, -1, EOB, EOB}, {-1, -1, 0, 1}, {-1, -1, 1, 1}, {-1, -1, 0, 2}, {-1, -1, 2, 1}, 
		{-1, -1, 0, 3}, {-1, -1, 3, 1}, {-1, -1, 4, 1}, {-1, -1, 1, 2}, {-1, -1, 5, 1}, {-1, -1, 6, 1}, 
		{-1, -1, 7, 1}, {-1, -1, 0, 4}, {-1, -1, 2, 2}, {-1, -1, 8, 1}, {-1, -1, 9, 1}, {-1, -1, DCT_ESCAPE, DCT_ESCAPE}, 
		{-1, -1, 0, 5}, {-1, -1, 0, 6}, {-1, -1, 1, 3}, {-1, -1, 3, 2}, {-1, -1, 10, 1}, {-1, -1, 11, 1}, 
		{-1, -1, 12, 1}, {-1, -1, 13, 1}, {-1, -1, 0, 7}, {-1, -1, 1, 4}, {-1, -1, 2, 3}, {-1, -1, 4, 2}, 
		{-1, -1, 5, 2}, {-1, -1, 14, 1}, {-1, -1, 15, 1}, {-1, -1, 16, 1}, {-1, -1, 0, 8}, {-1, -1, 0, 9}, 
		{-1, -1, 0, 10}, {-1, -1, 0, 11}, {-1, -1, 1, 5}, {-1, -1, 2, 4}, {-1, -1, 3, 3}, {-1, -1, 4, 3}, 
		{-1, -1, 6, 2}, {-1, -1, 7, 2}, {-1, -1, 8, 2}, {-1, -1, 17, 1}, {-1, -1, 18, 1}, {-1, -1, 19, 1}, 
		{-1, -1, 20, 1}, {-1, -1, 21, 1}, {-1, -1, 0, 12}, {-1, -1, 0, 13}, {-1, -1, 0, 14}, {-1, -1, 0, 15}, 
		{-1, -1, 1, 6}, {-1, -1, 1, 7}, {-1, -1, 2, 5}, {-1, -1, 3, 4}, {-1, -1, 5, 3}, {-1, -1, 9, 2}, 
		{-1, -1, 10, 2}, {-1, -1, 22, 1}, {-1, -1, 23, 1}, {-1, -1, 24, 1}, {-1, -1, 25, 1}, {-1, -1, 26, 1}, 
		{-1, -1, 0, 16}, {-1, -1, 0, 17}, {-1, -1, 0, 18}, {-1, -1, 0, 19}, {-1, -1, 0, 20}, {-1, -1, 0, 21}, 
		{-1, -1, 0, 22}, {-1, -1, 0, 23}, {-1, -1, 0, 24}, {-1, -1, 0, 25}, {-1, -1, 0, 26}, {-1, -1, 0, 27}, 
		{-1, -1, 0, 28}, {-1, -1, 0, 29}, {-1, -1, 0, 30}, {-1, -1, 0, 31}, {-1, -1, 0, 32}, {-1, -1, 0, 33}, 
		{-1, -1, 0, 34}, {-1, -1, 0, 35}, {-1, -1, 0, 36}, {-1, -1, 0, 37}, {-1, -1, 0, 38}, {-1, -1, 0, 39}, 
		{-1, -1, 0, 40}, {-1, -1, 1, 8}, {-1, -1, 1, 9}, {-1, -1, 1, 10}, {-1, -1, 1, 11}, {-1, -1, 1, 12}, 
		{-1, -1, 1, 13}, {-1, -1, 1, 14}, {-1, -1, 1, 15}, {-1, -1, 1, 16}, {-1, -1, 1, 17}, {-1, -1, 1, 18}, 
		{-1, -1, 6, 3}, {-1, -1, 11, 2}, {-1, -1, 12, 2}, {-1, -1, 13, 2}, {-1, -1, 14, 2}, {-1, -1, 15, 2}, 
		{-1, -1, 16, 2}, {-1, -1, 27, 1}, {-1, -1, 28, 1}, {-1, -1, 29, 1}, {-1, -1, 30, 1}, {-1, -1, 31, 1}, 
		{99, 98, 0, 0}, {130, 114, 0, 0}, {115, 132, 0, 0}, {116, 135, 0, 0}, {-1, 117, 0, 0}, {118, 215, 0, 0}, 
		{119, 200, 0, 0}, {120, 185, 0, 0}, {121, 170, 0, 0}, {122, 162, 0, 0}, {123, 17, 0, 0}, {124, 151, 0, 0}, 
		{125, 148, 0, 0}, {126, 145, 0, 0}, {127, 142, 0, 0}, {128, 141, 0, 0}, {101, 100, 0, 0}, {102, 108, 0, 0}, 
		{131, 137, 0, 0}, {103, 113, 0, 0}, {136, 133, 0, 0}, {134, 139, 0, 0}, {105, 104, 0, 0}, {107, 106, 0, 0}, 
		{110, 109, 0, 0}, {140, 138, 0, 0}, {112, 111, 0, 0}, {1, 2, 0, 0}, {143, 3, 0, 0}, {4, 5, 0, 0}, 
		{155, 6, 0, 0}, {144, 146, 0, 0}, {8, 7, 0, 0}, {9, 10, 0, 0}, {149, 147, 0, 0}, {12, 11, 0, 0}, 
		{13, 15, 0, 0}, {152, 150, 0, 0}, {14, 16, 0, 0}, {18, 22, 0, 0}, {158, 153, 0, 0}, {157, 154, 0, 0}, 
		{25, 19, 0, 0}, {156, 159, 0, 0}, {21, 20, 0, 0}, {24, 23, 0, 0}, {26, 28, 0, 0}, {166, 160, 0, 0}, 
		{161, 164, 0, 0}, {27, 32, 0, 0}, {163, 165, 0, 0}, {31, 29, 0, 0}, {33, 30, 0, 0}, {40, 34, 0, 0}, 
		{167, 180, 0, 0}, {172, 168, 0, 0}, {175, 169, 0, 0}, {35, 47, 0, 0}, {171, 177, 0, 0}, {41, 36, 0, 0}, 
		{176, 173, 0, 0}, {174, 179, 0, 0}, {37, 44, 0, 0}, {46, 38, 0, 0}, {39, 43, 0, 0}, {178, 181, 0, 0}, 
		{42, 45, 0, 0}, {49, 48, 0, 0}, {50, 65, 0, 0}, {186, 182, 0, 0}, {183, 195, 0, 0}, {189, 184, 0, 0}, 
		{52, 51, 0, 0}, {54, 53, 0, 0}, {190, 187, 0, 0}, {192, 188, 0, 0}, {56, 55, 0, 0}, {58, 57, 0, 0}, 
		{193, 191, 0, 0}, {60, 59, 0, 0}, {62, 61, 0, 0}, {196, 194, 0, 0}, {64, 63, 0, 0}, {67, 66, 0, 0}, 
		{201, 197, 0, 0}, {203, 198, 0, 0}, {207, 199, 0, 0}, {69, 68, 0, 0}, {71, 70, 0, 0}, {204, 202, 0, 0}, 
		{73, 72, 0, 0}, {75, 74, 0, 0}, {208, 205, 0, 0}, {210, 206, 0, 0}, {77, 76, 0, 0}, {79, 78, 0, 0}, 
		{211, 209, 0, 0}, {81, 80, 0, 0}, {82, 97, 0, 0}, {212, 226, 0, 0}, {213, 224, 0, 0}, {218, 214, 0, 0}, 
		{84, 83, 0, 0}, {219, 216, 0, 0}, {221, 217, 0, 0}, {86, 85, 0, 0}, {88, 87, 0, 0}, {222, 220, 0, 0}, 
		{90, 89, 0, 0}, {92, 91, 0, 0}, {225, 223, 0, 0}, {94, 93, 0, 0}, {96, 95, 0, 0}  
	};

	/* The method "decode" is the VLC (Hufmann) decoder. It traverses the  */
	/* "tree" given as parameter till a NIL(-1) index occurs. It returns   */
	/* the decoded value(s). The return type is an array because some VLCs */
	/* refer to more than 1 result.					       */


	public int[] decode(int max_length, int tab[][]) {
		int idx1 = 0, idx = 0;
		max_length++;
		int mask = (1 << max_length);
		int bits = mpeg_stream.get_bits(max_length); // make sure that enough bits are 
							     // available

		while (idx != -1) {	// NIL ????
			mask >>>= 1;    // next bit
			idx1 = idx;     // notice
			idx = ((bits & mask) != 0) ? tab[idx][1] : tab[idx][0]; // get next index
			max_length--;	// count the length
		}
		mpeg_stream.unget_bits(max_length + 1); // unget unused bits
		return(tab[idx1]);
	}
}
