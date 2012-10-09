package org.fyfa;

import java.util.HashMap;
import java.util.Map;

import org.fyfa.annotations.FieldDef;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.fyfa.repositories.FieldTypes;


/** Creates instances of Field objects for components
 *
 * @author EinarValen@gmail.com */
public class FieldFactory {
	private final Context context;

	static class Annotations {
		String label, description, fieldId, fieldTypeId;
		Integer maxLength;
	}

	public FieldFactory( Context context ) {
		this.context = context;
	}

	public Map<ItemId, Field> assessFields( Class<?> clazz, ComponentId componentId ) {
		Map<ItemId, Field> map = new HashMap<ItemId, Field>();
		Map<ItemId, FieldTypeId> typeIds = FieldTypes.assessTypeIds( clazz );
		for (ItemId itemId : typeIds.keySet()) {
			map.put( itemId, newField( clazz, componentId, itemId, typeIds.get( itemId ) ) );
		}
		return map;
	}

	public Field newField( Class<?> clazz, ComponentId componentId, ItemId itemId, FieldTypeId initialFieldTypeId ) {
		String className = null;
		try {
			className = clazz.getName();
			Annotations annotations = getAnnotations( clazz.getDeclaredField( itemId.toString() ) );
			FieldId fieldId = composeFieldId( annotations, componentId, itemId );
			String label = composeLabel( annotations, itemId );
			String description = composeDescription( annotations, label );
			FieldTypeId fieldTypeId = composeFieldTypeId( annotations, initialFieldTypeId );
			int maxLength = composeMaxLength( annotations, fieldTypeId );
			return new Field( fieldId, fieldTypeId, label, maxLength, description );
		} catch (Exception e) {
			throw new RuntimeException( String.format( this.context.translate( "Unable to find %s.%s." ), className, itemId ), e );
		}
	}

	public Map<ItemId, Field> assessFields( Map<String, ?> row, ComponentId componentId ) {
		Map<ItemId, Field> map = new HashMap<ItemId, Field>();
		Map<ItemId, FieldTypeId> typeIds = FieldTypes.assessTypeIds( row );
		for (ItemId itemId : typeIds.keySet()) {
			FieldId fieldId = new FieldId( componentId, itemId );
			String label = this.context.translate( composeLabel( itemId ) );
			map.put( itemId, new Field( fieldId, typeIds.get( itemId ), label ) );
		}
		return map;
	}

	public String composeLabel( ItemId itemId ) {
		if (itemId == null) return "";
		StringBuilder sb = new StringBuilder();
		char[] ca = itemId.toString().toCharArray();
		for (int i = 0; i < ca.length; i++) {
			char ch = ca[i];
			if (i == 0) {
				ch = Character.toUpperCase( ch );
			} else if (Character.isUpperCase( ch )) {
				sb.append( ' ' );
			}
			sb.append( ch );
		}
		return sb.toString();
	}

	public String composeFieldId( String componentId, String colId ) {
		return componentId + "_" + colId;
	}

	public Context getContext() {
		return context;
	}

	private int composeMaxLength( Annotations annotations, FieldTypeId fieldTypeId ) {
		int maxLength = (FieldTypeDate.ID.equals( fieldTypeId ) ? 10 : 50);
		if (annotations.maxLength != null) maxLength = annotations.maxLength;
		return maxLength;
	}

	private String composeDescription( Annotations annotations, String label ) {
		String description = (annotations.description == null) ? "" : annotations.description;
		description = this.context.translate( description );
		return composeDescription( label.replace( "<br>", " " ), description );
	}

	private String composeLabel( Annotations annotations, ItemId itemId ) {
		String label = (annotations.label == null) ? composeLabel( itemId ) : annotations.label;
		return this.context.translate( label );
	}

	private FieldId composeFieldId( Annotations annotations, ComponentId componentId, ItemId itemId ) {
		return (annotations.fieldId == null) ? new FieldId( componentId, itemId ) : new FieldId( annotations.fieldId );
	}

	private FieldTypeId composeFieldTypeId( Annotations annotations, FieldTypeId initialFieldTypeId ) {
		return (annotations.fieldTypeId == null) ? initialFieldTypeId : new FieldTypeId( annotations.fieldTypeId );
	}

	private Annotations getAnnotations( java.lang.reflect.Field declaredField ) {
		Annotations annotations = new Annotations();
		if (declaredField.isAnnotationPresent( FieldDef.class )) {
			FieldDef fieldDef = declaredField.getAnnotation( FieldDef.class );
			if (fieldDef.fieldId().length() > 0) annotations.fieldId = fieldDef.fieldId();
			if (fieldDef.label().length() > 0) annotations.label = fieldDef.label();
			if (fieldDef.description().length() > 0) annotations.description = fieldDef.description();
			if (fieldDef.maxLength() != 0) annotations.maxLength = fieldDef.maxLength();
			if (fieldDef.fieldTypeId().length() > 0) annotations.fieldTypeId = fieldDef.fieldTypeId();
		}
		return annotations;
	}

	private String composeDescription( String label, String description ) {
		if (description == null || description.trim().length() == 0) {
			return String.format( "%s", label );
		}
		return String.format( "%s -- %s", label, description );
	}

}
