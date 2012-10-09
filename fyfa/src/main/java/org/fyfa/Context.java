package org.fyfa;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.fyfa.ids.FieldId;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateId;
import org.fyfa.ids.TextId;
import org.fyfa.nls.NlsDefault;
import org.fyfa.repositories.FieldTypes;
import org.fyfa.repositories.Fields;
import org.fyfa.repositories.Selections;
import org.fyfa.repositories.TemplateTypes;
import org.fyfa.repositories.Templates;
import org.fyfa.repositories.Texts;
import org.fyfa.repositories.Validators;


/** Core class that contains a number of repositories where all knowledge about data types, fields text and templates are kept.
 *
 * @author EinarValen@gmail.com */
public class Context {
	/** FieldTypes is a repository that holds objects involved in data transformations between String- and computational data representation. */
	private final FieldTypes fieldTypes = new FieldTypes();
	/** Templates is a repository of Html-templates used to form parts of pages. Default values are loaded initDefaultTemplateProperties(). The templates can be totally or selectively replaced. */
	private final Templates templates = new Templates();
	/** Different templates may require different rendering logic. This is a repository if all the known different classes of TemplateType */
	private final TemplateTypes templateTypes = new TemplateTypes();
	/**	Fields is a repository that contain properties related to a java class (DTO) like label, type and max length. */
	private final Fields fields = new Fields();
	/** Selections is a repository of objects used in forming drop down lists. */
	private final Selections selections = new Selections();
	/** Validators is a repository of objects used for extended field validation - more than data type validation. */
	private final Validators validators = new Validators();
	/** Texts are stowed away into a repository as an abstraction to ease translation and support for several languages. */
	private final Texts texts = new Texts();
	private final Locale locale;
	private final Properties templateProperties = new Properties();
	private final String templatereplacementPropertiesResourceName;

	/**
	 * @param nls - national language support
	 * @param templateReplacementPropertiesResourceName - The name of a property file. You may replace some or all of the intrinsic default templates by you own customized versions.
	 */
	public Context( Nls nls, String templateReplacementPropertiesResourceName ) {
		this.templatereplacementPropertiesResourceName = templateReplacementPropertiesResourceName;
		this.locale = nls.getLocale();
		this.initDefaultTemplateProperties( this.templateProperties );
		this.loadTemplatePropertiesFromClasspath( this.templateProperties );
		this.initDefaults();
		for (FieldTypeId key : fieldTypes.keySet()) {
			nls.localize( fieldTypes.get( key ) );
		}
	}

	public Context() {
		this( new NlsDefault() );
	}

	/** @param templateReplacementPropertiesResourceName - The name of a property file. You may replace some or all of the intrinsic default templates by you own customized versions. */
	public Context( String templateReplacementPropertiesResourceName ) {
		this( new NlsDefault(), templateReplacementPropertiesResourceName );
	}

	public Context( Nls nls ) {
		this( nls, "" );
	}

	/** Loads properties with default values from IntrinsicValues */
	protected void initDefaultTemplateProperties( Properties properties ) {
		IntrinsicValues.initDefaultTemplateProperties( properties );
	}

	/** Fills all repositories by initial values */
	protected void initDefaults() {
		IntrinsicValues.initDefaults( this );
	}

