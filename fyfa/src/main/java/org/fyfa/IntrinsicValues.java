package org.fyfa;

import java.util.Properties;

import org.fyfa.fieldtypes.FieldTypeBoolean;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeDouble;
import org.fyfa.fieldtypes.FieldTypeFloat;
import org.fyfa.fieldtypes.FieldTypeInteger;
import org.fyfa.fieldtypes.FieldTypeLong;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.TemplateId;
import org.fyfa.repositories.FieldTypes;
import org.fyfa.repositories.TemplateTypes;
import org.fyfa.templatetypes.TemplateTypeDate;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.fyfa.templatetypes.TemplateTypeSelect;
import org.fyfa.templatetypes.TemplateTypeUri;

/** Holds default valuse from Contex repositories, Also performs initiations of the same repositories.
 * @author EinarValen@gmail.com */
public class IntrinsicValues {

	/* TemplateType ids */
	private static final String TEMPLATE_DEFAULT = TemplateTypeDefault.ID.toString();
	private static final String TEMPLATE_DATE = TemplateTypeDate.ID.toString();
	private static final String TEMPLATE_SELECT = TemplateTypeSelect.ID.toString();
	private static final String TEMPLATE_URI = TemplateTypeUri.ID.toString();

	/* Template ids */
	public static final TemplateId TemplateIdFormInputPassword = new TemplateId( TEMPLATE_DEFAULT + "inputPassword" );
	public static final TemplateId TemplateIdFormInputPasswordRequired = new TemplateId( TEMPLATE_DEFAULT + "inputPasswordRequied" );
	public static final TemplateId TemplateIdFormInputTextarea = new TemplateId( TEMPLATE_DEFAULT + "inputTextarea" );
	public static final TemplateId TemplateIdFormInputTextareaRequired = new TemplateId( TEMPLATE_DEFAULT + "inputTextareaRequied" );
	public static final TemplateId TemplateIdFormInputFile = new TemplateId( TEMPLATE_DEFAULT + "inputFile" );
	public static final TemplateId TemplateIdFormInputFileRequired = new TemplateId( TEMPLATE_DEFAULT + "inputFileRequied" );
	public static final TemplateId TemplateIdFormInputCheckbox = new TemplateId( TEMPLATE_DEFAULT + "inputCheckbox" );
	public static final TemplateId TemplateIdFormInputCheckboxRequired = new TemplateId( TEMPLATE_DEFAULT + "inputCheckboxRequied" );
	public static final TemplateId TemplateIdTableColumnNumeric = new TemplateId( TEMPLATE_DEFAULT + "columnNumeric" );
	public static final TemplateId TemplateIdTableColumnUri = new TemplateId( TEMPLATE_URI + "columnUri" );
	public static final TemplateId TemplateIdTableColumnAction = new TemplateId( TEMPLATE_URI + "columnAction" );
	public static final TemplateId TemplateIdTableColumnText = new TemplateId( TEMPLATE_DEFAULT + "columnText" );
	public static final TemplateId TemplateIdMenuItem = new TemplateId( TEMPLATE_DEFAULT + "menuItem" );
	public static final TemplateId TemplateIdTableColumnHeadNumeric = new TemplateId( TEMPLATE_DEFAULT + "columnHeadNumeric" );
	public static final TemplateId TemplateIdTableColumnHeadText = new TemplateId( TEMPLATE_DEFAULT + "columnHeadText" );
	public static final TemplateId TemplateIdFormSubmitButton = new TemplateId( TEMPLATE_DEFAULT + "submitButton" );
	public static final TemplateId TemplateIdButton = new TemplateId( TEMPLATE_DEFAULT + "button" );
	public static final TemplateId TemplateIdFormInputDate = new TemplateId( TEMPLATE_DATE + "inputDate" );
	public static final TemplateId TemplateIdFormInputDateHidden = new TemplateId( TEMPLATE_DATE + "inputDateHidden" );
	public static final TemplateId TemplateIdFormInputDateReadOnly = new TemplateId( TEMPLATE_DATE + "inputDateReadOnly" );
	public static final TemplateId TemplateIdFormInputDateRequired = new TemplateId( TEMPLATE_DATE + "inputDateRequired" );
	public static final TemplateId TemplateIdFormInputNumeric = new TemplateId( TEMPLATE_DEFAULT + "inputNumeric" );
	public static final TemplateId TemplateIdFormInputNumericHidden = new TemplateId( TEMPLATE_DEFAULT + "inputNumericHidden" );
	public static final TemplateId TemplateIdFormInputNumericReadOnly = new TemplateId( TEMPLATE_DEFAULT + "inputNumericReadOnly" );
	public static final TemplateId TemplateIdFormInputNumericRequired = new TemplateId( TEMPLATE_DEFAULT + "inputNumericRequired" );
	public static final TemplateId TemplateIdFormInputText = new TemplateId( TEMPLATE_DEFAULT + "inputText" );
	public static final TemplateId TemplateIdFormInputTextHidden = new TemplateId( TEMPLATE_DEFAULT + "inputTextHidden" );
	public static final TemplateId TemplateIdFormInputTextReadOnly = new TemplateId( TEMPLATE_DEFAULT + "inputTextReadOnly" );
	public static final TemplateId TemplateIdFormInputTextRequired = new TemplateId( TEMPLATE_DEFAULT + "inputTextRequired" );
	public static final TemplateId TemplateIdFormSelect = new TemplateId( TEMPLATE_SELECT + "select" );
	public static final TemplateId TemplateIdFormSelectHidden = new TemplateId( TEMPLATE_SELECT + "selectHidden" );
	public static final TemplateId TemplateIdFormSelectReadOnly = new TemplateId( TEMPLATE_SELECT + "selectReadOnly" );
	public static final TemplateId TemplateIdFormSelectRequired = new TemplateId( TEMPLATE_SELECT + "selectRequired" );
	public static final TemplateId TemplateIdFormFieldUri = new TemplateId( TEMPLATE_URI + "formFieldUri" );;
	public static final TemplateId TemplateIdFormOption = new TemplateId( TEMPLATE_SELECT + "select.option" );
	public static final TemplateId TemplateIdFormOptionSelected = new TemplateId( TEMPLATE_SELECT + "select.option.selected" );
	public static final TemplateId TemplateIdTable = new TemplateId( TEMPLATE_DEFAULT + "table" );
	public static final TemplateId TemplateIdTableBody = new TemplateId( TEMPLATE_DEFAULT + "body" );
	public static final TemplateId TemplateIdTableHead = new TemplateId( TEMPLATE_DEFAULT + "head" );
	public static final TemplateId TemplateIdTableFoot = new TemplateId( TEMPLATE_DEFAULT + "foot" );
	public static final TemplateId TemplateIdTableRow = new TemplateId( TEMPLATE_DEFAULT + "row" );
	public static final TemplateId TemplateIdFormDefaultSubmit = new TemplateId( TEMPLATE_DEFAULT + "Submit" );
	public static final TemplateId TemplateIdForm = new TemplateId( TEMPLATE_DEFAULT + "Form" );
	public static final TemplateId TemplateIdMenuPanel = new TemplateId( TEMPLATE_DEFAULT + "MenuItemPanel" );

