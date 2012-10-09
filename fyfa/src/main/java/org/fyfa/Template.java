package org.fyfa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.fyfa.ids.TemplateId;
import org.fyfa.ids.TemplateTypeId;
import org.fyfa.templatetypes.TemplateTypeDefault;


/**	A Template is typically an HTML snippet used to form parts of pages.
 *
 *  @author EinarValen@gmail.com */
public class Template implements Identifyable<TemplateId> {
	private final List<String> listVariables = new ArrayList<String>();
	private final List<String> listConstants = new ArrayList<String>();
	private final String format;
	private final TemplateId id;
	private final TemplateTypeId templateTypeId;

	public Template( TemplateId id, TemplateTypeId templateTypeId, String format ) {
		this.id = id;
		this.format = format;
		this.templateTypeId = templateTypeId;
		parse( format );
	}

	public Template( String format ) {
		this( new TemplateId( "" + format.hashCode() ), TemplateTypeDefault.ID, format );
	}

	/** Parses a template string into constants and variables. Variable notation is $VariableName$. */
	private void parse( String str ) {
		if (str == null) return;
		String strToken = null;
		StringTokenizer st = new StringTokenizer( str, "$" );
		for (int i = 0; st.hasMoreTokens(); i++) {
			strToken = st.nextToken();
			strToken = strToken.intern();
			if (i % 2 == 0) {
				listConstants.add( strToken );
			} else {
				listVariables.add( strToken );
			}
		}
	}

	/** Replaces all occurrences of model.key in Template.format by model.value */
	public String expand( Map<String, String> model ) throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();
		// Concatinates constants and variables from lists
		Object[] oVariables = listVariables.toArray();
		Object[] oConstants = listConstants.toArray();
		for (int i = 0; i < oConstants.length; i++) {
			// Retrieves named variable values from parameter properties
			sb.append( oConstants[i].toString() );
			if (i < oVariables.length) {
				String str = model.get( oVariables[i] );
				// System.out.println( "Will replace " + oVariables[i].toString() + " with " + str);
				if (str != null) sb.append( str );
			}
		}
		return sb.toString();
	}

	@Override
	public TemplateId getId() {
		return this.id;
	}

	@Override
	public String toString() {
		if (this.format == null) return "";
		String format = this.format.replace( "\n", "\\n" );
		return String.format( "id='%s', format='%s'", this.id, format );
	}

	public String getFormat() {
		return this.format;
	}

	public TemplateTypeId getType() {
		return templateTypeId;
	}
}
