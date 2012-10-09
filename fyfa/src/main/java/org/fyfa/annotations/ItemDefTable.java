package org.fyfa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Override default assumptions by ItemFactory.
 * Used for table items
*
* @author EinarValen@gmail.com */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemDefTable {

	String templateId() default "";

	String uriTemplate() default "";

}
