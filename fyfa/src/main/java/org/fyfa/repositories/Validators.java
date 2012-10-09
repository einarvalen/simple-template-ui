package org.fyfa.repositories;

import java.util.Map;

import org.fyfa.Registry;
import org.fyfa.Validator;
import org.fyfa.ids.ValidatorId;


/** Repository. A collection of Validator objects.
*
* @author EinarValen@gmail.com */
public class Validators extends Registry<ValidatorId, Validator> {
	public Validators() {}

	public Validators( Map<ValidatorId, Validator> map ) {
		super( map );
	}

}
