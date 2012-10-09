package org.fyfa.components;

import java.util.HashMap;
import java.util.Map;

import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.FieldType;
import org.fyfa.Item;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.fyfa.repositories.FieldTypes;
import org.fyfa.repositories.Fields;


/** Exception used when parsing form data
 *
 * @author EinarValen@gmail.com */
public class ParseException extends Exception {
	private static final long serialVersionUID = 1L;
	private final Map<ItemId, String> hints = new HashMap<ItemId, String>();
	private final Map<ItemId, Exception> problems = new HashMap<ItemId, Exception>();
	private final Component component;
	private final String prolog;
	private final Context context;

	public ParseException( Component component ) {
		super();
		this.component = component;
		this.context = this.component.getContext();
		this.prolog = this.context.translate( "Invalid input. Hint: " );
	}

	public boolean hasProblems() {
		return this.problems.size() > 0;
	}

	/** Assign an exception to a form item */
	public void addProblem( Item item, String hint, Exception e ) {
		problems.put( item.getId(), e );
		hints.put( item.getId(), hint );
	}

	/** Assign an exception to a form item */
	public void addProblem( Item item, Exception e ) {
		this.addProblem( item, createHint( item ), e );
	}

	/** Assign an exception to a form item */
	public void addProblem( ItemId itemId, String hint, Exception e ) {
		Item item = this.component.getItems().get( itemId );
		this.addProblem( item, hint, e );
	}

	/** Assign an exception to a form item */
	public void addProblem( ItemId itemId, Exception e ) {
		Item item = this.component.getItems().get( itemId );
		this.addProblem( item, e );
	}

	/** The FieldType if the form item may supply a hint of expected data */
	public String createHint( Item item ) {
		Fields fields = this.context.getFields();
		FieldId fieldId = item.getFieldId();
		Field field = fields.get( fieldId );
		FieldTypes fieldTypes = this.context.getFieldTypes();
		FieldTypeId fieldTypeId = field.getFieldTypeId();
		FieldType<?> fieldType = fieldTypes.get( fieldTypeId );
		String hint = fieldType.getHint();
		return this.prolog + hint;
	}

	public Exception getProblem( ItemId itemId ) {
		return problems.get( itemId );
	}

	public String getHint( ItemId itemId ) {
		return hints.get( itemId );
	}

	public Map<ItemId, String> getHints() {
		return hints;
	}

	public Map<ItemId, Exception> getProblems() {
		return problems;
	}

}
