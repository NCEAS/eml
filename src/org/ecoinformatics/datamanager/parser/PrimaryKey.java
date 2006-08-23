/**
 *    '$RCSfile: PrimaryKey.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-23 23:04:27 $'
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

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * This class represents a primary key constraint in eml
 * @author Jing Tao
 *
 */

public class PrimaryKey implements Constraint
{
  private static final int type = Constraint.PRIMARYKEY;
  private String   name = null;
  private String[] keys = null;

  /*private static Log log;
  
  static {
	  log = LogFactory.getLog( "org.kepler.objectmanager.data.db.PrimaryKey" );
  }*/


  /*
   * Default constructor
   */
  public PrimaryKey()
  {

  }

  /**
   * Method to get constrain type
   * @return int
   */
  public int getType()
  {
    return type;
  }

  /**
   * Method to get constrain name
   * @return String
   */
  public String getName()
  {
    return name;
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
   * Method to set constraint name
   * @param constraintName String
   */
  public void setName(String constraintName)
  {
    name = constraintName;
  }

  /**
   * Method to set keys in constraint
   * @param keyList String[]
   */
  public void setKeys(String[] keyList)
  {
    keys = keyList;
  }

  /**
   * Method to transfer a primary key into sql command (table constraint)
   * The string will look like:
   *  CONSTRAINT constrain_name Primary Key ( col1, col2, ...)
   * @return String
   */
  public String printString() throws UnWellFormedConstraintException
  {
    String sql = null;
    if (name == null || name.trim().equals(""))
    {
      throw new UnWellFormedConstraintException("No Constraint name assign " +
                                                "to Primary key");
    }
    if (keys == null || keys.length == 0)
    {
      throw new UnWellFormedConstraintException("No key is specified in " +
                                                "primary key");
    }

    StringBuffer buffer = new StringBuffer();
    buffer.append(Constraint.SPACESTRING);
    buffer.append(Constraint.CONSTRAINT);
    buffer.append(Constraint.SPACESTRING);
    buffer.append(name);
    buffer.append(Constraint.SPACESTRING);
    buffer.append(Constraint.PRIMARYKEYSTRING);
    buffer.append(Constraint.SPACESTRING);
    buffer.append(Constraint.LEFTPARENTH);
    // add keys into parenthesis
    boolean firstKey = true;
    for (int i = 0; i< keys.length; i++)
    {
      String keyName = keys[i];
      // if any key is null or empty, we will throw a exception
      if (keyName == null || keyName.trim().equals(""))
      {
        throw new UnWellFormedConstraintException("key name empty or null in " +
                                                "primary key");
      }
      // if this is not the first key, we need add a comma
      if (!firstKey)
      {
         buffer.append(Constraint.COMMA);
      }
      buffer.append(keyName);
      firstKey = false;
    }
    buffer.append(Constraint.RIGHTPARENTH);
    buffer.append(Constraint.SPACESTRING);
    sql = buffer.toString();
    //log.debug("Primary key part in sql command is " + sql);
    return sql;

  }

}
