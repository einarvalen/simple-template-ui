package org.fyfa.components;

import junit.framework.Assert;

import org.fyfa.AgeSelection;
import org.fyfa.Context;
import org.fyfa.Item;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.ids.ItemId;
import org.fyfa.nls.NlsNo;
import org.junit.Before;
import org.junit.Test;

public class ParseExceptionTest {
	private Context context;

	@Before
	public void setup() {
		this.context = createContext();
	}

	@Test(expected = ParseException.class)
	public void problems() throws ParseException {
		Form<CompFoo> form = createForm();
		ParseException parseException = new ParseException( form );
		addProblems( form, parseException );
		try {
			throw parseException;
		} catch (ParseException e) {
			verifyHints( e );
			verifyProblems( e );
			throw e;
		}
	}

	private void verifyProblems( ParseException e ) {
		for (ItemId itemId : e.getProblems().keySet()) {
			Exception problem = e.getProblem( itemId );
			Assert.assertTrue( problem instanceof IllegalArgumentException );
			//System.out.println( problem.getMessage() );
		}
	}

	private void verifyHints( ParseException e ) {
		for (ItemId itemId : e.getHints().keySet()) {
			String hint = e.getHint( itemId );
			Assert.assertNotNull( hint );
			//System.out.println( hint );
		}
	}

	private void addProblems( Form<CompFoo> form, ParseException parseException ) {
		for (ItemId itemId : form.getItems().keySet()) {
			Item item = form.getItems().get( itemId );
			parseException.addProblem( item, new IllegalArgumentException( form.getField( itemId ).getLabel() ) );
		}
	}

	private Form<CompFoo> createForm() {
		FormParams<CompFoo> params = new FormParams<CompFoo>( "CompFoo", CompFoo.class, context );
		params.setColumnNamesInSequence( new String[] { "name", "age", "married", "date" } );
		Form<CompFoo> form = new Form<CompFoo>( params );
		return form;
	}

	private Context createContext() {
		Context context = new Context( new NlsNo() );
		context.registerSelecton( new AgeSelection() );
		return context;
	}

}
