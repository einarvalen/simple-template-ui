package org.fyfa.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.MenuItem;
import org.fyfa.TemplateType;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateId;
import org.fyfa.templatetypes.TemplateTypeDefault;


/** Component that can put together code to display a panel of buttons.
*
* @author EinarValen@gmail.com */
public class MenuPanel {
	private final Map<ItemId, MenuItem> menuItemMap = new LinkedHashMap<ItemId, MenuItem>();
	private final Context context;
	private final ComponentId id;
	private final TemplateType templateType;

	public MenuPanel( Context context, String id ) {
		this.context = context;
		this.id = new ComponentId( id );
		this.templateType = context.getTemplateTypes().get( TemplateTypeDefault.ID );
	}

	public void add( MenuItem menuItem ) {
		translate( menuItem );
		menuItemMap.put( menuItem.getId(), menuItem );
	}

	private void translate( MenuItem menuItem ) {
		menuItem.setLabel( context.translate( menuItem.getLabel() ) );
		menuItem.setDescription( context.translate( menuItem.getDescription() ) );
	}

	public void add( String label, String description, String action ) {
		add( new MenuItem( new ItemId( label ), label, description, action ) );
	}

	/** Create code to display the button panel */
	public String render() {
		Component component = createComponent();
		Map<String, String> row = new HashMap<String, String>();
		for (ItemId itemId : this.menuItemMap.keySet()) {
			MenuItem menuItem = this.menuItemMap.get( itemId );
			addItemToComponent( component, menuItem );
			row.put( itemId.toString(), menuItem.getAction() );
		}
		Map<String, String> model = new HashMap<String, String>();
		model.put( TemplateTypeDefault.LABEL, this.id.toString() );
		model.put( TemplateTypeDefault.CONTENT, component.render( row ) );
		return this.templateType.render( this.context, IntrinsicValues.TemplateIdMenuPanel, model );
	}

	private Component createComponent() {
		return new Component( this.id, this.context, suggestColumnIdsInSequence() );
	}

	private String[] suggestColumnIdsInSequence() {
		ArrayList<String> list = new ArrayList<String>();
		for (ItemId itemId : this.menuItemMap.keySet()) {
			list.add( itemId.toString() );
		}
		return list.toArray( new String[list.size()] );
	}

	private void addItemToComponent( Component component, MenuItem menuItem ) {
		FieldId fieldId = new FieldId( this.id, menuItem.getId() );
		Field field = new Field( fieldId, FieldTypeString.ID, menuItem.getLabel(), 0, menuItem.getDescription() );
		context.getFields().putIfMissing( field );
		TemplateId templateId = IntrinsicValues.TemplateIdMenuItem;
		component.getItems().putIfMissing( new Item( menuItem.getId(), templateId, field.getId() ) );
	}

}
