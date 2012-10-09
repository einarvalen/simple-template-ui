package org.fyfa.components;

import java.util.List;

import org.fyfa.Button;
import org.fyfa.Context;
import org.fyfa.Marshal;


/** Used to supply a Component with directions on how to behave.
 *
 * @author EinarValen@gmail.com */
public abstract class ComponentParams<T> {
	private final static Marshal marshal = new Marshal();
	private final String componentName;
	private final Class<T> classOfDto;
	private final Context context;
	private final ButtonPanel buttonPanel;
	private String[] columnNamesInSequence;
	private String title;

	public ComponentParams( String componentName, Class<T> classOfDto, Context context ) {
		this.componentName = componentName;
		this.classOfDto = classOfDto;
		this.context = context;
		this.buttonPanel = new ButtonPanel( this.context, this.componentName + "Buttons" );
		this.columnNamesInSequence = toArray( marshal.keys( classOfDto ) );
		this.title = componentName;
	}

	public void addButton( Button button ) {
		this.buttonPanel.add( button );
	}

	private static String[] toArray( List<String> list ) {
		return list.toArray( new String[list.size()] );
	}

	/** Marshal does marshalling and unmarshaling */
	public static Marshal getMarshal() {
		return marshal;
	}

	public String getComponentName() {
		return componentName;
	}

	public Class<T> getClassOfDto() {
		return classOfDto;
	}

	public Context getContext() {
		return context;
	}

	public ButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public String[] getColumnNamesInSequence() {
		return columnNamesInSequence;
	}

	/** Sequence of field names that will be rendered */
	public void setColumnNamesInSequence( String... colmnNamesInSequence ) {
		this.columnNamesInSequence = colmnNamesInSequence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

}
