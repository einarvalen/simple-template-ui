package org.fyfa.templatetypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.fyfa.Context;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.Selection;
import org.fyfa.Template;
import org.fyfa.TemplateParams;
import org.fyfa.TemplateType;
import org.fyfa.ids.TemplateTypeId;
import org.fyfa.repositories.Selections;


/** Used for rendering selection type fields. (drop down lists)
*
* @author EinarValen@gmail.com */
public class TemplateTypeSelect extends TemplateTypeDefault implements TemplateType {
	public static TemplateTypeId ID = new TemplateTypeId( "TemplateTypeSelect" );
	public static final String KEY = "KEY", OPTIONS = "OPTIONS", VALUE = TemplateTypeDefault.VALUE;

	/** Set up the data model for template expansion */
	@Override
	protected void updateModel( TemplateParams params ) {
		super.updateModel( params );
		TemplateTypeOption templateTypeOption = new TemplateTypeOption( params );
		Map<String, String> model = params.getModel();
		String optionSelected = model.get( TemplateTypeDefault.VALUE );
		model.put( OPTIONS, templateTypeOption.render( optionSelected ) );
	}

	@Override
	public TemplateTypeId getId() {
		return ID;
	}

	class TemplateTypeOption {
		private final Map<String, String> optionsModel = new HashMap<String, String>();
		private final Template optionSelectedFormat;
		private final Template optionFormat;
		private final TemplateParams params;

		public TemplateTypeOption( TemplateParams params ) {
			this.params = params;
			Context context = params.getContext();
			this.optionSelectedFormat = context.getTemplates().get( IntrinsicValues.TemplateIdFormOptionSelected );
			this.optionFormat = context.getTemplates().get( IntrinsicValues.TemplateIdFormOption );

		}

		public String render( String optionSelected ) {
			StringBuilder sb = new StringBuilder();
			for (Entry<String, String> entry : getAvailableOptions().entrySet()) {
				Template t = getTemplate( optionSelected, entry.getKey() );
				sb.append( t.expand( createModel( entry ) ) );
			}
			return sb.toString();
		}

		private Template getTemplate( String optionKeySelected, String optionKey ) {
			return (optionKeySelected.equals( optionKey )) ? optionSelectedFormat : optionFormat;
		}

		private Map<String, String> createModel( Entry<String, String> entry ) {
			optionsModel.clear();
			optionsModel.put( KEY, entry.getKey() );
			optionsModel.put( VALUE, entry.getValue() );
			return this.optionsModel;
		}

		private Map<String, String> getAvailableOptions() {
			Item item = this.params.getItem();
			Selections selections = params.getContext().getSelections();
			Selection selection = selections.get( item.getSelectionId() );
			return selection.getSelection();
		}
	}
}
