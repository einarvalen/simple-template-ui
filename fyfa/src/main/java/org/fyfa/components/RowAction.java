package org.fyfa.components;

import org.fyfa.ids.ItemId;


/** Action to be carried out when for instance a button is pressed.
 * RowActions are used in Table to provide row data aware actions.
 *
 * @author EinarValen@gmail.com */
public class RowAction {
	private final ItemId itemId;
	private final String buttonLabel, action;

	public RowAction( ItemId itemId, String buttonLabel, String action ) {
		this.itemId = itemId;
		this.buttonLabel = buttonLabel;
		this.action = action;
	}

	public ItemId getItemId() {
		return itemId;
	}

	public String getButtonLabel() {
		return buttonLabel;
	}

	public String getAction() {
		return action;
	}
}
