package org.fyfa;

import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.FieldTypeId;

/** Context neutral data properties
 *
 * @author EinarValen@gmail.com */
public class Field implements Identifyable<FieldId> {
	private FieldId id;
	private FieldTypeId fieldTypeId;
	private String label, description;
	private int maxLength;

	public Field() {}

	public Field( FieldId id, FieldTypeId fieldTypeId, String label, int maxLength, String description ) {
		this.id = id;
		this.fieldTypeId = fieldTypeId;
		this.label = label;
		this.maxLength = maxLength;
		this.description = description;
	}

	public Field( FieldId id, FieldTypeId fieldTypeId, String label ) {
		this( id, fieldTypeId, label, (FieldTypeDate.ID.equals( fieldTypeId ) ? 10 : 50), null );
	}

	public String getDescription() {
		return description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel( String label ) {
		this.label = label;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength( int maxLength ) {
		this.maxLength = maxLength;
	}

	public FieldId getId() {
		return id;
	}

	public FieldTypeId getFieldTypeId() {
		return fieldTypeId;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format( "id=%s, fieldTypeId=%s, label=%s, maxLength=%s, description=%s", id, fieldTypeId, label, maxLength, description );
	}

	public void setId( FieldId id ) {
		this.id = id;
	}

	public void setFieldTypeId( FieldTypeId fieldTypeId ) {
		this.fieldTypeId = fieldTypeId;
	}

}
