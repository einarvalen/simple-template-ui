package org.fyfa.samples.dbcrud.reference;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefTable;
import org.fyfa.samples.dbcrud.DomainObject;

public class CountryDo implements DomainObject {
	final String edit = "", delete = "", view = "";

	@ItemDefFormModify(readOnly = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "Country Id", description = "Unique alfanumeric internal handle for country",maxLength = 10)
	private String COUNTRY_ID;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "Country Key", description = "Unique numeric handle for country")
	private int COUNTRY_KEY;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "Country Name", description = "Official name of country", maxLength = 100)
	private String COUNTRY_NAME;
	@FieldDef(label= "Capital", description = "Capital city", maxLength = 100)
	private String CAPITAL;
	@FieldDef(label= "Eu")
	private int EU;
	@FieldDef(label= "Subregion Id")
	private int SUBREGION_ID;
	@FieldDef(label= "Lcc")
	private int LCC;
	@FieldDef(label= "Emerging Market")
	private int EMERGING_MARKET;

	public String getCOUNTRY_ID() {
		return COUNTRY_ID;
	}

	public void setCOUNTRY_ID(String cOUNTRY_ID) {
		COUNTRY_ID = cOUNTRY_ID;
	}

	public int getCOUNTRY_KEY() {
		return COUNTRY_KEY;
	}

	public void setCOUNTRY_KEY(int cOUNTRY_KEY) {
		COUNTRY_KEY = cOUNTRY_KEY;
	}

	public String getCOUNTRY_NAME() {
		return COUNTRY_NAME;
	}

	public void setCOUNTRY_NAME(String cOUNTRY_NAME) {
		COUNTRY_NAME = cOUNTRY_NAME;
	}

	public String getCAPITAL() {
		return CAPITAL;
	}

	public void setCAPITAL(String cAPITAL) {
		CAPITAL = cAPITAL;
	}

	public int getEU() {
		return EU;
	}

	public void setEU(int eU) {
		EU = eU;
	}

	public int getSUBREGION_ID() {
		return SUBREGION_ID;
	}

	public void setSUBREGION_ID(int sUBREGION_ID) {
		SUBREGION_ID = sUBREGION_ID;
	}

	public int getLCC() {
		return LCC;
	}

	public void setLCC(int lCC) {
		LCC = lCC;
	}

	public int getEMERGING_MARKET() {
		return EMERGING_MARKET;
	}

	public void setEMERGING_MARKET(int eMERGING_MARKET) {
		EMERGING_MARKET = eMERGING_MARKET;
	}

	@Override
	public String toString() {
		return String
				.format("CountryDo [edit=%s, delete=%s, view=%s, COUNTRY_ID=%s, COUNTRY_KEY=%s, COUNTRY_NAME=%s, CAPITAL=%s, EU=%s, SUBREGION_ID=%s, LCC=%s, EMERGING_MARKET=%s]",
						edit, delete, view, COUNTRY_ID, COUNTRY_KEY, COUNTRY_NAME, CAPITAL, EU, SUBREGION_ID, LCC, EMERGING_MARKET);
	}

	@Override
	public void clear() {
		COUNTRY_ID = null;
		COUNTRY_KEY = 0;
		COUNTRY_NAME = null;
		CAPITAL = null;
		EU = 0;
		SUBREGION_ID = 0;
		LCC = 0;
		EMERGING_MARKET = 0;
	}


	@Override
	public Class<? extends DomainObject> clazz() {
		return CountryDo.class;
	}

}
