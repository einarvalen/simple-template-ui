package org.fyfa;

import org.fyfa.ids.ValidatorId;

/** Input validation. To be used when more than mere data type validation is required
 *
 *  @author EinarValen@gmail.com */
public interface Validator extends Identifyable<ValidatorId> {

	void check( Object value ) throws InvalidInputException;

}
