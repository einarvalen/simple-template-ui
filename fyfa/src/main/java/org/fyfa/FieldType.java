package org.fyfa;

import org.fyfa.ids.FieldTypeId;

/** Fields may have a host of different data types. Transitions to and from their String representations may vary.
 * Subclasses of this interface takes care of the aforementioned transformations.
 *
 * @author EinarValen@gmail.com */
public interface FieldType<T> extends Identifyable<FieldTypeId> {

	FieldTypeId getId();

	String getHint();

	String format( T value );

	T parse( String value );

}
