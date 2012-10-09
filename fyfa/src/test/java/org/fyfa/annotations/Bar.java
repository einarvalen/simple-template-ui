package org.fyfa.annotations;

import org.fyfa.annotations.FieldDef;

public class Bar {
	@SuppressWarnings("unused")
	@FieldDef(fieldTypeId = "MyFieldTypeId", label = "The FieldA Label", fieldId = "FieldA", description = "The FeildA Description", maxLength = 34)
	private String fieldA;
}
