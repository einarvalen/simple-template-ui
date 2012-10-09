package org.fyfa;

/** A registry must have a way to identify each object uniquely
*
* @author EinarValen@gmail.com */
public class Id implements Comparable<Id> {
	private final String value;

	public Id( String value ) {
		if (value == null) throw new IllegalArgumentException( "Value of Id cannot be null." );
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj instanceof Id) {
			Id anotherId = (Id)obj;
			return this.value.equals( anotherId.toString() );
		}
		return false;
	}

	@Override
	public int compareTo( Id anotherId ) {
		return this.value.compareTo( anotherId.toString() );
	}
}
