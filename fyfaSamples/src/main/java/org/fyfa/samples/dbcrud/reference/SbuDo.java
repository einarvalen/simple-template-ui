package org.fyfa.samples.dbcrud.reference;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefTable;
import org.fyfa.samples.dbcrud.DomainObject;

public class SbuDo implements DomainObject {
	final String edit = "", delete = "", view = "";

	@ItemDefFormModify(readOnly = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "SBU Id", maxLength = 10)
	private String SBU_ID;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "SBU Key")
	private int SBU_KEY;
	@ItemDefFormModify(required = true)
	@ItemDefFormNew(required = true)
	@FieldDef(label= "Name", maxLength = 100)
	private String SBU_NAME;

	public String getSBU_ID() {
		return SBU_ID;
	}

	public void setSBU_ID(String sBU_ID) {
		SBU_ID = sBU_ID;
	}

	public int getSBU_KEY() {
		return SBU_KEY;
	}

	public void setSBU_KEY(int sBU_KEY) {
		SBU_KEY = sBU_KEY;
	}

	public String getSBU_NAME() {
		return SBU_NAME;
	}

	public void setSBU_NAME(String sBU_NAME) {
		SBU_NAME = sBU_NAME;
	}

	@Override
	public String toString() {
		return String.format("SbuDo [SBU_ID=%s, SBU_KEY=%s, SBU_NAME=%s]", SBU_ID, SBU_KEY, SBU_NAME);
	}

	@Override
	public void clear() {
		SBU_ID = null;
		SBU_KEY = 0;
		SBU_NAME = null;
	}

	@Override
	public Class<? extends DomainObject> clazz() {
		return SbuDo.class;
	}

}
