package org.fyfa.nls;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.fyfa.FieldType;
import org.fyfa.Nls;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeNumeric;


/** National Language Support. Defines behavior required for Norwegian locale.
*
* @author EinarValen@gmail.com */
public class NlsNo implements Nls {
	public final static String DateFormatString = "dd.MM.yyyy";
	private final static Locale locale = new Locale( "no", "NO" );

	public void localize( FieldType<?> fieldType ) {
		if (fieldType instanceof FieldTypeDate) {
			FieldTypeDate fieldTypeDate = (FieldTypeDate)fieldType;
			fieldTypeDate.setFormatString( DateFormatString );
		} else if (fieldType instanceof FieldTypeNumeric) {
			FieldTypeNumeric<?> fieldTypeNumeric = (FieldTypeNumeric<?>)fieldType;
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols( locale );
			decimalFormatSymbols.setGroupingSeparator( '.' );
			fieldTypeNumeric.setDecimalFormatSymbols( decimalFormatSymbols );
		}
	}

	public Locale getLocale() {
		return locale;
	}

}