	/* Short names */
	private static final String FIELD_ID = TemplateTypeDefault.FIELD_ID;
	private static final String LABEL = TemplateTypeDefault.LABEL;
	private static final String DESCRIPTION = TemplateTypeDefault.DESCRIPTION;
	private static final String MAXLENGTH = TemplateTypeDefault.MAXLENGTH;
	private static final String VALUE = TemplateTypeDefault.VALUE;
	private static final String HINT = TemplateTypeDefault.HINT;
	private static final String DATEFORMAT = TemplateTypeDate.DATEFORMAT;
	private static final String OPTIONS = TemplateTypeSelect.OPTIONS;
	private static final String KEY = TemplateTypeSelect.KEY;
	private static final String CONTENT = TemplateTypeDefault.CONTENT;
	private static final String STYLE = TemplateTypeDefault.STYLE;
	private static final String ACTION = TemplateTypeDefault.ACTION;
	private static final String METHOD = TemplateTypeDefault.METHOD;
	private static final String BUTTONS = TemplateTypeDefault.BUTTONS;
	private static final String URI = TemplateTypeUri.URI;
	private static final String BUTTON_LABEL = TemplateTypeUri.BUTTON_LABEL;

	/* Template formats */
	public final static String InputPassword = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='password' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$"
			+ VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputFile = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='file' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputCheckbox = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='checkbox' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$"
			+ VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputTextarea = "<tr><td colspan=2><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td> name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$' </td></tr><tr><td colspan=2> <textarea name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' maxlength='$" + MAXLENGTH + "$' rows='5' cols='80'>$" + VALUE + "$ </textarea> </td></tr><tr><td colspan=2 class='error'>$" + HINT
			+ "$</td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputPasswordRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><input type='password' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputFileRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><input type='file' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputCheckboxRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><input type='checkbox' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputTextareaRequired = "<tr><td colspan=2><label for='$" + FIELD_ID + "$' *>$" + LABEL + "$</label></td><td> name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$"
			+ VALUE + "$' </td></tr><tr><td colspan=2> <textarea name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' maxlength='$" + MAXLENGTH + "$' rows='5' cols='80'>$" + VALUE + "$ </textarea> </td></tr><tr><td colspan=2 class='error'>$"
			+ HINT + "$</td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputText = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputTextRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><input type='text' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputTextReadOnly = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' readonly='readonly' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$"
			+ MAXLENGTH + "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputTextHidden = "<tr><td></td><td><input type='hidden' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputNumeric = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$"
			+ VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputNumericRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><input type='text' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputNumericReadOnly = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' readonly='readonly' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$"
			+ MAXLENGTH + "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputNumericHidden = "<tr><td></td><td><input type='hidden' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputDate = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=40 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/>&nbsp;<input TYPE='button' class='imgbutton' id='calendar' VALUE='Calendar' Name='Calendar' title='Calendar Popup Window' onclick=\"show_calendar( 'document.forms[0].$" + FIELD_ID + "$', '', '$" + DATEFORMAT
			+ "$'); return false;\"/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputDateRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><input type='text' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=40 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/>&nbsp;<input TYPE='button' class='imgbutton' id='calendar' VALUE='Calendar' Name='Calendar' title='Calendar Popup Window' onclick=\"show_calendar( 'document.forms[0].$" + FIELD_ID + "$', '', '$"
			+ DATEFORMAT + "$'); return false;\"/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputDateReadOnly = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' readonly='readonly' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=40 maxlength='$"
			+ MAXLENGTH + "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String InputDateHidden = "<tr><td></td><td><input type='hidden' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String Select = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><select title='$" + DESCRIPTION + "$' size=1 name='$" + FIELD_ID + "$'>$" + OPTIONS
			+ "$</select></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String SelectRequired = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$ *</label></td><td><select title='$" + DESCRIPTION + "$' size=1 name='$" + FIELD_ID + "$'>$" + OPTIONS
			+ "$</select></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String SelectReadOnly = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><input type='text' readonly='readonly' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH
			+ "$' value='$" + VALUE + "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String SelectHidden = "<tr><td></td><td><input type='hidden' name='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' size=50 maxlength='$" + MAXLENGTH + "$' value='$" + VALUE
			+ "$'/></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String FormFieldUri = "<tr><td><label for='$" + FIELD_ID + "$'>$" + LABEL + "$</label></td><td><a href='$" + URI + "$'>'$" + VALUE + "$'</a></td></tr><tr><td colspan=2 class='error'>$" + HINT + "$</td></tr>\n";
	public final static String OptionSelected = "<option value='$" + KEY + "$' selected>$" + VALUE + "$</option>\n";
	public final static String Option = "<option value='$" + KEY + "$'>$" + VALUE + "$</option>\n";
	public final static String Button = "<input TYPE='button' value='$" + LABEL + "$' title='$" + DESCRIPTION + "$' onclick='$" + VALUE + "$' />";
	public final static String SubmitButton = "<input TYPE='submit' value='$" + LABEL + "$' title='$" + DESCRIPTION + "$' />";
	public final static String ColumnHeadText = "<th align='left' title='$" + DESCRIPTION + "$'>$" + LABEL + "$</th>\n";
	public final static String ColumnHeadNumeric = "<th align='right' title='$" + DESCRIPTION + "$'>$" + LABEL + "$</th>\n";
	public final static String MenuItem = "<a class='menuItem' id='$" + FIELD_ID + "$' title='$" + DESCRIPTION + "$' href='$" + VALUE + "$'>$" + LABEL + "$</a><br>\n";
	public final static String ColumnText = "<td align='left' title='$" + DESCRIPTION + "$'>$" + VALUE + "$</td>\n";
	public final static String ColumnNumeric = "<td align='right' title='$" + DESCRIPTION + "$'>$" + VALUE + "$</td>\n";
	public final static String ColumnUri = "<td align='left' title='$" + DESCRIPTION + "$'><a href='$" + URI + "$'>$" + VALUE + "$</a></td>\n";
	public final static String ColumnAction = "<td align='left' title='$" + DESCRIPTION + "$'><button type='button' onclick='$" + ACTION + "$'>$" + BUTTON_LABEL + "$</button>&nbsp;$" + VALUE + "$</td>\n";
	public final static String Table = "<table>\n$" + CONTENT + "$</table>\n<br>$" + BUTTONS + "$\n<br>";
	public final static String Body = "<tbody>\n$" + CONTENT + "$</tbody>\n";
	public final static String Head = "<thead>\n$" + CONTENT + "$</thead>\n";
	public final static String Foot = "<tfoot>\n$" + CONTENT + "$</tfoot>\n";
	public final static String Row = "<tr class='$" + STYLE + "$'>$" + BUTTONS + "$\n$" + CONTENT + "$</tr>\n";
	public final static String Submit = "<input type='submit' value='Submit'/>";
	public final static String Form = "<table>\n<form action='$" + ACTION + "$' method='$" + METHOD + "$'>\n$" + CONTENT + "$</table>\n<br>$" + BUTTONS + "$\n<br></form>";
	public final static String MenuItemPanel = "<h3>$" + LABEL + "$</h3>\n$" + CONTENT + "$\n";

