package org.fyfa.fieldtypes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.fyfa.FieldType;
import org.fyfa.ids.FieldTypeId;


/** Parsing and rendering of Numeric data fields.
*
* @author EinarValen@gmail.com */
public abstract class FieldTypeNumeric<T extends Number> implements FieldType<T> {
	private final FieldTypeId id;
	private DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();

	public FieldTypeNumeric( FieldTypeId id ) {
		this.id = id;
	}

	public String format( T value ) {
		if (value == null) return "";
		return newDecimalFormat().format( value );
	}

	@Override
	abstract public T parse( String value );

	@Override
	public String getHint() {
		return "123" + decimalFormatSymbols.getDecimalSeparator() + "45 ";
	}

	@Override
	public FieldTypeId getId() {
		return id;
	}

	protected DecimalFormat newDecimalFormat() {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols( this.getDecimalFormatSymbols() );
		return format;
	}

	public DecimalFormatSymbols getDecimalFormatSymbols() {
		return decimalFormatSymbols;
	}

	public void setDecimalFormatSymbols( DecimalFormatSymbols decimalFormatSymbols ) {
		this.decimalFormatSymbols = decimalFormatSymbols;
	}

}
