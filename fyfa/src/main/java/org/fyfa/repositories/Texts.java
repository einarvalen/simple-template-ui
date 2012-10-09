package org.fyfa.repositories;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.fyfa.Registry;
import org.fyfa.Text;
import org.fyfa.ids.TextId;


/** Repository. A collection of Text objects.
*
* @author EinarValen@gmail.com */
public class Texts extends Registry<TextId, Text> {

	public Texts() {
		super();
	}

	public Texts( Map<TextId, Text> map ) {
		super( map );
	}

	public Texts( Properties p ) {
		this();
		putAll( p );
	}

	@Override
	public Text get( TextId id ) {
		return this.getMap().get( id );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (TextId key : this.getMap().keySet()) {
			Text text = get( key );
			sb.append( text.toString() ).append( "\n" );
		}
		return sb.toString();
	}

	public void putAll( Properties p ) {
		for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {
			String id = (String)e.nextElement();
			String value = p.getProperty( id );
			put( new Text( new TextId( id ), value ) );
		}
	}

}
