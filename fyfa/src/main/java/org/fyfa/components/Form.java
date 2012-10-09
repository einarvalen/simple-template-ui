package org.fyfa.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fyfa.Component;
import org.fyfa.Field;
import org.fyfa.FieldType;
import org.fyfa.IntrinsicValues;
import org.fyfa.InvalidInputException;
import org.fyfa.Item;
import org.fyfa.Operation;
import org.fyfa.TemplateType;
import org.fyfa.Validator;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.ValidatorId;
import org.fyfa.itemfactories.ItemFactoryForm;
import org.fyfa.templatetypes.TemplateTypeDefault;


/** Component that can put together code to display a form based on a given java class T.
 * Can also parse submitted form data into the same java class T.
 *
 * @author EinarValen@gmail.com */
public class Form<T> extends Component {
	private final String action, method, title;
	private String buttonPanel = "";
	private final TemplateType templateType;
	private final Operation operation;
	private final ItemFactoryForm<T> itemFactory;

	public Form( FormParams<T> params ) {
		super( new ComponentId( params.getComponentName() ), params.getContext(), params.getColumnNamesInSequence() );
		this.action = params.getFormActionUri();
		this.method = params.getHtmlMethod();
		this.operation = params.getOperation();
		this.itemFactory = new ItemFactoryForm<T>( this );
		this.templateType = getTemplateType( params );
		this.buttonPanel = renderButtonPanel( params );
		this.title = params.getTitle();
		assessItems( params.getClassOfDto() );
	}

	private TemplateType getTemplateType( FormParams<T> params ) {
		return params.getContext().getTemplateTypes().get( TemplateTypeDefault.ID );
	}

	/** Creates code for rendering action button panel for the form */
	private String renderButtonPanel( FormParams<T> params ) {
		String buttonPanelHtml = params.getButtonPanel().render();
		if (buttonPanelHtml.length() != 0) return buttonPanelHtml;
		return this.templateType.render( params.getContext(), IntrinsicValues.TemplateIdFormDefaultSubmit, new HashMap<String, String>() );
	}

	/** Suggest Item setup for the class T */
	protected void assessItems( Class<T> clazz ) {
		Map<ItemId, Field> itemIdFieldMap = this.assessFields( clazz );
		for (ItemId columnId : itemIdFieldMap.keySet()) {
			Field field = itemIdFieldMap.get( columnId );
			this.getContext().registerField( field );
		}
		List<Item> itemList = this.itemFactory.assessItems( this.operation, clazz );
		this.getItems().putAllIfMissing( itemList );
	}

	/** Create code for a form based on a given java class T */
	public String render( T domainObject ) {
		return this.render( domainObject, null );
	}

	/** Create code for a form based on a given java class T, and includes error messages after a ParseException */
	public String render( T domainObject, ParseException e ) {
		Map<ItemId, String> hints = (e == null) ? null : e.getHints();
		String contents = super.render( this.marshal.toMap( domainObject ), hints );
		Map<String, String> model = new HashMap<String, String>();
		model.put( TemplateTypeDefault.ACTION, this.action );
		model.put( TemplateTypeDefault.METHOD, this.method );
		model.put( TemplateTypeDefault.CONTENT, contents );
		model.put( TemplateTypeDefault.BUTTONS, this.buttonPanel );
		return this.templateType.render( this.getContext(), IntrinsicValues.TemplateIdForm, model );
	}

	/** Transformation from key-value-map - i.e. submitted form input, to a java object of class T */
	public T parse( Map<String, String> row, T domainObject ) throws ParseException {
		ParseException parseException = new ParseException( this );
		for (String strFieldId : row.keySet()) {
			String sValue = row.get( strFieldId );
			Item item = null;
			ItemId itemId = null;
			try {
				FieldId fieldId = new FieldId( strFieldId );
				item = this.getItems().getItemByFieldId( fieldId );
				itemId = item.getColumnId();
				if ((sValue == null || sValue.trim().length() == 0) && item.isRequired()) throw new InvalidInputException( trn( "Required field. Cannot be empty" ) );
				Field field = this.getContext().getFields().get( fieldId );
				FieldType<?> fieldType = this.getContext().getFieldTypes().get( field.getFieldTypeId() );
				Object value = fieldType.parse( sValue );
				marshal.set( domainObject, itemId.toString(), value );
				check( item, value );
			} catch (InvalidInputException e) {
				parseException.addProblem( item, trn( e.getMessage() ), e );
			} catch (Exception e) {
				String txtToFormat = trn( "Parse problem. Failed to assign value %s to %s.%s" );
				String errorMsg = String.format( txtToFormat, sValue, domainObject.getClass().getName(), itemId );
				try {
					parseException.addProblem( item, new IllegalArgumentException( errorMsg, e ) );
				} catch (Exception ex) {
					throw new RuntimeException( errorMsg, e );
				}
			}
		}
		if (parseException.hasProblems()) throw parseException;
		return domainObject;
	}

	/** Finds a text in context, adds it if not found */
	public String trn( String text ) {
		return this.getContext().translate( text );
	}

	/** Input validation */
	private void check( Item item, Object value ) throws InvalidInputException {
		ValidatorId validatorId = item.getValidatorId();
		if (null == validatorId) return;
		Validator validator = this.getContext().getValidators().get( validatorId );
		validator.check( value );
	}

	public String getAction() {
		return action;
	}

	public String getTitle() {
		return title;
	}

}
