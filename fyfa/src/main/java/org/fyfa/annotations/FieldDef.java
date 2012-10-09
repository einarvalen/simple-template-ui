package org.fyfa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Override default assumptions by FieldFactory
 *
 * @author EinarValen@gmail.com */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldDef {
	String label() default "";

	String description() default "";

	String fieldId() default "";

	String fieldTypeId() default "";

	int maxLength() default 0;
}
