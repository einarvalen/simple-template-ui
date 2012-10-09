package org.fyfa;

import java.util.Map;
import java.util.TreeMap;

import org.fyfa.Selection;
import org.fyfa.ids.SelectionId;


public class AgeSelection implements Selection {
	public static final String ID = "AgeSelection";

	@Override
	public Map<String, String> getSelection() {
		Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < 200; i++) {
			String show = String.format( "%03d", i );
			String value = String.valueOf( i );
			map.put( value, show );
		}
		return map;
	}

	@Override
	public SelectionId getId() {
		return new SelectionId( ID );
	}

}
