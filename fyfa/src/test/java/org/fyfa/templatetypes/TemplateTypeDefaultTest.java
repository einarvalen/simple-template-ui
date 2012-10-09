package org.fyfa.templatetypes;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.TemplateParams;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.junit.Before;
import org.junit.Test;

public class TemplateTypeDefaultTest {
	private static final String MyLabel = "MyLabel";
	private static final String MyValue = "my value";
	private static final String ColumnId = "MyColumnId";
	private static final String MyDescription = "My description";
	private static final String MyHint = "My hint";
	private static final int MaxLength = 78;
	private static final ItemId itemId = new ItemId( ColumnId );
	private static final FieldId fieldId = new FieldId( new ComponentId( "MyComponentId" ), itemId );
	private TemplateTypeDefault templateTypeDefault;

	@Before
	public void setup() {
		templateTypeDefault = new TemplateTypeDefault();
	}

	@Test
	public void render() {
		TemplateParams templateParams = createTemplateParams();
		String rendered = templateTypeDefault.render( templateParams );
		verifyAllInputToTemplateParamsIsThere( rendered );
		verifyAllInputToTemplateParamsIsThere( templateParams.toString() );
	}

	private void verifyAllInputToTemplateParamsIsThere( String rendered ) {
		Assert.assertTrue( rendered.contains( MyLabel ) );
		Assert.assertTrue( rendered.contains( MyDescription ) );
		Assert.assertTrue( rendered.contains( "" + MaxLength ) );
		Assert.assertTrue( rendered.contains( fieldId.toString() ) );
		Assert.assertTrue( rendered.contains( MyValue ) );
		Assert.assertTrue( rendered.contains( MyHint ) );
	}

	private TemplateParams createTemplateParams() {
		Context context = new Context();
		context.getFields().put( createField() );
		Item item = new Item( itemId, IntrinsicValues.TemplateIdFormInputText, fieldId );
		TemplateParams templateParams = new TemplateParams( context, item, new HashMap<String, String>() );
		Map<String, String> model = templateParams.getModel();
		model.put( TemplateTypeDefault.VALUE, MyValue );
		model.put( TemplateTypeDefault.HINT, MyHint );
		return templateParams;
	}

	private Field createField() {
		Field field = new Field( fieldId, FieldTypeString.ID, MyLabel );
		field.setDescription( MyDescription );
		field.setMaxLength( MaxLength );
		return field;
	}
}
