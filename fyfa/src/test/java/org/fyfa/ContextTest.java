package org.fyfa;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.Marshal;
import org.fyfa.Template;
import org.fyfa.components.CompFoo;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.ids.TemplateId;
import org.fyfa.nls.NlsNo;
import org.fyfa.nls.NlsUs;
import org.fyfa.repositories.Templates;
import org.fyfa.templatetypes.TemplateTypeDefault;
import org.junit.Test;

public class ContextTest {
	Marshal marshal = new Marshal();
	private static final String FNR = "12345678901", ZIP = "5001";

	@Test
	public void dump() {
		Context context = createContextWithFields();
		String str = context.toString();
		verifyFieldsAreInContextDump( str );
	}

	@Test
	public void nlsDefault() {
		Context context = new Context();
		FieldTypeDate fieldTypeDate = (FieldTypeDate)context.getFieldTypes().get( FieldTypeDate.ID );
		Assert.assertEquals( "yyyy-MM-dd", fieldTypeDate.getFormatString() );
	}

	@Test
	public void nlsNo() {
		Context context = new Context( new NlsNo() );
		FieldTypeDate fieldTypeDate = (FieldTypeDate)context.getFieldTypes().get( FieldTypeDate.ID );
		Assert.assertEquals( "dd.MM.yyyy", fieldTypeDate.getFormatString() );
	}

	@Test
	public void nlsUs() {
		Context context = new Context( new NlsUs() );
		FieldTypeDate fieldTypeDate = (FieldTypeDate)context.getFieldTypes().get( FieldTypeDate.ID );
		Assert.assertEquals( "MM/dd/yyyy", fieldTypeDate.getFormatString() );
	}

	@Test
	public void replaceTemplatesFromProperties() {
		Context context = new Context( new NlsUs(), "template.properties" );
		Templates templates = context.getTemplates();
		Assert.assertEquals( "A_REPLACEMENT_TEMPLATE", templates.get( new TemplateId( "TemplateTypeDefaultinputText" ) ).getFormat() );
	}

	@Test
	public void addTemplate() {
		Context context = new Context();
		List<Template> templateList = new ArrayList<Template>();
		templateList.add( new Template( new TemplateId( "AdditionalTemplateId" ), TemplateTypeDefault.ID, "ADDITIONAL_TEMPLATE_FORMAT" ) );
		context.setTemplateList( templateList );
		Templates templates = context.getTemplates();
		Assert.assertEquals( "ADDITIONAL_TEMPLATE_FORMAT", templates.get( new TemplateId( "AdditionalTemplateId" ) ).getFormat() );
	}

	@Test
	public void loadTextsFromProperties() throws IOException {
		Properties p = loadProperties();
		Assert.assertEquals( ZIP, p.getProperty( "Poststed" ) );
		Assert.assertEquals( FNR, p.getProperty( "Mottakers_fødselsnr" ) );
		Context context = new Context();
		context.setTextProperties( p );
		Assert.assertEquals( ZIP, context.getText( "Poststed" ) );
		Assert.assertEquals( FNR, context.getText( "Mottakers_fødselsnr" ) );
	}

	private Properties loadProperties() throws IOException {
		String sample = "Mottakers_fødselsnr=" + FNR + "\n" + "Poststed=" + ZIP + "\n";
		Properties p = new Properties();
		StringReader reader = new StringReader( sample );
		p.load( reader );
		return p;
	}

	private Context createContextWithFields() {
		Context context = new Context();
		context.registerSelecton( new AgeSelection() );
		new Form<CompFoo>( new FormParams<CompFoo>( "CompFoo", CompFoo.class, context ) );
		new Form<Bar>( new FormParams<Bar>( "Bar", Bar.class, context ) );
		return context;
	}

	private void verifyFieldsAreInContextDump( String str ) {
		List<String> keys = marshal.keys( CompFoo.class );
		for (String key : keys) {
			Assert.assertTrue( str.contains( key ) );
		}
	}

}
