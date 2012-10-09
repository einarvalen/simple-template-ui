package org.fyfa.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fyfa.Button;
import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.SubmitButton;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateId;


/** Component that can put together code to display a panel of buttons.
*
* @author EinarValen@gmail.com */
public class ButtonPanel {
	private final Map<ItemId, Button> buttonActionMap = new LinkedHashMap<ItemId, Button>();
	private final Context context;
	private final ComponentId id;

	public ButtonPanel( Context context, String id ) {
		this.context = context;
		this.id = new ComponentId( id );
	}

	public void add( Button button ) {
		translate( button );
		buttonActionMap.put( button.getId(), button );
	}

	private void translate( Button button ) {
		button.setLabel( context.translate( button.getLabel() ) );
		button.setDescription( context.translate( button.getDescription() ) );
	}

	public void addButton( String label, String description, String action ) {
		add( new Button( new ItemId( label ), label, description, action ) );
	}

	public void addSubmitButton( String label, String description ) {
		add( new SubmitButton( new ItemId( label ), label, description ) );
	}

	/** Create code to display the button panel */
	public String render() {
		Component component = createComponent();
		Map<String, String> row = new HashMap<String, String>();
		for (ItemId itemId : this.buttonActionMap.keySet()) {
			Button button = this.buttonActionMap.get( itemId );
			addButtonToComponent( component, button );
			row.put( itemId.toString(), button.getAction() );
		}
		return component.render( row );
	}

	private Component createComponent() {
		return new Component( this.id, this.context, suggestColumnIdsInSequence() );
	}

	private String[] suggestColumnIdsInSequence() {
		ArrayList<String> list = new ArrayList<String>();
		for (ItemId itemId : this.buttonActionMap.keySet()) {
			list.add( itemId.toString() );
		}
		return list.toArray( new String[list.size()] );
	}

	private void addButtonToComponent( Component component, Button button ) {
		FieldId fieldId = new FieldId( id, button.getId() );
		Field field = new Field( fieldId, FieldTypeString.ID, button.getLabel(), 0, button.getDescription() );
		context.getFields().putIfMissing( field );
		TemplateId templateId = (button instanceof SubmitButton) ? IntrinsicValues.TemplateIdFormSubmitButton : IntrinsicValues.TemplateIdButton;
		component.getItems().putIfMissing( new Item( button.getId(), templateId, field.getId() ) );
	}
}
