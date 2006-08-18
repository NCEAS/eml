/**
 *    '$RCSfile: ForeignKey.java,v $'
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

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * This class represents a foreignkey in table level
 * @author Jing Tao
 *
 */

public class ForeignKey implements Constraint
{
  private int type = Constraint.FOREIGNKEY;
  private String name = null;
  private String[] keys = null;
  private String entityReference = null;

  /*private static Log log;
  
  static {
	  log = LogFactory.getLog( "org.kepler.objectmanager.data.db.ForeignKey" );
  }*/

  /**
   * Default constructor
   */
  public ForeignKey()
  {

  }

  /**
   * Method to get type
   * @return int
   */
  public int getType()
  {
    return type;
  }

  /**
   * Method to get constraint name
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
   * Method to get referenced entity
   * @return String
   */
  public String getEntityReference()
  {
    return entityReference;
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
   * Method to set keys
   * @param constraintKeys String[]
   */
  public void setKeys(String[] constraintKeys)
  {
    keys = constraintKeys;
  }

  /**
   * Method to set referenced entity name
   * @param referencedEntity String
   */
  public void setEntityReference(String referencedEntity)
  {
    entityReference = referencedEntity;
  }

  /**
   * Method to print out a part sql command about foreign key
   * In foreign key, you don't need to specify the referenced entity's column
   * name, the primary key column(s) of the referenced table is used as the
   * referenced column(s)
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
    buffer.append(Constraint.FOREIGNKEYSTRING);
    buffer.append(Constraint.SPACESTRING);

    // add foreign key list
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
                                                "foreign key");
      }
      // if this is not the first key, we need add a comma
      if (!firstKey)
      {
         buffer.append(Constraint.COMMA);
      }
      buffer.append(keyName);
      firstKey = false;
    }//for
    buffer.append(Constraint.RIGHTPARENTH);
    buffer.append(Constraint.SPACESTRING);
    buffer.append(Constraint.REFERENCESTRING);
    buffer.append(Constraint.SPACESTRING);
    buffer.append(entityReference);
    buffer.append(Constraint.SPACESTRING);
    sql = buffer.toString();
    //log.debug("The foreign key part of sql is " + sql);
    return sql;
  }

}
