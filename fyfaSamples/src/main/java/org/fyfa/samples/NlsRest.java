package org.fyfa.samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Button;
import org.fyfa.Context;
import org.fyfa.annotations.FieldDef;
import org.fyfa.components.ButtonPanel;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.nls.NlsNo;
import org.fyfa.nls.NlsUs;

/**
 * Like AnnotationRest.java, but adds a adds support for two languages.
 * Operates with two contexts, US and Norwegian, which the user may switch between.
 * The Norwegian translations are loaded from a properties file - norwegian.properties
 *
 * @author EinarValen@gmail.com */
@Path("/nls")
public class NlsRest {
	private final Context contextUs = new Context( new NlsUs() );
	private final Context contextNo = new Context( new NlsNo() );
	private final RenderingEngine renderingEngine;
	private final static String[] fieldnamesInSequence = { "name", "age", "height", "width", "date" };
	private Form<NlsDo> formNo;
	private Form<NlsDo> formUs;
	private Table<NlsDo> tableNo;
	private Table<NlsDo> tableUs;

	/** The DTO/domain object */
	static class NlsDo {
		@FieldDef(description = "Your full name", maxLength = 60)
		String name;
		@FieldDef(label = "Age", description = "Preferred age of dating partner. In years.", maxLength = 2)
		Integer age;
		@FieldDef(label = "Favorit height", description = "for dating partner. In cm.", maxLength = 3)
		double height;
		@FieldDef(label = "Favorit width", description = " for dating partner. In cm.", maxLength = 6)
		float width;
		@FieldDef(label = "Favorit date", description = "Best date so far", maxLength = 10)
		Date date;
	}

	public NlsRest( RenderingEngine renderingEngine ) throws IOException {
		this.renderingEngine = renderingEngine;
		Properties properties = loadNorwegianTranslations();
		this.contextUs.putText( "DESCRIPTION", "Supports two languages English and Norwegian. The user may switch between the languages by using the buttons at the bottom of the screen." );
		this.contextNo.setTextProperties( properties );
		createComponents();
	}

	private Properties loadNorwegianTranslations() throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream( "norwegian.properties" );
		properties.load( inputStream );
		return properties;
	}

	/**	Renders html for an input form tailored to SelectionDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form( @javax.ws.rs.core.Context HttpHeaders hh ) {
		try {
			return renderForm( hh );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a SelectionDo object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( @javax.ws.rs.core.Context HttpHeaders hh, MultivaluedMap<String, String> multivaluedMap ) {
		NlsDo nlsDo = new NlsDo();
		try {
			nlsDo = parse( hh, multivaluedMap );
			List<NlsDo> list = createList( nlsDo );
			return renderTable( hh, list );
		} catch (ParseException e) {
			return renderErrorForm( hh, nlsDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** To download the Norwegian context */
	@GET
	@Path("context-no")
	@Produces("text/simple")
	public String viewContextNo() {
		return this.contextNo.toString();
	}

	/** To download the US context */
	@GET
	@Path("context-us")
	@Produces("text/simple")
	public String viewContextUs() {
		return this.contextUs.toString();
	}

	private String renderErrorForm( HttpHeaders hh, NlsDo nlsDo, ParseException e ) {
		Form<NlsDo> form = this.getForm( getLanguage( hh ) );
		String html = form.render( nlsDo, e );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String renderTable( HttpHeaders hh, List<NlsDo> list ) {
		Table<NlsDo> table = getTable( getLanguage( hh ) );
		String html = table.render( list );
		return this.renderingEngine.render( html, table.getTitle() );
	}

	private NlsDo parse( HttpHeaders hh, MultivaluedMap<String, String> multivaluedMap ) throws ParseException {
		Map<String, String> map = this.renderingEngine.toMap( multivaluedMap );
		Form<NlsDo> form = this.getForm( getLanguage( hh ) );
		return form.parse( map, new NlsDo() );
	}

	private String renderForm( HttpHeaders hh ) {
		Form<NlsDo> form = getForm( getLanguage( hh ) );
		String description = getContext( getLanguage( hh ) ).getText( "DESCRIPTION" );
		String downloadContext = "Download Context : <a href='context-no'>no</a>,&nbsp;<a href='context-us'>us</a>";
		String htmlForm = form.render( new NlsDo() );
		String htmlLanguageChoiseButtons = this.renderLanguageChoiseButtons( getLanguage( hh ) );
		String html = String.format( "%s%s\n<hr/>\n%s\n<h3>Locale: %s</h3>%s", description, htmlForm, htmlLanguageChoiseButtons, getLanguage( hh ), downloadContext );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private Form<NlsDo> newForm( Context context ) {
		FormParams<NlsDo> params = new FormParams<NlsDo>( "NlsForm", NlsDo.class, context );
		params.setColumnNamesInSequence( fieldnamesInSequence );
		params.setFormActionUri( "/fyfaSamples/service/rest/nls/response" );
		return new Form<NlsDo>( params );
	}

	private Table<NlsDo> newTable( Context context ) {
		TableParams<NlsDo> params = new TableParams<NlsDo>( "NlsTable", NlsDo.class, context );
		return new Table<NlsDo>( params );
	}

	/** Renders Button panel to let the user swith language */
	private String renderLanguageChoiseButtons( String language ) {
		Context context = getContext( language );
		ButtonPanel buttonPanel = new ButtonPanel( context, "NlsLanguageChoise" );
		buttonPanel.add( new Button( "no", "Switch to Norwegian locale", "javascript:document.cookie=\"nls=no; path=/\";window.location.reload();" ) );
		buttonPanel.add( new Button( "us", "Switch to US locale", "javascript:document.cookie=\"nls=us; path=/\";window.location.reload();" ) );
		return buttonPanel.render();
	}

	private Context getContext( String language ) {
		return ("us".equals( language )) ? this.contextUs : this.contextNo;
	}

	private void createComponents() {
		this.formNo = newForm( this.contextNo );
		this.formUs = newForm( this.contextUs );
		this.tableNo = newTable( this.contextNo );
		this.tableUs = newTable( this.contextUs );
	}

	private Table<NlsDo> getTable( String language ) {
		if ("us".equals( language )) return this.tableUs;
		return this.tableNo;
	}

	private Form<NlsDo> getForm( String language ) {
		if ("us".equals( language )) return this.formUs;
		return this.formNo;
	}

	private String getLanguage( HttpHeaders hh ) {
		if (hh == null) return "no-hh";
		Map<String, Cookie> cookies = hh.getCookies();
		if (cookies == null) return "no-cookies";
		Cookie cookie = cookies.get( "nls" );
		if (cookie == null) return "no-nls";
		return cookie.getValue();
	}

	private List<NlsDo> createList( NlsDo nlsDo ) {
		List<NlsDo> list = new ArrayList<NlsDo>();
		for (int i = 0; i < 10; i++) {
			list.add( nlsDo );
		}
		return list;
	}

}
