package org.fyfa.nls;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.fyfa.FieldType;
import org.fyfa.Nls;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeNumeric;


/** National Language Support. Defines behavior required for US locale.
*
* @author EinarValen@gmail.com */
public class NlsUs implements Nls {
	public final static String DateFormatString = "MM/dd/yyyy";
	private final static Locale locale = Locale.US;

	public void localize( FieldType<?> fieldType ) {
		if (fieldType instanceof FieldTypeDate) {
			FieldTypeDate fieldTypeDate = (FieldTypeDate)fieldType;
			fieldTypeDate.setFormatString( DateFormatString );
		} else if (fieldType instanceof FieldTypeNumeric) {
			FieldTypeNumeric<?> fieldTypeNumeric = (FieldTypeNumeric<?>)fieldType;
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols( locale );
			fieldTypeNumeric.setDecimalFormatSymbols( decimalFormatSymbols );
		}
	}

	public Locale getLocale() {
		return locale;
	}

}
