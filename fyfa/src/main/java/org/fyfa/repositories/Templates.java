package org.fyfa.repositories;

import java.util.Map;

import org.fyfa.Registry;
import org.fyfa.Template;
import org.fyfa.ids.TemplateId;


/** Repository. A collection of Template objects.
*
* @author EinarValen@gmail.com */
public class Templates extends Registry<TemplateId, Template> {

	public Templates() {}

	public Templates( Map<TemplateId, Template> map ) {
		super( map );
	}

}
