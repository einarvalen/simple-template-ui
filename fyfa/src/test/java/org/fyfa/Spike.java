package org.fyfa;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class Spike {
	Locale norwegianLocale = new Locale( "no", "NO" );

	@Test
	public void locals() {
		System.out.println( "DefaultLocale:" + Locale.getDefault() );
		for (Locale locale : Locale.getAvailableLocales()) {
			if (locale.toString().startsWith( "no" )) {
				System.out.println( "getAvailableLocale(no):" + locale.getDisplayCountry() );
			}
		}
		System.out.println( "NorwegianLocale:" + norwegianLocale.getDisplayName() );
	}

	@Test
	public void date() {
		DateFormat dateFormat = DateFormat.getDateInstance( DateFormat.SHORT, norwegianLocale );
		System.out.println( "Norwegian DateFormat.SHORT:" + dateFormat.format( new Date() ) );
		dateFormat = DateFormat.getDateInstance( DateFormat.SHORT, Locale.US );
		System.out.println( "US DateFormat.SHORT:" + dateFormat.format( new Date() ) );

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy" );
		simpleDateFormat = new SimpleDateFormat();
		String localizedPattern = simpleDateFormat.toLocalizedPattern();
		System.out.println( "SimpleDateFormat:" + simpleDateFormat.format( new Date() ) + localizedPattern );
	}

	@Test
	public void number() {
		NumberFormat numberFormat = NumberFormat.getInstance( norwegianLocale );
		System.out.println( "NumberFormat:" + numberFormat.format( 123456.7890123 ) );
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance( norwegianLocale );
		decimalFormatSymbols.setGroupingSeparator( '.' );
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setDecimalFormatSymbols( decimalFormatSymbols );
		System.out.println( "DecimalFormat:" + decimalFormat.format( 123456.7890123 ) );
	}

}
