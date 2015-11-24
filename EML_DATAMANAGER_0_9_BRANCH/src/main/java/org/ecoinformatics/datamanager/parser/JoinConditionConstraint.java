/**
 *    '$RCSfile: JoinConditionConstraint.java,v $'
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

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;


/**
 * This class represents a join condition (join two tables without
 * primary/foreign key) in table level.
 * 
 * @author tao
 */
public class JoinConditionConstraint implements Constraint
{
  /*
   * Instance fields
   */
  private int type = Constraint.JOINCONDITIONCONSTRAINT;
  private String name = null;
  private String[] keys = null;
  private String entityReference = null;
  private String[] referencedKeys  = null;

  /*private static Log log;
  
  static {
	 log = LogFactory.getLog( "org.kepler.objectmanager.data.db.JoinConditionConstraint" );
  }*/

  /*
   * Constructors
   */
  
  /**
   * Default constructor
   */
  public JoinConditionConstraint()
  {

  }
  
  
  /*
   * Instance methods
   */

  /**
   * Method to get type.
   * 
   * @return an int representing the constraint type (see Constraint interface)
   */
  public int getType()
  {
    return type;
  }
  

  /**
   * Method to get constraint name.
   * 
   * @return the value of the name field, a String
   */
  public String getName()
  {
    return name;
  }

  
  /**
   * Method to get keys.
   * 
   * @return the value of the keys field, a String[]
   */
  public String[] getKeys()
  {
    return keys;
  }

  
  /**
   * Method to get referenced entity (parent table).
   * 
   * @return String  the name of the entity referenced by this constraint
   */
  public String getEntityReference()
  {
    return entityReference;
  }
  

  /**
   * Method to get referenced keys in parent table.
   * 
   * @return   the keys referenced by this constraint, a String[] 
   */
  public String[] getReferencedKeys()
  {
    return referencedKeys;
  }

  
  /**
   * Method to set constraint name.
   * 
   * @param constraintName, the name value to set for this constraint, a String
   */
  public void setName(String constraintName)
  {
    name = constraintName;
  }

  
  /**
   * Method to set keys.
   * 
   * @param constraintKeys, the array of keys to set, a String[]
   */
  public void setKeys(String[] constraintKeys)
  {
    keys = constraintKeys;
  }

  
  /**
   * Method to set referenced entity name.
   * 
   * @param referencedEntity, the entityReference value to set, a String
   */
  public void setEntityReference(String referencedEntity)
  {
    entityReference = referencedEntity;
  }

  
  /**
   * Method to set up referenced key in parent table (referencedEntity).
   * 
   * @param keyList, the referencedKeys value to set, a String[]
   */

  public void setReferencedKeys(String[] keyList)
  {
    referencedKeys = keyList;
  }


  /**
   * Method to print out a partial sql command about join condition.
   * In joinCondition, we need to specify the referenced entity's column
   * name. It is same as foreign key except to specify referenced key name in
   * parent table
   * 
   * @return  a partial SQL command about the join condition, a String
   */
  public String printString() throws UnWellFormedConstraintException
  {
    String sql = null;
    
    if (referencedKeys == null || referencedKeys.length ==0)
    {
      throw new UnWellFormedConstraintException("The refrenced key in parent "+
                       "table is empty in join condition constaint");
    }
    
    // In joincondition the first part is as same as foreign key,
    // so we can first create a foreginkey object and print out the sql.
    ForeignKey foreignKey = new ForeignKey();
    foreignKey.setName(name);
    foreignKey.setKeys(keys);
    foreignKey.setEntityReference(entityReference);
    String foreignKeySql = foreignKey.printString();

    StringBuffer buffer = new StringBuffer(foreignKeySql);

    // add referencedkey list in parent table
    buffer.append(Constraint.LEFTPARENTH);
    
    // add keys into parenthesis
    boolean firstKey = true;
    
    for (int i = 0; i< referencedKeys.length; i++)
    {
      String keyName = keys[i];
      
      // if any key is null or empty, we will throw a exception
      if (keyName == null || keyName.trim().equals(""))
      {
        throw new UnWellFormedConstraintException("refernced key name in " +
                              "parent table is empty or null in join condition");
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
    sql = buffer.toString();
    //log.debug("The foreign key part of sql is " + sql);
    
    return sql;
  }

}