	/** Fill properties with default template formats and theis TemplateIds */
	public static void initDefaultTemplateProperties( Properties properties ) {
		properties.setProperty( TemplateIdFormInputText.toString(), InputText );
		properties.setProperty( TemplateIdFormInputTextRequired.toString(), InputTextRequired );
		properties.setProperty( TemplateIdFormInputTextReadOnly.toString(), InputTextReadOnly );
		properties.setProperty( TemplateIdFormInputTextHidden.toString(), InputTextHidden );
		properties.setProperty( TemplateIdFormInputNumeric.toString(), InputNumeric );
		properties.setProperty( TemplateIdFormInputNumericRequired.toString(), InputNumericRequired );
		properties.setProperty( TemplateIdFormInputNumericReadOnly.toString(), InputNumericReadOnly );
		properties.setProperty( TemplateIdFormInputNumericHidden.toString(), InputNumericHidden );
		properties.setProperty( TemplateIdButton.toString(), Button );
		properties.setProperty( TemplateIdFormSubmitButton.toString(), SubmitButton );
		properties.setProperty( TemplateIdTableColumnHeadText.toString(), ColumnHeadText );
		properties.setProperty( TemplateIdTableColumnHeadNumeric.toString(), ColumnHeadNumeric );
		properties.setProperty( TemplateIdFormInputDate.toString(), InputDate );
		properties.setProperty( TemplateIdFormInputDateRequired.toString(), InputDateRequired );
		properties.setProperty( TemplateIdFormInputDateReadOnly.toString(), InputDateReadOnly );
		properties.setProperty( TemplateIdFormInputDateHidden.toString(), InputDateHidden );
		properties.setProperty( TemplateIdMenuItem.toString(), MenuItem );
		properties.setProperty( TemplateIdTableColumnText.toString(), ColumnText );
		properties.setProperty( TemplateIdTableColumnNumeric.toString(), ColumnNumeric );
		properties.setProperty( TemplateIdTableColumnUri.toString(), ColumnUri );
		properties.setProperty( TemplateIdTableColumnAction.toString(), ColumnAction );
		properties.setProperty( TemplateIdFormOptionSelected.toString(), OptionSelected );
		properties.setProperty( TemplateIdFormOption.toString(), Option );
		properties.setProperty( TemplateIdFormSelect.toString(), Select );
		properties.setProperty( TemplateIdFormSelectRequired.toString(), SelectRequired );
		properties.setProperty( TemplateIdFormSelectReadOnly.toString(), SelectReadOnly );
		properties.setProperty( TemplateIdFormSelectHidden.toString(), SelectHidden );
		properties.setProperty( TemplateIdFormFieldUri.toString(), FormFieldUri );
		properties.setProperty( TemplateIdTable.toString(), Table );
		properties.setProperty( TemplateIdTableBody.toString(), Body );
		properties.setProperty( TemplateIdTableHead.toString(), Head );
		properties.setProperty( TemplateIdTableFoot.toString(), Foot );
		properties.setProperty( TemplateIdTableRow.toString(), Row );
		properties.setProperty( TemplateIdFormDefaultSubmit.toString(), Submit );
		properties.setProperty( TemplateIdForm.toString(), Form );
		properties.setProperty( TemplateIdMenuPanel.toString(), MenuItemPanel );
		properties.setProperty( TemplateIdFormInputPassword.toString(), InputPassword );
		properties.setProperty( TemplateIdFormInputPasswordRequired.toString(), InputPasswordRequired );
		properties.setProperty( TemplateIdFormInputTextarea.toString(), InputTextarea );
		properties.setProperty( TemplateIdFormInputTextareaRequired.toString(), InputTextareaRequired );
		properties.setProperty( TemplateIdFormInputFile.toString(), InputFile );
		properties.setProperty( TemplateIdFormInputFileRequired.toString(), InputFileRequired );
		properties.setProperty( TemplateIdFormInputCheckbox.toString(), InputCheckbox );
		properties.setProperty( TemplateIdFormInputCheckboxRequired.toString(), InputCheckboxRequired );
	}

