package start.pleer;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;

/**
 * Created by IntelliJ IDEA.
 * User: Евгений
 * Date: 24.02.2011
 * Time: 22:48:52
 * To change this template use File | Settings | File Templates.
 */
class Element {
	public Image Picture;				// The Image
	public int Frame_idx;				// notice the frame number (display order)
	public int Frame_type;				// I, B or P
	public int Pix_Map[];				// The pixels in Java RGB model
	private final int CR_FAC = 0x166EA; /* 1.402*2^16 */
	private final int CB_FAC = 0x1C5A2; /* 1.772*2^16 */
	private final int CR_DIFF_FAC = 0xB6D2; /* 0.71414 * 2^16 */
	private final int  CB_DIFF_FAC = 0x581A; /* 0.34414 * 2^16 */

	/* The constructor is called every time the scanner has decoded a frame. It 	   */
	/* expects the frame information as YUV values and translates them into the Java   */
	/* RGB color system. The Applet (app) object is necessary to call the 		   */
	/* "createImage" routine. The dispatcher "s" is informed of the existance of a	   */
	/*  new frame. 						   			   */

	Element (Applet app, int Pixels[][], ColorModel cm, int f_idx, int f_type, int w, int h, int o_w, int o_h) {
		int red, green, blue, luminance, cr, cb, cr_g, cb_g, i, j;
		Frame_idx = f_idx; Frame_type = f_type; // notice
		Element ptr;
    	/*  because one crominance information is applied to 4 luminace values
		 *  2 "pointers" are established, which point to the 2 lines containing
		 *  the appropriate luminace values:
		 */

		int lum_idx1 = 0, lum_idx2 = w;
		int size = w * h;
     	Pix_Map = new int[size]; // memory for the translated RGB values

		/*
		 *  expand the list
		 */

		size >>>= 2;		 // the size of the crominance values

		for (i = 0; i < size; i++) { // for all crominance values ...
			cb = Pixels[2][i] - 128; // extract the
			cr = Pixels[1][i] - 128;// chrominace information

			cr_g = cr * CR_DIFF_FAC;
			cb_g = cb * CB_DIFF_FAC;

			cb *= CB_FAC;
			cr *= CR_FAC;

			for (j = 0; j < 2; j++) { // apply to 2 neighbouring points
				 luminance = Pixels[0][lum_idx1] << 16; // extract lum.
				 red = (luminance + cr);
				 blue = (luminance + cb) >> 16;
				 green = (luminance - cr_g - cb_g) >> 8;

				 red = (red > 0xff0000) ? 0xff0000 : (red < 0) ? 0 : red & 0xff0000; 	// CLAMP
				 green = (green > 0xff00)  ? 0xff00 : (green < 0) ? 0 : green & 0xff00;//CLAMP
				 blue = (blue > 255)  ? 255 : (blue < 0) ? 0 : blue;	//CLAMP

				 Pix_Map[lum_idx1] =  ( red | green  | blue);

				 lum_idx1++; // next point in first line

                luminance = Pixels[0][lum_idx2] << 16; // extract lum.
				 red = (luminance + cr);
				 blue = (luminance + cb) >> 16;
				 green = (luminance - cr_g - cb_g) >> 8;

				 red = (red > 0xff0000) ? 0xff0000 : (red < 0) ? 0 : red & 0xff0000; 	// CLAMP
				 green = (green > 0xff00)  ? 0xff00 : (green < 0) ? 0 : green & 0xff00;//CLAMP
				 blue = (blue > 255)  ? 255 : (blue < 0) ? 0 : blue;	//CLAMP

				 Pix_Map[lum_idx2] =  ( red  | green  | blue);
				 lum_idx2++; // next point in second line
				 if (lum_idx2 % w == 0) { // end of line ?
					lum_idx2 += w;
					lum_idx1 += w;
				 }
			}
		}
/*+++ JAVA offers 2 possibilities to create an image:				+++*/
/*+++										+++*/
/*+++			1. createImage(int width, int height);			+++*/
/*+++			2. createImage(ImageProducer producer);			+++*/
/*+++										+++*/
/*+++ The first method isn't suitable here because it requires to draw the	+++*/
/*+++ pixel values by means of a sequence of "draw...()" methods into the image.+++*/
/*+++ Since all the pixel values and the color model are known the images 	+++*/
/*+++ are produced according to the second method by means of objects of the	+++*/
/*+++ class "MemoryImageSource" which implements the interface "ImageProducer".	+++*/
/*+++ An image producer is an object which supplies the pixel values on demand  +++*/
/*+++ of an image consumer. The pixel values are delivered in recpect of a 	+++*/
/*+++ certain color model. The "MemoryImageSource" objects uses a Direct Color  +++*/
/*+++ model.									+++*/
		Picture = app.createImage(new MemoryImageSource(o_w, o_h, cm, Pix_Map, 0, w));
        Pix_Map=null;
    }
}
