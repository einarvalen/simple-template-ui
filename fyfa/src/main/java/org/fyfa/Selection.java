package org.fyfa;

import java.util.Map;

import org.fyfa.ids.SelectionId;


/** Drop down option lists
 *
 *  @author EinarValen@gmail.com */
public interface Selection extends Identifyable<SelectionId> {
	Map<String, String> getSelection();

}
