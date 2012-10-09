package org.fyfa.templatetypes;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.TemplateParams;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.nls.NlsNo;
import org.fyfa.nls.NlsUs;
import org.fyfa.templatetypes.TemplateTypeDate;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.junit.Before;
import org.junit.Test;

public class TemplateTypeDateTest {
	private static final String MyLabel = "MyLabel";
	private static final String MyValue = "2010.11.12";
	private static final String ColumnId = "MyColumnId";
	private static final String MyDescription = "My description";
	private static final String MyHint = "My hint";
	private static final int MaxLength = 78;
	private static final ItemId itemId = new ItemId( ColumnId );
	private static final FieldId fieldId = new FieldId( new ComponentId( "MyComponentId" ), itemId );
	private TemplateTypeDate templateTypeDate;

	@Before
	public void setup() {
		templateTypeDate = new TemplateTypeDate();
	}

	@Test
	public void render() {
		TemplateParams templateParams = createTemplateParams( new Context() );
		String renderedHtml = templateTypeDate.render( templateParams );
		verifyAllInputToTemplateParamsIsThere( renderedHtml );
		String templateParamsRendered = templateParams.toString();
		verifyAllInputToTemplateParamsIsThere( templateParamsRendered );
		Assert.assertTrue( templateParamsRendered.contains( TemplateTypeDate.DATEFORMAT ) );
		//System.out.println( templateParamsRendered );
	}

	@Test
	public void renderNlsNo() {
		TemplateParams templateParams = createTemplateParams( new Context( new NlsNo() ) );
		String renderedHtml = templateTypeDate.render( templateParams );
		verifyAllInputToTemplateParamsIsThere( renderedHtml );
		String templateParamsRendered = templateParams.toString();
		verifyAllInputToTemplateParamsIsThere( templateParamsRendered );
		Assert.assertTrue( templateParamsRendered.contains( NlsNo.DateFormatString ) );
	}

	@Test
	public void renderNlsUs() {
		TemplateParams templateParams = createTemplateParams( new Context( new NlsUs() ) );
		String renderedHtml = templateTypeDate.render( templateParams );
		verifyAllInputToTemplateParamsIsThere( renderedHtml );
		String templateParamsRendered = templateParams.toString();
		verifyAllInputToTemplateParamsIsThere( templateParamsRendered );
		Assert.assertTrue( templateParamsRendered.contains( NlsUs.DateFormatString ) );
	}

	private void verifyAllInputToTemplateParamsIsThere( String rendered ) {
		Assert.assertTrue( rendered.contains( MyLabel ) );
		Assert.assertTrue( rendered.contains( MyDescription ) );
		Assert.assertTrue( rendered.contains( "" + MaxLength ) );
		Assert.assertTrue( rendered.contains( fieldId.toString() ) );
		Assert.assertTrue( rendered.contains( MyValue ) );
		Assert.assertTrue( rendered.contains( MyHint ) );
	}

	private TemplateParams createTemplateParams( Context context ) {
		context.getFields().put( createField() );
		Item item = new Item( itemId, IntrinsicValues.TemplateIdFormInputDate, fieldId );
		TemplateParams templateParams = new TemplateParams( context, item, new HashMap<String, String>() );
		Map<String, String> model = templateParams.getModel();
		model.put( TemplateTypeDefault.VALUE, MyValue );
		model.put( TemplateTypeDefault.HINT, MyHint );
		return templateParams;
	}

	private Field createField() {
		Field field = new Field( fieldId, FieldTypeDate.ID, MyLabel );
		field.setDescription( MyDescription );
		field.setMaxLength( MaxLength );
		return field;
	}

}