	/** Load contents of file represented by this.templatereplacementPropertiesResourceName into properties */
	private void loadTemplatePropertiesFromClasspath( Properties properties ) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream( this.templatereplacementPropertiesResourceName );
		if (inputStream != null) {
			try {
				properties.load( inputStream );
			} catch (IOException e) {
				throw new RuntimeException( "Failed to load template.properties from classpath", e );
			}
		}
	}

	/** Fetches the template format form this.templateProperties, then creates a Template and adds it to the repository */
	public void registerTemplate( TemplateType templateType, TemplateId templateId ) {
		String format = this.templateProperties.getProperty( templateId.toString() );
		registerTemplate( templateId, templateType, format );
	}

	/** Creates a Template and adds it to the repository */
	public void registerTemplate( TemplateId templateId, TemplateType templateType, String format ) {
		templateTypes.putIfMissing( templateType );
		templates.putIfMissing( new Template( templateId, templateType.getId(), format ) );
	}

	@Deprecated
	public Item newItem( ItemId columnId, Field field, TemplateId templateId ) {
		this.fields.putIfMissing( field );
		return new Item( columnId, templateId, field.getId() );
	}

	/** Add Field to repository */
	public void registerField( Field field ) {
		this.fields.putIfMissing( field );
	}

	/** Add Selection to repository */
	public void registerSelecton( Selection selection ) {
		this.selections.put( selection );
	}

	/** Add Validator to repository */
	public void registerValidator( Validator validator ) {
		this.validators.put( validator );
	}

	/** Dump contents of repositories - for test an debug purposes */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "\nFields: { " ).append( this.fields.toString() ).append( " }\n" );
		sb.append( "\nTemplates: { " ).append( this.templates.toString() ).append( " }\n" );
		sb.append( "\nFieldTypes { " ).append( this.fieldTypes.toString() ).append( " }\n" );
		sb.append( "\nTemplateTypes: { " ).append( this.templateTypes.toString() ).append( " }\n" );
		sb.append( "\nSelections: { " ).append( this.selections.toString() ).append( " }\n" );
		sb.append( "\nValidators: { " ).append( this.validators.toString() ).append( " }\n" );
		sb.append( "\nTexts: { \n" ).append( this.texts.toString() ).append( " }\n" );
		sb.append( "\nTemplateProperties: { \n" ).append( toString( this.templateProperties ) ).append( " }\n" );
		return sb.toString();
	}

	private String toString( Properties properties ) {
		StringBuilder sb = new StringBuilder();
		for (Object key : properties.keySet()) {
			String value = properties.getProperty( (String)key );
			sb.append( key ).append( "=" ).append( value.replace( "\n", "\\n" ) ).append( "\n" );
		}
		return sb.toString();
	}

	public FieldTypes getFieldTypes() {
		return fieldTypes;
	}

	public Templates getTemplates() {
		return templates;
	}

	public TemplateTypes getTemplateTypes() {
		return templateTypes;
	}

	public Fields getFields() {
		return fields;
	}

	public Field getField( FieldId fieldId ) {
		return this.fields.get( fieldId );
	}

	/** Add to text repository if missing.
	 *  Make a hash of str. Use the hash as key to try and locate the text, or it's translated equivalent in the repository.
	 *  If not found in repository, store it. */
	public String translate( String str ) {
		if (str == null || str.equals( "" )) return "";
		Text text = this.texts.get( Text.computeId( str ) );
		if (text != null) return text.getValue();
		this.texts.put( new Text( str ) );
		return str;
	}

	public String getText( String key ) {
		Text text = this.texts.get( new TextId( key ) );
		return text == null ? "" : text.getValue();
	}

	/** Add to text repository */
	public void putText( String key, String text ) {
		this.texts.put( new Text( new TextId( key ), text ) );
	}

	public Texts getTexts() {
		return texts;
	}

	public void setFieldTypeList( List<FieldType<?>> fieldTypes ) {
		this.fieldTypes.putAll( fieldTypes );
	}

	/** Add list of Template objects to repository */
	public void setTemplateList( List<Template> templates ) {
		this.templates.putAll( templates );
	}

	/** Add list of TemplateType objects to repository */
	public void setTemplateTypeList( List<TemplateType> templateTypes ) {
		this.templateTypes.putAll( templateTypes );
	}

	/** Add list of Field objects to repository */
	public void setFieldList( List<Field> fields ) {
		this.fields.putAll( fields );
	}

	/** Add list of Selection objects to repository */
	public void setSelectionList( List<Selection> selections ) {
		this.selections.putAll( selections );
	}

	/** Add list of Validator objects to repository */
	public void setValidatorList( List<Validator> validators ) {
		this.validators.putAll( validators );
	}

	/** Add properties to text repository */
	public void setTextProperties( Properties properties ) {
		this.texts.putAll( properties );
	}

	public Locale getLocale() {
		return locale;
	}

	/** Returns all template formats and their lookup keys */
	public Properties getTemplateProperties() {
		return templateProperties;
	}

	public Selections getSelections() {
		return selections;
	}

	public Validators getValidators() {
		return this.validators;
	}

}
