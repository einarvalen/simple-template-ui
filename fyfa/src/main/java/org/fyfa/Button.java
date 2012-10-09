package org.fyfa;

import org.fyfa.ids.ItemId;

/** Invoking user actions
 *
 * @author EinarValen@gmail.com */
public class Button implements Identifyable<ItemId> {
	private String label, description;
	private final String action;
	private final ItemId id;

	public Button( String label, String description, String action ) {
		this( new ItemId( label ), label, description, action );
	}

	public Button( ItemId id, String label, String description, String action ) {
		this.id = id;
		this.label = label;
		this.description = description;
		this.action = action;
	}

	public ItemId getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public String getAction() {
		return action;
	}

	public void setLabel( String label ) {
		this.label = label;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

}
