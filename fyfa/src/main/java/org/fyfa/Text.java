package org.fyfa;

import org.fyfa.ids.TextId;

/** Used to support more languages
 *
 * @author EinarValen@gmail.com */
public class Text implements Identifyable<TextId> {
	private final String value;
	private final TextId id;

	public Text( TextId id, String value ) {
		this.value = value;
		this.id = id;
	}

	public Text( String value ) {
		this( computeId( value ), value );
	}

	@Override
	public TextId getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getId() + "=" + getValue();
	}

	public static TextId computeId( String text ) {
		return new TextId( "ID" + text.hashCode() );
	}

}
