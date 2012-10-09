package org.fyfa.components;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.fyfa.Button;
import org.fyfa.Context;
import org.fyfa.Marshal;
import org.fyfa.components.RowAction;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.fieldtypes.FieldTypeDouble;
import org.fyfa.ids.ItemId;
import org.junit.Test;

public class TableTest {
	Context context = new Context();

	@Test
	public void contructor() {
		TableParams<CompFoo> params = new TableParams<CompFoo>( "CompFooTestTable", CompFoo.class, this.context );
		Table<CompFoo> table = new Table<CompFoo>( params );
		Assert.assertNotNull( table );
		String[] fieldNamesOfFoo = getFieldNames();
		String[] colIdsInSequence = table.getColIdsInSequence();
		Assert.assertEquals( fieldNamesOfFoo.length, colIdsInSequence.length );
		for (int i = 0; i < fieldNamesOfFoo.length; i++) {
			Assert.assertEquals( fieldNamesOfFoo[i], colIdsInSequence[i] );
		}
	}

	@Test
	public void render() throws Exception {
		int rowCount = 4;
		Table<CompFoo> table = createTable();
		List<CompFoo> rowList = createRowList( rowCount );
		table.includeFoot( createFoot( getMarriedCount( rowList ) ) );
		String sTable = table.render( rowList );
		verfyGeneratedHtml( rowCount, sTable );
	}

	private String[] getFieldNames() {
		List<String> keys = new Marshal().keys( CompFoo.class );
		String[] fieldNamesOfFoo = keys.toArray( new String[keys.size()] );
		return fieldNamesOfFoo;
	}

	private void verfyGeneratedHtml( int rowCount, String sTable ) {
		//System.out.println( "TABLE{ " + sTable + "}TABLE" );
		assertTrue( sTable.indexOf( "$" ) == -1 );
		assertTrue( sTable.indexOf( "%s" ) == -1 );
		assertTrue( sTable.contains( "<th align='left' title='Navn" ) );
		assertTrue( sTable.contains( "<th align='right' title='Alder" ) );
		assertTrue( sTable.contains( "Help" ) );
		for (int i = 0; i < rowCount; i++) {
			verifyGeneratedRowHtml( sTable, i );
		}
		//view( sTable );
	}

	private void verifyGeneratedRowHtml( String sTable, int i ) {
		assertTrue( sTable.indexOf( "http://www.google.com/search?q=Name" + i ) > -1 );
		assertTrue( sTable.indexOf( "Name" + i ) > -1 );
		assertTrue( sTable.indexOf( "" + (10 * i) ) > -1 );
		assertTrue( sTable.indexOf( "" + (i % 2 == 0) ) > -1 );
		assertTrue( sTable.indexOf( "theEditUrl?id=" + i ) > -1 );
	}

	private Table<CompFoo> createTable() {
		TableParams<CompFoo> params = new TableParams<CompFoo>( "CompFooTestTable", CompFoo.class, this.context );
		params.setColumnNamesInSequence( new String[] { "id", "name", "age", "married", "date" } );
		params.addRowAction( new RowAction( new ItemId( "id" ), "Edit", "document.location=\"theEditUrl?id=$id$\"" ) );
		params.addButton( new Button( "Help", "Help display", "alert(\"help\");" ) );
		Table<CompFoo> table = new Table<CompFoo>( params );
		return table;
	}

	private List<CompFoo> createRowList( int rowCount ) {
		List<CompFoo> rowList = new ArrayList<CompFoo>();
		for (int i = 0; i < rowCount; i++) {
			long age = (10 * i);
			boolean married = (i % 2 == 0);
			rowList.add( new CompFoo( "" + i, "Name" + i, age, married, new Date() ) );
		}
		return rowList;
	}

	private long getMarriedCount( List<CompFoo> list ) {
		long marriedCount = 0;
		for (CompFoo compFoo : list) {
			marriedCount += (compFoo.isMarried()) ? 1 : 0;
		}
		return marriedCount;
	}

	private String createFoot( double marriedCount ) {
		return String.format( "<tr><td colspan='2'>#married people</td><td align='center'>%s</td><td></td></tr>", new FieldTypeDouble().format( marriedCount ) );
	}

	public static void view( String generatedHtml ) throws Exception {
		File js = new File( "calendar.js" );
		String html = String.format( "<html>\n<head>\n<script language='JavaScript' src='file://%s' ></script>\n</head>\n<body>\n%s\n</body>\n</html>", js.getAbsoluteFile(), generatedHtml );
		File file = new File( "target/table-page.html" );
		FileWriter fw = new FileWriter( file );
		fw.write( html );
		fw.close();
		Runtime.getRuntime().exec( "firefox " + file.getPath() );
	}

}
