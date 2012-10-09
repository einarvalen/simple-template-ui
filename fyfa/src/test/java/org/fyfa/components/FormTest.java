package org.fyfa.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.fyfa.AgeSelection;
import org.fyfa.Button;
import org.fyfa.Context;
import org.fyfa.InvalidInputException;
import org.fyfa.Item;
import org.fyfa.Selection;
import org.fyfa.SubmitButton;
import org.fyfa.Validator;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.SelectionId;
import org.fyfa.ids.ValidatorId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FormTest {
	private static final String ALDER = "Alder";
	private static final String THIS_YEAR_LESS_THE_YEAR_YOU_WERE_BORN = "This year less the year you were born";
	private Context context;

	@Before
	public void setup() {
		this.context = new Context();
		context.registerSelecton( new AgeSelection() );
		List<Selection> selectionList = new ArrayList<Selection>();
		selectionList.add( createSelectionMarried() );
		context.setSelectionList( selectionList );
		context.registerValidator( createAgeValidator() );
	}

	@Test
	public void renderWithButtons() throws Exception {
		Form<CompFoo> form = newFormCompFoo();
		CompFoo foo = new CompFoo();
		String htmlForm = form.render( foo );
		//view( htmlForm );
		verify( foo, htmlForm );
		verifyButtons( htmlForm );
	}

	@Test
	public void renderWithSubmitButtonOnly() throws Exception {
		Form<CompFoo> form = new Form<CompFoo>( new FormParams<CompFoo>( "CompFoo", CompFoo.class, context ) );
		CompFoo foo = new CompFoo();
		String htmlForm = form.render( foo );
		//view( htmlForm );
		verify( foo, htmlForm );
	}

	@Test
	public void renderExceptions() throws Exception {
		Form<CompFoo> form = newFormCompFoo();
		ParseException parseException = new ParseException( form );
		for (ItemId itemId : form.getItems().keySet()) {
			Item item = form.getItems().get( itemId );
			parseException.addProblem( item, new IllegalArgumentException( form.getField( itemId ).getLabel() ) );
		}
		String htmlForm = form.render( new CompFoo(), parseException );
		assertTrue( htmlForm.indexOf( "Invalid input" ) > -1 );
	}

	@Test
	public void parse() throws Exception {
		CompFoo foo = createFooTestData();
		Form<CompFoo> form = newFormCompFoo();
		CompFoo foo2 = form.parse( createFooRow( form ), new CompFoo() );
		assertEquals( foo.getName(), foo2.getName() );
		assertEquals( foo.getAge(), foo2.getAge() );
		assertEquals( foo.isMarried(), foo2.isMarried() );
		assertEquals( foo.getDate(), foo2.getDate() );
	}

	@Test
	public void parseException() throws Exception {
		Form<CompFoo> form = newFormCompFoo();
		Map<String, String> row = createFooRow( form );
		row.put( new FieldId( form.getId(), new ItemId( "date" ) ).toString(), "qwe" );
		try {
			form.parse( row, new CompFoo() );
		} catch (ParseException e) {
			Assert.assertNotNull( e.getProblem( new ItemId( "date" ) ) );
			Assert.assertNotNull( e.getHint( new ItemId( "date" ) ) );
			return;
		}
		fail();
	}

	@Test
	public void validate() throws Exception {
		FormParams<CompBar> params = new FormParams<CompBar>( "CompBar", CompBar.class, context );
		Form<CompBar> form = new Form<CompBar>( params );
		try {
			form.parse( createBarRow( form ), new CompBar() );
		} catch (ParseException e) {
			Assert.assertTrue( e.getHint( new ItemId( "age" ) ).contains( "18 and 35" ) );
			String hint = e.getHint( new ItemId( "name" ) );
			Assert.assertNotNull( hint );
			Assert.assertTrue( hint.contains( "Required field" ) );
			return;
		}
		fail();
	}

	private Form<CompFoo> newFormCompFoo() {
		FormParams<CompFoo> params = new FormParams<CompFoo>( "CompFoo", CompFoo.class, context );
		params.addButton( new Button( "Help", "Help display", "\"help( );\"" ) );
		params.addButton( new SubmitButton( "Insert", "Submit to host" ) );
		return new Form<CompFoo>( params );
	}

	private Validator createAgeValidator() {
		return new Validator() {
			@Override
			public void check( Object value ) throws InvalidInputException {
				if (value instanceof Integer) {
					int age = (Integer)value;
					if (18 <= age && age <= 35) return;
				}
				throw new InvalidInputException( "Must be between 18 and 35." );
			}

			@Override
			public ValidatorId getId() {
				return new ValidatorId( "AgeValidator" );
			}
		};
	}

	private Selection createSelectionMarried() {
		Selection selectionMarried = new Selection() {
			@Override
			public Map<String, String> getSelection() {
				Map<String, String> map = new HashMap<String, String>();
				map.put( "true", "Married" );
				map.put( "false", "Not married" );
				return map;
			}

			@Override
			public SelectionId getId() {
				return new SelectionId( "marriedSelectionId" );
			}
		};
		return selectionMarried;
	}

	private void verify( CompFoo foo, String formHtml ) {
		System.out.println( formHtml );
		assertTrue( formHtml.contains( THIS_YEAR_LESS_THE_YEAR_YOU_WERE_BORN ) );
		assertTrue( formHtml.contains( ALDER ) );
		assertTrue( formHtml.contains( foo.getName() ) );
		assertTrue( formHtml.contains( "" + foo.getAge() ) );
		assertTrue( formHtml.contains( "" + foo.isMarried() ) );
		assertTrue( formHtml.contains( "<option value='113'>113</option>" ) );
		assertTrue( !formHtml.contains( "$" ) );
	}

	private void verifyButtons( String formHtml ) {
		assertTrue( formHtml.contains( "Help" ) );
		assertTrue( formHtml.contains( "Insert" ) );
	}

	public static void view( String generatedHtml ) throws Exception {
		File js = new File( "calendar.js" );
		String html = String.format( "<html>\n<head>\n<script language='JavaScript' src='file://%s' ></script>\n</head>\n<body>\n%s\n</body>\n</html>", js.getAbsoluteFile(), generatedHtml );
		File file = new File( "target/form-page.html" );
		FileWriter fw = new FileWriter( file );
		fw.write( html );
		fw.close();
		Runtime.getRuntime().exec( "firefox " + file.getPath() );
	}

	private CompFoo createFooTestData() throws java.text.ParseException {
		CompFoo foo = new CompFoo();
		DateFormat dateFormat = new SimpleDateFormat( "dd.MM.yyyy" );
		foo.setName( "Einar Valen" );
		foo.setAge( 15 );
		foo.setMarried( true );
		foo.setDate( dateFormat.parse( "28.06.1984" ) );
		return foo;
	}

	private Map<String, String> createFooRow( Form<CompFoo> form ) throws java.text.ParseException {
		CompFoo foo = createFooTestData();
		Map<String, String> row = new HashMap<String, String>();
		row.put( new FieldId( form.getId(), new ItemId( "name" ) ).toString(), form.format( new ItemId( "name" ), foo.getName() ) );
		row.put( new FieldId( form.getId(), new ItemId( "age" ) ).toString(), form.format( new ItemId( "age" ), foo.getAge() ) );
		row.put( new FieldId( form.getId(), new ItemId( "married" ) ).toString(), form.format( new ItemId( "married" ), foo.isMarried() ) );
		row.put( new FieldId( form.getId(), new ItemId( "date" ) ).toString(), form.format( new ItemId( "date" ), foo.getDate() ) );
		return row;
	}

	private Map<String, String> createBarRow( Form<CompBar> form ) throws java.text.ParseException {
		CompBar bar = createBarTestData();
		Map<String, String> row = new HashMap<String, String>();
		row.put( new FieldId( form.getId(), new ItemId( "name" ) ).toString(), "" );
		row.put( new FieldId( form.getId(), new ItemId( "age" ) ).toString(), form.format( new ItemId( "age" ), bar.getAge() ) );
		row.put( new FieldId( form.getId(), new ItemId( "married" ) ).toString(), form.format( new ItemId( "married" ), bar.isMarried() ) );
		row.put( new FieldId( form.getId(), new ItemId( "date" ) ).toString(), form.format( new ItemId( "date" ), bar.getDate() ) );
		return row;
	}

	private CompBar createBarTestData() throws java.text.ParseException {
		CompBar bar = new CompBar();
		DateFormat dateFormat = new SimpleDateFormat( "dd.MM.yyyy" );
		bar.setName( "Einar Valen" );
		bar.setAge( 15 );
		bar.setMarried( true );
		bar.setDate( dateFormat.parse( "28.06.1984" ) );
		return bar;
	}

}
