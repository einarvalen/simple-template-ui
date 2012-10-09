package org.fyfa;

import org.fyfa.ids.ItemId;

/** To submit form data
 *
 * @author EinarValen@gmail.com */
public class SubmitButton extends Button {

	public SubmitButton( String label, String description ) {
		super( label, description, null );
	}

	public SubmitButton( ItemId id, String label, String description ) {
		super( id, label, description, null );
	}

}
