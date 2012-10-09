package org.fyfa;

import java.util.Date;

public class Foo {
	private String name;
	private long age;
	private boolean married;
	private Date date;

	public Foo() {
		this( "", 0, false, new Date() );
	}

	public Foo( String name, long age, boolean married, Date date ) {
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
