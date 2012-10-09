package org.fyfa.templatetypes;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.Selection;
import org.fyfa.TemplateParams;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.SelectionId;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.fyfa.templatetypes.TemplateTypeSelect;
import org.junit.Before;
import org.junit.Test;

public class TemplateTypeSelectTest {
	private static final String MyLabel = "MyLabel";
	private static final String MyValue = "SelectionKeyB";
	private static final String ColumnId = "MyColumnId";
	private static final String MyDescription = "My description";
	private static final String MyHint = "My hint";
	private static final int MaxLength = 78;
	private static final ItemId itemId = new ItemId( ColumnId );
	private static final FieldId fieldId = new FieldId( new ComponentId( "MyComponentId" ), itemId );
	private TemplateTypeSelect templateTypeSelect;
	private static final Selection MySelection = new Selection() {
		@Override
		public Map<String, String> getSelection() {
			Map<String, String> map = new HashMap<String, String>();
			map.put( "SelectionKeyA", "A value" );
			map.put( "SelectionKeyB", "B value" );
			map.put( "SelectionKeyC", "C value" );
			return map;
		}

		@Override
		public SelectionId getId() {
			return new SelectionId( "MySelectionId" );
		}
	};

	@Before
	public void setup() {
		templateTypeSelect = new TemplateTypeSelect();
	}

	@Test
	public void render() {
		TemplateParams templateParams = createTemplateParams();
		String renderedHtml = templateTypeSelect.render( templateParams );
		verifyAllInputToTemplateParamsIsThere( renderedHtml );
		String renderedTemplateParams = templateParams.toString();
		verifyAllInputToTemplateParamsIsThere( renderedTemplateParams );
		Assert.assertTrue( renderedTemplateParams.contains( "" + MaxLength ) );
	}

	private void verifyAllInputToTemplateParamsIsThere( String rendered ) {
		Assert.assertTrue( rendered.contains( MyLabel ) );
		Assert.assertTrue( rendered.contains( MyDescription ) );
		Assert.assertTrue( rendered.contains( fieldId.toString() ) );
		Assert.assertTrue( rendered.contains( MyValue ) );
		Assert.assertTrue( rendered.contains( MyHint ) );
		Assert.assertTrue( rendered.contains( "value='SelectionKeyB' selected" ) );
		Assert.assertTrue( rendered.contains( "value='SelectionKeyA'" ) );
		Assert.assertTrue( rendered.contains( "value='SelectionKeyB'" ) );
	}

	private TemplateParams createTemplateParams() {
		Context context = new Context();
		context.getSelections().put( MySelection );
		context.getFields().put( createField() );
		Item item = new Item( itemId, IntrinsicValues.TemplateIdFormSelect, fieldId );
		item.setSelectionId( MySelection.getId() );
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
