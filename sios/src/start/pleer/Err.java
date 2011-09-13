package start.pleer; /****************************************************************************************/
/*											*/
/*				     "start.pleer.Err.java"						*/
/*											*/
/* This file contains only the class "start.pleer.Err" which is used to hold a possible error	*/
/* message. If an error occurs the property "Msg" is overwritten. So the applet method 	*/
/* "update()" can recognize whether an error occured. In this case it displays the	*/
/* error message instead of the animation.						*/
/*											*/
/*--------------------------------------------------------------------------------------*/
/*											*/
/*		Joerg Anders, TU Chemnitz, Fakultaet fuer Informatik, GERMANY		*/
/*		ja@informatik.tu-chemnitz.de						*/
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

public class Err {	// contains a possible error message;
	public static String Msg = null;
}
