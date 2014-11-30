package org.fyfa.samples.dbcrud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fyfa.samples.link.LinkDo;


/** Data Access class.  Uses a memory map as database
 *
 * @author EinarValen@gmail.com */
public class Dao {
	private final Map<String, CountryDo> map = new HashMap<String, CountryDo>();

	public Dao() {
		supplyTestData();
	}

	public CountryDo get( String countryId ) {
		return map.get( countryId );
	}

	public void remove( String countryId ) {
		map.remove( countryId );
	}

	public void put( CountryDo countryDo ) {
		map.put( countryDo.getCOUNTRY_ID(), countryDo );
	}

	public List<CountryDo> find() {
		return new ArrayList<CountryDo>( this.map.values() );
	}

	public List<CountryDo> find( CountryDo match ) {
		ArrayList<CountryDo> response = new ArrayList<CountryDo>();
		for (CountryDo countryDo : find()) {
			if (isToBeIncluded( countryDo, match )) response.add( countryDo );
		}
		return response;
	}

	private boolean isToBeIncluded( CountryDo countryDo, CountryDo match ) {
		return isMatching( countryDo.getCOUNTRY_ID(), match.getCOUNTRY_ID() ) && isMatching( countryDo.getCOUNTRY_NAME(), match.getCOUNTRY_NAME() );
	}

	private boolean isMatching( String value, String match ) {
		if (match == null) return true;
		if (match.trim().length() == 0) return true;
		if (value == null) return false;
		if (value != null && value.toLowerCase().contains( match.toLowerCase() )) return true;
		return false;
	}

	private void supplyTestData() {
		this.put( new CountryDo( "1", 1, "USA" ) );
		this.put( new CountryDo( "2", 2, "Canada" ) );
	}
}
