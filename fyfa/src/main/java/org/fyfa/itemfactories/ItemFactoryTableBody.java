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
import org.fyfa.annotations.ItemDefTable;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateId;


/** Used  to create Item objects for Table components
*
* @author EinarValen@gmail.com */
public class ItemFactoryTableBody<T> extends ItemFactory<T> {
	private final Marshal marshal = new Marshal();
	private final Context context;
	private final Component component;

	public ItemFactoryTableBody( Component component ) {
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
			TemplateId templateId = suggestTemplateId( field.getFieldTypeId(), false );
			Item item = new Item( itemId, templateId, field.getId() );
			java.lang.reflect.Field declaredField = clazz.getDeclaredField( itemId.toString() );
			return assignAnnotatedItemDefs( item, declaredField, field.getFieldTypeId() );
		} catch (Exception e) {
			throw new RuntimeException( String.format( this.context.translate( "Unable to find %s.%s." ), clazz.getSimpleName(), itemId ), e );
		}
	}

	protected Item assignAnnotatedItemDefs( Item item, java.lang.reflect.Field declaredField, FieldTypeId fieldTypeId ) {
		if (declaredField.isAnnotationPresent( ItemDefTable.class )) {
			ItemDefTable an = declaredField.getAnnotation( ItemDefTable.class );
			String annotatedTemplateId = an.templateId();
			String annotatedUriTemplate = an.uriTemplate();
			boolean uri = annotatedUriTemplate.length() > 0;
			if (annotatedTemplateId.length() == 0) {
				item.setTemplateId( suggestTemplateId( fieldTypeId, uri ) );
			} else {
				item.setTemplateId( new TemplateId( annotatedTemplateId ) );
			}
			if (uri) {
				item.setUriTemplate( annotatedUriTemplate );
			}
		}
		return item;
	}

	protected TemplateId suggestTemplateId( FieldTypeId fieldTypeId, boolean uri ) {
		if (uri) return IntrinsicValues.TemplateIdTableColumnUri;
		return (ItemFactory.isNumericType( fieldTypeId )) ? IntrinsicValues.TemplateIdTableColumnNumeric : IntrinsicValues.TemplateIdTableColumnText;
	}

}
