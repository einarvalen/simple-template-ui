package org.fyfa.samples.dbcrud;

public interface DomainObject {
	void clear();
	Class<? extends DomainObject> clazz();
}
