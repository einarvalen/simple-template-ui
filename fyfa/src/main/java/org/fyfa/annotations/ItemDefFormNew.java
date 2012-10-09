package org.fyfa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Override default assumptions by ItemFactory.
* Used when Form.operation is Operation.New
*
* @author EinarValen@gmail.com */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemDefFormNew {
	String templateId() default "";

	boolean required() default false;

	boolean hidden() default false;

	boolean readOnly() default false;

	String selectionId() default "";

	String uriTemplate() default "";

	String validatorId() default "";

}
