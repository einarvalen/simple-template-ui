package org.fyfa.components;

import java.util.Date;

import org.fyfa.AgeSelection;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefTable;


public class CompFoo {

	String id;

	@FieldDef(label = "Navn", description = "Your full name", maxLength = 60)
	@ItemDefTable(uriTemplate = "http://www.google.com/search?q=$name$&hl=en")
	String name;

	@FieldDef(label = "Alder", description = "This year less the year you were born")
	@ItemDefFormNew(selectionId = AgeSelection.ID)
	long age;

	@ItemDefFormNew(selectionId = "marriedSelectionId")
	boolean married;

	Date date;

	public CompFoo() {
		this( "", "", 23, false, new Date() );
	}

	public CompFoo( String id, String name, long age, boolean married, Date date ) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.married = married;
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public long getAge() {
		return this.age;
	}

	public boolean isMarried() {
		return this.married;
	}

	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public void setAge( long age ) {
		this.age = age;
	}

	public void setMarried( boolean married ) {
		this.married = married;
	}
}
