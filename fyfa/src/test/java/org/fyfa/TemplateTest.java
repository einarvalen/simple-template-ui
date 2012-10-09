package org.fyfa;

import java.util.HashMap;
import java.util.Map;


import org.fyfa.Template;
import org.fyfa.ids.TemplateId;
import org.fyfa.ids.TemplateTypeId;
import org.junit.Assert;
import org.junit.Test;

public class TemplateTest {

	@Test
	public void expand() {
		Map<String, String> model = createModel();
		String format = "$VariableA_Name$$VariableC_Name$\n<html>\n<h1>$VariableA_Name$</h1>\n<p>\n$VariableB_Name$\n</p>\n</html>\n";
		Template t = createTemplate( format );
		String expanded = t.expand( model );
		verifyExpandedResult( expanded );
	}

	private void verifyExpandedResult( String expanded ) {
		//System.out.println( "EXPANDED:\n" + expanded );
		Assert.assertTrue( expanded.indexOf( "$" ) == -1 );
		Assert.assertTrue( expanded.indexOf( "Replaced_VariavbleA" ) > -1 );
		Assert.assertTrue( expanded.indexOf( "Replaced_VariavbleB" ) > -1 );
		Assert.assertTrue( expanded.indexOf( "Replaced_VariavbleC" ) > -1 );
	}

	private Template createTemplate( String format ) {
		return new Template( new TemplateId( "id" ), new TemplateTypeId( "type" ), format );
	}

	private Map<String, String> createModel() {
		Map<String, String> p = new HashMap<String, String>();
		p.put( "VariableA_Name", "Replaced_VariavbleA" );
		p.put( "VariableB_Name", "Replaced_VariavbleB" );
		p.put( "VariableC_Name", "Replaced_VariavbleC" );
		return p;
	}
}
