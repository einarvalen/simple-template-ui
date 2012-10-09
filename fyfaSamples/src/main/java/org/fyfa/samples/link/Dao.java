package org.fyfa.samples.link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Data Access class.  Uses a memory map as database
 *
 * @author EinarValen@gmail.com */
public class Dao {
	private final Map<String, LinkDo> map = new HashMap<String, LinkDo>();

	public Dao() {
		supplyTestData();
	}

	public LinkDo get( String ssn ) {
		return map.get( ssn );
	}

	public void remove( String ssn ) {
		map.remove( ssn );
	}

	public void put( LinkDo linkDo ) {
		map.put( linkDo.getSsn(), linkDo );
	}

	public List<LinkDo> find() {
		return new ArrayList<LinkDo>( this.map.values() );
	}

	public List<LinkDo> find( LinkDo match ) {
		ArrayList<LinkDo> response = new ArrayList<LinkDo>();
		for (LinkDo linkDo : find()) {
			if (isToBeIncluded( linkDo, match )) response.add( linkDo );
		}
		return response;
	}

	private boolean isToBeIncluded( LinkDo linkDo, LinkDo match ) {
		return isMatching( linkDo.getCity(), match.getCity() ) && isMatching( linkDo.getName(), match.getName() );
	}

	private boolean isMatching( String value, String match ) {
		if (match == null) return true;
		if (match.trim().length() == 0) return true;
		if (value == null) return false;
		if (value != null && value.toLowerCase().contains( match.toLowerCase() )) return true;
		return false;
	}

	private void supplyTestData() {
		this.put( new LinkDo( "1", "John Smith", "London" ) );
		this.put( new LinkDo( "2", "Oluf Rallkattli", "Tromsø" ) );
		this.put( new LinkDo( "3", "Frank L Wright", "Scottsdale, Arizona" ) );
		this.put( new LinkDo( "4", "Ronald McDonald", "Washington, D.C." ) );
	}

}
