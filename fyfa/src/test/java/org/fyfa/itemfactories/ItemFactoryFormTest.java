package org.fyfa.itemfactories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.Operation;
import org.fyfa.annotations.ItemDefFormModify;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.annotations.ItemDefFormSearch;
import org.fyfa.annotations.ItemDefFormView;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.ids.ItemId;
import org.fyfa.repositories.Items;
import org.junit.Test;

public class ItemFactoryFormTest {
	static class NoAnnotations {
		int myInt;
		String myString;
		Date myDate;
		double myDouble;
	}

	static class WithAnnotations {
		@ItemDefFormNew(hidden = true)
		@ItemDefFormModify(hidden = true)
		@ItemDefFormSearch(hidden = true)
		@ItemDefFormView(hidden = true)
		Long myLong;

		@ItemDefFormNew(templateId = "myTemplateId", required = true, selectionId = "mySelectionId", validatorId = "myValidatorId")
		@ItemDefFormModify(templateId = "myTemplateId", required = true, selectionId = "mySelectionId", validatorId = "myValidatorId")
		@ItemDefFormSearch(templateId = "myTemplateId", required = true, selectionId = "mySelectionId", validatorId = "myValidatorId")
		@ItemDefFormView(templateId = "myTemplateId")
		String myString;

		@ItemDefFormNew(readOnly = true, uriTemplate = "myUriTemplate")
		@ItemDefFormModify(readOnly = true, uriTemplate = "myUriTemplate")
		@ItemDefFormSearch(readOnly = true, uriTemplate = "myUriTemplate")
		@ItemDefFormView(readOnly = true, uriTemplate = "myUriTemplate")
		Float myFloat;

		@ItemDefFormNew(uriTemplate = "myUriTemplate")
		@ItemDefFormModify(uriTemplate = "myUriTemplate")
		@ItemDefFormSearch(uriTemplate = "myUriTemplate")
		@ItemDefFormView(uriTemplate = "myUriTemplate")
		Boolean myBoolean;
	}

	private final Context context = new Context();

	@Test
	public void noAnnotations() {
		List<Form<NoAnnotations>> list = createFormsForNoAnnotations();
		for (Form<?> form : list) {
			Assert.assertEquals( 4, form.getItems().getMap().size() );
			verifyItem( form, "myInt", IntrinsicValues.TemplateIdFormInputNumeric.toString() );
			verifyItem( form, "myString", IntrinsicValues.TemplateIdFormInputText.toString() );
			verifyItem( form, "myDate", IntrinsicValues.TemplateIdFormInputDate.toString() );
			verifyItem( form, "myDouble", IntrinsicValues.TemplateIdFormInputNumeric.toString() );
			Assert.assertEquals( false, getItem( form, "myDouble" ).isRequired() );
			Assert.assertNull( getItem( form, "myDouble" ).getValidatorId() );
			Assert.assertNull( getItem( form, "myDouble" ).getSelectionId() );
			Assert.assertNull( getItem( form, "myDouble" ).getUriTemplate() );
		}
	}

	@Test
	public void noAnnotationsView() {
		Form<NoAnnotations> form = newFormNoAnnotations( Operation.View );
		Assert.assertEquals( 4, form.getItems().getMap().size() );
		verifyItem( form, "myInt", IntrinsicValues.TemplateIdFormInputNumericReadOnly.toString() );
		verifyItem( form, "myString", IntrinsicValues.TemplateIdFormInputTextReadOnly.toString() );
		verifyItem( form, "myDate", IntrinsicValues.TemplateIdFormInputDateReadOnly.toString() );
		verifyItem( form, "myDouble", IntrinsicValues.TemplateIdFormInputNumericReadOnly.toString() );
	}

	@Test
	public void viewhWithAnnotations() {
		Form<WithAnnotations> form = newFormWithAnnotations( Operation.View );
		Assert.assertEquals( 4, form.getItems().getMap().size() );
		verifyItem( form, "myLong", IntrinsicValues.TemplateIdFormInputNumericReadOnly.toString() );
		verifyItem( form, "myString", "myTemplateId" );
		verifyItem( form, "myFloat", IntrinsicValues.TemplateIdFormInputNumericReadOnly.toString() );
		verifyItem( form, "myBoolean", IntrinsicValues.TemplateIdFormFieldUri.toString() );
		Assert.assertEquals( false, getItem( form, "myFloat" ).isRequired() );
		Assert.assertEquals( "myUriTemplate", getItem( form, "myFloat" ).getUriTemplate().toString() );
	}

