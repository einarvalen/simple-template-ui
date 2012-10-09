package org.fyfa.repositories;

import java.util.Map;

import org.fyfa.Registry;
import org.fyfa.Selection;
import org.fyfa.ids.SelectionId;


/** Repository. A collection of Selection objects.
*
* @author EinarValen@gmail.com */
public class Selections extends Registry<SelectionId, Selection> {
	public Selections() {}

	public Selections( Map<SelectionId, Selection> map ) {
		super( map );
	}

}
