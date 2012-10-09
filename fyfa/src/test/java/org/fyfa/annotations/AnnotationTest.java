package org.fyfa.annotations;

import java.lang.reflect.Field;

import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationTest {
	Bar fieldAnnotatedDto = new Bar();

	static class ItemAnnotatedDto {
		@ItemDefFormNew(templateId = "MyTemplateId", required = true, readOnly = true)
		String name;
		String ssn;
	}

	@Test
	public void fieldDef() {
		Field[] fields = this.fieldAnnotatedDto.getClass().getDeclaredFields();
		for (Field field : fields) {
			Assert.assertTrue( field.isAnnotationPresent( FieldDef.class ) );
			FieldDef fieldDef = field.getAnnotation( FieldDef.class );
			Assert.assertEquals( "The FieldA Label", fieldDef.label() );
			Assert.assertEquals( "FieldA", fieldDef.fieldId() );
			Assert.assertEquals( "The FeildA Description", fieldDef.description() );
			Assert.assertEquals( "MyFieldTypeId", fieldDef.fieldTypeId() );
			Assert.assertEquals( 34, fieldDef.maxLength() );
		}
	}

	@Test
	public void itemDefForm() throws SecurityException, NoSuchFieldException {
		ItemAnnotatedDto itemAnnotatedDto = new ItemAnnotatedDto();
		Field declaredField = itemAnnotatedDto.getClass().getDeclaredField( "name" );
		Assert.assertTrue( declaredField.isAnnotationPresent( ItemDefFormNew.class ) );
		ItemDefFormNew annotation = declaredField.getAnnotation( ItemDefFormNew.class );
		Assert.assertEquals( "MyTemplateId", annotation.templateId() );
		Assert.assertEquals( true, annotation.required() );
		Assert.assertEquals( false, annotation.hidden() );
		Assert.assertEquals( true, annotation.readOnly() );
		Assert.assertEquals( "", annotation.selectionId() );
		Assert.assertEquals( "", annotation.validatorId() );
	}

}
