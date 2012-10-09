package org.fyfa.samples.link;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefTable;

public class LinkDo {
	private final String edit = "", delete = "", view = "";

	@ItemDefFormModify(readOnly = true)
	@ItemDefFormNew(required = true)
	private String ssn;

	@FieldDef(description = "Full name")
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	private String name;

	@FieldDef(description = "Favorit large city")
	@ItemDefFormNew(required = true)
	@ItemDefTable(uriTemplate = "http://en.wikipedia.org/wiki/$city$")
	private String city;

	public LinkDo() {}

	public LinkDo( String ssn, String name, String city ) {
		this.ssn = ssn;
		this.name = name;
		this.city = city;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn( String ssn ) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity( String city ) {
		this.city = city;
	}

}
