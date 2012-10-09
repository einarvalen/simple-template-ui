package org.fyfa;

/** To signal that a class can be uniquely identified in a registry
* @author EinarValen@gmail.com */
public interface Identifyable<I> {
	public I getId();
}
