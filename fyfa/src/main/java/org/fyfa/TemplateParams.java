package org.fyfa;

import java.util.HashMap;
import java.util.Map;

/** Holding tank used by TemplateType subclasses when expanding Templates
 *
 * @author EinarValen@gmail.com */
public class TemplateParams {
	private final Context context;
	private final Item item;
	private final Map<String, String> model = new HashMap<String, String>();
	private final Map<String, String> valuesFormated;

	public TemplateParams( Context context, Item item, Map<String, String> valuesFormated ) {
		this.context = context;
		this.item = item;
		this.valuesFormated = valuesFormated;
	}

	public Map<String, String> getModel() {
		return this.model;
	}

	public Context getContext() {
		return this.context;
	}

	public Item getItem() {
		return this.item;
	}

	public Map<String, String> getValuesFormated() {
		return valuesFormated;
	}

	@Override
	public String toString() {
		return String.format( "{ item=%s, model=%s, valuesFormated=%s }", this.getItem().toString(), toString( this.getModel() ), toString( this.getValuesFormated() ) );
	}

	private String toString( Map<String, String> map ) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			String value = map.get( key );
			sb.append( String.format( "%s='%s', ", key, value ) );
		}
		return "{ " + sb.toString() + " }";
	}
}
