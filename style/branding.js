 /*
  *   '$RCSfile: branding.js,v $'
  *     Purpose: Default style sheet for KNB project web pages 
  *              Using this stylesheet rather than placing styles directly in 
  *              the KNB web documents allows us to globally change the 
  *              formatting styles of the entire site in one easy place.
  *   Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *     Authors: Matt Jones
  *
  *    '$Author: brooke $'
  *      '$Date: 2003-11-20 20:12:43 $'
  *  '$Revision: 1.1 $'
  *
  * This program is free software; you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation; either version 2 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
  */



/*******************************************************************************
********************************************************************************
********************************************************************************
 *  NOTE: 
 *  
 *  This is just a placeholder file. If you wish to auto-include html 
 *  title and navigation bars in your EML pages, then grab the *real* 
 *  implementation of this file from the metacat source at 
 *  http://knb.ecoinformatics.org//software/download.html. 
 *  
 *  If you're not interested in that stuff, then just leave this file as is, 
 *  make sure your installed eml XSL stylesheets can find it, and everything 
 *  should work seamlessly... 
********************************************************************************
********************************************************************************
*******************************************************************************/

 
//  These settings allow you to include and display common content (eg a common 
//  header) on all your pages, in much the same way as a frameset allows you to 
//  do, but through the use of iframes and a table instead. You can include up 
//  to 4 external pages, each one within the header, footer, left or right areas
//
//  looks like this (if you're using a fixed width font to display these notes):
//    ___________________
//    |     header      |
//    |-----------------|
//    | |             | |
//    | |             | |
//    |L|   content   |R|
//    | |             | |
//    | |             | |
//    |-----------------|
//    |     footer      | 
//    -------------------
//
//  Each area may display another page on the local site, or a page on a 
//  different server, or may be set to display nothing (in which case an iframe 
//  will not be drawn, although the containing table cell will still need to be 
//  resized using the css style - see below) 

function insertTemplateOpening() {

    //do not delete this function
}

function insertTemplateClosing() {

    //do not delete this function
}


