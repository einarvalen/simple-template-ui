package org.fyfa.components;

import java.util.HashSet;
import java.util.Set;

import org.fyfa.Context;


/** Used to supply a Table with directions on how to behave.
*
* @author EinarValen@gmail.com */
public class TableParams<T> extends ComponentParams<T> {
	private final Set<RowAction> rowActions = new HashSet<RowAction>();

	public TableParams( String componentName, Class<T> classOfDto, Context context ) {
		super( componentName, classOfDto, context );
	}

	public void addRowAction( RowAction rowAction ) {
		rowActions.add( rowAction );
	}

	public Set<RowAction> getRowActions() {
		return rowActions;
	}

}
