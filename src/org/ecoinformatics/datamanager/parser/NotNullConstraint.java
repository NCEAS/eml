/**
 *    '$RCSfile: NotNullConstraint.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:41:10 $'
 *   '$Revision: 1.1 $'
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
 * This class represents no null constraint in column level
 * @author Jing Tao
 *
 */

public class NotNullConstraint implements Constraint
{
  private int type = Constraint.NOTNULLCONSTRAINT;
  private String[] keys = null;



  /**
   * Default constructor
   */
  public NotNullConstraint()
  {

  }

  /**
   * method to get type
   * @return int
   */
  public int getType()
  {
    return type;
  }

  /**
   * Method to get keys
   * @return String[]
   */
  public String[] getKeys()
  {
    return keys;
  }

  /**
   * Method to set keys
   * @param myKeys String[]
   */
  public void setKeys(String[] myKeys)
  {
    keys = myKeys;
  }

  /**
   * Method to print not null key words in sql cmommand
   * @throws UnWellFormedConstraintException
   * @return String
   */
  public String printString() throws UnWellFormedConstraintException
  {
    String sql = Constraint.NOTNULLSTRING;
    return sql;
  }


}
