package org.fyfa.components;

import java.util.Date;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;


public class CompBar {
	public static final String theAgeDescriptionRef = "theAgeDescriptionRef";
	@FieldDef(label = "Navn", description = "Your full name", maxLength = 60)
	@ItemDefFormNew(required = true)
	String name;

	@FieldDef(label = "Alder", description = "This year less the year you were born")
	@ItemDefFormNew(validatorId = "AgeValidator")
	long age;

	@ItemDefFormNew(selectionId = "marriedSelectionId")
	boolean married;
	Date date;

	public CompBar() {
		this( "", 23, false, new Date() );
	}

	public CompBar( String name, long age, boolean married, Date date ) {
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
