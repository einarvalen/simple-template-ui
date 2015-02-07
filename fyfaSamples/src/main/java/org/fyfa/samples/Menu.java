package org.fyfa.samples;

import org.fyfa.Context;
import org.fyfa.components.MenuPanel;

public class Menu {
	private final MenuPanel menuPanel = new MenuPanel( new Context(), "Menu" );

	public Menu() {
		this.menuPanel.add( "Simple", "See SimpleRest.java", "/fyfaSamples/service/rest/simple/form" );
		this.menuPanel.add( "Annotation", "See AnnotationRest.java", "/fyfaSamples/service/rest/annotation/form" );
		this.menuPanel.add( "Selection", "See SelectionRest.java", "/fyfaSamples/service/rest/selection/form" );
		this.menuPanel.add( "Nls", "See NlsRest.java and norwegian.properties", "/fyfaSamples/service/rest/nls/form" );
		this.menuPanel.add( "Template", "See TemplateRest.java and template.properties", "/fyfaSamples/service/rest/template/form" );
		this.menuPanel.add( "FieldType", "See FieldTypeRest.java", "/fyfaSamples/service/rest/fieldtype/form" );
		this.menuPanel.add( "Validator", "See ValidatorRest.java", "/fyfaSamples/service/rest/validator/form" );
		this.menuPanel.add( "Spring", "See SpringRest.java and springContext.java", "/fyfaSamples/service/rest/spring/form" );
		this.menuPanel.add( "Links", "See samples.link package", "/fyfaSamples/service/rest/link/table" );
		this.menuPanel.add( "DbCrud", "See samples.dbcrud package", "/fyfaSamples/service/rest/dbcrud/link/table" );
	}

	public String render() {
		return this.menuPanel.render();
	}

}
