package org.fyfa.fieldtypes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fyfa.FieldType;
import org.fyfa.ids.FieldTypeId;


/** Parsing and rendering of Date data fields when both date and time is of significance.
 *
 * @author trond.ivar.heltveit@accenture.com */
public class FieldTypeDateTime implements FieldType<Date> {
	public static FieldTypeId ID = new FieldTypeId( "DateTime" );
	private String formatString = "yyyy-MM-dd HH:mm";
	private final FieldTypeId id;

	public FieldTypeDateTime( FieldTypeId id ) {
		this.id = id;
	}

	public FieldTypeDateTime() {
		this( ID );
	}

	@Override
	public String format( Date date ) {
		if (date == null) return "";
		DateFormat dateFormat = new SimpleDateFormat( formatString );
		return dateFormat.format( date );
	}

	@Override
	public String getHint() {
		return this.format( new Date() );
	}

	@Override
	public FieldTypeId getId() {
		return id;
	}

	@Override
	public Date parse( String value ) {
		if (value == null || value.trim().length() < 1) return null;
		DateFormat dateFormat = new SimpleDateFormat( formatString );
		try {
			return (Date)dateFormat.parseObject( value );
		} catch (ParseException e) {
			throw new RuntimeException( "DATE_PARSE_PROBLEM", e );
		}
	}

	public String getFormatString() {
		return formatString;
	}

	public void setFormatString( String formatString ) {
		this.formatString = formatString;
	}

}
