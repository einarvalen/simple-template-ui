package org.fyfa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Override default assumptions by ItemFactory.
 * Used when Form.operation is Operation.View
*
* @author EinarValen@gmail.com */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemDefFormView {
	String templateId() default "";

	boolean hidden() default false;

	boolean readOnly() default true;

	String uriTemplate() default "";

}
