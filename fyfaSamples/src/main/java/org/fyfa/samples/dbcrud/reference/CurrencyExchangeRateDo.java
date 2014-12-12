package org.fyfa.samples.dbcrud.reference;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefTable;
import org.fyfa.samples.dbcrud.DomainObject;

public class CurrencyExchangeRateDo implements DomainObject {
	final String edit = "", delete = "", view = "";

	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "From Currency", maxLength = 3)
	private String FROM_CURRENCY;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "To Currency", maxLength = 3)
	private String TO_CURRENCY;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "Rate")
	private double RATE;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "Period")
	private int PERIOD;

	public String getFROM_CURRENCY() {
		return FROM_CURRENCY;
	}

	public void setFROM_CURRENCY(String fROM_CURRENCY) {
		FROM_CURRENCY = fROM_CURRENCY;
	}

	public String getTO_CURRENCY() {
		return TO_CURRENCY;
	}

	public void setTO_CURRENCY(String tO_CURRENCY) {
		TO_CURRENCY = tO_CURRENCY;
	}

	public double getRATE() {
		return RATE;
	}

	public void setRATE(double rATE) {
		RATE = rATE;
	}

	public int getPERIOD() {
		return PERIOD;
	}

	public void setPERIOD(int pERIOD) {
		PERIOD = pERIOD;
	}

	@Override
	public void clear() {
		this.FROM_CURRENCY = null;
		this.TO_CURRENCY = null;
		this.RATE = 0.0;
		this.PERIOD = 0;
	}

	@Override
	public Class<? extends DomainObject> clazz() {
		return CurrencyExchangeRateDo.class;
	}

	@Override
	public String toString() {
		return String.format("CurrencyExchangeRateDo [FROM_CURRENCY=%s, TO_CURRENCY=%s, RATE=%s, PERIOD=%s]", FROM_CURRENCY, TO_CURRENCY, RATE, PERIOD);
	}

}
