package org.fyfa;

import org.fyfa.ids.ItemId;

/** Invoking user actions
*
* @author EinarValen@gmail.com */
public class MenuItem implements Identifyable<ItemId> {
	private String label, description;
	private final String action;
	private final ItemId id;

	public MenuItem( ItemId id, String label, String description, String action ) {
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
