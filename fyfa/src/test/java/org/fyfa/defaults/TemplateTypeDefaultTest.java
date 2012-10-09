package org.fyfa.defaults;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.TemplateParams;
import org.fyfa.TemplateType;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.junit.Before;
import org.junit.Test;

public class TemplateTypeDefaultTest {
	private Context context;
	private static final FieldId FieldId = new FieldId( "MyComponent_MyFieldId" );
	private static final ItemId ItemId = new ItemId( "MyItemId" );
	private static final String Label = "MyLabel", Description = "MyDescription", TextValue = "MyTextValue", NumericValue = "117";
	private static final int MaxLength = 43;

	@Before
	public void init() {
		context = new Context();
		Field field = new Field( FieldId, FieldTypeString.ID, Label, 43, Description );
		context.getFields().put( field );
	}

	@Test
	public void inputText() {
		Item item = new Item( ItemId, IntrinsicValues.TemplateIdFormInputText, FieldId );
		TemplateType templateType = context.getTemplateTypes().get( TemplateTypeDefault.ID );
		Map<String, String> valuesFormated = new HashMap<String, String>();
		valuesFormated.put( ItemId.toString(), TextValue );
		TemplateParams params = new TemplateParams( context, item, valuesFormated );
		params.getModel().put( TemplateTypeDefault.VALUE, TextValue );
		String string = templateType.render( params );
		Assert.assertTrue( string.indexOf( "" + MaxLength ) > -1 );
		Assert.assertTrue( string.indexOf( FieldId.toString() ) > -1 );
		Assert.assertTrue( string.indexOf( Label ) > -1 );
		Assert.assertTrue( string.indexOf( Description ) > -1 );
		Assert.assertTrue( string.indexOf( TextValue ) > -1 );
	}

	@Test
	public void inputNumeric() {
		Item item = new Item( ItemId, IntrinsicValues.TemplateIdFormInputNumeric, FieldId );
		TemplateType templateType = context.getTemplateTypes().get( TemplateTypeDefault.ID );
		Map<String, String> valuesFormated = new HashMap<String, String>();
		valuesFormated.put( ItemId.toString(), NumericValue );
		TemplateParams params = new TemplateParams( context, item, valuesFormated );
		params.getModel().put( TemplateTypeDefault.VALUE, NumericValue );
		String string = templateType.render( params );
		Assert.assertTrue( string.indexOf( "" + MaxLength ) > -1 );
		Assert.assertTrue( string.indexOf( FieldId.toString() ) > -1 );
		Assert.assertTrue( string.indexOf( Label ) > -1 );
		Assert.assertTrue( string.indexOf( Description ) > -1 );
		Assert.assertTrue( string.indexOf( NumericValue ) > -1 );
	}

}
