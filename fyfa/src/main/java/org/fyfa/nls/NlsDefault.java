package org.fyfa.nls;

import java.util.Locale;

import org.fyfa.FieldType;
import org.fyfa.Nls;


/** National Language Support. Defines default behavior.
*
* @author EinarValen@gmail.com */
public class NlsDefault implements Nls {

	public void localize( FieldType<?> fieldType ) {}

	public Locale getLocale() {
		return Locale.getDefault();
	}

}
