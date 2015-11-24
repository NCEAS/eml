/**
 *    '$RCSfile: Constraint.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-10-31 21:00:40 $'
 *   '$Revision: 1.2 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2004 The Regents of the University of California.
 * All rights reserved.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, provided that the
 * above copyright notice and the following two paragraphs appear in
 * all copies of this software.
 *
 * IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 * FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN
 * IF THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 * THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 * PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY
 * OF CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.ecoinformatics.datamanager.parser;

/**
 * This intergace represents an abstract Constraint in eml2
 * 
 * @author tao
 *
 */

public interface Constraint
{
  /*
   * Class fields (constants)
   */
  
  public static final int      PRIMARYKEY              = 0;
  public static final int      UNIQUEKEY               = 1;
  public static final int      FOREIGNKEY              = 2;
  public static final int      NOTNULLCONSTRAINT       = 3;
  public static final int      JOINCONDITIONCONSTRAINT = 4;
  public static final String   CONSTRAINT              = "CONSTRAINT";
  public static final String   SPACESTRING             = " ";
  public static final String   RIGHTPARENTH            = ")";
  public static final String   LEFTPARENTH             = "(";
  public static final String   COMMA                   = ",";
  public static final String   PRIMARYKEYSTRING        = "PRIMARY KEY";
  public static final String   UNIQUEKEYSTRING         = "UNIQUE";
  public static final String   FOREIGNKEYSTRING        = "FOREIGN KEY";
  public static final String   REFERENCESTRING         = "REFERENCES";
  public static final String   NOTNULLSTRING           = " NOT NULL ";
  
  
  /*
   * Instance methods
   */
  
  // abtract method to get constraint type
  public int getType();

  // abtract method to transfer a constraint to a string
  public String printString() throws UnWellFormedConstraintException;
  
}
