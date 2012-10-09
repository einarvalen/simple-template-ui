package org.fyfa.itemfactories;

import java.util.ArrayList;
import java.util.List;

import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.ItemFactory;
import org.fyfa.Marshal;
import org.fyfa.Operation;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefFormSearch;
import org.fyfa.annotations.ItemDefFormView;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.SelectionId;
import org.fyfa.ids.TemplateId;
import org.fyfa.ids.ValidatorId;


/** Used  to create Item objects for Form components 
*
* @author EinarValen@gmail.com */
public class ItemFactoryForm<T> extends ItemFactory<T> {
	private final Marshal marshal = new Marshal();
	private final Context context;
	private final Component component;

	public ItemFactoryForm( Component component ) {
		this.context = component.getContext();
		this.component = component;
	}

	@Override
	public List<Item> assessItems( Operation operation, Class<T> clazz ) {
		List<Item> itemList = new ArrayList<Item>();
		for (String colId : this.marshal.keys( clazz )) {
			itemList.add( newItem( new ItemId( colId ), operation, clazz, this.component.getField( new ItemId( colId ) ) ) );
		}
		return itemList;
	}

	Item newItem( ItemId itemId, Operation operation, Class<T> clazz, Field field ) {
		try {
			TemplateId templateId = suggestTemplateId( field.getFieldTypeId(), false, false, false, Operation.View.equals( operation ), false );
			Item item = new Item( itemId, templateId, field.getId() );
			java.lang.reflect.Field declaredField = clazz.getDeclaredField( itemId.toString() );
			return assignAnnotatedItemDefs( operation, item, declaredField, field.getFieldTypeId() );
		} catch (Exception e) {
			throw new RuntimeException( String.format( this.context.translate( "Unable to find %s.%s." ), clazz.getSimpleName(), itemId ), e );
		}
	}

	protected Item assignAnnotatedItemDefs( Operation operation, Item item, java.lang.reflect.Field declaredField, FieldTypeId fieldTypeId ) {
		if (Operation.New.equals( operation ) && declaredField.isAnnotationPresent( ItemDefFormNew.class )) {
			ItemDefFormNew an = declaredField.getAnnotation( ItemDefFormNew.class );
			configItem( item, fieldTypeId, an.templateId(), an.selectionId(), an.validatorId(), an.uriTemplate(), an.readOnly(), an.required(), an.hidden() );
		} else if (Operation.Modify.equals( operation ) && declaredField.isAnnotationPresent( ItemDefFormModify.class )) {
			ItemDefFormModify an = declaredField.getAnnotation( ItemDefFormModify.class );
			configItem( item, fieldTypeId, an.templateId(), an.selectionId(), an.validatorId(), an.uriTemplate(), an.readOnly(), an.required(), an.hidden() );
		} else if (Operation.Search.equals( operation ) && declaredField.isAnnotationPresent( ItemDefFormSearch.class )) {
			ItemDefFormSearch an = declaredField.getAnnotation( ItemDefFormSearch.class );
			configItem( item, fieldTypeId, an.templateId(), an.selectionId(), an.validatorId(), an.uriTemplate(), an.readOnly(), an.required(), an.hidden() );
		} else if (Operation.View.equals( operation ) && declaredField.isAnnotationPresent( ItemDefFormView.class )) {
			ItemDefFormView an = declaredField.getAnnotation( ItemDefFormView.class );
			configItem( item, fieldTypeId, an.templateId(), "", "", an.uriTemplate(), an.readOnly(), false, an.hidden() );
		}
		return item;
	}

	protected void configItem( Item item, FieldTypeId fieldTypeId, String annotatedTemplateId, String annotatedSelectionId, String annotatedValidatorId, String annotatedUriTemplate, boolean readOnly, boolean required, boolean hidden ) {
		boolean selection = annotatedSelectionId.length() > 0;
		boolean validator = annotatedValidatorId.length() > 0;
		boolean uri = annotatedUriTemplate.length() > 0;
		if (annotatedTemplateId.length() == 0) {
			item.setTemplateId( suggestTemplateId( fieldTypeId, selection, hidden, required, readOnly, uri ) );
		} else {
			item.setTemplateId( new TemplateId( annotatedTemplateId ) );
		}
		if (selection) {
			item.setSelectionId( new SelectionId( annotatedSelectionId ) );
		}
		if (validator) {
			item.setValidatorId( new ValidatorId( annotatedValidatorId ) );
		}
		if (uri) {
			item.setUriTemplate( annotatedUriTemplate );
		}
		item.setRequired( required );
	}

	protected TemplateId suggestTemplateId( FieldTypeId fieldTypeId, boolean isSelection, boolean isHidden, boolean isRequired, boolean isReadOnly, boolean isUri ) {
		TemplateId templateId = IntrinsicValues.TemplateIdFormInputText;
		boolean isNumeric = ItemFactory.isNumericType( fieldTypeId );
		boolean isDate = ItemFactory.isDate( fieldTypeId );
		if (isSelection) {
			templateId = IntrinsicValues.TemplateIdFormSelect;
			if (isHidden) templateId = IntrinsicValues.TemplateIdFormSelectHidden;
			if (isReadOnly) templateId = IntrinsicValues.TemplateIdFormSelectReadOnly;
			if (isRequired) templateId = IntrinsicValues.TemplateIdFormSelectRequired;
		} else if (isNumeric) {
			templateId = IntrinsicValues.TemplateIdFormInputNumeric;
			if (isHidden) templateId = IntrinsicValues.TemplateIdFormInputNumericHidden;
			if (isReadOnly) templateId = IntrinsicValues.TemplateIdFormInputNumericReadOnly;
			if (isRequired) templateId = IntrinsicValues.TemplateIdFormInputNumericRequired;
		} else if (isDate) {
			templateId = IntrinsicValues.TemplateIdFormInputDate;
			if (isHidden) templateId = IntrinsicValues.TemplateIdFormInputDateHidden;
			if (isReadOnly) templateId = IntrinsicValues.TemplateIdFormInputDateReadOnly;
			if (isRequired) templateId = IntrinsicValues.TemplateIdFormInputDateRequired;
		} else if (isUri) {
			templateId = IntrinsicValues.TemplateIdFormFieldUri;
		} else {
			templateId = IntrinsicValues.TemplateIdFormInputText;
			if (isHidden) templateId = IntrinsicValues.TemplateIdFormInputTextHidden;
			if (isReadOnly) templateId = IntrinsicValues.TemplateIdFormInputTextReadOnly;
			if (isRequired) templateId = IntrinsicValues.TemplateIdFormInputTextRequired;
		}
		return templateId;
	}

}
