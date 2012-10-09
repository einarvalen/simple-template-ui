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
import org.fyfa.ids.TemplateId;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.fyfa.templatetypes.TemplateTypeUri;
import org.junit.Before;
import org.junit.Test;

public class TemplateTypeUriTest {
	private static final String MyLabel = "MyLabel";
	private static final String ColumnId = "MyColumnId";
	private static final String MyHint = "My hint";
	private static final String uriTemplate = "http://$lang$.wikipedia.org/wiki/$city$";
	private static final String City = "Rome", Lang = "en";
	private static final ItemId itemId = new ItemId( ColumnId );
	private static final FieldId fieldId = new FieldId( new ComponentId( "MyComponentId" ), itemId );
	private TemplateTypeUri templateTypeUri;

	@Before
	public void setup() {
		templateTypeUri = new TemplateTypeUri();
	}

	@Test
	public void renderUri() {
		TemplateParams templateParams = createTemplateParams( IntrinsicValues.TemplateIdFormFieldUri );
		String rendered = templateTypeUri.render( templateParams );
		//System.out.println( rendered );
		verifyAllInputToTemplateParamsIsThere( rendered );
		verifyAllInputToTemplateParamsIsThere( templateParams.toString() );
	}

	@Test
	public void renderAction() {
		TemplateParams templateParams = createTemplateParams( IntrinsicValues.TemplateIdTableColumnUri );
		String renderedHtml = templateTypeUri.render( templateParams );
		//System.out.println( renderedHtml );
		verifyAllInputToTemplateParamsIsThere( renderedHtml );
		String renderedTemplateParams = templateParams.toString();
		verifyAllInputToTemplateParamsIsThere( renderedTemplateParams );
		Assert.assertTrue( renderedTemplateParams.contains( MyLabel ) );
		Assert.assertTrue( renderedTemplateParams.contains( fieldId.toString() ) );
		Assert.assertTrue( renderedTemplateParams.contains( MyHint ) );
	}

	private void verifyAllInputToTemplateParamsIsThere( String rendered ) {
		Assert.assertTrue( rendered.contains( City ) );
		Assert.assertTrue( rendered.contains( Lang ) );
		Assert.assertTrue( rendered.contains( "wikipedia.org" ) );
	}

	private TemplateParams createTemplateParams( TemplateId templateId ) {
		Context context = new Context();
		context.getFields().put( createField() );
		Item item = new Item( itemId, templateId, fieldId );
		item.setUriTemplate( uriTemplate );
		TemplateParams templateParams = new TemplateParams( context, item, getValuesFormated() );
		Map<String, String> model = templateParams.getModel();
		model.put( TemplateTypeDefault.VALUE, City );
		model.put( TemplateTypeDefault.HINT, MyHint );
		model.put( TemplateTypeUri.URI, uriTemplate );
		return templateParams;
	}

	private Map<String, String> getValuesFormated() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put( "city", "Rome" );
		map.put( "lang", "en" );
		return map;
	}

	private Field createField() {
		return new Field( fieldId, FieldTypeString.ID, MyLabel );
	}

}