	private Form<WithAnnotations> newFormWithAnnotations( Operation operation ) {
		final String[] columnsForView = { "myInt", "myString", "myDate", "myDouble" };
		final String[] columns = { "myLong", "myString", "MyFloat", "myBoolean" };
		FormParams<WithAnnotations> params = new FormParams<WithAnnotations>( operation.name() + "WithAnnotations", WithAnnotations.class, context );
		params.setColumnNamesInSequence( operation.equals( Operation.New ) ? columns : columnsForView );
		params.setFormActionUri( "MyAction" );
		params.setHtmlMethod( "POST" );
		params.setOperation( operation );
		return new Form<WithAnnotations>( params );
	}

	private Form<NoAnnotations> newFormNoAnnotations( Operation operation ) {
		FormParams<NoAnnotations> params = new FormParams<NoAnnotations>( operation.name() + "NoAnnotations", NoAnnotations.class, context );
		params.setColumnNamesInSequence( new String[] { "myInt", "myString", "myDate", "myDouble" } );
		params.setFormActionUri( "MyAction" );
		params.setHtmlMethod( "POST" );
		params.setOperation( operation );
		return new Form<NoAnnotations>( params );
	}

	private List<Form<NoAnnotations>> createFormsForNoAnnotations() {
		List<Form<NoAnnotations>> list = new ArrayList<Form<NoAnnotations>>();
		list.add( newFormNoAnnotations( Operation.New ) );
		list.add( newFormNoAnnotations( Operation.Modify ) );
		list.add( newFormNoAnnotations( Operation.Search ) );
		return list;
	}

	@Test
	public void withAnnotations() {
		List<Form<WithAnnotations>> list = createFormsForWithAnnotations();
		for (Form<WithAnnotations> form : list) {
			Assert.assertEquals( 4, form.getItems().getMap().size() );
			verifyItem( form, "myLong", IntrinsicValues.TemplateIdFormInputNumericHidden.toString() );
			verifyItem( form, "myString", "myTemplateId" );
			verifyItem( form, "myFloat", IntrinsicValues.TemplateIdFormInputNumericReadOnly.toString() );
			verifyItem( form, "myBoolean", IntrinsicValues.TemplateIdFormFieldUri.toString() );
			Assert.assertEquals( false, getItem( form, "myFloat" ).isRequired() );
			Assert.assertEquals( "mySelectionId", getItem( form, "myString" ).getSelectionId().toString() );
			Assert.assertEquals( "myValidatorId", getItem( form, "myString" ).getValidatorId().toString() );
			Assert.assertEquals( "myUriTemplate", getItem( form, "myFloat" ).getUriTemplate().toString() );
			Assert.assertEquals( true, getItem( form, "myString" ).isRequired() );
		}
	}

	private List<Form<WithAnnotations>> createFormsForWithAnnotations() {
		List<Form<WithAnnotations>> list = new ArrayList<Form<WithAnnotations>>();
		list.add( newFormWithAnnotations( Operation.New ) );
		list.add( newFormWithAnnotations( Operation.Modify ) );
		list.add( newFormWithAnnotations( Operation.Search ) );
		return list;
	}

	private Item getItem( Form<?> form, String name ) {
		Items items = form.getItems();
		Assert.assertNotNull( items );
		Item item = items.get( new ItemId( name ) );
		Assert.assertNotNull( item );
		return item;
	}

	private void verifyItem( Form<?> form, String name, String templateTypeId ) {
		Item item = getItem( form, name );
		Assert.assertEquals( name, item.getId().toString() );
		Assert.assertEquals( form.getId().toString() + "_" + name, item.getFieldId().toString() );
		Assert.assertEquals( templateTypeId, item.getTemplateId().toString() );
	}

}