	/** Initiation of Context Repositories */
	public static void initDefaults( Context context ) {
		FieldTypes fieldTypes = context.getFieldTypes();
		fieldTypes.putIfMissing( new FieldTypeDouble() );
		fieldTypes.putIfMissing( new FieldTypeFloat() );
		fieldTypes.putIfMissing( new FieldTypeInteger() );
		fieldTypes.putIfMissing( new FieldTypeLong() );
		fieldTypes.putIfMissing( new FieldTypeDate() );
		fieldTypes.putIfMissing( new FieldTypeBoolean() );
		fieldTypes.putIfMissing( new FieldTypeString() );
		TemplateTypeDefault templateTypeDefault = new TemplateTypeDefault();
		TemplateTypes templateTypes = context.getTemplateTypes();
		templateTypes.putIfMissing( templateTypeDefault );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputText );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputTextRequired );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputTextReadOnly );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputTextHidden );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputNumeric );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputNumericRequired );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputNumericReadOnly );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputNumericHidden );
		context.registerTemplate( templateTypeDefault, TemplateIdButton );
		context.registerTemplate( templateTypeDefault, TemplateIdFormSubmitButton );
		context.registerTemplate( templateTypeDefault, TemplateIdTableColumnHeadText );
		context.registerTemplate( templateTypeDefault, TemplateIdTableColumnHeadNumeric );
		context.registerTemplate( templateTypeDefault, TemplateIdMenuItem );
		context.registerTemplate( templateTypeDefault, TemplateIdTableColumnText );
		context.registerTemplate( templateTypeDefault, TemplateIdTableColumnNumeric );
		context.registerTemplate( templateTypeDefault, TemplateIdTable );
		context.registerTemplate( templateTypeDefault, TemplateIdTableBody );
		context.registerTemplate( templateTypeDefault, TemplateIdTableHead );
		context.registerTemplate( templateTypeDefault, TemplateIdTableFoot );
		context.registerTemplate( templateTypeDefault, TemplateIdTableRow );
		context.registerTemplate( templateTypeDefault, TemplateIdFormDefaultSubmit );
		context.registerTemplate( templateTypeDefault, TemplateIdForm );
		context.registerTemplate( templateTypeDefault, TemplateIdMenuPanel );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputPassword );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputPasswordRequired );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputTextarea );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputTextareaRequired );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputFile );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputFileRequired );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputCheckbox );
		context.registerTemplate( templateTypeDefault, TemplateIdFormInputCheckboxRequired );
		TemplateTypeDate templateTypeDate = new TemplateTypeDate();
		templateTypes.putIfMissing( templateTypeDate );
		context.registerTemplate( templateTypeDate, TemplateIdFormInputDate );
		context.registerTemplate( templateTypeDate, TemplateIdFormInputDateRequired );
		context.registerTemplate( templateTypeDate, TemplateIdFormInputDateReadOnly );
		context.registerTemplate( templateTypeDate, TemplateIdFormInputDateHidden );
		TemplateTypeSelect templateTypeSelect = new TemplateTypeSelect();
		templateTypes.putIfMissing( templateTypeSelect );
		context.registerTemplate( templateTypeSelect, TemplateIdFormOptionSelected );
		context.registerTemplate( templateTypeSelect, TemplateIdFormOption );
		context.registerTemplate( templateTypeSelect, TemplateIdFormSelect );
		context.registerTemplate( templateTypeSelect, TemplateIdFormSelectRequired );
		context.registerTemplate( templateTypeSelect, TemplateIdFormSelectReadOnly );
		context.registerTemplate( templateTypeSelect, TemplateIdFormSelectHidden );
		TemplateTypeUri templateTypeUri = new TemplateTypeUri();
		templateTypes.putIfMissing( templateTypeUri );
		context.registerTemplate( templateTypeUri, TemplateIdTableColumnUri );
		context.registerTemplate( templateTypeUri, TemplateIdTableColumnAction );
		context.registerTemplate( templateTypeUri, TemplateIdFormFieldUri );
	}

}
