package org.fyfa;

import java.util.Map;

import org.fyfa.ids.TemplateId;
import org.fyfa.ids.TemplateTypeId;


/** Different templates may require different rendering logic.
 *
 * @author EinarValen@gmail.com */
public interface TemplateType extends Identifyable<TemplateTypeId> {

	String render( TemplateParams param );

	String render( Context context, TemplateId templateId, Map<String, String> model );

	TemplateTypeId getId();

}
