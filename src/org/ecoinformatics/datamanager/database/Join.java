/**
 *    '$RCSfile: Join.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-12-01 22:02:06 $'
 *   '$Revision: 1.2 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2003 The Regents of the University of California.
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
package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;


/**
 * This class represents a join relation in a query. Currently we only
 * support natural join.
 * 
 * @author tao
 */
public class Join implements ConditionInterface
{
    /*
     * Instance fields
     */
  
	private Entity entity1 = null;
	private Entity entity2 = null;
	private Attribute attribute1 = null;
	private Attribute attribute2 = null;
	
    
	/**
	 * Constructor of Join
     * 
	 * @param entity1 first entity 
	 * @param attribute1 attribute in entity 1
	 * @param entity2 second entity
	 * @param attribute2 attribute in entity 2
	 */
	public Join (Entity entity1,
                 Attribute attribute1, 
                 Entity entity2, 
                 Attribute attribute2)
	{
		this.entity1 = entity1;
		this.entity2 = entity2;
		this.attribute1 = attribute1;
		this.attribute2 = attribute2;
	}
	
    
   /*
    * Instance methods
    */ 
    
   /**
	* Transfers a Condition object to sql string which 
	* has real entity and attribute name in db.
    * 
	* @return condition sql string
    * @throws UnWellFormedQueryException
    */
   public String toSQLString() throws UnWellFormedQueryException
   {
	  if (entity1 == null || entity2 == null)
	  {
		  throw new UnWellFormedQueryException(
                                UnWellFormedQueryException.JOIN_ENTITY_IS_NULL);
	  }
	  
	  if (attribute1 == null || attribute2 == null)
	  {
		  throw new UnWellFormedQueryException(
                             UnWellFormedQueryException.JOIN_ATTRIBUTE_IS_NULL);
	  }
	  
	  String entity1Name = entity1.getDBTableName();
	  String attribute1Name = attribute1.getDBFieldName();
	  String entity2Name = entity2.getDBTableName();
	  String attribute2Name = attribute2.getDBFieldName();
	  
	  if (entity1Name == null || 
          entity2Name == null ||
          entity1Name.trim().equals(BLANK)|| 
          entity2Name.trim().equals(BLANK))
	  {
		  throw new UnWellFormedQueryException(
                           UnWellFormedQueryException.JOIN_ENTITY_NAME_IS_NULL);
	  }
	  
      if (attribute2Name == null || 
          attribute1Name == null ||
          attribute1Name.trim().equals(BLANK) || 
          attribute2Name.trim().equals(BLANK))
	  {
		  throw new UnWellFormedQueryException(
                        UnWellFormedQueryException.JOIN_ATTRIBUTE_NAME_IS_NULL);
	  }
	  
	  StringBuffer sql = new StringBuffer();
	  sql.append(entity1Name);
	  sql.append(SEPERATER);
	  sql.append(attribute1Name);
	  sql.append(SPACE);
	  sql.append(EQUAL_OPERATOR);
	  sql.append(SPACE);
	  sql.append(entity2Name);
	  sql.append(SEPERATER);
	  sql.append(attribute2Name);
      
	  return sql.toString();
   }
}
