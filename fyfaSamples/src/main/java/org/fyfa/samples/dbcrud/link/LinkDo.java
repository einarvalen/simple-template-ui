package org.fyfa.samples.dbcrud.link;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefTable;
import org.fyfa.samples.dbcrud.DomainObject;

public class LinkDo implements DomainObject {
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

	public LinkDo(String ssn, String name, String city) {
		this.ssn = ssn;
		this.name = name;
		this.city = city;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void clear() {
		this.ssn = null;
		this.city = null;
		this.name = null;

	}

	@Override
	public Class<? extends DomainObject> clazz() {
		return LinkDo.class;
	}

	@Override
	public String toString() {
		return String.format("LinkDo [ssn=%s, name=%s, city=%s]", ssn, name, city);
	}

}
