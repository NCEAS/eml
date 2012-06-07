package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;

public class AggregateSelectionItem extends SelectionItem {
	
	public static String MIN_FUNCTION = "MIN";
	public static String MAX_FUNCTION = "MAX";
	public static String AVERAGE_FUNCTION = "AVG";
	public static String COUNT_FUNCTION = "COUNT";
	
	private String function;
	
	public AggregateSelectionItem(Entity entity, Attribute attribute, String function) {
		super(entity, attribute);
		this.function = function;
	}

	/**
	 * Gets one selection item string (real name in DB) in sql query string.
     * 
	 * @return string contains one selection item
	 * @throws UnWellFormedQueryException
	 */
	public String toSQLString() throws UnWellFormedQueryException
	{
		String selectionItem = super.toSQLString();
       
		if (function != null && !function.trim().equals(ConditionInterface.BLANK)) {
			selectionItem =
				function
				+ ConditionInterface.LEFT_PARENSIS
				+ selectionItem
				+ ConditionInterface.RIGHT_PARENSIS
				//+ ConditionInterface.SPACE
				//+ ConditionInterface.AS
				//+ ConditionInterface.SPACE
				//TODO: attribute name here?
				;
		} 
		else {
			throw new UnWellFormedQueryException(
               UnWellFormedQueryException.AGGREGATE_SELECTION_FUNCTION_IS_NULL);
		}
		
		return selectionItem;
	}
	
}
