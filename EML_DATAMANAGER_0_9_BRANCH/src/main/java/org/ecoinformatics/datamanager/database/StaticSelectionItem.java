package org.ecoinformatics.datamanager.database;

public class StaticSelectionItem extends SelectionItem {

	private String name;
	private String value;
	
	public StaticSelectionItem(String name, String value) {
		super(null, null);
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Gets one selection item string (real name in DB) in sql query string.
     * 
	 * @return string contains one selection item
	 * @throws UnWellFormedQueryException
	 */
	public String toSQLString() throws UnWellFormedQueryException
	{
		String selectionItem = "";
       
		if (name != null) {
			selectionItem =
				ConditionInterface.SINGLEQUOTE
				+ value
				+ ConditionInterface.SINGLEQUOTE
				+ ConditionInterface.SPACE
				+ ConditionInterface.AS
				+ ConditionInterface.SPACE
				+ name;
		} 
		else {
			throw new UnWellFormedQueryException(
               UnWellFormedQueryException.SELECTION_ATTRIBUTE_NAME_IS_NULL);
		}
		
		return selectionItem;
	}
	
}
