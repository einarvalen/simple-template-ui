package org.fyfa;

import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.SelectionId;
import org.fyfa.ids.TemplateId;
import org.fyfa.ids.ValidatorId;

/** When rendering data , a Comonent need to know the template to use for a field
 *
 * @author EinarValen@gmail.com */
public class Item implements Identifyable<ItemId> {
	private ItemId columnId;
	private FieldId fieldId;
	private TemplateId templateId;
	private SelectionId selectionId;
	private ValidatorId validatorId;
	private boolean required = false;
	private String uriTemplate = null, actionTemplate = null;
	private String actionButtonLabel = "";

	public Item( ItemId columnId, TemplateId templateId, FieldId fieldId ) {
		super();
		this.columnId = columnId;
		this.templateId = templateId;
		this.fieldId = fieldId;
	}

	public ItemId getColumnId() {
		return columnId;
	}

	public TemplateId getTemplateId() {
		return templateId;
	}

	public FieldId getFieldId() {
		return fieldId;
	}

	@Override
	public ItemId getId() {
		return getColumnId();
	}

	@Override
	public String toString() {
		return String.format( "{id=%s, required=%s, templateId=%s, fieldId=%s, selection=%s}", this.getId(), this.isRequired(), this.getTemplateId(), this.getFieldId(), this.getSelectionId() );
	}

	public void setColumnId( ItemId columnId ) {
		this.columnId = columnId;
	}

	public void setTemplateId( TemplateId templateId ) {
		this.templateId = templateId;
	}

	public void setFieldId( FieldId fieldId ) {
		this.fieldId = fieldId;
	}

	public SelectionId getSelectionId() {
		return selectionId;
	}

	public void setSelectionId( SelectionId selectionId ) {
		this.selectionId = selectionId;
	}

	public void setValidatorId( ValidatorId validatorId ) {
		this.validatorId = validatorId;
	}

	public ValidatorId getValidatorId() {
		return validatorId;
	}

	public void setRequired( boolean required ) {
		this.required = required;
	}

	public boolean isRequired() {
		return required;
	}

	public void setUriTemplate( String uriTemplate ) {
		this.uriTemplate = uriTemplate;
	}

	public String getUriTemplate() {
		return uriTemplate;
	}

	public void setActionTemplate( String actionTemplate ) {
		this.actionTemplate = actionTemplate;
	}

	public String getActionTemplate() {
		return actionTemplate;
	}

	public void setActionButtonLabel( String actionButtonLabel ) {
		this.actionButtonLabel = actionButtonLabel;
	}

	public String getActionButtonLabel() {
		return actionButtonLabel;
	}
}
