package org.fyfa.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.Operation;
import org.fyfa.TemplateType;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateId;
import org.fyfa.itemfactories.ItemFactoryTableBody;
import org.fyfa.itemfactories.ItemFactoryTableHead;
import org.fyfa.templatetypes.TemplateTypeDefault;


/** Component that can put together code to display a table based on a list of java objects of type  T.
 *
 * @author EinarValen@gmail.com */
public class Table<T> extends Component {
	private final Component head, foot;
	private final Context context;
	private final TemplateType templateType;
	private boolean skipHead;
	private final String title;
	private final ItemFactoryTableBody<T> itemFactoryBody;
	private final ItemFactoryTableHead<T> itemFactoryHead;
	private final Operation operation = Operation.List;
	private final Class<T> clazz;
	private String buttonPanel = "", htmlFoot = null;

	public Table( TableParams<T> params ) {
		super( new ComponentId( params.getComponentName() ), params.getContext(), params.getColumnNamesInSequence() );
		this.context = this.getContext();
		this.clazz = params.getClassOfDto();
		this.itemFactoryBody = new ItemFactoryTableBody<T>( this );
		this.itemFactoryHead = new ItemFactoryTableHead<T>( this );
		this.head = new Component( new ComponentId( params.getComponentName() + "TableHead" ), params.getContext(), params.getColumnNamesInSequence() );
		this.foot = new Component( new ComponentId( params.getComponentName() + "TableFoot" ), params.getContext(), params.getColumnNamesInSequence() );
		this.templateType = this.context.getTemplateTypes().get( TemplateTypeDefault.ID );
		this.title = params.getTitle();
		assessItems( clazz );
		createRowActions( params );
		this.buttonPanel = params.getButtonPanel().render();
	}

	private String createRowActions( TableParams<T> params ) {
		Set<RowAction> rowActions = params.getRowActions();
		for (RowAction rowAction : rowActions) {
			setRowAction( rowAction );
		}
		return null;
	}

	private void setRowAction( RowAction rowAction ) {
		Item item = this.getItems().get( rowAction.getItemId() );
		item.setActionTemplate( rowAction.getAction() );
		item.setActionButtonLabel( rowAction.getButtonLabel() );
		item.setTemplateId( IntrinsicValues.TemplateIdTableColumnAction );
	}

	/** If your table needs a footer, you'll have to supply the code */
	public void includeFoot( String html ) {
		this.htmlFoot = html;
	}

	/** Suggest Item setup for the class T */
	protected void assessItems( Class<T> clazz ) {
		Map<ItemId, Field> columnIdFieldMap = this.assessFields( clazz );
		for (ItemId itemId : columnIdFieldMap.keySet()) {
			Field field = columnIdFieldMap.get( itemId );
			this.context.registerField( field );
		}
		this.getItems().putAllIfMissing( this.itemFactoryBody.assessItems( this.operation, clazz ) );
		this.head.getItems().putAllIfMissing( this.itemFactoryHead.assessItems( this.operation, clazz ) );
	}

	/** Create code for a table based on the list of objects of class T */
	public String render( List<T> domainObjects ) {
		if (domainObjects.size() < 1) return null;
		StringBuilder sb = new StringBuilder();
		if (!this.skipHead) sb.append( renderContent( IntrinsicValues.TemplateIdTableHead, this.renderHead( domainObjects ) ) );
		sb.append( renderContent( IntrinsicValues.TemplateIdTableBody, this.renderBody( domainObjects ) ) );
		if (this.htmlFoot != null) sb.append( renderContent( IntrinsicValues.TemplateIdTableFoot, this.htmlFoot ) );
		return renderContent( IntrinsicValues.TemplateIdTable, sb.toString() );
	}

	private String renderContent( TemplateId templateId, String content ) {
		Map<String, String> model = new HashMap<String, String>();
		model.put( TemplateTypeDefault.BUTTONS, this.buttonPanel );
		model.put( TemplateTypeDefault.CONTENT, content );
		return this.templateType.render( this.getContext(), templateId, model );
	}

	/** Create code for a table column headers based on class T */
	protected String renderHead( List<T> domainObjects ) {
		List<Map<String, ?>> rows = this.marshal.toMapList( domainObjects );
		return this.head.render( rows.get( 0 ) );
	}

	/** Create code for a table body based on the list of objects of class T */
	protected String renderBody( List<T> domainObjects ) {
		List<Map<String, ?>> rows = this.marshal.toMapList( domainObjects );
		Map<String, String> model = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		boolean isEven = true;
		for (Map<String, ?> row : rows) {
			isEven = isEven ? false : true;
			String rowFormated = this.render( row );
			model.put( TemplateTypeDefault.CONTENT, rowFormated );
			model.put( TemplateTypeDefault.STYLE, isEven ? "even" : "odd" );
			String renderedRow = this.templateType.render( this.getContext(), IntrinsicValues.TemplateIdTableRow, model );
			sb.append( renderedRow );
		}
		return sb.toString();
	}

	public Component getHead() {
		return head;
	}

	public Component getFoot() {
		return foot;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public String getTitle() {
		return title;
	}

}
