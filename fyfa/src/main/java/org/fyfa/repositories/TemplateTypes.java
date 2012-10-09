package org.fyfa.repositories;

import java.util.Map;

import org.fyfa.Registry;
import org.fyfa.TemplateType;
import org.fyfa.ids.TemplateTypeId;


/** Repository. A collection of TemplateType objects.
*
* @author EinarValen@gmail.com */
public class TemplateTypes extends Registry<TemplateTypeId, TemplateType> {

	public TemplateTypes() {
		super();
	}

	public TemplateTypes( Map<TemplateTypeId, TemplateType> map ) {
		super( map );
	}
}
