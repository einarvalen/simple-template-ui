package org.fyfa;

import java.util.Locale;

/** Supportin more than one language
 *
 * @author EinarValen@gmail.com */
public interface Nls {

	void localize( FieldType<?> fieldType );

	Locale getLocale();

}
