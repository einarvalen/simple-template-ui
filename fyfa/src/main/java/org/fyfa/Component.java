package org.fyfa;

import java.util.HashMap;
import java.util.Map;

import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateTypeId;
import org.fyfa.repositories.Items;
import org.fyfa.templatetypes.TemplateTypeDefault;


/** Can put together code to render fields
 *
 * @author EinarValen@gmail.com */
public class Component implements Identifyable<ComponentId> {
	protected final Marshal marshal = new Marshal();
	private final Items items = new Items();
	private final ComponentId id;
	private final Context context;
	private FieldFactory fieldFactory;
	private final String[] colIdsInSequence;

	public Component( ComponentId id, Context context, String[] colIdsInSequence ) {
		this.id = id;
		this.context = context;
		this.colIdsInSequence = colIdsInSequence;
		this.fieldFactory = new FieldFactory( context );
	}

	/** Suggest Field setup for clazz */
	public Map<ItemId, Field> assessFields( Class<?> clazz ) {
		return this.fieldFactory.assessFields( clazz, this.id );
	}

	public Field getField( ItemId itemId ) {
		return this.context.getFields().get( new FieldId( this.id, itemId ) );
	}

	/** Create code that can render an Item */
	@SuppressWarnings("unchecked")
	public String format( Item item, Object value ) {
		Field field = this.context.getFields().get( item.getFieldId() );
		FieldType fieldType = this.context.getFieldTypes().get( field.getFieldTypeId() );
		return fieldType.format( value );
	}

	/** Create code that can render an Item */
	public String format( ItemId colId, Object value ) {
		return format( this.items.get( colId ), value );
	}

	/** Create code that can render an data from a map of columnId as key values */
	public String render( Map<String, ?> row ) {
		return this.render( row, null );
	}

	/** Create code that can render an data from a map of columnId as key values. */
	public String render( Map<String, ?> row, Map<ItemId, String> hints ) {
		if (row == null) return "";
		Map<String, String> valuesFormated = formatAllItemValues( row );
		StringBuilder sb = new StringBuilder();
		for (String columnId : this.colIdsInSequence) {
			Item item = this.items.get( new ItemId( columnId ) );
			String valueFormated = valuesFormated.get( columnId );
			sb.append( this.renderItem( item, valueFormated, getHint( hints, item ), valuesFormated ) );
		}
		return sb.toString();
	}

	private Map<String, String> formatAllItemValues( Map<String, ?> row ) {
		Map<String, String> valuesFormated = new HashMap<String, String>();
		for (String columnId : this.colIdsInSequence) {
			valuesFormated.put( columnId, formatItemValue( row, columnId ) );
		}
		return valuesFormated;
	}

	private String formatItemValue( Map<String, ?> row, String columnId ) {
		Item item = this.items.get( new ItemId( columnId ) );
		Object value = row.get( columnId );
		String valueFormated = this.format( item, value );
		return valueFormated;
	}

	private String getHint( Map<ItemId, String> hints, Item item ) {
		if (hints == null) return "";
		String hint = hints.get( item.getId() );
		return hint == null ? "" : hint;
	}

	/** Low level render method */
	protected String renderItem( Item item, String value, String hint, Map<String, String> valuesFormated ) {
		if (value == null) return "";
		TemplateType templateType = getTemplatetype( item );
		TemplateParams params = createTemplateParameters( item, value, hint, valuesFormated );
		return templateType.render( params );
	}

	private TemplateParams createTemplateParameters( Item item, String value, String hint, Map<String, String> valuesFormated ) {
		TemplateParams params = new TemplateParams( this.context, item, valuesFormated );
		Map<String, String> model = params.getModel();
		model.put( TemplateTypeDefault.VALUE, value );
		model.put( TemplateTypeDefault.HINT, hint );
		return params;
	}

	private TemplateType getTemplatetype( Item item ) {
		Template template = this.context.getTemplates().get( item.getTemplateId() );
		TemplateTypeId templateTypeId = template.getType();
		TemplateType templateType = this.context.getTemplateTypes().get( templateTypeId );
		return templateType;
	}

	public Items getItems() {
		return items;
	}

	public ComponentId getId() {
		return id;
	}

	public Context getContext() {
		return context;
	}

	public String[] getColIdsInSequence() {
		return colIdsInSequence;
	}

	public FieldFactory getFieldFactory() {
		return fieldFactory;
	}

	public void setFieldFactory( FieldFactory fieldFactory ) {
		this.fieldFactory = fieldFactory;
	}

}
