package org.fyfa.samples;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class RenderingEngine {
	final static Logger logger = Logger.getLogger( RenderingEngine.class );
	private final VelocityEngine velocityEngine;
	private final String menu;

	public RenderingEngine( VelocityEngine velocityEngine ) {
		this.velocityEngine = velocityEngine;
		this.menu = new Menu().render();
	}

	public Map<String, String> toMap( MultivaluedMap<String, String> multivaluedMap ) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			for (String key : multivaluedMap.keySet()) {
				map.put( key, multivaluedMap.getFirst( key ) );
			}
			return map;
		} catch (Exception e) {
			throw handleExceptionHtml( e );
		}
	}

	public String render( String content, String title ) {
		try {
			Map<String, String> model = new HashMap<String, String>();
			model.put( "menu", menu );
			model.put( "title", title );
			model.put( "content", (content == null || content.trim().length() == 0) ? "<h3>Ingen ting å vise!</h3>" : content );
			String result = VelocityEngineUtils.mergeTemplateIntoString( this.velocityEngine, "thePage.html", "UTF-8", model );
			return result;
		} catch (Exception e) {
			logger.error( "Rendering engine has problems", e );
			return "Teknisk feil! Venligst meld fra til teknisk support! " + e.getMessage();
		}
	}

	public WebApplicationException handleExceptionHtml( Exception e ) {
		logger.error( e.getMessage(), e );
		String userErrorMessage = e.getMessage();
		Status userErrorCode = Status.INTERNAL_SERVER_ERROR;
		ResponseBuilder builder = Response.status( userErrorCode );
		builder.type( "text/html" );
		builder.entity( render( String.format( "<h3>%s</h3>", userErrorMessage ), "ERROR!" ) );
		return new WebApplicationException( builder.build() );
	}

}
